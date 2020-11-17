package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

// Google Login
public class GoogleLogin extends AppCompatActivity {


    // google
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    // database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    private String first_name;
    private String last_name;
    private String email;
    private String subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // google login
        createRequest();
        signIn();
    }


    // create request to google
    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    // when user clicks on sign in button
    private void signIn() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // get result from user
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Firestore Database to store personal information

                            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(GoogleLogin.this);
                            if(signInAccount != null) {
                                first_name = signInAccount.getGivenName();
                                last_name = signInAccount.getFamilyName();
                                email = signInAccount.getEmail();
                            }

                            //userID = user.getUid();
                            userID = user.getUid();
                            DocumentReference documentReference = db.collection("users").document(userID);

                            // get subscription model of user
                            /*
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        subscription = documentSnapshot.getString("subscription");
                                    }
                                }


                            if(subscription != null && subscription.equals("premium")) {
                                Intent intent = new Intent(GoogleLogin.this, Activity6.class);
                                startActivity(intent);
                            }
                            });

                             */

                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    DocumentSnapshot document = task.getResult();
                                    subscription = document.getString("subscription");

                                    if(subscription != null && (subscription.equals("premium") || subscription.equals("trial"))) {
                                        Toast.makeText(GoogleLogin.this,"Google Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(GoogleLogin.this, Activity4.class);
                                        startActivity(intent);
                                    } else if (subscription != null && subscription.equals("free")){
                                        Toast.makeText(GoogleLogin.this,"Google Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(GoogleLogin.this, Activity5.class);
                                        startActivity(intent);
                                    } else {
                                        Map<String, Object> google = new HashMap<>();
                                        google.put("first_name", first_name);
                                        google.put("last_name", last_name);
                                        google.put("email", email);

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

                                        documentReference.set(google).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(GoogleLogin.this,"Google Login Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Intent intent = new Intent(GoogleLogin.this, Activity5.class);
                                        startActivity(intent);
                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(GoogleLogin.this, "Login failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }



}