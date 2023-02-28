package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button chooseFloorBTN;
    Button chooseKitchenBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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