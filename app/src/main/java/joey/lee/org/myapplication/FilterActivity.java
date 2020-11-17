package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    private Button btn_apply;

    private Spinner spinner_brand;
    private Spinner spinner_card;
    private Spinner spinner_type;
    private Spinner spinner_availability;

    private SeekBar slide1;
    private SeekBar slide2;

    private TextView tv_slide1;
    private TextView tv_slide2;

    private static String brand;
    private static String card;
    private static String type;
    private static String availability;
    private static String kwh;

    private double maxPrice;
    private double minKwh;

    public String getBrand() {
        return brand;
    }

    public String getCard() {
        return card;
    }

    public String getType() {
        return type;
    }

    public String getAvailability() {
        return availability;
    }

    public static String getKwh() {
        return kwh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // Dropdown menu memberships, list created in values->strings
        spinner_brand = findViewById(R.id.spinner_brand);
        ArrayAdapter<String> adapter_brand = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.brands));
        adapter_brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_brand.setAdapter(adapter_brand);

        spinner_card = findViewById(R.id.spinner_card);
        ArrayAdapter<String> adapter_card = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.cards));
        adapter_card.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_card.setAdapter(adapter_card);

        spinner_type = findViewById(R.id.spinner_type);
        ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.plugs));
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_type);

        spinner_availability = findViewById(R.id.spinner_availability);
        ArrayAdapter<String> adapter_availability = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.availabilities));
        adapter_availability.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_availability.setAdapter(adapter_availability);


        slide1 = findViewById(R.id.slide1);
        slide2 = findViewById(R.id.slide2);

        tv_slide1 = findViewById(R.id.tv_slide1);
        tv_slide2 = findViewById(R.id.tv_slide2);

        double step1 = 0.01;
        int max1 = 1;
        int min1 = 0;

        slide1.setMax(100);

        slide1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxPrice = ((double)((int)((min1 + (progress * step1))*100.0)))/100.0;
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                tv_slide1.setText("max: " + maxPrice);
                tv_slide1.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        int step2 = 1;
        int max2 = 250;
        int min2 = 0;

        slide2.setMax(250);

        slide2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minKwh = 0 + (progress * step2);
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                tv_slide2.setText("min: " + minKwh);
                minKwh = ((double)((int)(minKwh)*10.0))/10.0;
                kwh = String.valueOf(minKwh);
                tv_slide2.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        btn_apply = findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //GoogleMapFilter googleMapFilter = new GoogleMapFilter();
                //googleMapFilter.setRestart(0);

                brand = spinner_brand.getSelectedItem().toString();
                card = spinner_card.getSelectedItem().toString();
                type = spinner_type.getSelectedItem().toString();
                availability = spinner_availability.getSelectedItem().toString();

                Intent intent = new Intent(FilterActivity.this, GoogleMapFilter.class);
                startActivity(intent);
            }
        });
    }
}