package se.miun.ordernow.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.ApiCommunicator;
import se.miun.ordernow.model.BackgroundApiFetcher;
import se.miun.ordernow.model.MenuItem;
import se.miun.ordernow.model.MenuList;
import se.miun.ordernow.view.FloorActivity;
import se.miun.ordernow.view.KitchenMenuActivity;

public class MainActivity2 extends AppCompatActivity {
    Button chooseFloorBTN;
    Button chooseKitchenBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Start background thread to receive notifications from kitchen.
        BackgroundApiFetcher updater = new BackgroundApiFetcher();

        chooseFloorBTN = (Button) findViewById(R.id.activity_main2FloorButton);
        chooseKitchenBTN = (Button) findViewById(R.id.activity_main2KitchenButton);

        chooseFloorBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, FloorActivity.class);
                startActivity(intent);
            }
        });

        chooseKitchenBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, KitchenMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}