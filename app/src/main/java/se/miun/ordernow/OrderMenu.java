package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderMenu extends AppCompatActivity {
    private List<String> items = Arrays.asList("Förrätt", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items1 = Arrays.asList("Varmrätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items2 = Arrays.asList("Efterätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");

    private RecyclerView recyclerView;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter1;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter2;
    private OrderMenuRecyclerAdapter.RecyclerViewClickListner listener;

    private TabLayout tabLayout;
    public static int currentType;

    private ArrayList<Order> orderList;
    private Button tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orderList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        listener = new OrderMenuRecyclerAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("Clicked!");
            }
        };

        recyclerView = findViewById(R.id.menuRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        orderMenuRecyclerAdapter = new OrderMenuRecyclerAdapter(items, listener);
        orderMenuRecyclerAdapter1 = new OrderMenuRecyclerAdapter(items1, listener);
        orderMenuRecyclerAdapter2 = new OrderMenuRecyclerAdapter(items2, listener);
        recyclerView.setAdapter(orderMenuRecyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        tabLayout = findViewById(R.id.simpleTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                currentType = pos;

                switch(pos) {
                    case 0: {
                        recyclerView.swapAdapter(orderMenuRecyclerAdapter, false);
                        break;
                    }
                    case 1: {
                        recyclerView.swapAdapter(orderMenuRecyclerAdapter1, false);
                        break;
                    }
                    case 2: {
                        recyclerView.swapAdapter(orderMenuRecyclerAdapter2, false);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        OrderList listObject = new OrderList();
        orderList = listObject.getList();

        orderMenuRecyclerAdapter.setOrderList(orderList);
        orderMenuRecyclerAdapter1.setOrderList(orderList);
        orderMenuRecyclerAdapter2.setOrderList(orderList);

        tempButton = findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(OrderMenu.this, OrderStatus.class);
                startActivity(switchActivity);
            }
        });
    }
}