package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class TableChoiceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<String> tableList = Arrays.asList("table 1", "table 2", "table 3", "table 4", "table 5", "table 6", "table 7");
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListner listner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_table_layout);

        displayItem();
    }

    private void displayItem() {
        listner = new RecyclerAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                System.out.println("YeS! YOU CLICked!");
                Intent intent =new Intent(getApplicationContext(),OrderMenu.class) ;
                //startActivity(intent);  check with Mattias
                //ToDo: check with Mattias
            }
        };
        recyclerView = findViewById(R.id.recyclerView_tableList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerAdapter = new RecyclerAdapter(tableList, listner);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));


    }
};