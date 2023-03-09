package se.miun.ordernow.view;

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

import se.miun.ordernow.R;
import se.miun.ordernow.model.MenuItem;
import se.miun.ordernow.model.OrderItem;
import se.miun.ordernow.model.OrderList;

public class OrderMenuRecyclerAdapter extends RecyclerView.Adapter<OrderMenuRecyclerAdapter.MyViewHolder> {
    private List<MenuItem> menuNameList;

    private OrderList orderList;
    private TextView orderCounter;
    private int tableNumber;


    public OrderMenuRecyclerAdapter(List<MenuItem> tablesList, OrderList orderList, TextView orderCounter, int tableNumber){
        this.menuNameList = tablesList;
        this.orderList = orderList;
        this.orderCounter = orderCounter;
        this.tableNumber = tableNumber;
    }

    public void setOrderList(OrderList list) {
        orderList = list;
    }
    public void setOrderCounter(TextView view) {
        orderCounter = view;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView menuName;
        private Button editButton;
        private EditText editText;
        public Button addButton;
        public MyViewHolder(final View view){
            super(view);
            menuName = view.findViewById(R.id.menu_item_name);
            editButton = view.findViewById(R.id.menu_edit_button);
            addButton = view.findViewById(R.id.menu_add_button);
            editText = view.findViewById(R.id.menu_edit_text);


            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuItem clickedItem = (MenuItem) addButton.getTag();
                    orderList.add(new OrderItem(clickedItem, editText.getText().toString(), tableNumber - 1));
                    System.out.println("OrderItem added to table " + tableNumber);

                    // Reset editText view. And close keyboard after ActionSend.
                    editText.setText("");
                    editText.setVisibility(View.GONE);
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
    }
    @NonNull
    @Override
    public OrderMenuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuRecyclerAdapter.MyViewHolder holder, int position) {
        MenuItem item = menuNameList.get(position);
        holder.menuName.setText(item.getName());

        // Bind the order name to the add button.
        holder.addButton.setTag(item);
        holder.editButton.setTag("");
    }

    @Override
    public int getItemCount() {
        return menuNameList.size();
    }

}
