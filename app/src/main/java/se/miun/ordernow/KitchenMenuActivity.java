package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class KitchenMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KitchenMenuAdapter kitchenMenuAdapter;
    private KitchenMenuAdapter.RecyclerViewClickListener listener;
    List<Order> orderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_menu);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Item> item = new ArrayList<Item>();
        item.add(new Item("potatis", "utan potatis", "mayt"));
        item.add(new Item("kött", "utan potatis", "mayt"));
        item.add(new Item("morot   ", "utan potatis", "mayt"));
        item.add(new Item("mos", "utan potatis", "mayt"));
        item.add(new Item("godis", "utan potatis", "mayt"));
        Table table = new Table(1);
        orderList.add(new Order(01, item, table));
        orderList.add(new Order(02, item, table));
        orderList.add(new Order(03, item, table));
        displayItem();

    }
    private void displayItem() {



        recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kitchenMenuAdapter = new KitchenMenuAdapter(orderList);
        recyclerView.setAdapter(kitchenMenuAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));


    }
}