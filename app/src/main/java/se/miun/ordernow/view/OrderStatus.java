package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import se.miun.ordernow.R;
import se.miun.ordernow.model.MasterOrderList;
import se.miun.ordernow.model.OrderItem;
import se.miun.ordernow.model.OrderList;

public class OrderStatus extends AppCompatActivity {
    private static Context context;
    private static MasterOrderList masterList;
    private static int tableNumber;

    private TextView title;
    private static Button readyButton;
    private Button menuButton;

    private RecyclerView recyclerView;
    private static OrderStatusRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_order_status);

        Intent intent = getIntent();
        tableNumber = intent.getIntExtra("tableNumber", 1);

        title = findViewById(R.id.title);
        title.setText("Table " + tableNumber + " Status");

        masterList = new MasterOrderList();
        OrderList currentTableOrderList = masterList.getOrderList(tableNumber - 1);
        initRecyclerView(currentTableOrderList);

        menuButton = findViewById(R.id.orderButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(OrderStatus.this, OrderMenu.class);
                switchActivity.putExtra("tableNumber", tableNumber);
                startActivity(switchActivity);
            }
        });

        readyButton = findViewById(R.id.readyButton);
        setReadyButtonState();

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTableOrderList.updateOrderStatus(OrderStatus.this);
                updateView();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        tableNumber = intent.getIntExtra("tableNumber", 1);
        updateView();
    }

    public static Context getContext() {
        return context;
    }

    public static void updateAdapter() {
        if(adapter == null)
            return;

        adapter.notifyDataSetChanged();
    }

    public static void updateView() {
        if(masterList == null)
            return;

        updateAdapter();
        setReadyButtonState();
    }

    private void initRecyclerView(OrderList orderList) {
        adapter = new OrderStatusRecyclerAdapter(orderList);

        recyclerView = findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }



    private static void setReadyButtonState() {
        String buttonText = getReadyButtonText();
        readyButton.setText(buttonText);

        if(buttonText.isEmpty()) {
            readyButton.setVisibility(View.GONE);
        }
        else
            readyButton.setVisibility(View.VISIBLE);

        if(buttonText.contains("Waiting")) {
            readyButton.setBackgroundColor(Color.GRAY);
            readyButton.setEnabled(false);
        }
        else {
            readyButton.setBackgroundColor(Color.BLUE);
            readyButton.setEnabled(true);
        }
    }

    private static String getReadyButtonText() {
        OrderList orderList = masterList.getOrderList(tableNumber - 1);
        for(int i = 0; i < orderList.size(); ++i) {
            if(orderList.get(i).getStatus() == OrderItem.Status.DONE)
                continue;
            // Check that all orders of the same type has the same status.
            boolean allSameStatus = true;
            int j = i;
            for(; j < orderList.size(); ++j) {
                if(orderList.get(i).getType() != orderList.get(j).getType()) {
                    break;
                }

                if(orderList.get(i).getStatus() != orderList.get(j).getStatus()) {
                    allSameStatus = false;
                    break;
                }
            }
            if(!allSameStatus) {
                // Prioritize "less ready" status, so HOLD goes before COOK
                if(!orderList.get(i).getStatus().lessThan(orderList.get(j).getStatus())) {
                    i = j;
                }
            }
            return buttonStateString[orderList.get(i).getType().ordinal() * 3 + orderList.get(i).getStatus().ordinal()];
        }
        return "";
    }
    private static String[] buttonStateString = {"Send Apetizer", "Waiting for Apetizer", "Apetizer delivered", "Send Main", "Waiting for Main", "Main delivered", "Send Dessert", "Waiting for Dessert", "Dessert delivered"};
}