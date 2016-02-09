package test.com.carwash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;


public class MainActivity_buyAdvance extends ActionBarActivity {
    TextView tv1,tv2;
    String order_type,amount,timestamp,address1,address2,model,paymentmode,description;
    int user_id;
    String s,Type2,carmodel,currentadd="";
    String paymentMode1="Cash On Delivery";
    String paymentMode2="Pay Online";
    UserLocalStore userLocalStore;
    String deladd,carbrand;
    String OILCHANGE,SERVICE,WASHING,POLISHING,AIRCLEANING,EXTRAONDEMAND;
    AppLocationService appLocationService;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("Ad84wtLea_F9-J6vrTs2psljRLGJzGx_lB-S90jCtzoj9o7Wlb2dYLlgBRvU40LuXERWEbAw2ZpYPhht");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_buy_advance);
        userLocalStore = new UserLocalStore(this);
        appLocationService = new AppLocationService(
                MainActivity_buyAdvance.this);
        tv2= (TextView) findViewById(R.id.tt);
        Intent intent=getIntent();
carmodel=intent.getStringExtra("carmodel");
        carbrand=intent.getStringExtra("carbrand");
        deladd=intent.getStringExtra("deladd");
         s=intent.getStringExtra("Total Sum");
         Type2=intent.getStringExtra("Advance");
        OILCHANGE=intent.getStringExtra("OILCHANGE");
        SERVICE=intent.getStringExtra("SERVICE");
        WASHING=intent.getStringExtra("WASHING");
        POLISHING=intent.getStringExtra("POLISHING");
        AIRCLEANING=intent.getStringExtra("AIRCLEANING");
        EXTRAONDEMAND=intent.getStringExtra("EXTRAONDEMAND");
        // paymentmodee1=intent.getStringExtra("paymentmode1");

       order_type=Type2;
        tv2.setText(" Rs " + s);



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

     /*  amount=s; //amount
        User user = userLocalStore.getLoggedInUser();
        user_id=user.id; //id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestamp = sdf.format(new Date()); // date

        address1="?";  //pickup add
        address2=user.address; //delivery add
        description="?";
        model="?"; //model
        paymentmode="?"; //payment mode

*/






        intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);




    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
    public void onBuyPressed(View pressed) {

        Toast.makeText(getApplicationContext(), "Please Wait.......Processing may take a while..", Toast.LENGTH_LONG).show();

        final Intent i = new Intent(MainActivity_buyAdvance.this, Successfull_Advance_Online.class);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {


                    Thread.sleep(15000); // As I am using LENGTH_LONG in Toast
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        i.putExtra("paymentmode2",paymentMode2);
        i.putExtra("Total Sum",s);
        i.putExtra("deladd",deladd);
        i.putExtra("carbrand",carbrand);
        i.putExtra("Advance",Type2);
        i.putExtra("currentadd", currentadd);
        i.putExtra("OILCHANGE",OILCHANGE);
        i.putExtra("SERVICE",SERVICE);
        i.putExtra("WASHING",WASHING);
        i.putExtra("POLISHING",POLISHING);
        i.putExtra("AIRCLEANING",AIRCLEANING);
        i.putExtra("EXTRAONDEMAND",EXTRAONDEMAND);





        //address
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);


        PayPalPayment payment = new PayPalPayment(new BigDecimal("1.75"), "USD", "hipster jeans",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

























    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
    public void cod(View v){


        Toast.makeText(getApplicationContext(), "Please Wait.......Processing may take a while..", Toast.LENGTH_LONG).show();

        final Intent intent = new Intent(MainActivity_buyAdvance.this, Successful_Advance_COD.class);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(15000); // As I am using LENGTH_LONG in Toast
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();



        //Intent i=new Intent(MainActivity_buyAdvance.this,Successful_Advance_COD.class);
        intent.putExtra("paymentmode1",paymentMode1);
        intent.putExtra("Total Sum",s);
        intent.putExtra("Advance",Type2);
        intent.putExtra("carmodel",carmodel);
        intent.putExtra("carbrand",carbrand);
        intent.putExtra("deladd",deladd);
        intent.putExtra("currentadd", currentadd);
        intent.putExtra("OILCHANGE",OILCHANGE);
        intent.putExtra("SERVICE",SERVICE);
        intent.putExtra("WASHING",WASHING);
        intent.putExtra("POLISHING",POLISHING);
        intent.putExtra("AIRCLEANING",AIRCLEANING);
        intent.putExtra("EXTRAONDEMAND",EXTRAONDEMAND);
//startActivity(intent);


    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity_buyAdvance.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity_buyAdvance.this.startActivity(intent);
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






    @Override
    public void onBackPressed() {
        return;
    }

}
