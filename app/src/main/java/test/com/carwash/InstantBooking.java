package test.com.carwash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nairit on 29/5/15.
 */
public class InstantBooking extends Activity {
    private CheckBox box1,box2,box3,box4,box5,box6;
    TextView tv22;
    String c,instant="Instant Booking";
    String currentadd="";
    String OILCHANGE="OIL CHANGE";
    String SERVICE="SERVICE",WASHING="WASHING",POLISHING="POLISHING",AIRCLEANING="AIR CLEANING",EXTRAONDEMAND="EXTRA ON DEMAND";
    AppLocationService appLocationService;
    Double Sum=0.0;

    //private String MY_PREFS_NAME="DEFAULT";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instantbook1);
        tv22= (TextView) findViewById(R.id.tv22);

        appLocationService = new AppLocationService(
                InstantBooking.this);
        addListenerbox1();
        addListenerbox2();
        addListenerbox3();
        addListenerbox4();
        addListenerbox5();
addListenerbox6();
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }





    }

public void proceed(View v){
   // Intent i=new Intent(this,CaraskInstant1.class);


   // Toast.makeText(getApplicationContext(), "Please Wait.......Processing may take a while..", Toast.LENGTH_LONG).show();

    final Intent i = new Intent(InstantBooking.this, CaraskInstant1.class);




i.putExtra("Total Sum", c);
i.putExtra("Instant", instant);
//    i.putExtra("currentadd",currentadd);
    if (box1.isChecked()){
        i.putExtra("OILCHANGE",OILCHANGE);

    }

    if(box2.isChecked()){
        i.putExtra("SERVICE",SERVICE);

    }
    if(box3.isChecked()){
        i.putExtra("WASHING",WASHING);

    }
    if(box4.isChecked()){
        i.putExtra("POLISHING",POLISHING);

    }
    if(box5.isChecked()){
        i.putExtra("AIRCLEANING",AIRCLEANING);

    }
    if(box6.isChecked()){
        i.putExtra("EXTRAONDEMAND", EXTRAONDEMAND);

    }



    startActivity(i);

}



    public void addListenerbox1() {
        box1 = (CheckBox) findViewById(R.id.checkBox1);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 1000.0;
                    c = Sum.toString();
                    tv22.setText(c);

//checked
                } else if ((buttonView.isChecked()) != true) {
                    box1.setChecked(false);
//not checked
                    Sum -= 1000.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });



    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                InstantBooking.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        InstantBooking.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    public  class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    currentadd=locationAddress;
                    break;
                default:
                    locationAddress = null;
            }

        }
    }




    public void addListenerbox2() {
        box2 = (CheckBox) findViewById(R.id.checkBox2);
        box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 1500.0;
                    c = Sum.toString();
                    tv22.setText(c);

//checked
                } else if ((buttonView.isChecked())!=true) {
                    box2.setChecked(false);
//not checked
                    Sum-=1500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });

    }
    public void addListenerbox3() {
        box3 = (CheckBox) findViewById(R.id.checkBox3);
        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox4() {
        box4 = (CheckBox) findViewById(R.id.checkBox4);
        box4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 10000.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=10000.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });

    }
    public void addListenerbox5() {
        box5 = (CheckBox) findViewById(R.id.checkBox5);
        box5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox6() {
        box6 = (CheckBox) findViewById(R.id.checkBox6);
        box6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {

//checkedSys
                    System.out.print("checked");
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    System.out.print("not checked");
                }

            }
        });
    }
}
