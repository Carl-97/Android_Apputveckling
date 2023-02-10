package se.miun.ordernow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<String> tablesList;
    private RecyclerViewClickListner listner;
    public RecyclerAdapter(List<String> tablesList, RecyclerViewClickListner listner){
        this.tablesList = tablesList;
        this.listner = listner;

    }
    public interface RecyclerViewClickListner{
        void onClick(View v, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tableNameText;
        public MyViewHolder(final View view){
            super(view);
            view.setOnClickListener(this);
            tableNameText = view.findViewById(R.id.menu_item_name);
        }

        @Override
        public void onClick(View view) {
            listner.onClick(view, getAbsoluteAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = tablesList.get(position);
        holder.tableNameText.setText(name);

    }

    @Override
    public int getItemCount() {
        return tablesList.size();
    }
}
