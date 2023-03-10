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
    private List<OrderItem> itemList;
    private int tableNumber;
    private OrderItemAdapter.RecyclerViewClickListener listener;

    public OrderItemAdapter(List<OrderItem> itemList, OrderItemAdapter.RecyclerViewClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }
    public OrderItemAdapter(List<OrderItem> itemList, int tableNumber) {
        System.out.println("Constructing adapter with tableNumber: " + tableNumber);
        this.itemList = itemList;
        this.tableNumber = tableNumber;
    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTypeName;
        private TextView itemName;
        private TextView itemDescription;

        private OrderItem item;

        private Button readyButton;

        public MyViewHolder(final View view){
            super(view);
            //view.setOnClickListener(this);
            readyButton = view.findViewById(R.id.itemReadyButton);
            itemTypeName = view.findViewById(R.id.itemMealTypeTextView);
            itemName = view.findViewById(R.id.itemMealNameTextView);
            itemDescription =view.findViewById(R.id.itemMealDescriptionTextView);

            readyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Send item to api
                    System.out.println("Ready button was clicked!");
                    System.out.println("Sending item: " + item.getName() + " to API");
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

        String itemType = itemList.get(position).getType().toString();
        holder.itemTypeName.setText("Type: " + itemType);

        String itemName = itemList.get(position).getName();
        holder.itemName.setText("Meal: " + itemName);

        String itemDescription = itemList.get(position).getDescription();
        holder.itemDescription.setText(itemDescription);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeItem(OrderItem item) {
        // When the order is empty we remove the list.
        // This cause our itemList to not function properly because it was a reference to that list.
        // So in case try to remove an item while the itemList is empty we need a new reference to the new orderlist.
        if(itemList.size() == 0) {
            KitchenOrderList masterKitchenList = new KitchenOrderList();
            List<OrderItem> tempList = masterKitchenList.requestNewReference(tableNumber);
            if(tempList == null) {
                System.out.println("Null reference list for tablenumber: " + tableNumber);
                masterKitchenList.printCurrentOrderNumbers();
            }
            else
                itemList = tempList;
        }

        boolean itemRemoved = false;

        System.out.println("removeItem call");

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
            kitchenList.removeEmptyOrders(this);
            KitchenMenuActivity.updateAdapter();
            System.out.println(itemList);
        }
    }

    public void update() {
        if(itemList.size() == 0) {
            System.out.println("Order is empty, removing order");
            KitchenOrderList kitchenList = new KitchenOrderList();
            kitchenList.removeEmptyOrders(this);
        }
        notifyDataSetChanged();
    }
}
