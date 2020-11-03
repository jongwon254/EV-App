package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity5 extends AppCompatActivity {

    // Buttons
    private Button btn_subscribe;
    private Button btn_later;
    private Button btn_back;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // unique UserID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        // back button
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        // Instance of Authentication Server and Database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // get buttons
        btn_subscribe = findViewById(R.id.btn_subscribe);
        btn_later = findViewById(R.id.btn_later);

        // action for button subscribe
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Firestore Database to store profile information
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> freemium = new HashMap<>();
                freemium.put("subscription", "premium");

                documentReference.update(freemium).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Activity5.this,"Great Choice", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(Activity5.this, Activity6.class));
            }
        });

        // action for button maybe later...
        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Firestore Database to store profile information
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(userID);
                Map<String, Object> freemium = new HashMap<>();
                freemium.put("subscription", "free");

                documentReference.update(freemium).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Activity5.this,"", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(Activity5.this, Activity6.class));
            }
        });

    }

    // go to register activity for back button
    public void openActivity4() {
        Intent intent = new Intent(this, Activity4.class);
        startActivity(intent);
    }

}