package se.miun.ordernow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import se.miun.ordernow.R;
import se.miun.ordernow.model.TableList;

public class TableChoiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    TableList tableList; /*= Arrays.asList("table 1", "table 2", "table 3", "table 4", "table 5", "table 6", "table 7");*/
    private static RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListener listner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_table_layout);

        tableList = new TableList();

        displayItem();
    }

    private void displayItem() {
        listner = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
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

    public static void updateAdapter() {
        if(recyclerAdapter == null)
            return;

        recyclerAdapter.notifyDataSetChanged();
    }
};