package se.miun.ordernow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Button myButton;
    private int buttonCounter;
    private TextView buttonTextArea;


    // List stuff
    private ScrollView scrollView;
    private TableLayout myTable;
    private FloatingActionButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myTable = (TableLayout) findViewById(R.id.tableLayout);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);

        String[] items = new String[]{" ", "Förrätt", "Varmrätt", "Efterrätt"};
        String[] apetizer = new String[]{" ", "Soppa", "Vitlöksbröd"};
        String[] orders = new String[]{" ", "Oxfile", "Pasta", "More stuff"};
        String[] empty = new String[]{" "};
        TableRow row = new TableRow(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        Spinner dropdown = new Spinner(this, Spinner.MODE_DROPDOWN);
        dropdown.setAdapter(adapter);


        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orders);
        Spinner orderMenu = new Spinner(this, 1);
        orderMenu.setAdapter(orderAdapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currentValue = adapterView.getSelectedItem().toString();
                ArrayAdapter<String> newAdapter;
                switch (currentValue) {
                    case "Förrätt": {
                        newAdapter = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, apetizer);
                        break;
                    }
                    case "Varmrätt": {
                        newAdapter = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orders);
                        break;
                    }
                    case "Efterrätt": {
                        newAdapter = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, empty);
                        break;
                    }
                    default: {
                        newAdapter = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, empty);
                        // Error
                    }
                }
                orderMenu.setAdapter(newAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        row.addView(dropdown);
        row.addView(orderMenu);

        myTable.addView(row);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow rowCopy = new TableRow(view.getContext());

                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
                Spinner dropdown1 = new Spinner(view.getContext());
                dropdown1.setAdapter(adapter1);

                ArrayAdapter<String> orderAdapter1 = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orders);
                Spinner orderMenu1 = new Spinner(view.getContext());
                orderMenu1.setAdapter(orderAdapter1);

                rowCopy.addView(dropdown1);
                rowCopy.addView(orderMenu1);
                myTable.addView(rowCopy);
            }
        });
    }
}