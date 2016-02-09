package test.com.carwash;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

/**
 * Created by ritesh_kumar on 09-Jun-15.
 */
public class PlacePickerActivity extends ActionBarActivity {

    Button placePicker;
    int REQUEST_PLACE_PICKER=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_picker);
        placePicker=(Button)findViewById(R.id.btPickPlace);
    }

    /*public void onClickPickPlace(View v){
        // Construct an intent for the place picker
        try {

            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }*/
}
