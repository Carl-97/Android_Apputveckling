package se.miun.ordernow;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderMenuRecyclerAdapter extends RecyclerView.Adapter<OrderMenuRecyclerAdapter.MyViewHolder> {
    private List<String> menuNameList;

    private List<Order> orderList;
    private TextView orderCounter;

    private RecyclerViewClickListner listner;
    public OrderMenuRecyclerAdapter(List<String> tablesList, RecyclerViewClickListner listner){
        this.menuNameList = tablesList;
        this.listner = listner;
    }

    public void setOrderList(List<Order> list) {
        orderList = list;
    }
    public void setOrderCounter(TextView view) {
        orderCounter = view;
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
                    Order.OrderType type = Order.OrderType.FÖRRÄTT;

                    if(OrderMenu.currentType == 1) {
                        type = Order.OrderType.VARMRÄTT;
                    }
                    else if(OrderMenu.currentType == 2) {
                        type = Order.OrderType.EFTERÄTT;
                    }
                    //orderList.add(new Order(addButton.getTag().toString(), type, editButton.getTag().toString()));
                    OrderList data = new OrderList();
                    data.addElement(new Order(addButton.getTag().toString(), type, editText.getText().toString()));
                    System.out.println("Order added: " + addButton.getTag());
                    System.out.println("Type: " + type.toString());
                    System.out.println("Description: " + editText.getText());
                    editButton.setTag("");
                    editText.setText("");
                    editText.setVisibility(View.GONE);
                    // Closes keyboard after ActionSend.
                    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    orderCounter.setText("Order Count: " + orderList.size());
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editText.getVisibility() == View.VISIBLE) {
                        editText.setVisibility(View.GONE);
                        // Closes keyboard after ActionSend.
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    else
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
                        addButton.performClick();

                        // Closes keyboard after ActionSend.
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
    public OrderMenuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuRecyclerAdapter.MyViewHolder holder, int position) {
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
