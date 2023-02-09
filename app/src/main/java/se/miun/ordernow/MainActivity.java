package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    Button chooseTableBTN;
    Button statusTableBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseTableBTN = (Button) findViewById(R.id.TableButton);
        statusTableBTN = (Button) findViewById(R.id.statusButton);

        chooseTableBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this ,TableChoiceActivity.class);
                startActivity(intent);

            }
        });
    }

}