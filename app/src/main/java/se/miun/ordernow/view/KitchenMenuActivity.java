package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.Table;
import se.miun.ordernow.model.Order;
import se.miun.ordernow.model.OrderItem;

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
        ArrayList<OrderItem> item = new ArrayList<OrderItem>();
        item.add(new OrderItem("potatis", OrderItem.Type.VARMRÄTT, "mayt"));
        item.add(new OrderItem("kött", OrderItem.Type.VARMRÄTT, "utan potatis"));
        item.add(new OrderItem("morot   ", OrderItem.Type.FÖRRÄTT, "utan potatis"));
        item.add(new OrderItem("mos", OrderItem.Type.VARMRÄTT, "utan potatis"));
        item.add(new OrderItem("godis", OrderItem.Type.EFTERÄTT, "mayt"));
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