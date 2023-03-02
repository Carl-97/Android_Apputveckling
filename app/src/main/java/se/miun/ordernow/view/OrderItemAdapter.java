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
import se.miun.ordernow.model.OrderItem;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
    private List<OrderItem> itemList;
    private OrderItemAdapter.RecyclerViewClickListener listener;

    public OrderItemAdapter(List<OrderItem> itemList, OrderItemAdapter.RecyclerViewClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }
    public OrderItemAdapter(List<OrderItem> itemList) {
        this.itemList = itemList;
    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTypeName;
        private TextView itemName;
        private TextView itemDescription;

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
                    System.out.println("I AM READY");
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
}
