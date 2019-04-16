package sg.edu.rp.c346.shippingcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvCompanies;
    ArrayList<String> alCompanies = new ArrayList<>();
    ArrayAdapter<String> aaCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCompanies = findViewById(R.id.listViewCompany);
        alCompanies.add("Singapore Post (Suitable for letters and small items)");
        alCompanies.add("SmartPac (Suitable for items)");
        alCompanies.add("Speedpost (Suitable for large items)");
        alCompanies.add("Ninja Van (Suitable for large items)");

        aaCompanies = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alCompanies);
        lvCompanies.setAdapter(aaCompanies);

        lvCompanies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsToPass = new Intent(getBaseContext(), companyService.class);
                detailsToPass.putExtra("selectedPosition", position);
                startActivity(detailsToPass);
            }
        });
    }
}
