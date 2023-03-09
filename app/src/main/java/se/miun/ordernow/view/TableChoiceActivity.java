package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.Table;

public class TableChoiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Table> tableList; /*= Arrays.asList("table 1", "table 2", "table 3", "table 4", "table 5", "table 6", "table 7");*/
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListener listner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_table_layout);
        // ToDo: Fetch from list instead of just creating "random" tables.
        tableList = new ArrayList<>();
        for(int i = 1; i <= 8; ++i) {
            tableList.add(new Table(i, 4));
        }

        displayItem();
    }

    private void displayItem() {
        listner = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("YeS! YOU CLICked!");
                Intent intent = new Intent(getApplicationContext(), OrderStatus.class);
                intent.putExtra("tableNumber", position + 1);
                startActivity(intent);
            }
        };
        recyclerView = findViewById(R.id.recyclerView_tableList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerAdapter = new RecyclerAdapter(tableList, listner);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerAdapter.notifyDataSetChanged();
    }
};