package test.com.carwash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nairit on 10/6/15.
 */
public class DrawerHeader extends Activity {

    TextView etName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

setContentView(R.layout.drawer_header);

        etName = (TextView) findViewById(R.id.etName);
       // etPhone = (TextView) findViewById(R.id.etPhone);
        Intent sender=getIntent();
        String name=sender.getExtras().getString("Name");
       // String d=sender.getExtras().getString("Phone");
        etName.setText(name);
       // etPhone.setText(d);

    }

}