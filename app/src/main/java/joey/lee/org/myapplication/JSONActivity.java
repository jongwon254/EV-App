package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JSONActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "JSONActivity";
    private static ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    private static ArrayList<ArrayList<String>> listOfStations = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>> listOfPrices;
    private static double startkey;
    private String jsonInputString = "{\"data\":{\"type\":\"charge_price_request\",\"attributes\":{\"data_adapter\":\"going_electric\",\"station\":{\"longitude\":8.502494,\"latitude\":49.564103,\"country\":\"Deutschland\",\"network\":\"EnBW\",\"charge_points\":[{\"power\":22,\"plug\":\"Typ2\"}]},\"options\":{\"energy\":1,\"duration\":60}}}}";

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
    }

    public ArrayList<ArrayList<String>> getListOfStations() {
        return listOfStations;
    }

    public ArrayList<ArrayList<String>> getListOfPrices() {
        return listOfPrices;
    }

    public double getStartkey() {
        return startkey;
    }

    private static String no_match;

    public String getJsonInputString() {
        return jsonInputString;
    }

    public String getNo_match() {
        return no_match;
    }

    public void setJsonInputString(String lng, String lat, String network) {

        String s1 = "{\"data\":{\"type\":\"charge_price_request\",\"attributes\":{\"data_adapter\":\"going_electric\",\"station\":{\"longitude\":";
        String s2 = ",\"latitude\":";
        String s3 =",\"country\":\"Deutschland\",\"network\":\"";
        String s4 = "\",\"charge_points\":[{\"power\":22,\"plug\":\"Typ2\"}]},\"options\":{\"energy\":1,\"duration\":60}}}}";

        jsonInputString = s1 + lng + s2 + lat + s3 + network + s4;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_j_s_o_n);




        // without button
        //sendRequestWithHttpURLConnectionTwo();
        Intent intent = new Intent(JSONActivity.this, GoogleMap.class);
        startActivity(intent);

        finish();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mSendTwo) {
            //sendRequestWithHttpURLConnectionTwo();
            Intent intent = new Intent(JSONActivity.this, GoogleMap.class);
            startActivity(intent);
        }

    }

    public void sendRequestWithHttpURLConnectionTwo2(String url1) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader mReader = null;
                HttpURLConnection con = null;

                try {

                    URL url = new URL(url1);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");

                    con.addRequestProperty("Content-Type","application/json");
                    con.addRequestProperty("Api-Key","ac73108982a771b1b02c4366b11f47f0"); //x-
                    con.addRequestProperty("Accept-Language", "en");
                    con.addRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                    con.addRequestProperty("Accept","*/*");

                    con.setDoOutput(true);
                    //jsonInputString = "{\"data\":{\"type\":\"charge_price_request\",\"attributes\":{\"data_adapter\":\"going_electric\",\"station\":{\"longitude\":8.602494,\"latitude\":49.464103,\"country\":\"Deutschland\",\"network\":\"EnBW\",\"charge_points\":[{\"power\":22,\"plug\":\"Typ2\"}]},\"options\":{\"energy\":1,\"duration\":60}}}}";

                    OutputStream os = con.getOutputStream();
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);

                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);

                    InputStream in = con.getInputStream();

                    // read the input stream
                    mReader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = mReader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse2(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("json", "1");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("json", "2");
                } finally {
                    if (mReader != null) {
                        try {
                            mReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("json", "3");
                        }
                    }
                    if (con != null) {
                        con.disconnect();

                    }
                }
            }
        }).start();
    }

    private void showResponse2(final String s) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    //gson
                    Gson gson = new Gson();
                    Response2 response2 = gson.fromJson(s, Response2.class);
                    listOfPrices = new ArrayList<ArrayList<String>>();

                    int length = response2.getData().size();


                    Log.d("price", "try json");

                    for(int i = 0; i<length; i++) {
                        ArrayList<String> prices= new ArrayList<String>();

                        prices.add(response2.getData().get(i).getAttributes().getProvider());
                        Log.d("GETME", response2.getData().get(i).getAttributes().getProvider());
                        Log.d("price", "try json for loop" + i);

                        int length2 = response2.getData().get(i).getAttributes().getCharge_point_prices().size();
                        for(int j = 0; j<length2; j++) {
                            prices.add(String.valueOf(response2.getData().get(i).getAttributes().getCharge_point_prices().get(j).getPrice()));
                            Log.d(TAG, "execution price second loop, " + i + ", " + j);
                            Log.d("GETME", ""+ response2.getData().get(i).getAttributes().getCharge_point_prices().get(j).getPrice());
                        }

                        listOfPrices.add(prices);

                    }




                } catch (Exception e) { //JSONEXCEPTION
                    e.printStackTrace();
                    Log.d("HERE", "SHOWRESPONSE");

                }


            }
        });
    }



    public void sendRequestWithHttpURLConnectionTwo(String url1) {

        // create a http request
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader mReader = null;
                HttpURLConnection urlConnection = null;
                try {
                    // URL AND HEADER WITH KEY
                    //URL url = new URL("https://api.goingelectric.de/chargepoints/chargecardlist");

                    // Mannheim Radius 10 km
                    //URL url = new URL("https://api.goingelectric.de/chargepoints?lat=49.486&lng=8.467&radius=10");

                    URL url = new URL(url1); //https://api.goingelectric.de/chargepoints/?countries=DE&lat=49,007294&lng=8,343797&radius=200&verified=true&exclude_faults=true

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.addRequestProperty("Content-Type","application/json");
                    urlConnection.addRequestProperty("x-Api-Key","b1589c7c1140728fedf2649d5f4e82a2");

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();

                    // read the input stream
                    mReader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = mReader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (mReader != null) {
                        try {
                            mReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();

                    }
                }


            }
        }).start();


    }

    private void showResponse(final String s) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {


                    //gson
                    Gson gson = new Gson();
                    Response response = gson.fromJson(s, Response.class);
                    if((String.valueOf(response.getStartkey()).equals("false"))) {
                        no_match = "true";
                    } else {
                        no_match = "false";

                        listOfStations = new ArrayList<ArrayList<String>>();
                        latLngs = new ArrayList<LatLng>();

                        Log.d("startkey json!!!!", response.getStartkey() + "");
                        if (response.getStartkey() != 0) {
                            startkey = response.getStartkey();
                        } else {
                            startkey = 0;
                        }

                        int length = response.getChargelocations().size();
                        for (int i = 0; i < length; i++) {

                            latLngs.add(new LatLng(response.getChargelocations().get(i).getCoordinates().getLat(), response.getChargelocations().get(i).getCoordinates().getLng()));

                        }
                        Log.d("WHY", "2");

                        for (int i = 0; i < length; i++) {
                            ArrayList<String> station = new ArrayList<String>();

                            station.add(String.valueOf((response.getChargelocations().get(i).getGe_id())));
                            station.add(response.getChargelocations().get(i).getName());
                            station.add(String.valueOf(response.getChargelocations().get(i).getNetwork()));
                            station.add(response.getChargelocations().get(i).getUrl());
                            station.add(String.valueOf(response.getChargelocations().get(i).isFault_report()));
                            station.add(String.valueOf(response.getChargelocations().get(i).isVerified()));

                            station.add(response.getChargelocations().get(i).getAddress().getCity());
                            station.add(response.getChargelocations().get(i).getAddress().getCountry());
                            station.add(String.valueOf(response.getChargelocations().get(i).getAddress().getPostcode()));
                            station.add(response.getChargelocations().get(i).getAddress().getStreet());

                            int length1 = response.getChargelocations().get(i).getChargepoints().size();
                            for (int j = 0; j < length1; j++) {
                                station.add(response.getChargelocations().get(i).getChargepoints().get(j).getType());
                                station.add(String.valueOf(response.getChargelocations().get(i).getChargepoints().get(j).getPower()));
                                station.add(String.valueOf(response.getChargelocations().get(i).getChargepoints().get(j).getCount()));
                                //Log.d(TAG, "execution charger for loop, " + i + ", " + j);
                            }

                            listOfStations.add(station);

                        }

                    }


                } catch (Exception e) { //JSONEXCEPTION
                    e.printStackTrace();

                }


            }
        });
    }
}
