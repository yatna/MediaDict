package test.com.carwash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nairit on 27/6/15.
 */
public class CartnewInstant extends ActionBarActivity {
    String order_type,amount,timestamp,address,OILCHANGE,SERVICE,WASHING,POLISHING,AIRCLEANING,EXTRAONDEMAND;
    int user_id;
    UserLocalStore userLocalStore;
    TextView etName, etPhone, etUsername, etAddress,etDate,etdeladd;
String s,Type1,Type2,carmodel,carbrand,currentadd,deladd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartnew_instant);
        Intent intent=getIntent();
        etdeladd= (TextView) findViewById(R.id.etdeladd);
etAddress= (TextView) findViewById(R.id.etAddress);
        carbrand=intent.getStringExtra("carbrand");
        s=intent.getStringExtra("Total Sum");
        Type1=intent.getStringExtra("Instant");
        currentadd=intent.getStringExtra("currentadd");
      //  System.out.println(deladd+currentadd+"###################");
        deladd=intent.getStringExtra("deladd");
        etdeladd.setText(deladd);
        etAddress.setText(currentadd);
 carmodel=intent.getStringExtra("carmodel");
        OILCHANGE=intent.getStringExtra("OILCHANGE");
        SERVICE=intent.getStringExtra("SERVICE");
        WASHING=intent.getStringExtra("WASHING");
        POLISHING=intent.getStringExtra("POLISHING");
        AIRCLEANING=intent.getStringExtra("AIRCLEANING");
        EXTRAONDEMAND=intent.getStringExtra("EXTRAONDEMAND");
        order_type="advance";
        amount="2111";
        user_id=4;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestamp = sdf.format(new Date());
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
       // System.out.println(OILCHANGE+SERVICE+WASHING+AIRCLEANING+POLISHING+EXTRAONDEMAND+" ''''''''''''''''''''''' ");

        address="Pivet Drive";
        etDate= (TextView) findViewById(R.id.etdate);
        etUsername = (TextView) findViewById(R.id.etUsername);
        etName = (TextView) findViewById(R.id.etName);
        etPhone = (TextView) findViewById(R.id.etPhone);
        //etAddress=(TextView)findViewById(R.id.etAddress);
        etDate.setText(currentDateTimeString);
        userLocalStore = new UserLocalStore(this);
    }
    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        }else {
            startActivity(new Intent(CartnewInstant.this,First_Screen.class));
        }
    }
    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);


        etName.setText(user.name);
        etPhone.setText(user.phone);
       // etAddress.setText(user.address);

    }
    public void pay(View v){
        Intent i=new Intent(this,MainActivity_buyInstant.class);


        i.putExtra("Total Sum", s);
i.putExtra("Instant",Type1);
        i.putExtra("currentadd",currentadd);
        i.putExtra("deladd",deladd);
        i.putExtra("carmodel",carmodel);
        i.putExtra("OILCHANGE",OILCHANGE);
        i.putExtra("SERVICE",SERVICE);
        i.putExtra("carbrand",carbrand);
        i.putExtra("WASHING",WASHING);
        i.putExtra("POLISHING",POLISHING);
        i.putExtra("AIRCLEANING",AIRCLEANING);
        i.putExtra("EXTRAONDEMAND",EXTRAONDEMAND);

        startActivity(i);
    }
    public void change(View v){
        startActivity(new Intent(this,InstantBooking.class));
    }
    private void registerUser(Order order) {
        ServerRequest_order serverRequest = new ServerRequest_order(this);
        serverRequest.orderInBackground(order, new GetOrderCallback() {
            @Override
            public void done(Order returnedUser) {
                Toast.makeText(getApplicationContext(), "Your Order has been Placed", Toast.LENGTH_LONG).show();
            }
        });
    }
}