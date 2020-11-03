package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.here.sdk.core.Anchor2D;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.Metadata;
import com.here.sdk.core.Point2D;
import com.here.sdk.engine.OptionsReader;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;
import com.here.sdk.gestures.TapListener;
import com.here.sdk.mapview.MapViewBase;
import com.here.sdk.mapview.PickMapItemsResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Activity7 extends AppCompatActivity {

    private MapView mapView;
    private MapViewPin mapViewPin;
    private MapMarker mapMarker;

    private Button btn_charge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7);

        // create mapview instance
        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        btn_charge = findViewById(R.id.btn_charge);
        btn_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // test charging stations
                addMarker(view, "MVV Energie AG1", "Type 2", "50kW", "1/3", 49.494726f, 8.467645f);
                addMarker(view, "MVV Energie AG2", "Type 2", "50kW", "1/3", 49.483326f, 8.460645f);
                addMarker(view, "MVV Energie AG3", "Type 2", "50kW", "1/3", 49.472226f, 8.472645f);
                addMarker(view, "MVV Energie AG4", "Type 2", "50kW", "1/3", 49.463726f, 8.477645f);
                addMarker(view, "MVV Energie AG5", "Type 2", "50kW", "1/3", 49.494526f, 8.461645f);
                addMarker(view, "MVV Energie AG6", "Type 2", "50kW", "1/3", 49.484426f, 8.477645f);
                addMarker(view, "MVV Energie AG7", "Type 2", "50kW", "1/3", 49.474226f, 8.463645f);
                addMarker(view, "MVV Energie AG8", "Type 2", "50kW", "1/3", 49.464026f, 8.464645f);
                //addPin(v);
            }
        });

        // ASK FOR PERMISSION with permissionrequest class

        loadMapScene();
        setTapGestureHandler();
    }



    private void loadMapScene() {
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, mapError -> {
            if(mapError == null) {
                mapView.getCamera().lookAt(new GeoCoordinates(49.485511, 8.465826), 8000);
                //mapViewPin = new MapViewPin(Activity7.this, mapView);
            } else {
                // log error
            }
        });
    }

    public void addMarker(View view, String name, String type, String speed, String capacity, float latitude, float longitude) {

        MapImage mapImage = MapImageFactory.fromResource(this.getResources(), R.drawable.charging_station_32px);
        Anchor2D anchor2D = new Anchor2D(0.5f,1.0f);
        Metadata meta = new Metadata();
        meta.setString("name", name);
        meta.setString("plug", type);
        meta.setString("charging_speed", speed);
        meta.setString("capacity", capacity);

        mapMarker = new MapMarker(new GeoCoordinates(latitude, longitude), mapImage, anchor2D);
        mapMarker.setMetadata(meta);
        mapView.getMapScene().addMapMarker(mapMarker);
    }


    public void addPin(View view) {
        TextView textView = new TextView(getApplicationContext());
        textView.setBackgroundColor(android.graphics.Color.parseColor("#000000f000"));
        textView.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        textView.setText("information");
        textView.setTextSize(20);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundResource(R.color.white);
        linearLayout.setPadding(10,10,10,10);
        linearLayout.addView(textView);

        mapView.pinView(linearLayout, new GeoCoordinates(49.481162, 8.460113));

    }

    public void setTapGestureHandler() {
        mapView.getGestures().setTapListener(new TapListener() {
            @Override
            public void onTap(@NonNull Point2D touchPoint) {
                mapView.pickMapItems(touchPoint, 2, new MapViewBase.PickMapItemsCallback() {
                    @Override
                    public void onPickMapItems(@Nullable PickMapItemsResult pickMapItemsResult) {
                        List<MapMarker> mapMarkerList = pickMapItemsResult.getMarkers();
                        MapMarker pickedMarker = mapMarkerList.get(0);
                        Metadata meta = pickedMarker.getMetadata();
                        if(meta != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), meta.getString("charging_speed"), Toast.LENGTH_SHORT);
                            toast.show();

                            TextView textView = new TextView(getApplicationContext());
                            textView.setBackgroundColor(android.graphics.Color.parseColor("#000000"));
                            textView.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                            textView.setText(meta.getString("name") + "\n" + meta.getString("plug") + "\n"  + meta.getString("charging_speed") + "\n"
                                    + meta.getString("capacity") + "\n");
                            textView.setTextSize(20);

                            LinearLayout linearLayout = new LinearLayout(Activity7.this);
                            linearLayout.setBackgroundResource(R.color.white);
                            linearLayout.setPadding(10,10,10,10);
                            linearLayout.addView(textView);

                            MapView.ViewPin viewPin = mapView.pinView(linearLayout, new GeoCoordinates(pickedMarker.getCoordinates().latitude, pickedMarker.getCoordinates().longitude));
                            //viewPin.setAnchorPoint(new Anchor2D(1, 0.5f));

                        }
                    }
                });
            }
        });
    }

    public void defaultButtonClicked(View view) {
        mapViewPin.showMapViewPin();
    }

    public void anchoredButtonClicked(View view) {
        mapViewPin.showAnchoredMapViewPin();
    }

    public void clearMapButtonClicked(View view) {
        mapViewPin.clearMap();
    }

    public void clearMap() {
        // clear markers and pins
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
