package joey.lee.org.myapplication;


import android.util.Log;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class JSON_API {


        private static final String TAG = "JSONActivity";
        private static ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
        private static ArrayList<ArrayList<String>> listOfStations = new ArrayList<ArrayList<String>>();

        public ArrayList<LatLng> getLatLngs() {
            return latLngs;
        }

        public ArrayList<ArrayList<String>> getListOfStations() {
            return listOfStations;
        }


        public void sendRequestWithHttpURLConnectionTwo() {

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
                       // URL url = new URL("https://api.goingelectric.de/chargepoints?lat=49.486&lng=8.467&radius=10");

                        // Mannheim only Germany Radious 200km
                        URL url = new URL("https://api.goingelectric.de/chargepoints/?countries=DE&lat=49,007294&lng=8,343797&radius=200");

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


                    try {

                        //gson
                        Gson gson = new Gson();
                        Response response = gson.fromJson(s, Response.class);

                        for(int i = 0; i<500; i++) {

                            latLngs.add(new LatLng(response.getChargelocations().get(i).getCoordinates().getLat(), response.getChargelocations().get(i).getCoordinates().getLng()));
                        }
                        Log.d("WHY", "2");

                        for(int i = 0; i<500; i++) {
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

                            int length = response.getChargelocations().get(i).getChargepoints().size();
                            for(int j = 0; j<length; j++) {
                                station.add(response.getChargelocations().get(i).getChargepoints().get(j).getType());
                                station.add(String.valueOf(response.getChargelocations().get(i).getChargepoints().get(j).getPower()));
                                station.add(String.valueOf(response.getChargelocations().get(i).getChargepoints().get(j).getCount()));
                                //Log.d(TAG, "execution charger for loop, " + i + ", " + j);
                            }

                            listOfStations.add(station);

                        }




                    } catch (Exception e) { //JSONEXCEPTION
                        e.printStackTrace();

                    }


                }

        }