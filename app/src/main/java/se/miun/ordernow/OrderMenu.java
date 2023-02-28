package se.miun.ordernow;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.miun.ordernow.model.RetrofitClient;
import se.miun.ordernow.model.Temp;

public class OrderMenu extends AppCompatActivity {
    private List<String> items = Arrays.asList("Förrätt", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items1 = Arrays.asList("Varmrätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private List<String> items2 = Arrays.asList("Efterätt", "order12", "order1", "order1", "order1", "order1", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");

    private RecyclerView recyclerView;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter1;
    private OrderMenuRecyclerAdapter orderMenuRecyclerAdapter2;
    private OrderMenuRecyclerAdapter.RecyclerViewClickListner listener;

    private List<String> tempList;

    private TabLayout tabLayout;
    public static int currentType;

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

        // ToDo: Api call shouldnt be here, but it works! Also the MenuList doesnt update at first?
        tempList = new ArrayList<>();
        Call<List<Temp>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<Temp>>() {
            @Override
            public void onResponse(Call<List<Temp>> call, Response<List<Temp>> response) {
                List<Temp> list = response.body();
                for(int i = 0; i < list.size(); ++i) {
                    tempList.add(list.get(i).name);
                }
                System.out.println("API CALL SUCCESS!");
            }

            @Override
            public void onFailure(Call<List<Temp>> call, Throwable t) {
                tempList.add("Failure");
                System.out.println("API CALL FAILURE!");
                System.out.println(t.getMessage());
            }
        });

        masterOrderList = new MasterOrderList();
        OrderList currentTableOrderList = masterOrderList.getOrderList(tableNumber);

        orderCount = findViewById(R.id.numberOfOrders);
        orderCount.setText("Order Count: " + currentTableOrderList.size());

        initRecyclerView(currentTableOrderList, orderCount);
        initTabLayout();


        doneButton = findViewById(R.id.tempButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = 0;

                finish();
                /*
                Intent switchActivity = new Intent(OrderMenu.this, OrderStatus.class);
                startActivity(switchActivity);*/
            }
        });
    }

    private void initRecyclerView(OrderList orderList, TextView orderCountView) {
        listener = new OrderMenuRecyclerAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("Clicked!");
            }
        };

        recyclerView = findViewById(R.id.menuRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        orderMenuRecyclerAdapter = new OrderMenuRecyclerAdapter(tempList, listener);
        orderMenuRecyclerAdapter1 = new OrderMenuRecyclerAdapter(items1, listener);
        orderMenuRecyclerAdapter2 = new OrderMenuRecyclerAdapter(items2, listener);
        recyclerView.setAdapter(orderMenuRecyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // Set orderList and orderCounter for current adapters
        orderMenuRecyclerAdapter.setOrderList(orderList);
        orderMenuRecyclerAdapter1.setOrderList(orderList);
        orderMenuRecyclerAdapter2.setOrderList(orderList);

        orderMenuRecyclerAdapter.setOrderCounter(orderCountView);
        orderMenuRecyclerAdapter1.setOrderCounter(orderCountView);
        orderMenuRecyclerAdapter2.setOrderCounter(orderCountView);
    }
    private void initTabLayout() {
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
                int pos = tab.getPosition();
                currentType = pos;
            }
        });
    }
}

