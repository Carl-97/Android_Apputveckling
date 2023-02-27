package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {
    private ArrayList<Order> orderList;
    private int tableNumber;

    private RecyclerView recyclerView;
    private Button readyButton;
    private Button menuButton;

    private OrderStatusRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        OrderList listObject = new OrderList();
        orderList = listObject.getList();

        recyclerView = findViewById(R.id.orderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new OrderStatusRecyclerAdapter(orderList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        menuButton = findViewById(R.id.orderButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(OrderStatus.this, OrderMenu.class);
                startActivity(switchActivity);
            }
        });

        readyButton = findViewById(R.id.readyButton);
        String buttonText = listObject.getButtonState();

        if(buttonText.contains("Waiting")) {
            readyButton.setBackgroundColor(Color.GRAY);
        }
        else {
            readyButton.setBackgroundColor(Color.BLUE);
        }

        if(buttonText.isEmpty()) {
            readyButton.setVisibility(View.GONE);
        }
        else
            readyButton.setVisibility(View.VISIBLE);



        readyButton.setText(buttonText);
        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listObject.updateState();
                adapter.notifyDataSetChanged();
                String buttonText = listObject.getButtonState();
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
                readyButton.setText(buttonText);
            }
        });
    }

}