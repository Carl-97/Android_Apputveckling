package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.BackgroundApiFetcher;
import se.miun.ordernow.model.MasterOrderList;
import se.miun.ordernow.model.MenuItem;
import se.miun.ordernow.model.MenuList;
import se.miun.ordernow.model.OrderList;

public class OrderMenu extends AppCompatActivity {
    MenuList menuList;
    private RecyclerView recyclerView;
    private static OrderMenuRecyclerAdapter orderMenuRecyclerAdapter;
    private static OrderMenuRecyclerAdapter orderMenuRecyclerAdapter1;
    private static OrderMenuRecyclerAdapter orderMenuRecyclerAdapter2;

    private List<String> tempList;

    private TabLayout tabLayout;

    private MasterOrderList masterOrderList;
    private int tableNumber;
    private TextView orderCount;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        Intent intent = getIntent();
        tableNumber = intent.getIntExtra("tableNumber", 0);

        menuList = new MenuList();

        masterOrderList = new MasterOrderList();
        OrderList currentTableOrderList = masterOrderList.getOrderList(tableNumber);

        orderCount = findViewById(R.id.numberOfOrders);
        orderCount.setText("Order Count: " + currentTableOrderList.size());

        initTabLayout();
        initRecyclerView(currentTableOrderList, orderCount, tableNumber);


        doneButton = findViewById(R.id.tempButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView(OrderList orderList, TextView orderCountView, int table) {
        recyclerView = findViewById(R.id.menuRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        orderMenuRecyclerAdapter = new OrderMenuRecyclerAdapter(menuList.getAppetizers(), orderList, orderCountView, tableNumber);
        orderMenuRecyclerAdapter1 = new OrderMenuRecyclerAdapter(menuList.getMainDishes(), orderList, orderCountView, tableNumber);
        orderMenuRecyclerAdapter2 = new OrderMenuRecyclerAdapter(menuList.getDesserts(), orderList, orderCountView, tableNumber);
        recyclerView.setAdapter(orderMenuRecyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
    private void initTabLayout() {
        tabLayout = findViewById(R.id.simpleTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
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
    }

    public static void updateAdapters() {
        if(orderMenuRecyclerAdapter == null)
            return;

        orderMenuRecyclerAdapter.notifyDataSetChanged();
        orderMenuRecyclerAdapter1.notifyDataSetChanged();
        orderMenuRecyclerAdapter2.notifyDataSetChanged();
    }
}

