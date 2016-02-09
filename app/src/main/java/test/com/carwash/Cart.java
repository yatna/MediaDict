package test.com.carwash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Cart extends ActionBarActivity {
    String name, phone, username, address;
    TextView tv1, tv2, tv3, tv4;
    MainActivity m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent intent=getIntent();
        name=intent.getStringExtra("Name");
        phone=intent.getStringExtra("Phone");
        username=intent.getStringExtra("Email");
        address=intent.getStringExtra("address");
       /* Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("Name");
        String username=bundle.getString("Email");
        String phone=bundle.getString("Phone");
        String address=bundle.getString("Address"); */

       // SharedPreferences pref = getSharedPreferences("s", Context.MODE_PRIVATE);

       // name = pref.getString("Name", "sorry");
       // phone = pref.getString("Phone", "No_phone");
       // username = pref.getString("Email", "sorry");
      //  address = pref.getString("address", "sorry");

        System.out.println(name+" "+phone+" "+username+"  "+address+"***************");
        tv1 = (TextView) findViewById(R.id.name);
        tv2 = (TextView) findViewById(R.id.number);
        tv3 = (TextView) findViewById(R.id.email);
        tv4 = (TextView) findViewById(R.id.address);
    }

    // tv1.setText(name);
    // tv2.setText(phone);
    // tv3.setText(username);
    // tv4.setText(address);

public void pay(View v){
    startActivity(new Intent(this,MainActivity_buyInstant.class));
}


}



