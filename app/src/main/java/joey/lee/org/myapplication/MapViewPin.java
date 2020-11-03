package joey.lee.org.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.here.sdk.core.Anchor2D;
import com.here.sdk.core.GeoCircle;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.GeoPolygon;
import com.here.sdk.mapview.MapCamera;
import com.here.sdk.mapview.MapPolygon;
import com.here.sdk.mapview.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapViewPin {

    private Context context;
    private MapView mapView;
    private static final GeoCoordinates MAP_CENTER_GEO_COORDINATES = new GeoCoordinates(49.484043, 8.461228);


    public MapViewPin(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        MapCamera camera = mapView.getCamera();
        double distanceToEarthInMeters = 7000;
        camera.lookAt(MAP_CENTER_GEO_COORDINATES, distanceToEarthInMeters);

        // Add circle to indicate map center.
        addCirclePolygon(MAP_CENTER_GEO_COORDINATES);
    }

    public void showMapViewPin() {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText("Centered ViewPin");

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(R.color.green);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.addView(textView);

        mapView.pinView(linearLayout, MAP_CENTER_GEO_COORDINATES);
    }

    public void showAnchoredMapViewPin() {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText("Anchored MapViewPin");

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(R.color.black);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.addView(textView);

        MapView.ViewPin viewPin = mapView.pinView(linearLayout, MAP_CENTER_GEO_COORDINATES);
        viewPin.setAnchorPoint(new Anchor2D(0.5F, 1));
    }

    public void clearMap() {
        List<MapView.ViewPin> mapViewPins = mapView.getViewPins();
        for (MapView.ViewPin viewPin : new ArrayList<>(mapViewPins)) {
            viewPin.unpin();
        }
    }

    private void addCirclePolygon(GeoCoordinates geoCoordinates) {
        float radiusInMeters = 50;
        GeoCircle geoCircle = new GeoCircle(geoCoordinates, radiusInMeters);

        GeoPolygon geoPolygon = new GeoPolygon(geoCircle);
        com.here.sdk.core.Color fillColor =
                new com.here.sdk.core.Color((short) 0x00, (short) 0x90, (short) 0x8A, (short) 0xA0);
        MapPolygon mapPolygon = new MapPolygon(geoPolygon, fillColor);

        mapView.getMapScene().addMapPolygon(mapPolygon);
    }
}

