package joey.lee.org.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class GoogleMap_Free extends FragmentActivity implements OnMapReadyCallback {

    com.google.android.gms.maps.GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    private SearchView google_search;

    private RelativeLayout layout;

    private MarkerOptions options = new MarkerOptions();
    private Marker mAndrena;
    private Marker searchMarker;
    private Marker longMarker;
    private Marker locationMarker;

    private AdView ad_View2;

    private Location location;

    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_distance;
    private TextView tv_consumption;
    private TextView tv_price;
    private TextView tv_stations;
    private TextView tv_network;
    private TextView tv_id;

    private ImageView view_battery;

    private Button btn_back;
    private Button btn_location;
    private Button btn_search;
    private Button btn_recommend;
    private Button btn_website;

    private Button btn_battery;
    private Button btn_settings;
    private Button btn_energy;
    private Button btn_dashboard;

    private String url_marker = "";
    private String userID;
    private String tagMarker;

    private String[] infos = new String[20000];
    private  String[] ids = new String[20000];

    private static int re = 0;
    private int i;
    private static int count;

    private static int restart;

    // Authentication Server and Database
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();
    private ArrayList<String> networks = new ArrayList<>();
    private ArrayList<String> ids_tv = new ArrayList<>();

    private ArrayList<ArrayList<String>> stations = new ArrayList<ArrayList<String>>();

    private JSONActivity jsonActivity;

    public static void setRestart(int restart) {
        GoogleMap_Free.restart = restart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map__free);

        //ad version start ----------------

        //Banner1 ca-app-pub-5507911385347863/9227311693
        //Banner2 ca-app-pub-5507911385347863/9184253127
        //Interstitial ca-app-pub-5507911385347863/7610800082

        //Test Banner ca-app-pub-3940256099942544/6300978111
        //Test Interstitial ca-app-pub-3940256099942544/1033173712


        List<String> testDeviceIds = Arrays.asList("7E7D0E82B6D512E78E70E56DC3DF0923");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        ad_View2 = findViewById(R.id.ad_view2);

        AdRequest adRequest = new AdRequest.Builder().build();
        adRequest.isTestDevice(this);
        ad_View2.loadAd(adRequest);


        // Ad version end ----------------

        Log.d("START", ""+restart);
        if(restart==0) {

            finish();

            Log.d("RECREATE", "d");
            restart++;
        }

        Log.d("START", ""+restart);

        // Instance of all elements
        layout = findViewById(R.id.layout);

        btn_back = findViewById(R.id.btn_back);
        btn_location = findViewById(R.id.btn_location);
        btn_website = findViewById(R.id.btn_website);
        btn_search = findViewById(R.id.btn_search);
        btn_recommend = findViewById(R.id.btn_recommend);

        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_network = findViewById(R.id.tv_network);
        tv_id = findViewById(R.id.tv_id);
        tv_distance = findViewById(R.id.tv_distance);
        tv_consumption = findViewById(R.id.tv_consumption);
        tv_price = findViewById(R.id.tv_price);
        tv_stations = findViewById(R.id.tv_stations);
        tv_stations.setMovementMethod(new ScrollingMovementMethod());
        tv_network.setMovementMethod(new ScrollingMovementMethod());

        view_battery = findViewById(R.id.view_battery);

        google_search = findViewById(R.id.google_search);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Button functions

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                finish();
            }
        });

        btn_energy = findViewById(R.id.btn_energy);
        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoogleMap_Free.this, EnergyActivity_Free.class);
                startActivity(intent);
            }
        });

        btn_battery = findViewById(R.id.btn_battery);
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoogleMap_Free.this, BatteryActivity_Free.class);
                startActivity(intent);
            }
        });

        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoogleMap_Free.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoogleMap_Free.this, DashboardActivity_Free.class);
                startActivity(intent);
            }
        });


        // Current Location
        btn_location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(GoogleMap_Free.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(GoogleMap_Free.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


        // google search bar
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        google_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = google_search.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(GoogleMap_Free.this);
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(location));
                    String search = "search";
                    searchMarker = map.addMarker(new MarkerOptions().position(latLng));
                    marker.setTag(search);
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                    map.setPadding(0,0,0,300);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    // -----------------------------------------------------------------------------------------

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        map = googleMap;

        // fixed Andrena marker
        LatLng andrena = new LatLng(49.475365, 8.508518);
        mAndrena = map.addMarker(new MarkerOptions().position(andrena).title("andrena objects"));
        map.moveCamera(CameraUpdateFactory.newLatLng(andrena));
        mAndrena.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.suitcase));
        mAndrena.setSnippet("Experts in ASE | andrena.de");
        mAndrena.setTag("andrena");
        addMarkerToMap(49.48750130753848, 8.466373962457908);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.4883, 8.4647), 8.0f));
        map.setPadding(0,0,0,300);

        // clicking on any marker
        map.setOnMarkerClickListener(new com.google.android.gms.maps.GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //------------------------------------------------andrena marker----------------------------------------------
                if(marker.getTag().equals("andrena")) {

                    tv_name.setText("");
                    tv_address.setText("");
                    tv_price.setText("");
                    tv_stations.setText("");
                    tv_network.setText("");
                    tv_id.setText("");
                    btn_website.setText("");

                    layout.setBackgroundResource(0);
                    view_battery.setBackgroundResource(0);

                    map.setPadding(0,0,0,300);

                    url_marker = "https://www.andrena.de/";
                    tagMarker = "no";

                    searchMarker = map.addMarker(new MarkerOptions().position(marker.getPosition()));



                    // --------------------------------long + search marker------------------------------------------------
                } else if(marker.getTag().equals("long") || marker.getTag().equals("search")) {

                    map.setPadding(0,0,0,890);

                    layout.setBackgroundResource(R.drawable.map);
                    btn_website.setText("");
                    searchMarker = map.addMarker(new MarkerOptions().position(marker.getPosition()));


                    if(location != null) {
                        Location des = new Location(location);
                        des.setLatitude(marker.getPosition().latitude);
                        des.setLongitude(marker.getPosition().longitude);

                        // Tesla 3 consumption + battery capacity

                        float distance = location.distanceTo(des) / 1000;
                        double dis = ((double) ((int) (distance * 100.0))) / 100.0;
                        double consumption = ((double) ((int) ((0.143 * distance) * 100.0))) / 100.0;
                        double arrival_kwh = ((double) ((int) ((75 - (0.143 * distance)) * 100.0))) / 100.0;
                        double arrival_per = ((double) ((int) ((((75 - (0.143 * distance)) / 75) * 100) * 100.0))) / 100.0;

                        String s1 = dis + "km";
                        String s2 = "Energy Consumption: " + consumption + " kWh \n";
                        String s3 = "Battery at Arrival: " + arrival_kwh + " kWh = " + arrival_per + "% \n";

                        tv_distance.setText(s1);
                        tv_consumption.setText(s2 +s3);

                        if(arrival_per >= 70) {
                            view_battery.setBackgroundResource(R.drawable.battery_full);
                        } else if(arrival_per >= 30) {
                            view_battery.setBackgroundResource(R.drawable.battery_middle);
                        } else {
                            view_battery.setBackgroundResource(R.drawable.battery_low);
                        }

                        tv_name.setText("Selected Position");
                        tv_address.setText("Lat: " + marker.getPosition().latitude + ", Lng: "+ marker.getPosition().longitude);
                        tv_price.setText("");
                        tv_stations.setText("");
                        tv_network.setText("");
                        tv_id.setText("");

                    } else {

                        tv_name.setText("Selected Position");
                        tv_address.setText("Lat: " + marker.getPosition().latitude + ", Lng: "+ marker.getPosition().longitude);
                        tv_price.setText("");
                        tv_stations.setText("");

                        tv_network.setText("");
                        tv_id.setText("");

                        view_battery.setBackgroundResource(0);
                    }

                    url_marker = null;
                    tagMarker = "no";



                    //----------------------------------------------------charging station-------------------------------------------------
                } else if(marker.getTag().equals("loc")) {
                } else {

                    if(location != null) {
                        Location des = new Location(location);
                        des.setLatitude(marker.getPosition().latitude);
                        des.setLongitude(marker.getPosition().longitude);

                        // Tesla 3 consumption + battery capacity

                        float distance = location.distanceTo(des) / 1000;
                        double dis = ((double)((int)(distance*100.0)))/100.0;
                        double consumption = ((double)((int)((0.143 * distance)*100.0)))/100.0;
                        double arrival_kwh = ((double)((int)((75 -(0.143 * distance))*100.0)))/100.0; ;
                        double arrival_per = ((double)((int)((((75 -(0.143 * distance)) / 75 )*100)*100.0)))/100.0; ;

                        String s1 = dis + "km";
                        String s2 = "Energy Consumption: " + consumption + " kWh \n";
                        String s3 = "Battery at Arrival: " +  arrival_kwh + " kWh = " + arrival_per + "% \n";

                        tv_distance.setText(s1);
                        tv_consumption.setText(s2 +s3);

                        if(arrival_per >= 70) {
                            view_battery.setBackgroundResource(R.drawable.battery_full);
                        } else if(arrival_per >= 30) {
                            view_battery.setBackgroundResource(R.drawable.battery_middle);
                        } else {
                            view_battery.setBackgroundResource(R.drawable.battery_low);
                        }

                        tv_name.setText(names.get((int) marker.getTag()));
                        tv_address.setText(address.get((int) marker.getTag()));
                        // tv_price.setText(prices.get((int) marker.getTag()) + "???");
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i<stations.get((Integer) marker.getTag()).size(); i++) {
                            String s = String.valueOf(stations.get((Integer) marker.getTag()).get(i));
                            sb.append(s);
                        }

                        tv_stations.setText("Available: 1" +sb.toString());

                        if(networks.get((int) marker.getTag()).equals("false")) {
                            tv_network.setText("N/A");
                        } else {
                            tv_network.setText(networks.get((int) marker.getTag()));
                        }

                        tv_id.setText("ID: " + ids_tv.get((int) marker.getTag()));

                        // for favorites
                        tagMarker = String.valueOf(marker.getTag());

                    } else {

                        tv_name.setText(names.get((int) marker.getTag()));
                        tv_address.setText(address.get((int) marker.getTag()));
                        // tv_price.setText(prices.get((int) marker.getTag()) + "???");
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i<stations.get((Integer) marker.getTag()).size(); i++) {
                            String s = String.valueOf(stations.get((Integer) marker.getTag()).get(i));
                            sb.append(s);
                        }
                        tv_stations.setText("Available: 1" +sb.toString());
                        tv_network.setText(networks.get((int) marker.getTag()));
                        tv_id.setText("ID: " + ids_tv.get((int) marker.getTag()));

                        // for favorites
                        tagMarker = String.valueOf(marker.getTag());

                        if(networks.get((int) marker.getTag()).equals("false")) {
                            tv_network.setText("N/A");
                        } else {
                            tv_network.setText(networks.get((int) marker.getTag()));
                        }

                        view_battery.setBackgroundResource(0);

                    }

                    // prices
                    String lng = String.valueOf(marker.getPosition().longitude);
                    String lat = String.valueOf(marker.getPosition().latitude);
                    String network = networks.get((int) marker.getTag());
                    if(network.equals("false")) {
                        network = "EnBW";
                    }



                    String s2 =  "https://api.chargeprice.app/v1/charge_prices";
                    ArrayList<ArrayList<String>> listOfPrices;
                    jsonActivity = new JSONActivity();
                    jsonActivity.setJsonInputString(lng, lat, network);
                    Log.d("URL", jsonActivity.getJsonInputString());
                    jsonActivity.sendRequestWithHttpURLConnectionTwo2(s2);

                    listOfPrices = jsonActivity.getListOfPrices();
                    tv_price.setText(listOfPrices.get(0).get(1) + "???");

                    url_marker = urls.get((Integer) marker.getTag());
                    btn_website.setText("Website");

                    layout.setBackgroundResource(R.drawable.map);

                    map.setPadding(0,0,0,890);
                }


                map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15), 5000, null);
                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomIn(),2000, null);
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);

                return false;
            }
        });


        // ---------------long marker------------------------
        map.setOnMapLongClickListener(new com.google.android.gms.maps.GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                longMarker =  map.addMarker(new MarkerOptions()
                        .position(point)
                        .title("Selected Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                longMarker.setTag("long");
            }
        });

        // --------------------------search nearby stations of selected markers-------------------------------------
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchMarker != null) {
                    addMarkerToMap2(searchMarker.getPosition().latitude, searchMarker.getPosition().longitude);
                } else {
                    Toast.makeText(GoogleMap_Free.this,"Select Location", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // ----------------------------recommend nearby stations, fastest/nearest
        btn_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchMarker != null) {
                    if(count%2 == 0) {
                        addMarkerToMap3(searchMarker.getPosition().latitude, searchMarker.getPosition().longitude);
                        count++;
                    } else {
                        addMarkerToMap4(searchMarker.getPosition().latitude, searchMarker.getPosition().longitude);
                        count++;
                    }

                } else {
                    Toast.makeText(GoogleMap_Free.this,"Select Location", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //-----------------------------------go to website of station-----------------------------------
        btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!url_marker.equals(null)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url_marker));
                    startActivity(intent);
                }
            }
        });

        //---------------------------clicking on small info window, not used anymore-----------------------------------
        map.setOnInfoWindowClickListener(new com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if(marker.getTag().equals("andrena") && !(marker.getTag().equals("long") || marker.getTag().equals("search")))  {
                    String url = "https://www.andrena.de/";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } else if((marker.getTag().equals("long") || marker.getTag().equals("search"))) {

                } else {
                    String id = ids[(int) marker.getTag()];
                    String url = "https://www.chargeprice.app/?poi_id=" + id + "&poi_source=going_electric";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);

                }
            }
        });
    }

    // ----------------------------------get location------------------------------------------------
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                location = task.getResult();
                if(location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(GoogleMap_Free.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        LatLng loc = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                        locationMarker = map.addMarker(new MarkerOptions().position(loc));
                        map.moveCamera(CameraUpdateFactory.newLatLng(loc));
                        locationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
                        String tag = "loc";
                        locationMarker.setTag(tag);

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }





    public void addMarkerToMap(double lat1, double lng1) {

        String lat = String.valueOf(lat1);
        lat.replace(".", ",");

        String lng = String.valueOf(lng1);
        lng.replace(".", ",");

        String s = "https://api.goingelectric.de/chargepoints/?countries=DE&lat=" + lat + "&lng=" + lng + "&radius=50&verified=true&exclude_faults=true";
        String s2 =  "https://api.chargeprice.app/v1/charge_prices";

        jsonActivity = new JSONActivity();
        jsonActivity.sendRequestWithHttpURLConnectionTwo(s);
        jsonActivity.sendRequestWithHttpURLConnectionTwo2(s2);

        ArrayList<LatLng> latLngs = jsonActivity.getLatLngs();
        ArrayList<ArrayList<String>> listOfStations = jsonActivity.getListOfStations();




        //  int startkey = 100;
        double startkey = jsonActivity.getStartkey();
        Log.d("startkey", startkey + "");

        //jsonapi = new JSON_API();
        //jsonapi.sendRequestWithHttpURLConnectionTwo();
        //ArrayList<LatLng> latLngs = jsonapi.getLatLngs();
        //ArrayList<ArrayList<String>> listOfStations = jsonapi.getListOfStations();


        Iterator<LatLng> iter = latLngs.iterator();
        ArrayList<ArrayList<String>> listOfPrices;

        try {
            while (iter.hasNext()) {
                LatLng marker = iter.next();

                String l1 = String.valueOf(marker.longitude);
                String l2 = String.valueOf(marker.latitude);;
                /*
                jsonActivity.setJsonInputString(l1, l2);
                //Log.d("URL", jsonActivity.getJsonInputString());
                jsonActivity.sendRequestWithHttpURLConnectionTwo2(s2);
                listOfPrices = jsonActivity.getListOfPrices();

                 */



                options.position(marker);
                options.title(listOfStations.get(i).get(1));
                StringBuilder info = new StringBuilder();
                info.append(
                        "ID: " + listOfStations.get(i).get(0) + "\n" +
                                //"Name: " + listOfStations.get(i).get(1) + "\n" +
                                "Network: " + listOfStations.get(i).get(2) + "\n" +
                                //"URL : " + listOfStations.get(i).get(3) + "\n" +
                                //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                                //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                                "Address: " +
                                listOfStations.get(i).get(9) + "\n" + "                 " + listOfStations.get(i).get(8) + ", " + listOfStations.get(i).get(6) + "\n" +
                                //"Price: " + listOfPrices.get(0).get(1) + "\n" + // random provider..
                                "Charger: " + listOfStations.get(i).get(10) + " | " + listOfStations.get(i).get(11) + "kWh, Count: " + listOfStations.get(i).get(12));

                //Log.d("marker", "size " + listOfStations.get(i).size());

                if(listOfStations.get(i).size() > 13) {
                    info. append("\n" + "                " + listOfStations.get(i).get(13) + " | " + listOfStations.get(i).get(14) + ", Count: " + listOfStations.get(i).get(15) + ", Price: "); // + listOfPrices.get(0).get(2));
                }
                if(listOfStations.get(i).size() > 16) {
                    info. append("\n" + "                " + listOfStations.get(i).get(16) + " | " + listOfStations.get(i).get(17) + ", Count: " + listOfStations.get(i).get(18) + ", Price: "); //  + listOfPrices.get(0).get(3));
                }
                if(listOfStations.get(i).size() > 19) {
                    info. append("\n" +"                " + listOfStations.get(i).get(19) + " | " + listOfStations.get(i).get(20) + ", Count: " + listOfStations.get(i).get(21) + ", Price: "); // + listOfPrices.get(0).get(4));
                }
                if(listOfStations.get(i).size() > 22) {
                    info. append("\n" + "                " + listOfStations.get(i).get(22) + " | " + listOfStations.get(i).get(23) + ", Count: " + listOfStations.get(i).get(24) + ", Price: "); //  + listOfPrices.get(0).get(5));
                }
                if(listOfStations.get(i).size() > 25) {
                    info. append("\n" + "                " + listOfStations.get(i).get(25) + " | " + listOfStations.get(i).get(26) + ", Count: " + listOfStations.get(i).get(27) + ", Price: "); //  + listOfPrices.get(0).get(6));
                }
                if(listOfStations.get(i).size() > 28) {
                    info. append("\n" + "                " + listOfStations.get(i).get(28) + " | " + listOfStations.get(i).get(29) + ", Count: " + listOfStations.get(i).get(30) + ", Price: "); //  + listOfPrices.get(0).get(7));
                }

                ids[i] = listOfStations.get(i).get(0);

                infos[i] = info.toString();
                options.snippet(listOfStations.get(i).get(9) + " | " + listOfStations.get(i).get(6));
                Marker m = map.addMarker(options);
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                m.setTag(i);

                names.add(listOfStations.get(i).get(1));
                urls.add("https://" + listOfStations.get(i).get(3));
                address.add(listOfStations.get(i).get(9) + " | " + listOfStations.get(i).get(8) + ", " + listOfStations.get(i).get(6));
                networks.add(listOfStations.get(i).get(2));
                ids_tv.add(listOfStations.get(i).get(0));



                ArrayList<String> station = new ArrayList<>();

                station.add(listOfStations.get(i).get(10) + " | " + listOfStations.get(i).get(11) + "kWh, Count: " + listOfStations.get(i).get(12));
                if(listOfStations.get(i).size() > 13) {
                    station.add("\n" + listOfStations.get(i).get(13) + " | " + listOfStations.get(i).get(14) + ", Count: " + listOfStations.get(i).get(15)); // + listOfPrices.get(0).get(2));
                }
                if(listOfStations.get(i).size() > 16) {
                    station.add("\n" + listOfStations.get(i).get(16) + " | " + listOfStations.get(i).get(17) + ", Count: " + listOfStations.get(i).get(18)); //  + listOfPrices.get(0).get(3));
                }
                if(listOfStations.get(i).size() > 19) {
                    station.add("\n" + listOfStations.get(i).get(19) + " | " + listOfStations.get(i).get(20) + ", Count: " + listOfStations.get(i).get(21)); // + listOfPrices.get(0).get(4));
                }
                if(listOfStations.get(i).size() > 22) {
                    station.add("\n" + listOfStations.get(i).get(22) + " | " + listOfStations.get(i).get(23) + ", Count: " + listOfStations.get(i).get(24)); //  + listOfPrices.get(0).get(5));
                }
                if(listOfStations.get(i).size() > 25) {
                    station.add("\n" + listOfStations.get(i).get(25) + " | " + listOfStations.get(i).get(26) + ", Count: " + listOfStations.get(i).get(27)); //  + listOfPrices.get(0).get(6));
                }
                if(listOfStations.get(i).size() > 28) {
                    station.add("\n" + listOfStations.get(i).get(28) + " | " + listOfStations.get(i).get(29) + ", Count: " + listOfStations.get(i).get(30)); //  + listOfPrices.get(0).get(7));
                }

                stations.add(station);


                i++;
                //Log.d("count", ""+ i);

            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        if(startkey == 500) {

            Log.d("IF CLAUSE", "START");
            String url = s + "&startkey=" + startkey;
            jsonActivity.sendRequestWithHttpURLConnectionTwo(url);



            latLngs = jsonActivity.getLatLngs();
            listOfStations = jsonActivity.getListOfStations();

            startkey = jsonActivity.getStartkey();
            int j = 0;
            Iterator<LatLng> iter1 = latLngs.iterator();

            try {
                while (iter1.hasNext()) {
                    LatLng marker = iter1.next();
                    options.position(marker);

                    String l1 = String.valueOf(marker.longitude);
                    String l2 = String.valueOf(marker.latitude);;
                    //jsonActivity.setJsonInputString(l1, l2);
                    //Log.d("URL", jsonActivity.getJsonInputString());
                    //jsonActivity.sendRequestWithHttpURLConnectionTwo2(s2);
                    listOfPrices = jsonActivity.getListOfPrices();

                    options.title(listOfStations.get(j).get(1));
                    StringBuilder info = new StringBuilder();
                    info.append(
                            "ID: " + listOfStations.get(j).get(0) + "\n" +
                                    //"Name: " + listOfStations.get(i).get(1) + "\n" +
                                    "Network: " + listOfStations.get(j).get(2) + "\n" +
                                    //"URL : " + listOfStations.get(i).get(3) + "\n" +
                                    //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                                    //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                                    "Address: " +
                                    listOfStations.get(j).get(9) + "\n" + "                 " + listOfStations.get(j).get(8) + ", " + listOfStations.get(j).get(6) + "\n" +
                                    "Charger: " + listOfStations.get(j).get(10) + " | " + listOfStations.get(j).get(11) + ", Count: " + listOfStations.get(j).get(12));

                    //Log.d("marker", "size " + listOfStations.get(i).size());

                    if(listOfStations.get(j).size() > 13) {
                        info. append("\n" + "                " + listOfStations.get(j).get(13) + " | " + listOfStations.get(j).get(14) + ", Count: " + listOfStations.get(j).get(15));
                    }
                    if(listOfStations.get(j).size() > 16) {
                        info. append("\n" + "                " + listOfStations.get(j).get(16) + " | " + listOfStations.get(j).get(17) + ", Count: " + listOfStations.get(j).get(18));
                    }
                    if(listOfStations.get(j).size() > 19) {
                        info. append("\n" +"                " + listOfStations.get(j).get(19) + " | " + listOfStations.get(j).get(20) + ", Count: " + listOfStations.get(j).get(21));
                    }
                    if(listOfStations.get(j).size() > 22) {
                        info. append("\n" + "                " + listOfStations.get(j).get(22) + " | " + listOfStations.get(j).get(23) + ", Count: " + listOfStations.get(j).get(24));
                    }
                    if(listOfStations.get(j).size() > 25) {
                        info. append("\n" + "                " + listOfStations.get(j).get(25) + " | " + listOfStations.get(j).get(26) + ", Count: " + listOfStations.get(j).get(27));
                    }
                    if(listOfStations.get(j).size() > 28) {
                        info. append("\n" + "                " + listOfStations.get(j).get(28) + " | " + listOfStations.get(j).get(29) + ", Count: " + listOfStations.get(j).get(30));
                    }

                    ids[i] = listOfStations.get(j).get(0);

                    infos[i] = info.toString();
                    options.snippet(listOfStations.get(j).get(9) + " | " + listOfStations.get(j).get(6));
                    Marker m = map.addMarker(options);
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                    m.setTag(i);

                    names.add(listOfStations.get(j).get(1));
                    urls.add("https://" + listOfStations.get(j).get(3));
                    address.add(listOfStations.get(j).get(9) + " | " + listOfStations.get(j).get(8) + ", " + listOfStations.get(j).get(6));
                    networks.add(listOfStations.get(j).get(2));
                    ids_tv.add(listOfStations.get(j).get(0));

                    ArrayList<String> station = new ArrayList<>();
                    station.add(listOfStations.get(j).get(10) + " | " + listOfStations.get(j).get(11) + ", Count: " + listOfStations.get(j).get(12));

                    if(listOfStations.get(j).size() > 13) {
                        station.add("\n" + listOfStations.get(j).get(13) + " | " + listOfStations.get(j).get(14) + ", Count: " + listOfStations.get(j).get(15)); // + listOfPrices.get(0).get(2));
                    }
                    if(listOfStations.get(j).size() > 16) {
                        station.add("\n" + listOfStations.get(j).get(16) + " | " + listOfStations.get(j).get(17) + ", Count: " + listOfStations.get(j).get(18)); //  + listOfPrices.get(0).get(3));
                    }
                    if(listOfStations.get(j).size() > 19) {
                        station.add("\n" + listOfStations.get(j).get(19) + " | " + listOfStations.get(j).get(20) + ", Count: " + listOfStations.get(j).get(21)); // + listOfPrices.get(0).get(4));
                    }
                    if(listOfStations.get(j).size() > 22) {
                        station.add("\n" + listOfStations.get(j).get(22) + " | " + listOfStations.get(j).get(23) + ", Count: " + listOfStations.get(j).get(24)); //  + listOfPrices.get(0).get(5));
                    }
                    if(listOfStations.get(j).size() > 25) {
                        station.add("\n" + listOfStations.get(j).get(25) + " | " + listOfStations.get(j).get(26) + ", Count: " + listOfStations.get(j).get(27)); //  + listOfPrices.get(0).get(6));
                    }
                    if(listOfStations.get(j).size() > 28) {
                        station.add("\n" + listOfStations.get(j).get(28) + " | " + listOfStations.get(j).get(29) + ", Count: " + listOfStations.get(j).get(30)); //  + listOfPrices.get(0).get(7));
                    }

                    stations.add(station);

                    i++;
                    j++;
                    // Log.d("count2222", ""+ i);

                }


            } catch(Exception e) {
                e.printStackTrace();
            }
        }


        /*
        try {
            for(LatLng marker : latLngs) {
                options.position(marker);
                options.title(listOfStations.get(i).get(1));
                StringBuilder info = new StringBuilder();
                info.append(
                        "ID: " + listOfStations.get(i).get(0) + "\n" +
                        //"Name: " + listOfStations.get(i).get(1) + "\n" +
                        "Network: " + listOfStations.get(i).get(2) + "\n" +
                        //"URL : " + listOfStations.get(i).get(3) + "\n" +
                        //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                        //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                        "Address: " +
                        listOfStations.get(i).get(9) + "\n" + "                 " + listOfStations.get(i).get(8) + ", " + listOfStations.get(i).get(6) + "\n" +
                        "Charger: " + listOfStations.get(i).get(10) + " | " + listOfStations.get(i).get(11) + ", Count: " + listOfStations.get(i).get(12));

                //Log.d("marker", "size " + listOfStations.get(i).size());

                if(listOfStations.get(i).size() > 13) {
                    info. append("\n" + "                " + listOfStations.get(i).get(13) + " | " + listOfStations.get(i).get(14) + ", Count: " + listOfStations.get(i).get(15));
                }
                if(listOfStations.get(i).size() > 16) {
                    info. append("\n" + "                " + listOfStations.get(i).get(16) + " | " + listOfStations.get(i).get(17) + ", Count: " + listOfStations.get(i).get(18));
                }
                if(listOfStations.get(i).size() > 19) {
                    info. append("\n" +"                " + listOfStations.get(i).get(19) + " | " + listOfStations.get(i).get(20) + ", Count: " + listOfStations.get(i).get(21));
                }
                if(listOfStations.get(i).size() > 22) {
                    info. append("\n" + "                " + listOfStations.get(i).get(22) + " | " + listOfStations.get(i).get(23) + ", Count: " + listOfStations.get(i).get(24));
                }
                if(listOfStations.get(i).size() > 25) {
                    info. append("\n" + "                " + listOfStations.get(i).get(25) + " | " + listOfStations.get(i).get(26) + ", Count: " + listOfStations.get(i).get(27));
                }
                if(listOfStations.get(i).size() > 28) {
                    info. append("\n" + "                " + listOfStations.get(i).get(28) + " | " + listOfStations.get(i).get(29) + ", Count: " + listOfStations.get(i).get(30));
                }

                ids[i] = listOfStations.get(i).get(0);

                infos[i] = info.toString();
                options.snippet(listOfStations.get(i).get(9) + " | " + listOfStations.get(i).get(6));
                Marker m = map.addMarker(options);
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                m.setTag(i);

                names.add(listOfStations.get(i).get(1));
                urls.add("https://" + listOfStations.get(i).get(3));

                i++;
            }


        } catch(Exception e) {
            e.printStackTrace();
            }

         */


    }

    public void addMarkerToMap2(double lat1, double lng1) {


        String lat = String.valueOf(lat1);
        lat.replace(".", ",");

        String lng = String.valueOf(lng1);
        lng.replace(".", ",");

        String s = "https://api.goingelectric.de/chargepoints/?countries=DE&lat=" + lat + "&lng=" + lng + "&radius=15&verified=true&exclude_faults=true";


        jsonActivity = new JSONActivity();
        jsonActivity.sendRequestWithHttpURLConnectionTwo(s);


        ArrayList<LatLng> latLngs = jsonActivity.getLatLngs();
        ArrayList<ArrayList<String>> listOfStations = jsonActivity.getListOfStations();

        //jsonapi = new JSON_API();
        //jsonapi.sendRequestWithHttpURLConnectionTwo();
        //ArrayList<LatLng> latLngs = jsonapi.getLatLngs();
        //ArrayList<ArrayList<String>> listOfStations = jsonapi.getListOfStations();

        int k = 0;
        Iterator<LatLng> iter = latLngs.iterator();
        ArrayList<ArrayList<String>> listOfPrices;

        try {
            while (iter.hasNext()) {
                LatLng marker = iter.next();

                options.position(marker);
                options.title(listOfStations.get(k).get(1));
                StringBuilder info = new StringBuilder();
                info.append(
                        "ID: " + listOfStations.get(k).get(0) + "\n" +
                                //"Name: " + listOfStations.get(i).get(1) + "\n" +
                                "Network: " + listOfStations.get(k).get(2) + "\n" +
                                //"URL : " + listOfStations.get(i).get(3) + "\n" +
                                //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                                //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                                "Address: " +
                                listOfStations.get(k).get(9) + "\n" + "                 " + listOfStations.get(k).get(8) + ", " + listOfStations.get(k).get(6) + "\n" +
                                "Charger: " + listOfStations.get(k).get(10) + " | " + listOfStations.get(k).get(11) + ", Count: " + listOfStations.get(k).get(12));

                //Log.d("marker", "size " + listOfStations.get(i).size());

                if (listOfStations.get(k).size() > 13) {
                    info.append("\n" + "                " + listOfStations.get(k).get(13) + " | " + listOfStations.get(k).get(14) + ", Count: " + listOfStations.get(k).get(15) + ", Price: "); // + listOfPrices.get(0).get(2));
                }
                if (listOfStations.get(k).size() > 16) {
                    info.append("\n" + "                " + listOfStations.get(k).get(16) + " | " + listOfStations.get(k).get(17) + ", Count: " + listOfStations.get(k).get(18) + ", Price: "); //  + listOfPrices.get(0).get(3));
                }
                if (listOfStations.get(k).size() > 19) {
                    info.append("\n" + "                " + listOfStations.get(k).get(19) + " | " + listOfStations.get(k).get(20) + ", Count: " + listOfStations.get(k).get(21) + ", Price: "); // + listOfPrices.get(0).get(4));
                }
                if (listOfStations.get(k).size() > 22) {
                    info.append("\n" + "                " + listOfStations.get(k).get(22) + " | " + listOfStations.get(k).get(23) + ", Count: " + listOfStations.get(k).get(24) + ", Price: "); //  + listOfPrices.get(0).get(5));
                }
                if (listOfStations.get(k).size() > 25) {
                    info.append("\n" + "                " + listOfStations.get(k).get(25) + " | " + listOfStations.get(k).get(26) + ", Count: " + listOfStations.get(k).get(27) + ", Price: "); //  + listOfPrices.get(0).get(6));
                }
                if (listOfStations.get(k).size() > 28) {
                    info.append("\n" + "                " + listOfStations.get(k).get(28) + " | " + listOfStations.get(k).get(29) + ", Count: " + listOfStations.get(k).get(30) + ", Price: "); //  + listOfPrices.get(0).get(7));
                }

                ids[i] = listOfStations.get(k).get(0);

                infos[i] = info.toString();
                options.snippet(listOfStations.get(k).get(9) + " | " + listOfStations.get(k).get(6));
                Marker m = map.addMarker(options);
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                m.setTag(i);

                names.add(listOfStations.get(k).get(1));
                urls.add("https://" + listOfStations.get(k).get(3));
                address.add(listOfStations.get(k).get(9) + " | " + listOfStations.get(k).get(8) + ", " + listOfStations.get(k).get(6));
                networks.add(listOfStations.get(k).get(2));
                ids_tv.add(listOfStations.get(k).get(0));

                ArrayList<String> station = new ArrayList<>();

                station.add(listOfStations.get(k).get(10) + " | " + listOfStations.get(k).get(11) + "kWh, Count: " + listOfStations.get(k).get(12));
                if(listOfStations.get(k).size() > 13) {
                    station.add("\n" + listOfStations.get(k).get(13) + " | " + listOfStations.get(k).get(14) + ", Count: " + listOfStations.get(k).get(15)); // + listOfPrices.get(0).get(2));
                }
                if(listOfStations.get(k).size() > 16) {
                    station.add("\n" + listOfStations.get(k).get(16) + " | " + listOfStations.get(k).get(17) + ", Count: " + listOfStations.get(k).get(18)); //  + listOfPrices.get(0).get(3));
                }
                if(listOfStations.get(k).size() > 19) {
                    station.add("\n" + listOfStations.get(k).get(19) + " | " + listOfStations.get(k).get(20) + ", Count: " + listOfStations.get(k).get(21)); // + listOfPrices.get(0).get(4));
                }
                if(listOfStations.get(k).size() > 22) {
                    station.add("\n" + listOfStations.get(k).get(22) + " | " + listOfStations.get(k).get(23) + ", Count: " + listOfStations.get(k).get(24)); //  + listOfPrices.get(0).get(5));
                }
                if(listOfStations.get(k).size() > 25) {
                    station.add("\n" + listOfStations.get(k).get(25) + " | " + listOfStations.get(k).get(26) + ", Count: " + listOfStations.get(k).get(27)); //  + listOfPrices.get(0).get(6));
                }
                if(listOfStations.get(k).size() > 28) {
                    station.add("\n" + listOfStations.get(k).get(28) + " | " + listOfStations.get(k).get(29) + ", Count: " + listOfStations.get(k).get(30)); //  + listOfPrices.get(0).get(7));
                }

                stations.add(station);

                i++;
                k++;
                //Log.d("count", ""+ i);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMarkerToMap3(double lat1, double lng1) {


        String lat = String.valueOf(lat1);
        lat.replace(".", ",");

        String lng = String.valueOf(lng1);
        lng.replace(".", ",");

        String s = "https://api.goingelectric.de/chargepoints/?countries=DE&lat=" + lat + "&lng=" + lng + "&radius=15&verified=true&exclude_faults=true&orderby=distance";

        jsonActivity = new JSONActivity();
        jsonActivity.sendRequestWithHttpURLConnectionTwo(s);

        ArrayList<LatLng> latLngs = jsonActivity.getLatLngs();
        ArrayList<ArrayList<String>> listOfStations = jsonActivity.getListOfStations();

        int l = 0;
        Iterator<LatLng> iter = latLngs.iterator();


        try {
            LatLng marker = iter.next();
            marker = iter.next();

            options.position(marker);
            //options.title(listOfStations.get(l).get(1));
            StringBuilder info = new StringBuilder();
            info.append(
                    "ID: " + listOfStations.get(l).get(0) + "\n" +
                            //"Name: " + listOfStations.get(i).get(1) + "\n" +
                            "Network: " + listOfStations.get(l).get(2) + "\n" +
                            //"URL : " + listOfStations.get(i).get(3) + "\n" +
                            //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                            //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                            "Address: " +
                            listOfStations.get(l).get(9) + "\n" + "                 " + listOfStations.get(l).get(8) + ", " + listOfStations.get(l).get(6) + "\n" +

                            "Charger: " + listOfStations.get(l).get(10) + " | " + listOfStations.get(l).get(11) + ", Count: " + listOfStations.get(l).get(12));

            //Log.d("marker", "size " + listOfStations.get(i).size());

            if (listOfStations.get(l).size() > 13) {
                info.append("\n" + "                " + listOfStations.get(l).get(13) + " | " + listOfStations.get(l).get(14) + ", Count: " + listOfStations.get(l).get(15) + ", Price: "); // + listOfPrices.get(0).get(2));
            }
            if (listOfStations.get(l).size() > 16) {
                info.append("\n" + "                " + listOfStations.get(l).get(16) + " | " + listOfStations.get(l).get(17) + ", Count: " + listOfStations.get(l).get(18) + ", Price: "); //  + listOfPrices.get(0).get(3));
            }
            if (listOfStations.get(l).size() > 19) {
                info.append("\n" + "                " + listOfStations.get(l).get(19) + " | " + listOfStations.get(l).get(20) + ", Count: " + listOfStations.get(l).get(21) + ", Price: "); // + listOfPrices.get(0).get(4));
            }
            if (listOfStations.get(l).size() > 22) {
                info.append("\n" + "                " + listOfStations.get(l).get(22) + " | " + listOfStations.get(l).get(23) + ", Count: " + listOfStations.get(l).get(24) + ", Price: "); //  + listOfPrices.get(0).get(5));
            }
            if (listOfStations.get(l).size() > 25) {
                info.append("\n" + "                " + listOfStations.get(l).get(25) + " | " + listOfStations.get(l).get(26) + ", Count: " + listOfStations.get(l).get(27) + ", Price: "); //  + listOfPrices.get(0).get(6));
            }
            if (listOfStations.get(l).size() > 28) {
                info.append("\n" + "                " + listOfStations.get(l).get(28) + " | " + listOfStations.get(l).get(29) + ", Count: " + listOfStations.get(l).get(30) + ", Price: "); //  + listOfPrices.get(0).get(7));
            }

            ids[i] = listOfStations.get(l).get(0);

            infos[i] = info.toString();
            //options.snippet(listOfStations.get(l).get(9) + " | " + listOfStations.get(l).get(6));
            options.snippet("Fastest Charging Station");
            options.title("Recommendation");
            Marker m = map.addMarker(options);
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.fast));
            m.setTag(i);

            names.add(listOfStations.get(l).get(1));
            urls.add("https://" + listOfStations.get(l).get(3));
            address.add(listOfStations.get(l).get(9) + " | " + listOfStations.get(l).get(8) + ", " + listOfStations.get(l).get(6));
            networks.add(listOfStations.get(l).get(2));
            ids_tv.add(listOfStations.get(l).get(0));

            ArrayList<String> station = new ArrayList<>();

            station.add(listOfStations.get(l).get(10) + " | " + listOfStations.get(l).get(11) + "kWh, Count: " + listOfStations.get(l).get(12));
            if(listOfStations.get(l).size() > 13) {
                station.add("\n" + listOfStations.get(l).get(13) + " | " + listOfStations.get(l).get(14) + ", Count: " + listOfStations.get(l).get(15)); // + listOfPrices.get(0).get(2));
            }
            if(listOfStations.get(l).size() > 16) {
                station.add("\n" + listOfStations.get(l).get(16) + " | " + listOfStations.get(l).get(17) + ", Count: " + listOfStations.get(l).get(18)); //  + listOfPrices.get(0).get(3));
            }
            if(listOfStations.get(l).size() > 19) {
                station.add("\n" + listOfStations.get(l).get(19) + " | " + listOfStations.get(l).get(20) + ", Count: " + listOfStations.get(l).get(21)); // + listOfPrices.get(0).get(4));
            }
            if(listOfStations.get(l).size() > 22) {
                station.add("\n" + listOfStations.get(l).get(22) + " | " + listOfStations.get(l).get(23) + ", Count: " + listOfStations.get(l).get(24)); //  + listOfPrices.get(0).get(5));
            }
            if(listOfStations.get(l).size() > 25) {
                station.add("\n" + listOfStations.get(l).get(25) + " | " + listOfStations.get(l).get(26) + ", Count: " + listOfStations.get(l).get(27)); //  + listOfPrices.get(0).get(6));
            }
            if(listOfStations.get(l).size() > 28) {
                station.add("\n" + listOfStations.get(l).get(28) + " | " + listOfStations.get(l).get(29) + ", Count: " + listOfStations.get(l).get(30)); //  + listOfPrices.get(0).get(7));
            }

            stations.add(station);

            l++;
            //Log.d("count", ""+ i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMarkerToMap4(double lat1, double lng1) {


        String lat = String.valueOf(lat1);
        lat.replace(".", ",");

        String lng = String.valueOf(lng1);
        lng.replace(".", ",");

        String s = "https://api.goingelectric.de/chargepoints/?countries=DE&lat=" + lat + "&lng=" + lng + "&radius=15&verified=true&exclude_faults=true&orderby=power";

        jsonActivity = new JSONActivity();
        jsonActivity.sendRequestWithHttpURLConnectionTwo(s);

        ArrayList<LatLng> latLngs = jsonActivity.getLatLngs();
        ArrayList<ArrayList<String>> listOfStations = jsonActivity.getListOfStations();

        int m = 0;
        Iterator<LatLng> iter = latLngs.iterator();

        try {
            LatLng marker = iter.next();

            options.position(marker);
            //options.title(listOfStations.get(m).get(1));
            StringBuilder info = new StringBuilder();
            info.append(
                    "ID: " + listOfStations.get(m).get(0) + "\n" +
                            //"Name: " + listOfStations.get(i).get(1) + "\n" +
                            "Network: " + listOfStations.get(m).get(2) + "\n" +
                            //"URL : " + listOfStations.get(i).get(3) + "\n" +
                            //"Fault Report available: " + listOfStations.get(i).get(4) + " | " +
                            //"Verified: " + listOfStations.get(i).get(5) + "\n" +
                            "Address: " +
                            listOfStations.get(m).get(9) + "\n" + "                 " + listOfStations.get(m).get(8) + ", " + listOfStations.get(m).get(6) + "\n" +

                            "Charger: " + listOfStations.get(m).get(10) + " | " + listOfStations.get(m).get(11) + ", Count: " + listOfStations.get(m).get(12));

            //Log.d("marker", "size " + listOfStations.get(i).size());

            if (listOfStations.get(m).size() > 13) {
                info.append("\n" + "                " + listOfStations.get(m).get(13) + " | " + listOfStations.get(m).get(14) + ", Count: " + listOfStations.get(m).get(15) + ", Price: "); // + listOfPrices.get(0).get(2));
            }
            if (listOfStations.get(m).size() > 16) {
                info.append("\n" + "                " + listOfStations.get(m).get(16) + " | " + listOfStations.get(m).get(17) + ", Count: " + listOfStations.get(m).get(18) + ", Price: "); //  + listOfPrices.get(0).get(3));
            }
            if (listOfStations.get(m).size() > 19) {
                info.append("\n" + "                " + listOfStations.get(m).get(19) + " | " + listOfStations.get(m).get(20) + ", Count: " + listOfStations.get(m).get(21) + ", Price: "); // + listOfPrices.get(0).get(4));
            }
            if (listOfStations.get(m).size() > 22) {
                info.append("\n" + "                " + listOfStations.get(m).get(22) + " | " + listOfStations.get(m).get(23) + ", Count: " + listOfStations.get(m).get(24) + ", Price: "); //  + listOfPrices.get(0).get(5));
            }
            if (listOfStations.get(m).size() > 25) {
                info.append("\n" + "                " + listOfStations.get(m).get(25) + " | " + listOfStations.get(m).get(26) + ", Count: " + listOfStations.get(m).get(27) + ", Price: "); //  + listOfPrices.get(0).get(6));
            }
            if (listOfStations.get(m).size() > 28) {
                info.append("\n" + "                " + listOfStations.get(m).get(28) + " | " + listOfStations.get(m).get(29) + ", Count: " + listOfStations.get(m).get(30) + ", Price: "); //  + listOfPrices.get(0).get(7));
            }

            ids[i] = listOfStations.get(m).get(0);

            infos[i] = info.toString();
            //options.snippet(listOfStations.get(m).get(9) + " | " + listOfStations.get(m).get(6));
            options.snippet("Nearest Charging Station");
            options.title("Recommendation");
            Marker m1 = map.addMarker(options);
            m1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.distance));
            m1.setTag(i);

            names.add(listOfStations.get(m).get(1));
            urls.add("https://" + listOfStations.get(m).get(3));
            address.add(listOfStations.get(m).get(9) + " | " + listOfStations.get(m).get(8) + ", " + listOfStations.get(m).get(6));
            networks.add(listOfStations.get(m).get(2));
            ids_tv.add(listOfStations.get(m).get(0));

            ArrayList<String> station = new ArrayList<>();

            station.add(listOfStations.get(m).get(10) + " | " + listOfStations.get(m).get(11) + "kWh, Count: " + listOfStations.get(m).get(12));
            if(listOfStations.get(m).size() > 13) {
                station.add("\n" + listOfStations.get(m).get(13) + " | " + listOfStations.get(m).get(14) + ", Count: " + listOfStations.get(m).get(15)); // + listOfPrices.get(0).get(2));
            }
            if(listOfStations.get(m).size() > 16) {
                station.add("\n" + listOfStations.get(m).get(16) + " | " + listOfStations.get(m).get(17) + ", Count: " + listOfStations.get(m).get(18)); //  + listOfPrices.get(0).get(3));
            }
            if(listOfStations.get(m).size() > 19) {
                station.add("\n" + listOfStations.get(m).get(19) + " | " + listOfStations.get(m).get(20) + ", Count: " + listOfStations.get(m).get(21)); // + listOfPrices.get(0).get(4));
            }
            if(listOfStations.get(m).size() > 22) {
                station.add("\n" + listOfStations.get(m).get(22) + " | " + listOfStations.get(m).get(23) + ", Count: " + listOfStations.get(m).get(24)); //  + listOfPrices.get(0).get(5));
            }
            if(listOfStations.get(m).size() > 25) {
                station.add("\n" + listOfStations.get(m).get(25) + " | " + listOfStations.get(m).get(26) + ", Count: " + listOfStations.get(m).get(27)); //  + listOfPrices.get(0).get(6));
            }
            if(listOfStations.get(m).size() > 28) {
                station.add("\n" + listOfStations.get(m).get(28) + " | " + listOfStations.get(m).get(29) + ", Count: " + listOfStations.get(m).get(30)); //  + listOfPrices.get(0).get(7));
            }

            stations.add(station);
            m++;
            //Log.d("count", ""+ i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
