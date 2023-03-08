package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.KitchenOrderList;
import se.miun.ordernow.model.Table;
import se.miun.ordernow.model.Order;
import se.miun.ordernow.model.OrderItem;

public class KitchenMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KitchenMenuAdapter kitchenMenuAdapter;
    private KitchenMenuAdapter.RecyclerViewClickListener listener;
    public static KitchenOrderList orderList = new KitchenOrderList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_menu);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<OrderItem> item = new ArrayList<OrderItem>();
        /*
        item.add(new OrderItem(1, "potatis", OrderItem.Type.MAINDISH, "mayt", 1));
        item.add(new OrderItem(1, "kött", OrderItem.Type.MAINDISH, "utan potatis", 1));
        item.add(new OrderItem(1, "morot   ", OrderItem.Type.APPETIZER, "utan potatis", 1));
        item.add(new OrderItem(1, "mos", OrderItem.Type.MAINDISH, "utan potatis", 1));
        item.add(new OrderItem(1, "godis", OrderItem.Type.DESSERT, "mayt", 1));
        Table table = new Table(1);
        orderList.add(new Order(01, item, table));
        orderList.add(new Order(02, item, table));
        orderList.add(new Order(03, item, table));
         */
        displayItem();

    }
    private void displayItem() {



        recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kitchenMenuAdapter = new KitchenMenuAdapter(orderList.getOrderList());
        recyclerView.setAdapter(kitchenMenuAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));


    }
}