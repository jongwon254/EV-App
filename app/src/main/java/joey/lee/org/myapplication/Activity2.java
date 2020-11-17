package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// Register Activity
public class Activity2 extends AppCompatActivity {

    // Buttons
    private Button btn_back;
    private Button btn_register;
    private Button btn_login;

    // EditText Fields
    private EditText text_firstname;
    private EditText text_lastname;
    private EditText text_email;
    private EditText text_password;
    private EditText text_confirmpassword;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // unique UserID
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // back button
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        // Instance of Authentication Server and Database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Instance of EditText Fields
        text_firstname = findViewById(R.id.text_firstname);
        text_lastname = findViewById(R.id.text_lastname);
        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);
        text_confirmpassword = findViewById(R.id.text_confirmpassword);

        // login
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Activity2.this, Activity3.class));
             }
         });

        // clicking on register button
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store all information
                String first_name = text_firstname.getText().toString();
                String last_name = text_lastname.getText().toString();

                String email = text_email.getText().toString();
                String password = text_password.getText().toString();
                String confirm_password = text_confirmpassword.getText().toString();

                // requirements of text fields
                if(email.isEmpty()) {
                    text_email.setError("Please enter your Email");
                    text_email.requestFocus();
                } else if(password.isEmpty()) {
                    text_password.setError("Please enter your password");
                    text_password.requestFocus();
                } else if(password.length() < 6){
                    text_password.setError("At least 6 characters");
                } else if(!password.equals(confirm_password)) {
                    text_confirmpassword.setError("Passwords do not match");
                    text_confirmpassword.requestFocus();
                } else if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(Activity2.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && password.isEmpty())) {

                    // create user in authentication server
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Activity2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(Activity2.this,"Authentication failed, Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity2.this,"Register successful", Toast.LENGTH_SHORT).show();

                                // Firestore Database to store personal information
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("first_name", first_name);
                                user.put("last_name", last_name);
                                user.put("email", email);

                                DocumentReference documentReference1 = db.collection("users").document(userID).collection("statistics").document("car");
                                Map<String, Object> car = new HashMap<>();
                                car.put("today", "12");
                                car.put("week", "39");
                                car.put("month", "130");

                                car.put("battery", "75");
                                car.put("capacity", "94");
                                car.put("lifetime", "November 2029");
                                car.put("temperature", "11");
                                car.put("status", "Normal");

                                documentReference1.set(car);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity2.this,"User created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                startActivity(new Intent(Activity2.this, Activity5.class));

                            }
                        }
                    });
                } else {
                    Toast.makeText(Activity2.this,"Error Occurred :(", Toast.LENGTH_SHORT).show();


                }

            }


        });
    }

    // go to main activity for back button
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
