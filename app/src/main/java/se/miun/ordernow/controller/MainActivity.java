package se.miun.ordernow.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import se.miun.ordernow.R;
import se.miun.ordernow.model.BackgroundApiFetcher;
import se.miun.ordernow.view.FloorActivity;
import se.miun.ordernow.view.KitchenMenuActivity;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    Button chooseFloorBTN;
    Button chooseKitchenBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        // Start background thread to receive notifications from kitchen.
        BackgroundApiFetcher updater = new BackgroundApiFetcher();

        chooseFloorBTN = (Button) findViewById(R.id.activity_main2FloorButton);
        chooseKitchenBTN = (Button) findViewById(R.id.activity_main2KitchenButton);

        chooseFloorBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FloorActivity.class);
                startActivity(intent);
            }
        });

        chooseKitchenBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KitchenMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public static Context getContext() {
        return context;
    }
}