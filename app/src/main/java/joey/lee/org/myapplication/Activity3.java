package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

// Login Activity
public class Activity3 extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_login;
    private Button btn_register;
    private Button btn_google;

    // EditText Fields
    private EditText text_email;
    private EditText text_password;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;
    private String subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // Instance
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_google = findViewById(R.id.btn_google);

        // register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity3.this, Activity2.class));
            }
        });

        // google login
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity3.this, GoogleLogin.class));
            }
        });

        // login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // store information from text fields
                String email = text_email.getText().toString();
                String password = text_password.getText().toString();

                // requirements for text fields
                if(email.isEmpty()) {
                    text_email.setError("Please enter your Email");
                    text_email.requestFocus();
                } else if(password.isEmpty()) {
                    text_password.setError("Please enter your password");
                    text_password.requestFocus();
                } else if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(Activity3.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Activity3.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // log in to authentication server
                            if(!task.isSuccessful()) {
                                Toast.makeText(Activity3.this,"Login failed, Please try again :(", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity3.this,"Login successful", Toast.LENGTH_SHORT).show();

                                // show the get premium page if the user uses the free version, otherwise do not!
                                userID = mAuth.getCurrentUser().getUid();

                                DocumentReference documentReference = db.collection("users").document(userID);
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        DocumentSnapshot document = task.getResult();
                                        subscription = document.getString("subscription");

                                        if(subscription.equals("premium") || subscription.equals("trial")) {
                                            Intent intent = new Intent(Activity3.this, EnergyActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(Activity3.this, Activity5.class);
                                            startActivity(intent);
                                        }
                                    }
                                });


                            }
                        }
                    });

                } else {
                    Toast.makeText(Activity3.this,"Error Occurred :(", Toast.LENGTH_SHORT).show();


                }
            }

        });

        // back button function
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
