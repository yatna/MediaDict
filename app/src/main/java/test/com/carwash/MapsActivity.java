package test.com.carwash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener, View.OnClickListener, GoogleMap.OnCameraChangeListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    Button instBook, advBook;
    static Context AppContext;
    private Double currentLongitude, currentLatitude;
    String locationAddress = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        AppContext = getApplicationContext();
        initBookingButtons();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        setUpMapIfNeeded();
        // use mMap variable after this
        //mMap.setOnCameraChangeListener(this);
        mMap.setMyLocationEnabled(true);
        AppContext = getApplicationContext();
    }

    public void initBookingButtons() {
        instBook = (Button) findViewById(R.id.btInstBook);
        //advBook = (Button) findViewById(R.id.btAdvBook);
        instBook.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        CheckEnableGPS();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(0, 0));
            mMap.addMarker(markerOptions);
        }
    }


    private void CheckEnableGPS() {
      // locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("GPS is currently OFF! Please turn it ON !");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
//                    dialog.dismiss();
                    dialog.cancel();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Your location can not be determined now!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.create().show();

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();

        MarkerOptions mp = new MarkerOptions().draggable(true);
        currentLongitude = location.getLongitude();
        currentLatitude = location.getLatitude();
        mp.position(new LatLng(currentLatitude, currentLongitude));
        LocationAddressGeocoder locationAddressGeocoder = new LocationAddressGeocoder();
        locationAddressGeocoder.getAddressFromLocation(currentLatitude, currentLongitude,
                AppContext, new GeocoderHandler());
        mp.title(locationAddress);

        mMap.addMarker(mp);
        // mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));
        //here 16 is the zoom level
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btInstBook:
                LocationAddressGeocoder locationAddressGeocoder = new LocationAddressGeocoder();
                locationAddressGeocoder.getAddressFromLocation(currentLatitude, currentLongitude,
                        AppContext, new GeocoderHandler());
                showToast("Instant booking:long=" + currentLongitude + " lat= " + currentLatitude);
                showToast("add: "+locationAddress);
                syso("add: "+locationAddress);
                break;

        }
    }

    public void showToast(String msg) {
        Toast.makeText(AppContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void syso(String msg){
        System.out.println(msg);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(cameraPosition.target));
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
        }
    }
}
