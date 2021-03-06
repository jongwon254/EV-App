package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity {

    private TextView tv_favorite1;
    private TextView tv_favorite2;
    private TextView tv_favorite3;
    private TextView tv_favorite4;
    private TextView tv_favorite5;
    private TextView tv_favorite6;
    private TextView tv_favorite7;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userID;

    private Button btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        tv_favorite1 = findViewById(R.id.tv_favorite1);
        tv_favorite2 = findViewById(R.id.tv_favorite2);
        tv_favorite3 = findViewById(R.id.tv_favorite3);
        tv_favorite4 = findViewById(R.id.tv_favorite4);
        tv_favorite5 = findViewById(R.id.tv_favorite5);
        tv_favorite6 = findViewById(R.id.tv_favorite6);
        tv_favorite7 = findViewById(R.id.tv_favorite7);

        btn_delete = findViewById(R.id.btn_delete);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userID).collection("search_history").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> listofDocuments = task.getResult().getDocuments();

                    if (listofDocuments.size() == 1) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address") + "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 2) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address") + "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address") + "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 3) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address")+ "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address")+ "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);

                        tv_favorite3.setText(listofDocuments.get(2).getString("name") + "\n" + listofDocuments.get(2).getString("address")+ "\nSearched on: " + listofDocuments.get(2).getString("date"));
                        tv_favorite3.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 4) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address")+ "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address")+ "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);

                        tv_favorite3.setText(listofDocuments.get(2).getString("name") + "\n" + listofDocuments.get(2).getString("address")+ "\nSearched on: " + listofDocuments.get(2).getString("date"));
                        tv_favorite3.setBackgroundResource(R.drawable.search);

                        tv_favorite4.setText(listofDocuments.get(3).getString("name") + "\n" + listofDocuments.get(3).getString("address")+ "\nSearched on: " + listofDocuments.get(3).getString("date"));
                        tv_favorite4.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 5) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address")+ "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address")+ "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);

                        tv_favorite3.setText(listofDocuments.get(2).getString("name") + "\n" + listofDocuments.get(2).getString("address")+ "\nSearched on: " + listofDocuments.get(2).getString("date"));
                        tv_favorite3.setBackgroundResource(R.drawable.search);

                        tv_favorite4.setText(listofDocuments.get(3).getString("name") + "\n" + listofDocuments.get(3).getString("address")+ "\nSearched on: " + listofDocuments.get(3).getString("date"));
                        tv_favorite4.setBackgroundResource(R.drawable.search);

                        tv_favorite5.setText(listofDocuments.get(4).getString("name") + "\n" + listofDocuments.get(4).getString("address")+ "\nSearched on: " + listofDocuments.get(4).getString("date"));
                        tv_favorite5.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 6) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address")+ "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address")+ "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);

                        tv_favorite3.setText(listofDocuments.get(2).getString("name") + "\n" + listofDocuments.get(2).getString("address")+ "\nSearched on: " + listofDocuments.get(2).getString("date"));
                        tv_favorite3.setBackgroundResource(R.drawable.search);

                        tv_favorite4.setText(listofDocuments.get(3).getString("name") + "\n" + listofDocuments.get(3).getString("address")+ "\nSearched on: " + listofDocuments.get(3).getString("date"));
                        tv_favorite4.setBackgroundResource(R.drawable.search);

                        tv_favorite5.setText(listofDocuments.get(4).getString("name") + "\n" + listofDocuments.get(4).getString("address")+ "\nSearched on: " + listofDocuments.get(4).getString("date"));
                        tv_favorite5.setBackgroundResource(R.drawable.search);

                        tv_favorite6.setText(listofDocuments.get(5).getString("name") + "\n" + listofDocuments.get(5).getString("address")+ "\nSearched on: " + listofDocuments.get(5).getString("date"));
                        tv_favorite6.setBackgroundResource(R.drawable.search);
                    }

                    if (listofDocuments.size() == 7 || listofDocuments.size()>7) {
                        tv_favorite1.setText(listofDocuments.get(0).getString("name") + "\n" + listofDocuments.get(0).getString("address")+ "\nSearched on: " + listofDocuments.get(0).getString("date"));
                        tv_favorite1.setBackgroundResource(R.drawable.search);

                        tv_favorite2.setText(listofDocuments.get(1).getString("name") + "\n" + listofDocuments.get(1).getString("address")+ "\nSearched on: " + listofDocuments.get(1).getString("date"));
                        tv_favorite2.setBackgroundResource(R.drawable.search);

                        tv_favorite3.setText(listofDocuments.get(2).getString("name") + "\n" + listofDocuments.get(2).getString("address")+ "\nSearched on: " + listofDocuments.get(2).getString("date"));
                        tv_favorite3.setBackgroundResource(R.drawable.search);

                        tv_favorite4.setText(listofDocuments.get(3).getString("name") + "\n" + listofDocuments.get(3).getString("address")+ "\nSearched on: " + listofDocuments.get(3).getString("date"));
                        tv_favorite4.setBackgroundResource(R.drawable.search);

                        tv_favorite5.setText(listofDocuments.get(4).getString("name") + "\n" + listofDocuments.get(4).getString("address")+ "\nSearched on: " + listofDocuments.get(4).getString("date"));
                        tv_favorite5.setBackgroundResource(R.drawable.search);

                        tv_favorite6.setText(listofDocuments.get(5).getString("name") + "\n" + listofDocuments.get(5).getString("address")+ "\nSearched on: " + listofDocuments.get(5).getString("date"));
                        tv_favorite6.setBackgroundResource(R.drawable.search);

                        tv_favorite7.setText(listofDocuments.get(6).getString("name") + "\n" + listofDocuments.get(6).getString("address")+ "\nSearched on: " + listofDocuments.get(6).getString("date"));
                        tv_favorite7.setBackgroundResource(R.drawable.search);
                    }
                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> ids = new ArrayList<>();

                db.collection("users").document(userID).collection("search_history").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> listofDocuments = task.getResult().getDocuments();

                            for(int i = 0; i<listofDocuments.size(); i++) {
                                ids.add(listofDocuments.get(i).getId());
                            }

                            for(int i = 0; i< ids.size(); i++) {
                                db.collection("users").document(userID).collection("search_history").document(ids.get(i)).delete();
                            }
                        }
                    }


                });

                recreate();
            }
        });
    }
}