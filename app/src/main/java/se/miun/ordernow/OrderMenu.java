package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class OrderMenu extends AppCompatActivity {
    private List<String> items = Arrays.asList("Förrätt", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items1 = Arrays.asList("Varmrätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items2 = Arrays.asList("Efterätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter recyclerAdapter1;
    private RecyclerAdapter recyclerAdapter2;
    private RecyclerAdapter.RecyclerViewClickListner listener;

    private TabLayout tabLayout;

    private ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        orderList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        listener = new RecyclerAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("Clicked!");
            }
        };

        recyclerView = findViewById(R.id.menuRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerAdapter = new RecyclerAdapter(items, listener);
        recyclerAdapter1 = new RecyclerAdapter(items1, listener);
        recyclerAdapter2 = new RecyclerAdapter(items2, listener);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        tabLayout = findViewById(R.id.simpleTabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                switch(pos) {
                    case 0: {
                        recyclerView.swapAdapter(recyclerAdapter, false);
                        break;
                    }
                    case 1: {
                        recyclerView.swapAdapter(recyclerAdapter1, false);
                        break;
                    }
                    case 2: {
                        recyclerView.swapAdapter(recyclerAdapter2, false);
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

    public void addOrder(Order order) {
        orderList.add(order);
        System.out.println("Current orderList:");
        for(int i = 0; i < orderList.size(); ++i) {
            System.out.println(orderList.get(i).toString());
        }
    }
}