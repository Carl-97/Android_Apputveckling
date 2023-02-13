package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

public class OrderMenu extends AppCompatActivity {
    private List<String> items = Arrays.asList("item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4", "item1", "item2", "item3", "item4");
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListner listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
}