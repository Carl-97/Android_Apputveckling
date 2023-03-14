package se.miun.ordernow.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.MasterOrderList;
import se.miun.ordernow.model.OrderList;
import se.miun.ordernow.model.Table;
import se.miun.ordernow.model.TableList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private TableList tablesList;
    private RecyclerViewClickListener listener;
    public RecyclerAdapter(TableList tablesList, RecyclerViewClickListener listener){
        this.tablesList = tablesList;
        this.listener = listener;

    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tableNameText;
        public MyViewHolder(final View view){
            super(view);
            view.setOnClickListener(this);
            tableNameText = view.findViewById(R.id.tableNameTextView);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAbsoluteAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = "Table " + String.valueOf(tablesList.getTable(position).getTableId());
        holder.tableNameText.setText(name);
        setTableNameTextColor(holder.tableNameText, position);
    }
    @Override
    public int getItemCount() {
        return tablesList.size();
    }

    private void setTableNameTextColor(TextView tableName, int position) {
        MasterOrderList masterList = new MasterOrderList();
        OrderList orderList = masterList.getOrderList(position);

        // Set color depending on the state of the orderlist for that table.
        if(orderList.isEmpty()) {
            tableName.setTextColor(Color.GRAY);

        }
        else if(orderList.ordersReady()){
            tableName.setTextColor(Color.GREEN);
        }
        else
            tableName.setTextColor(Color.BLACK);
    }
}
