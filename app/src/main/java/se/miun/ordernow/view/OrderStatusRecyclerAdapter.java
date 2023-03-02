package se.miun.ordernow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import se.miun.ordernow.R;
import se.miun.ordernow.model.OrderItem;
import se.miun.ordernow.model.OrderList;

public class OrderStatusRecyclerAdapter extends RecyclerView.Adapter<OrderStatusRecyclerAdapter.MyViewHolder> {
    private OrderList orderList;


    public OrderStatusRecyclerAdapter(OrderList tablesList){
        this.orderList = tablesList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView orderName;
        private TextView orderType;
        private TextView orderStatus;

        public MyViewHolder(final View view){
            super(view);

            orderName = view.findViewById(R.id.orderName);
            orderType = view.findViewById(R.id.orderType);
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
        OrderItem order = orderList.get(position);
        holder.orderName.setText(order.getName());
        String type = "" + order.getType().toString().charAt(0);
        holder.orderType.setText(type);
        holder.orderStatus.setText(order.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
