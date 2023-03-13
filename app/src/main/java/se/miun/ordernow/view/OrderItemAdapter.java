package se.miun.ordernow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.ApiCommunicator;
import se.miun.ordernow.model.KitchenOrderList;
import se.miun.ordernow.model.Order;
import se.miun.ordernow.model.OrderItem;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    public List<OrderItem> itemList;
    public int tableNumber;

    public OrderItemAdapter(List<OrderItem> itemList, int tableNumber) {
        System.out.println("Constructing adapter with tableNumber: " + tableNumber);
        this.itemList = itemList;
        this.tableNumber = tableNumber;
    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemDescription;
        private TextView itemTypeName;

        private Button readyButton;

        private OrderItem item;


        public int table = 0;

        public MyViewHolder(final View view){
            super(view);

            itemName = view.findViewById(R.id.itemMealNameTextView);
            itemDescription =view.findViewById(R.id.itemMealDescriptionTextView);
            itemTypeName = view.findViewById(R.id.itemMealTypeTextView);

            readyButton = view.findViewById(R.id.itemReadyButton);
            readyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // DEBUGGING STUFF
                    System.out.println("Adapter table: " + tableNumber);
                    System.out.println("Item connected to ready button. name: " + item.getName() + ", id: " + item.getId() + ", table: " + item.getTableNumber());

                    // The OrderItemAdapter itemList is out of sync, and the adapter does not know what tableNumber it belongs to?
                    // Only the ViewHolder is working as it should, so we take information from it and send to the adapter.
                    if(OrderItemAdapter.this.itemList.size() == 0) {
                        System.out.println("Fetching new list reference");
                        OrderItemAdapter.this.resyncItemList(item.getTableNumber());
                    }

                    ApiCommunicator apiCommunicator = new ApiCommunicator();
                    apiCommunicator.postOrderItemCooked(OrderItemAdapter.this, itemList, getAbsoluteAdapterPosition(), item);
                }
            });
        }
    }

    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_menu_meal_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        holder.item = itemList.get(position);

        String itemName = holder.item.getName();
        holder.itemName.setText(itemName);

        String itemDescription = holder.item.getDescription();
        holder.itemDescription.setText(itemDescription);

        String itemType = holder.item.getType().toString();
        holder.itemTypeName.setText("Type: " + itemType);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void resyncItemList(int tableId) {
        KitchenOrderList masterKitchenList = new KitchenOrderList();
        List<OrderItem> tempList = masterKitchenList.requestNewReference(tableId);

        if(tempList == null) {
            System.out.println("Null reference list for tablenumber: " + tableId);
            return;
        }

        itemList = tempList;
    }

    public void removeItem(OrderItem item) {
        // When the order is empty we remove the list.
        // This cause our itemList to not function properly because it was a reference to that list.
        // So in case try to remove an item while the itemList is empty we need a new reference to the new orderlist.
        if(itemList.size() == 0) {
            resyncItemList(tableNumber);
        }

        boolean itemRemoved = false;

        for(int i = 0; i < itemList.size(); ++i) {
            if(item.equals(itemList.get(i))) {
                itemList.remove(i);
                System.out.println("Remove item success");
                notifyItemRemoved(i);
                itemRemoved = true;
                break;
            }
        }

        if(!itemRemoved) {
            System.out.println("Failed to remove item: " +item.getName() + " with id: " + item.getId());
            System.out.println("itemList size: " + itemList.size());
        }

        // If we remove the list we destroy our reference to it through itemList.
        // So if new orders come in that belong to that list/table our itemList and KitchenOrderList are no longer synced.
        if(itemList.size() == 0) {
            System.out.println("list is empty, removing it.");
            KitchenOrderList kitchenList = new KitchenOrderList();
            kitchenList.removeEmptyOrders();
            KitchenMenuActivity.updateAdapter();
        }
    }

    public void update() {
        if(itemList.size() == 0) {
            System.out.println("Order is empty, removing order");
            KitchenOrderList kitchenList = new KitchenOrderList();
            kitchenList.removeEmptyOrders();
        }
        KitchenMenuActivity.updateAdapter();
    }
}
