package test.com.carwash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class About_Us extends ActionBarActivity implements View.OnClickListener {
    Button email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        Toolbar toolbar =(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email=(Button)findViewById(R.id.email);
        email.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about__us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
         case R.id.email:
         {
             Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setData(Uri.parse("mailto:"));
             String to="mukesh.thapa.mec09@itbhu.ac.in";
             intent.putExtra(Intent.EXTRA_EMAIL,to);
             intent.putExtra(Intent.EXTRA_SUBJECT,"Hi! This is from the CarWash App");
             intent.putExtra(Intent.EXTRA_TEXT,"This is the Contact Us feature");
             intent.setType("message/rfc822");
             Intent chooser = Intent.createChooser(intent, "Send Email");
             startActivity(chooser);
         }
        }
    }
}
