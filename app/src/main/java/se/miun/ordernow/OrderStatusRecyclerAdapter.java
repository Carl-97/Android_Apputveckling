package se.miun.ordernow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderStatusRecyclerAdapter extends RecyclerView.Adapter<OrderStatusRecyclerAdapter.MyViewHolder> {
    private List<Order> orderList;


    public OrderStatusRecyclerAdapter(List<Order> tablesList){
        this.orderList = tablesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView orderName;
        private TextView orderStatus;

        public MyViewHolder(final View view){
            super(view);

            orderName = view.findViewById(R.id.orderName);
            orderStatus = view.findViewById(R.id.orderStatus);
        }


    }
    @NonNull
    @Override
    public OrderStatusRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusRecyclerAdapter.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderName.setText(order.getName());
        holder.orderStatus.setText(order.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
