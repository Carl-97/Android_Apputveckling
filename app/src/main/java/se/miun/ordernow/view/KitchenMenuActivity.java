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
    public static boolean active = false;
    private RecyclerView recyclerView;
    private static KitchenMenuAdapter kitchenMenuAdapter;
    private KitchenMenuAdapter.RecyclerViewClickListener listener;
    public static KitchenOrderList orderList = new KitchenOrderList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_menu);
        active = true;

        RecyclerView recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayItem();
    }
    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    private void displayItem() {
        recyclerView = findViewById(R.id.recyclerView_kitchenMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kitchenMenuAdapter = new KitchenMenuAdapter(orderList);
        recyclerView.setAdapter(kitchenMenuAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }

    public static void updateAdapter() {
        if(kitchenMenuAdapter == null)
            return;

        kitchenMenuAdapter.notifyDataSetChanged();
    }
}