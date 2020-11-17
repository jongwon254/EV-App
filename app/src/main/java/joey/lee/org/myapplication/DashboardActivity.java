package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_battery;
    private Button btn_map;
    private Button btn_entercost;
    private Button btn_payphone;
    private Button btn_energy;
    private Button btn_settings;

    private Spinner spinner_month;
    private TextView tv_table;
    private TextView tv_kwh;
    private TextView tv_km;
    private TextView tv_year;
    private TextView tv_month;
    private TextView tv_week;

    private int cost_year;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;
    private String month = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_battery = findViewById(R.id.btn_battery);
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_entercost= findViewById(R.id.btn_entercost);
        btn_entercost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EnterCost.class);
                startActivity(intent);
            }
        });

        btn_payphone = findViewById(R.id.btn_payphone);
        btn_payphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PayPhoneActivity.class);
                startActivity(intent);
            }
        });

        tv_table = findViewById(R.id.tv_table);
        tv_kwh = findViewById(R.id.tv_kwh);
        tv_km = findViewById(R.id.tv_km);
        tv_month = findViewById(R.id.tv_month);
        tv_week = findViewById(R.id.tv_week);
        tv_year = findViewById(R.id.tv_year);

        tv_table.setMovementMethod(new ScrollingMovementMethod());

        // Dropdown menu memberships, list created in values->strings
        spinner_month = findViewById(R.id.spinner_month);
        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(DashboardActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter_month);



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userID).collection("statistics").document("car");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        month = documentSnapshot.getString("month");
                        //Log.d("month", month +"");
                    }
                });


            db.collection("users").document(userID).collection("costs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> listofDocuments = task.getResult().getDocuments();

                        //Log.d("get1", listofDocuments.size()+"");

                        int cost_month = 0;
                        int cost_week = 0;
                        double cost_kwh = 0.0;

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < listofDocuments.size(); i++) {
                            String cost = listofDocuments.get(i).getString("cost");
                            String date = listofDocuments.get(i).getString("date");
                            String kwh = listofDocuments.get(i).getString("kwh");


                            if (date.substring(6, 10).equals("2020")) {
                                cost_year = Integer.parseInt(cost) + cost_year;

                                if (date.substring(3, 5).equals("11")) {
                                    cost_month = Integer.parseInt(cost) + cost_month;
                                    sb.append(date + "           " + cost + "€ \n");
                                    cost_kwh = cost_kwh + (Double.parseDouble(cost) / Double.parseDouble(kwh));

                                    if ((date.substring(0, 2).equals("16")) || (date.substring(0, 2).equals("17")) || (date.substring(0, 2).equals("18")) || (date.substring(0, 2).equals("19"))
                                            || (date.substring(0, 2).equals("20")) || (date.substring(0, 2).equals("21")) || (date.substring(0, 2).equals("22"))) {
                                        cost_week = Integer.parseInt(cost) + cost_week;
                                        //Log.d("cost", "week");
                                    }
                                }
                            }
                        }
                        tv_table.setText(sb.toString());
                        double cost = cost_kwh / listofDocuments.size();
                        tv_kwh.setText(((double) ((int) (cost * 100.0)) / 100.0) + "€");

                        int m = Integer.parseInt(month);
                        double km = (cost / m) * 100;
                        tv_km.setText(((double) ((int) (km * 100.0)) / 100.0) + "€");

                        tv_month.setText(cost_month + "€");
                        tv_week.setText(cost_week + "€");
                        tv_year.setText(cost_year + "€");


                    }
                }
            });

        }



}

