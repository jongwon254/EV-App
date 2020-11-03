package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Spinner spinner_membership;
    private Spinner spinner_car;
    private String membership;
    private String car;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // unique UserID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        // back button
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        // Instance of Authentication Server and Database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        // Dropdown menu memberships, list created in values->strings
        spinner_membership = findViewById(R.id.spinner_membership);
        ArrayAdapter<String> adapter_memberships = new ArrayAdapter<String>(Activity4.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.memberships));
        adapter_memberships.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_membership.setAdapter(adapter_memberships);

        // Dropdown menu memberships, list created in values->strings
        spinner_car = findViewById(R.id.spinner_car);
        ArrayAdapter<String> adapter_cars = new ArrayAdapter<String>(Activity4.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cars));
        adapter_memberships.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car.setAdapter(adapter_cars);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get information from dropdown menu
                membership = spinner_membership.getSelectedItem().toString();
                car = spinner_car.getSelectedItem().toString();

                // Firestore Database to store profile information
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> profile = new HashMap<>();
                profile.put("membership", membership);
                profile.put("car", car);

                documentReference.update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Activity4.this,"Profile saved", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(Activity4.this, Activity5.class));
            }
        });


    }

    // go to register activity for back button
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}