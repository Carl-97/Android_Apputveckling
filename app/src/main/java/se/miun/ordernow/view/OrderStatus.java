package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private MasterOrderList masterList;
    private int tableNumber;

    private RecyclerView recyclerView;
    private Button readyButton;
    private Button menuButton;
    private TextView title;

    private OrderStatusRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Intent intent = getIntent();
        tableNumber = intent.getIntExtra("tableNumber", 0);

        masterList = new MasterOrderList();
        OrderList currentTableOrderList = masterList.getOrderList(tableNumber);

        recyclerView = findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new OrderStatusRecyclerAdapter(currentTableOrderList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        title = findViewById(R.id.title);
        title.setText("Table " + tableNumber + " Status");

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
                currentTableOrderList.updateState();
                adapter.notifyDataSetChanged();
                setReadyButtonState();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        setReadyButtonState();

        adapter.notifyDataSetChanged();
    }

    private void setReadyButtonState() {
        String buttonText = getReadyButtonText();
        readyButton.setText(buttonText);
        if(buttonText.isEmpty()) {
            readyButton.setVisibility(View.GONE);
        }
        else
            readyButton.setVisibility(View.VISIBLE);
        if(buttonText.contains("Waiting")) {
            readyButton.setBackgroundColor(Color.GRAY);
        }
        else {
            readyButton.setBackgroundColor(Color.BLUE);
        }
    }

    private String getReadyButtonText() {
        for(OrderItem order: masterList.getOrderList(tableNumber).getList()) {
            if(order.getStatus() == OrderItem.Status.DONE)
                continue;
            return buttonStateString[order.getType().ordinal() * 3 + order.getStatus().ordinal()];
        }
        return "";
    }
    private String[] buttonStateString = {"Send Apetizer", "Waiting for Apetizer", "Apetizer delivered", "Send Main", "Waiting for Main", "Main delivered", "Send Dessert", "Waiting for Dessert", "Dessert delivered"};
}