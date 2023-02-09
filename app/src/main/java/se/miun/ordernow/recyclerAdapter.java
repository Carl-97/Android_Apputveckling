package se.miun.ordernow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private List<String> tablesList = Arrays.asList("table 1", "table 2", "table 3", "table 4", "table 5", "table 6", "table 7");
    public recyclerAdapter(ArrayList<String> tablesList){
        this.tablesList = tablesList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tableNameText;
        public MyViewHolder(final View view){
            super(view);
            tableNameText = view.findViewById(R.id.tableNameTextView);
        }

    }
    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = tablesList.get(position);
        holder.tableNameText.setText(name);

    }

    @Override
    public int getItemCount() {
        return tablesList.size();
    }
}
