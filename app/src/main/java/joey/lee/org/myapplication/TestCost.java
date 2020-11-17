package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class TestCost extends AppCompatActivity {

    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_cost;
    private TextView tv_kwh;
    private TextView tv_location;

    private Button btn_fill;
    private Button btn_entry;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cost);

        btn_entry = findViewById(R.id.btn_entry);
        btn_fill = findViewById(R.id.btn_fill);

        tv_date = findViewById(R.id.text_date);
        tv_time = findViewById(R.id.text_time);
        tv_cost = findViewById(R.id.text_cost);
        tv_kwh = findViewById(R.id.text_kwh);
        tv_location = findViewById(R.id.text_location);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        btn_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestCost.this, EnterCost.class);
                startActivity(intent);
            }
        });

        btn_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestCost.this, Activity7.class);
                startActivity(intent);


                //DocumentReference docRef = db.collection("users").document(userID).collection("costs").document("1604536220549");
                /*
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            //DocumentSnapshot document = task.getResult();
                            //tv_date.setText(document.getString("date"));
                        }
                    }
                });

                 */ /*
                DocumentReference documentReference = db.collection("users").document(userID).collection("costs").document("1604536220549");
                documentReference.addSnapshotListener(TestCost.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        tv_date.setText(documentSnapshot.getString("date"));
                        tv_time.setText(documentSnapshot.getString("time"));
                        tv_cost.setText(documentSnapshot.getString("cost"));
                        tv_kwh.setText(documentSnapshot.getString("kwh"));
                        tv_location.setText(documentSnapshot.getString("location"));
                    }
                });
                */



/*
                DocumentReference documentReference = db.collection("users").document(userID).collection("costs").document("1604536220549");
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                        tv_date.setText(documentSnapshot.getString("date"));
                        tv_time.setText(documentSnapshot.getString("time"));
                        tv_cost.setText(documentSnapshot.getString("cost"));
                        tv_kwh.setText(documentSnapshot.getString("kwh"));
                        tv_location.setText(documentSnapshot.getString("location"));
                    }
                });


 */

            }
        });
    }
}