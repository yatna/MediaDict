package test.com.carwash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Successfull_Advance_Online extends ActionBarActivity {
    // String s;
    String order_type, amount, timestamp, address1, address2, model, paymentmode, description;
    int user_id;
    String deladd,carbrand;
    UserLocalStore userLocalStore;
    String OILCHANGE,SERVICE,WASHING,POLISHING,AIRCLEANING,EXTRAONDEMAND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful__advance);
        Intent intent = getIntent();

        userLocalStore = new UserLocalStore(this);

        String s = intent.getStringExtra("Total Sum");
        String Type2 = intent.getStringExtra("Advance");
        carbrand=intent.getStringExtra("carbrand");
        deladd=intent.getStringExtra("deladd");
        String paymentmodee = intent.getStringExtra("paymentmode2");
       String carmodel=intent.getStringExtra("carmodel");
        String currentadd=intent.getStringExtra("currentadd");
        OILCHANGE=intent.getStringExtra("OILCHANGE");
        SERVICE=intent.getStringExtra("SERVICE");
        WASHING=intent.getStringExtra("WASHING");
        POLISHING=intent.getStringExtra("POLISHING");
        AIRCLEANING=intent.getStringExtra("AIRCLEANING");
        EXTRAONDEMAND=intent.getStringExtra("EXTRAONDEMAND");
        String descriptiont=OILCHANGE+"\n"+SERVICE+"\n"+WASHING+"\n"+POLISHING+"\n"+AIRCLEANING+"\n"+EXTRAONDEMAND+" ";
        order_type = Type2;
        amount = s; //amount
        User user = userLocalStore.getLoggedInUser();
        user_id = user.id; //id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestamp = sdf.format(new Date()); // date

        address1 = currentadd;  //pickup add
        address2 = deladd; //delivery add
        description = descriptiont;//cart 5 option
        model = carmodel; //model
        paymentmode = paymentmodee; //payment mode

        System.out.print(currentadd+"`````````````````````````````````````$$$$$$$$$$$$");
    }

    public void back(View v) {
        Order order = new Order(order_type, amount, user_id, timestamp, description, address1, address2, model, paymentmode);
        // startActivity(new Intent(this, AfterAdv.class));
        registerUser(order);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void registerUser(Order order) {
        ServerRequest_order serverRequest = new ServerRequest_order(this);
        serverRequest.orderInBackground(order, new GetOrderCallback() {
            @Override
            public void done(Order returnedUser) {
                Toast.makeText(getApplicationContext(), "Your Order Has Been Placed", Toast.LENGTH_LONG).show();

                final Intent intent = new Intent(Successfull_Advance_Online.this, AfterAdv.class);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
        });


    }
}
