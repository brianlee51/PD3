package sg.edu.rp.c346.shippingcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class showResults extends AppCompatActivity {

    TextView company, service, price, estDelivery;
    Button btnHome;
    ImageView ivTracking, ivDoorstep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        company = findViewById(R.id.textViewCompany);
        service = findViewById(R.id.textViewService);
        price = findViewById(R.id.textViewCost);
        estDelivery = findViewById(R.id.textViewEstimateDelivery);
        btnHome = findViewById(R.id.buttonHome);
        ivTracking = findViewById(R.id.imageViewTracking);
        ivDoorstep = findViewById(R.id.imageViewDelivery);

        Intent intentReceived = getIntent();
        String companyName = intentReceived.getStringExtra("company");
        String serviceName = intentReceived.getStringExtra("service");
        double tPrice = intentReceived.getDoubleExtra("price", 0.0);
        String estDeliveryDays = intentReceived.getStringExtra("estimatedDelivery");
        boolean tracking = intentReceived.getBooleanExtra("tracking",false);
        boolean doorstep = intentReceived.getBooleanExtra("doorstep", false);

        company.setText(companyName);
        service.setText(serviceName);
        price.setText("$"+tPrice);
        estDelivery.setText(estDeliveryDays);
        if (tracking == true) {
            ivTracking.setImageResource(R.drawable.tick);
        } else {
            ivTracking.setImageResource(R.drawable.cross);
        }
        if (doorstep == true) {
            ivDoorstep.setImageResource(R.drawable.tick);
        } else {
            ivDoorstep.setImageResource(R.drawable.cross);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(getBaseContext(), MainActivity.class);
                startActivity(backToHome);
            }
        });
    }
}
