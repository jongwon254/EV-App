package joey.lee.org.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;


    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }



    private void rendowWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tv_title = view.findViewById(R.id.tv_title);

        if(!tv_title.equals("")) {
            tv_title.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tv_snippet = view.findViewById(R.id.tv_snippet);

        if(!tv_snippet.equals("")) {
            tv_snippet.setText(snippet);
        }
    }
    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
