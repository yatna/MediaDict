package test.com.carwash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;


//import android.widget.Toolbar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Toolbar toolbar;
TextView tv1,tv2,tv3;
    private FloatingActionButton ViewButton;
    private FloatingActionButton AdvancedBook;
    private FloatingActionButton InstantBook;
    String Type1="Instant Booking";

String name,phone,username,address;
    //private FloatingActionButton VButton;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        toolbar = (Toolbar) findViewById(R.id.app_bar);

     setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2= (TextView) findViewById(R.id.textView2);











        Intent intent=getIntent();
      name=intent.getStringExtra("Name");
         phone=intent.getStringExtra("Phone");
         username=intent.getStringExtra("Email");
         address=intent.getStringExtra("address");
        System.out.println(name+" "+phone+" "+username+"  "+address+"***************");
         //tv1.setText(name);

        //tv2.setText(phone);

        SharedPreferences.Editor editor=  getSharedPreferences("s", Context.MODE_PRIVATE).edit();
        editor.putString("Name",name);
        editor.putString("Phone",phone);
        editor.putString("Email",username);
        editor.putString("address", address);

        editor.commit();
        //new MainActivity().check();

        drawerFragment.setup(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        setupFAB();
    }



    private void setupFAB() {
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
        ViewButton= (FloatingActionButton) findViewById(R.id.fab3);
        AdvancedBook= (FloatingActionButton) findViewById(R.id.fab1);
        InstantBook = (FloatingActionButton) findViewById(R.id.fab2);
        //VButton=(FloatingActionButton) findViewById(R.id.fab4);

        InstantBook.setOnClickListener(this);
        AdvancedBook.setOnClickListener(this);
        ViewButton.setOnClickListener(this);



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();




       if(id==R.id.nearbystations){
            startActivity(new Intent(this,FinalActivity.class));
        }
        if(id==R.id.share)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }
    private void toggleTranslateFAB(float slideOffset) {


    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab1:
                Intent i=new Intent(this,InstantBookinginit.class);


                startActivity(i);
                break;
            case R.id.fab2:
                startActivity(new Intent(this,AdvancedBookinginit.class));
                break;
            case R.id.fab3:
                startActivity(new Intent(this,GooglePlacesActivity.class));
                break;

        }

    }
}


