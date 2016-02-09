package test.com.carwash;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ritesh_kumar on 12-Jun-15.
 */
public class FinalActivity extends FragmentActivity implements LocationListener {

    GoogleMap mGoogleMap;

    double mLatitude = 0;
    double mLongitude = 0;
    ListView placesListView;
    HashMap<String, String> mMarkerPlaceLink = new HashMap<String, String>();
    ProgressDialog pg;
    ProgressBar progressBar;
    ArrayList<ServiceCentreLocation> allLocations = new ArrayList<>();
    String place_type = "hospital";
    public static final String API_BROWSER_KEY = "AIzaSyAEybZ25tEj8j8dQv1w3XeajBhfBL2hzoM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        pg = new ProgressDialog(this);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        placesListView =(ListView)findViewById(R.id.lvPlacesList);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());


        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Google Map
            mGoogleMap = fragment.getMap();

            // Enabling MyLocation in Google Map
            mGoogleMap.setMyLocationEnabled(true);


            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, this);


            new NearbyPlacesRetrieverTask(Double.toString(mLatitude), Double.toString(mLongitude)).execute();

        }
    }

    class NearbyPlacesRetrieverTask extends AsyncTask<Void, Void, Void> {

        String REQUEST_URL_F0R_NEARBY_PLACES;
        String place_name = "", lat = "", lng = "", vicinity = "", reference = "";

        NearbyPlacesRetrieverTask(String lat, String lng) {
            REQUEST_URL_F0R_NEARBY_PLACES = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=2000&types=%s&sensor=true&key=%s", lat, lng, place_type, API_BROWSER_KEY);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pg.setMessage("Retrieving nearby car wash centers..");
            pg.setCancelable(false);
            pg.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpClient client = new DefaultHttpClient();
                Log.d("http", "http request set");
                HttpUriRequest request = new HttpGet(REQUEST_URL_F0R_NEARBY_PLACES);
                HttpResponse response = client.execute(request);
                Log.d("http response", "http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject json = new JSONObject(result);
                System.out.println(json);
                JSONArray resultArray = json.getJSONArray("results");
                for (int i = 0; i < resultArray.length(); i++) {
                    ServiceCentreLocation serviceCentreLocation = new ServiceCentreLocation();
                    // Extracting Place name, if available
                    JSONObject jPlace = (JSONObject) resultArray.get(i);
                    if (!jPlace.isNull("name")) {
                        place_name = jPlace.getString("name");
                    }

                    // Extracting Place Vicinity, if available
                    if (!jPlace.isNull("vicinity")) {
                        vicinity = jPlace.getString("vicinity");
                    }

                    lat = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                    lng = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                    reference = jPlace.getString("reference");

                    serviceCentreLocation.setLat(lat);
                    serviceCentreLocation.setLng(lng);
                    serviceCentreLocation.setReference(reference);
                    serviceCentreLocation.setPlaceName(place_name);
                    serviceCentreLocation.setVicinity(vicinity);
                    allLocations.add(serviceCentreLocation);
                    System.out.println("allocations size :: "+allLocations.size());
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            System.out.println("allocations size 2:: "+allLocations.size());
            for (int i = 0; i < allLocations.size(); i++) {
                System.out.println("index::: "+ i);
                new DistanceNDurationCalculatorTask(Double.toString(mLatitude), Double.toString(mLongitude), allLocations.get(i).getLat(), allLocations.get(i).getLng(), i).execute();
                new PlaceFullInfoRetrieverTask(i).execute();
            }


        }
    }

    class PlaceFullInfoRetrieverTask extends AsyncTask<Void, Void, Void> {
        String reference = "";
        String REQUEST_URL_FOR_PLACE_FULL_DETAIL;
        ServiceCentreLocation serviceCentreLocation;
        String name = "-NA-";
        String icon = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String formatted_address = "-NA-";
        String formatted_phone = "-NA-";
        String website = "-NA-";
        String rating = "-NA-";
        String international_phone_number = "-NA-";
        String url = "-NA-";
        int index;

        PlaceFullInfoRetrieverTask(int index) {
            this.reference = allLocations.get(index).getReference();
            this.serviceCentreLocation = allLocations.get(index);
            this.index=index;
            REQUEST_URL_FOR_PLACE_FULL_DETAIL = String.format("https://maps.googleapis.com/maps/api/place/details/json?reference=%s&sensor=true&key=%s", reference, API_BROWSER_KEY);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setMessage("Retrieving full details...");
            if(!pg.isShowing())
                pg.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                Log.d("http", "http request set");
                HttpUriRequest request = new HttpGet(REQUEST_URL_FOR_PLACE_FULL_DETAIL);
                HttpResponse response = client.execute(request);
                Log.d("http response", "http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject json = new JSONObject(result);
                System.out.println(json);
                JSONObject jPlaceDetails = json.getJSONObject("result");

                // Extracting Place name, if available
                if (!jPlaceDetails.isNull("name")) {
                    name = jPlaceDetails.getString("name");
                }

                // Extracting Icon, if available
                if (!jPlaceDetails.isNull("icon")) {
                    icon = jPlaceDetails.getString("icon");
                }

                // Extracting Place Vicinity, if available
                if (!jPlaceDetails.isNull("vicinity")) {
                    vicinity = jPlaceDetails.getString("vicinity");
                }

                // Extracting Place formatted_address, if available
                if (!jPlaceDetails.isNull("formatted_address")) {
                    formatted_address = jPlaceDetails.getString("formatted_address");
                }

                // Extracting Place formatted_phone, if available
                if (!jPlaceDetails.isNull("formatted_phone_number")) {
                    formatted_phone = jPlaceDetails.getString("formatted_phone_number");
                }

                // Extracting website, if available
                if (!jPlaceDetails.isNull("website")) {
                    website = jPlaceDetails.getString("website");
                }

                // Extracting rating, if available
                if (!jPlaceDetails.isNull("rating")) {
                    rating = jPlaceDetails.getString("rating");
                }

                // Extracting rating, if available
                if (!jPlaceDetails.isNull("international_phone_number")) {
                    international_phone_number = jPlaceDetails.getString("international_phone_number");
                }

                // Extracting url, if available
                if (!jPlaceDetails.isNull("url")) {
                    url = jPlaceDetails.getString("url");
                }

                latitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lng");

                serviceCentreLocation.setAddress(formatted_address);
                serviceCentreLocation.setIcon(icon);
                serviceCentreLocation.setInternationalPhNo(international_phone_number);
                serviceCentreLocation.setPhone(formatted_phone);
                serviceCentreLocation.setLat(latitude);
                serviceCentreLocation.setLng(longitude);
                serviceCentreLocation.setPlaceName(name);
                serviceCentreLocation.setReference(reference);
                serviceCentreLocation.setRating(rating);
                serviceCentreLocation.setUrl(url);
                serviceCentreLocation.setVicinity(vicinity);
                serviceCentreLocation.setWebsite(website);
                allLocations.set(index, serviceCentreLocation);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("index "+index+"alllcations.size "+(allLocations.size()-1));

            if(index==allLocations.size()-1){
                progressBar.setVisibility(View.GONE);
                placesListView.setVisibility(View.VISIBLE);
                PlacesListAdapter adapter= new PlacesListAdapter(FinalActivity.this,allLocations);
                placesListView.setAdapter(adapter);
                if (pg.isShowing())
                    pg.dismiss();
            }

        }
    }

    class DistanceNDurationCalculatorTask extends AsyncTask<Void, Void, Void> {

        String UserLat, UserLng, DestLat, DestLng;
        String REQUEST_URL_FOR_DISTANCE_CALCULATION;
        int index;

        DistanceNDurationCalculatorTask(String userLat, String userLng, String destLat, String destLng, int index) {
            this.UserLat = userLat;
            this.UserLng = userLng;
            this.DestLng = destLng;
            this.DestLat = destLat;
            this.index = index;
            REQUEST_URL_FOR_DISTANCE_CALCULATION = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s,%s&destinations=%s,%s&sensor=false&key=%s", UserLat, UserLng, DestLat, DestLng, API_BROWSER_KEY);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setMessage("Calculating Distances...");
            if(!pg.isShowing())
                pg.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient client = new DefaultHttpClient();
                Log.d("http", "http request set");
                HttpUriRequest request = new HttpGet(REQUEST_URL_FOR_DISTANCE_CALCULATION);
                HttpResponse response = client.execute(request);
                Log.d("http response", "http request executed");
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                Log.d("json object", "going to initialize object");
                JSONObject json = new JSONObject(result);

                System.out.println(json);
                JSONArray jsonArray = json.getJSONArray("rows");
                final JSONObject jsonobject = jsonArray.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
                //JSONObject elementsjsonObject=jsonArray.getJSONArray("elements");
                String distance = jsonobject.getJSONObject("distance").getString("text");
                String time = jsonobject.getJSONObject("duration").getString("text");
                System.out.println("distance " + distance + "  time " + time);
                allLocations.get(index).setDistance(distance);
                allLocations.get(index).setDuration(time);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(Double.parseDouble(allLocations.get(index).getLat()), Double.parseDouble(allLocations.get(index).getLng()));
            // Setting the position for the marker
            markerOptions.position(latLng);

            String name = allLocations.get(index).getPlaceName();
            String distance = allLocations.get(index).getDistance();
            String duration = allLocations.get(index).getDuration();
            // Setting the title for the marker.
            //This will be displayed on taping the marker
            markerOptions.title(name + " : " + distance + " , " + duration);
            System.out.println(name + " : " + distance + " , " + duration);
            // Placing a marker on the touched position
            Marker m = mGoogleMap.addMarker(markerOptions);

            String reference = allLocations.get(index).getReference();
            // Linking Marker id and place reference
//            mMarkerPlaceLink.put(m.getId(), place.get("reference"));
            mMarkerPlaceLink.put(m.getId(), reference);
            System.out.println("refrence b4 listener" + reference);
            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker arg0) {

                    Intent intent = new Intent(FinalActivity.this, PlaceDetailsActivity.class);
                    String reference = mMarkerPlaceLink.get(arg0.getId());
                    System.out.println("reference 2 " + reference);
                    //System.out.println(" DistanceFinder.reference " + );
                    intent.putExtra("reference", reference);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Starting the Place Details Activity
                    FinalActivity.this.startActivity(intent);
                }
            });




        }
    }


    @Override
    public void onLocationChanged(Location location) {

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
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

}
