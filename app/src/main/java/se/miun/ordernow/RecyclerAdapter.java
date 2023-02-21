package se.miun.ordernow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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
        private EditText editText;
        public Button addButton;
        public MyViewHolder(final View view){
            super(view);
            view.setOnClickListener(this);
            menuName = view.findViewById(R.id.menu_item_name);
            editButton = view.findViewById(R.id.menu_edit_button);
            addButton = view.findViewById(R.id.menu_add_button);
            editText = view.findViewById(R.id.menu_edit_text);



            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Order added: " + addButton.getTag());
                    System.out.println("Description: " + editButton.getTag());
                    editButton.setTag("");
                    editText.setText("");
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setVisibility(View.VISIBLE);

                }
            });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;

                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        handled = true;

                        String description = v.getText().toString();
                        editButton.setTag(description);
                        editText.setVisibility(View.GONE);
                    }
                    return handled;
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

        // Bind the order name to the add button.
        holder.addButton.setTag(name);
        holder.editButton.setTag("");
    }

    @Override
    public int getItemCount() {
        return menuNameList.size();
    }
}
