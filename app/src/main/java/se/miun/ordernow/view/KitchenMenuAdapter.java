package se.miun.ordernow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import se.miun.ordernow.R;
import se.miun.ordernow.model.KitchenOrderList;
import se.miun.ordernow.model.Order;

public class KitchenMenuAdapter extends RecyclerView.Adapter<KitchenMenuAdapter.MyViewHolder> {
    private KitchenOrderList orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerViewClickListener listener;
    public KitchenMenuAdapter(KitchenOrderList orderList, RecyclerViewClickListener listener){
        this.orderList = orderList;
        this.listener =listener;
    }
    public KitchenMenuAdapter(KitchenOrderList orderList){
        this.orderList = orderList;
    }
    public interface RecyclerViewClickListener{
        void onClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tableNumber;
        private RecyclerView itemsList;

        public MyViewHolder(final View view){
            super(view);
            tableNumber = view.findViewById(R.id.tableNumberTextView);
            itemsList = view.findViewById(R.id.orderItemsRecyclerView);
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
        Order order = orderList.getOrder(position);

        String tableNumber = String.valueOf(order.getTable().getId());
        holder.tableNumber.setText("Table " + tableNumber);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.itemsList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItems().size());

        System.out.println("Binding order: " + order.getTable().getId());
        System.out.println("On position: " + position);

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(order.getItems(), order.getTable().getId());

        holder.itemsList.setLayoutManager(layoutManager);
        holder.itemsList.setAdapter(orderItemAdapter);
        holder.itemsList.setRecycledViewPool(viewPool);
        holder.itemsList.addItemDecoration(new DividerItemDecoration(holder.itemsList.getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
