package se.miun.ordernow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<String> menuNameList;

    private RecyclerViewClickListner listner;
    public RecyclerAdapter(List<String> tablesList, RecyclerViewClickListner listner){
        this.menuNameList = tablesList;
        this.listner = listner;
    }
    public interface RecyclerViewClickListner{
        void onClick(View v, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView menuName;
        private Button editButton;
        private Button addButton;
        public MyViewHolder(final View view){
            super(view);
            view.setOnClickListener(this);
            menuName = view.findViewById(R.id.menu_item_name);
            editButton = view.findViewById(R.id.menu_edit_button);
            addButton = view.findViewById(R.id.menu_add_button);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    System.out.println("Edit button clicked, with id: " + id);
                    System.out.println("Tag is: " + view.getTag());
                }
            });
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
        String name = menuNameList.get(position);
        holder.menuName.setText(name);
        // @TEST testing the Tag system.
        holder.editButton.setTag(name);
    }

    @Override
    public int getItemCount() {
        return menuNameList.size();
    }
}
