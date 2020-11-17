package joey.lee.org.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

// test activity
public class Activity6 extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_car;
    private TextView tv_membership;
    private TextView tv_subscription;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;
    private String name;

    private Button btn_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);

        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_car = findViewById(R.id.tv_car);
        tv_membership = findViewById(R.id.tv_membership);
        tv_subscription = findViewById(R.id.tv_subscription);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name = documentSnapshot.getString("first_name") + " " + documentSnapshot.getString("last_name");
                tv_name.setText(name);
                tv_email.setText(documentSnapshot.getString("email"));
                tv_car.setText(documentSnapshot.getString("car"));
                tv_membership.setText(documentSnapshot.getString("membership"));
                tv_subscription.setText(documentSnapshot.getString("subscription"));
            }
        });



        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity6.this, Activity7.class);
                startActivity(intent);
            }
        });



    }
}