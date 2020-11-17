package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    private EditText text_subject;
    private EditText text_message;
    private EditText text_email;

    private Button btn_submit;
    private Button btn_battery;
    private Button btn_map;
    private Button btn_energy;
    private Button btn_dashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btn_submit = findViewById(R.id.btn_submit);
        text_message = findViewById(R.id.text_message);
        text_subject = findViewById(R.id.text_subject);
        text_email = findViewById(R.id.text_email);

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, EnergyActivity.class);
                startActivity(intent);
            }
        });

        btn_battery = findViewById(R.id.btn_battery);
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, JSONActivity.class);
                startActivity(intent);
            }
        });

        btn_dashboard= findViewById(R.id.btn_dashboard);
        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "jongwon254@gmail.com"));
               Log.d("CONTACT", text_subject.getText().toString());
               Log.d("CONTACT", text_message.getText().toString());
               intent.putExtra(Intent.EXTRA_SUBJECT, text_subject.getText().toString());
               intent.putExtra(Intent.EXTRA_TEXT, text_message.getText().toString());
               startActivity(intent);

                 */
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"jongwon254@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, text_subject.getText().toString());
                email.putExtra(Intent.EXTRA_TEXT, text_message.getText().toString() + "\nReply To: " + text_email.getText().toString());
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Send Mail Using :"));
            }
        });
    }
}
