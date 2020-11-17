package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// Profile setting activity
public class Activity4 extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_next;

    // Dropdown menu

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;

    private String card1;
    private String card2;
    private String card3;
    private String card4;

    private Spinner spinner_car;
    private Spinner spinner_model;
    private String car;
    private String model;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // unique UserID
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);



        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);

        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked()) {
                    card1 = "ADAC e-Charge";
                } else {
                    card1 = "";
                }
            }
        });

        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb2.isChecked()) {
                    card2 = "Shell Recharge";
                } else {
                    card2 = "";
                }
            }
        });

        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb3.isChecked()) {
                    card3 = "Plugsurfing";
                } else {
                    card3 = "";
                }
            }
        });

        cb4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (cb4.isChecked()) {
                   card4 = "Schneider Solar";
               } else {
                   card4 = "";
               }
           }
       });

        // back button
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Instance of Authentication Server and Database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();








        // Dropdown menu memberships, list created in values->strings

        spinner_model = findViewById(R.id.spinner_model);
        ArrayAdapter<String> adapter_model = new ArrayAdapter<String>(Activity4.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.model));
        adapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_model.setAdapter(adapter_model);

        // Dropdown menu memberships, list created in values->strings
        spinner_car = findViewById(R.id.spinner_car);
        ArrayAdapter<String> adapter_cars = new ArrayAdapter<String>(Activity4.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cars));
        adapter_cars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car.setAdapter(adapter_cars);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get information from dropdown menu
                car = spinner_car.getSelectedItem().toString();
                model = spinner_model.getSelectedItem().toString();

                // Firestore Database to store profile information
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> profile = new HashMap<>();
                profile.put("brand", car);
                profile.put("model", model);
                profile.put("card_1", card1);
                profile.put("card_2", card2);
                profile.put("card_3", card3);
                profile.put("card_4", card4);

                documentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Activity4.this,"Profile saved", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(Activity4.this, CarConnection2.class));
            }
        });


    }

}