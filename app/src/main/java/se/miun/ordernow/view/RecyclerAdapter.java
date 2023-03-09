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
import se.miun.ordernow.model.Table;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Table> tablesList;
    private RecyclerViewClickListener listener;
    public RecyclerAdapter(List<Table> tablesList, RecyclerViewClickListener listener){
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
        String name = "Table " + String.valueOf(tablesList.get(position).getTableId());
        holder.tableNameText.setText(name);
        MasterOrderList masterList = new MasterOrderList();
        if(masterList.getOrderList(position).ordersReady()) {
            holder.tableNameText.setTextColor(Color.rgb(200, 181, 15));
        }
        else {
            holder.tableNameText.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return tablesList.size();
    }
}
