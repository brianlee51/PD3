package sg.edu.rp.c346.shippingcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class companyService extends AppCompatActivity {

    TextView display, tvLabel;
    Spinner spnService;
    EditText etWeight;
    CheckBox cbRegistered;
    Button btnCalc;
    ArrayList<String> alService;
    ArrayAdapter<String> aaService;
    int value = 0;
    int globalWeight = 0;
    String companyName = "";
    String serviceName = "";
    String estimatedDelivery = "";
    boolean tracking = false;
    boolean doorStepDelivery = false;
    double price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_service);
        tvLabel = findViewById(R.id.textViewLabel);
        display = findViewById(R.id.textViewDisplay);
        spnService = findViewById(R.id.spinnerEntries);
        cbRegistered = findViewById(R.id.checkBoxRegistered);
        etWeight = findViewById(R.id.editTextWeight);
        btnCalc = findViewById(R.id.buttonCalc);
        cbRegistered.setVisibility(CheckBox.INVISIBLE);
        tvLabel.setVisibility(TextView.INVISIBLE);

        alService = new ArrayList<>();
        aaService = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alService);
        Intent intentReceived = getIntent();
        value = intentReceived.getIntExtra("selectedPosition", 0);
        if (value == 0) {
            display.setText("Singapore Post");
            String[] strService = getResources().getStringArray(R.array.singaporepost);
            alService.addAll(Arrays.asList(strService));
            spnService.setAdapter(aaService);
            cbRegistered.setVisibility(CheckBox.VISIBLE);
            tvLabel.setVisibility(TextView.VISIBLE);
            tvLabel.setText("Registered Article");
        } else if (value == 1) {
            display.setText("SmartPac");
            spnService.setEnabled(false);
        } else if (value == 2) {
            display.setText("Speedpost");
            String[] strService = getResources().getStringArray(R.array.speedpost);
            alService.addAll(Arrays.asList(strService));
            spnService.setAdapter(aaService);
            cbRegistered.setVisibility(CheckBox.VISIBLE);
            tvLabel.setVisibility(TextView.VISIBLE);
            tvLabel.setText("Central Business District (CBD) ?");
        } else if (value == 3) {
            display.setText("Ninja van");
            String[] strService = getResources().getStringArray(R.array.ninjavan);
            alService.addAll(Arrays.asList(strService));
            spnService.setAdapter(aaService);
        }

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = etWeight.getText().toString();
                int position = spnService.getSelectedItemPosition();
                String message = "";
                if (weight.length() == 0) {
                    message = "Weight cannot be empty!";
                } else {
                    int weight1 = Integer.parseInt(weight);
                    globalWeight = weight1;
                }

                if (value == 0) {
                    companyName = "Singpost";
                    estimatedDelivery = "2-5 working days";
                    if (position == 0) {
                        serviceName = "Standard Regular";
                        if (globalWeight <= 20) {
                            price = 0.30;
                        } else if (globalWeight <= 40) {
                            price = 0.37;
                        } else {
                            message = "Weight entered exceeded allowed by Service. Maximum weight is 40grams. Choose another service Standard Large or Non-Standard.";
                        }
                    } else if (position == 1) {
                        serviceName = "Standard Large";
                        if (globalWeight <= 100) {
                            price = 0.60;
                        } else if (globalWeight <= 250) {
                            price = 0.90;
                        } else if (globalWeight <= 500) {
                            price = 1.15;
                        } else if (globalWeight <= 1000) {
                            price = 2.25;
                        } else if (globalWeight <= 2000) {
                            price = 3.35;
                        } else {
                            message = "Weight entered exceeded allowed by all services. Maximum weight is 2kg or 2000grams. Choose other companies.";
                        }
                    } else if (position == 2) {
                        serviceName = "Non Standard";
                        if (globalWeight <= 40) {
                            price = 0.60;
                        } else if (globalWeight <= 100) {
                            price = 0.90;
                        } else if (globalWeight <= 250) {
                            price = 1.15;
                        } else if (globalWeight <= 500) {
                            price = 1.70;
                        } else if (globalWeight <= 1000) {
                            price = 2.55;
                        } else if (globalWeight <= 2000) {
                            price = 3.35;
                        } else {
                            message = "Weight entered exceeded allowed by all services. Maximum weight is 2kg or 2000grams. Choose other companies.";
                        }
                    }
                    if (cbRegistered.isChecked()) {
                        price += 2.24;
                        tracking = true;
                        doorStepDelivery = true;
                        estimatedDelivery = "2 working days";
                    }
                } else if (value == 1) {
                    companyName = "SmartPac";
                    if (globalWeight > 3000) {
                        message = "Weight entered exceeded allowed by all services. Maximum weight is 3kg or 3000grams. Choose other companies.";
                    } else if (globalWeight > 1000) {
                        price = 4.70;
                        serviceName = "Smartpac";
                    } else if (globalWeight > 600) {
                        price = 3.80;
                        serviceName = "Smartpac Lite";
                    } else {
                        price = 3.20;
                        serviceName = "Smartpac mini";
                    }
                    tracking = true;
                    estimatedDelivery = "3-7 working days";
                } else if (value == 2) {
                    companyName = "Speedpost";
                    if (position == 0) {
                        serviceName = "Speedpost Express";
                        if (cbRegistered.isChecked() && globalWeight <= 5000) {
                            price = 13.00;
                        } else if (!cbRegistered.isChecked() && globalWeight <= 5000) {
                            price = 22.00;
                        } else {
                            message = "Weight entered exceeded allowed by Service. Maximum weight is 5kg or 5000grams. Choose another service.";
                        }
                        tracking = true;
                        doorStepDelivery = true;
                        estimatedDelivery = "2 hours on same day before cut-off time 4pm - 1 day";
                    } else if (position == 1) {
                        serviceName = "Speedpost Priority";
                        tracking = true;
                        doorStepDelivery = true;
                        estimatedDelivery = "Same working day before cut-off time 11.30am - 1 day";
                        if (globalWeight <= 2000) {
                            price = 15.00;
                        } else if (globalWeight <= 5000) {
                            price = 16.00;
                        } else if (globalWeight <= 10000) {
                            price = 17.00;
                        } else if (globalWeight <= 15000) {
                            price = 18.00;
                        } else if (globalWeight <= 20000) {
                            price = 19.00;
                        } else if (globalWeight <= 30000) {
                            price = 21.00;
                        } else {
                            message = "Weight entered exceeded allowed by all services. Maximum weight is 30kg or 30000grams. Sorry, no other companies offer shipping above 30kg.";
                        }
                    } else if (position == 2) {
                        serviceName = "Speedpost Standard";
                        tracking = true;
                        doorStepDelivery = true;
                        estimatedDelivery = "1 - 2 days";
                        if (globalWeight <= 2000) {
                            price = 10.00;
                        } else if (globalWeight <= 5000) {
                            price = 10.50;
                        } else if (globalWeight <= 10000) {
                            price = 11.50;
                        } else if (globalWeight <= 15000) {
                            price = 12.50;
                        } else if (globalWeight <= 20000) {
                            price = 13.50;
                        } else if (globalWeight <= 30000) {
                            price = 16.00;
                        } else {
                            message = "Weight entered exceeded allowed by all services. Maximum weight is 30kg or 30000grams. Sorry, no other companies offer shipping above 30kg.";
                        }
                    }
                } else if (value == 3) {
                    companyName = "Ninja Van";
                    tracking = true;
                    if (globalWeight > 4000) {
                        message = "Weight entered exceeded allowed by all services. Maximum weight is 4kg or 4000grams. Choose other companies.";
                    } else {
                        if (position == 0) {
                            serviceName = "Ninja Van Standard Courier";
                            price = 5.00;
                            doorStepDelivery = true;
                            estimatedDelivery = "1-3 working days";
                        } else if (position == 1) {
                            serviceName = "Ninja Van Express Courier";
                            price = 6.00;
                            doorStepDelivery = true;
                            estimatedDelivery = "1-2 working days";
                        } else if (position == 2) {
                            serviceName = "Ninja Collect";
                            price = 2.50;
                            estimatedDelivery = "1-3 working days";
                        }
                    }
                }

                if (message.length() == 0) {
                    Intent intent = new Intent(companyService.this, showResults.class);
                    intent.putExtra("company", companyName);
                    intent.putExtra("service", serviceName);
                    intent.putExtra("price", price);
                    intent.putExtra("doorstep", doorStepDelivery);
                    intent.putExtra("estimatedDelivery", estimatedDelivery);
                    intent.putExtra("tracking", tracking);
                    startActivity(intent);
                    companyName = "";
                    serviceName = "";
                    estimatedDelivery = "";
                    tracking = false;
                    doorStepDelivery = false;
                    price = 0.0;
                } else {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(companyService.this);
                    myBuilder.setTitle("Error");
                    myBuilder.setMessage(message);
                    myBuilder.setCancelable(false);
                    myBuilder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(companyService.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    myBuilder.setNegativeButton("Cancel", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
            }
        });
    }
}
