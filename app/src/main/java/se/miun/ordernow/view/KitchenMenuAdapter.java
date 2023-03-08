package se.miun.ordernow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.Order;
import se.miun.ordernow.model.OrderItem;

public class KitchenMenuAdapter extends RecyclerView.Adapter<KitchenMenuAdapter.MyViewHolder> {
    private List<Order> orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerViewClickListener listener;
    public KitchenMenuAdapter(List<Order> orderList, RecyclerViewClickListener listener){
        this.orderList = orderList;
        this.listener =listener;
    }
    public KitchenMenuAdapter(List<Order> orderList){
        this.orderList = orderList;
    }
    public interface RecyclerViewClickListener{
        void onClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView orderNumber;
        private TextView tableNumber;
        private RecyclerView itemsList;
        private Button cancelButton;

        public MyViewHolder(final View view){
            super(view);
            orderNumber = view.findViewById(R.id.orderNumbertextView);
            tableNumber = view.findViewById(R.id.tableNumberTextView);
            itemsList = view.findViewById(R.id.orderItemsRecyclerView);
            cancelButton = view.findViewById(R.id.OrderCancelButton);

            view.findViewById(R.id.OrderCancelButton).setOnClickListener(view1 -> {
                orderList.remove(getAbsoluteAdapterPosition());
                notifyItemRemoved(getAbsoluteAdapterPosition());
            });
        }


    }
    @NonNull
    @Override
    public KitchenMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_menu_order_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KitchenMenuAdapter.MyViewHolder holder, int position) {
        String orderNumber = String.valueOf(orderList.get(position).getOrderNumber());
        holder.orderNumber.setText("Order: " + orderNumber);

        Order order = orderList.get(position);
        String tableNumber = String.valueOf(orderList.get(position).getTable().getTableId());
        holder.tableNumber.setText("Table: " + tableNumber);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.itemsList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItems().size());

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(order.getItems());

        holder.itemsList.setLayoutManager(layoutManager);
        holder.itemsList.setAdapter(orderItemAdapter);
        holder.itemsList.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }



}
