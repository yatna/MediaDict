package test.com.carwash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;



public class Splash extends ActionBarActivity {
   ProgressBar pg;
   // String name,phone;
   // private String MY_PREFS_NAME="DEFAULT";
    int progress=0;
    Handler h=new Handler();
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pg = (ProgressBar) findViewById(R.id.pb);
        userLocalStore = new UserLocalStore(this);


            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 2; i++) {
                        progress += 50;
                        h.post(new Runnable() {

                            @Override
                            public void run() {
                                pg.setProgress(progress);
                                if (progress == pg.getMax()) {
                                    //pg.setVisibility(View.INVISIBLE);

                                    if (userLocalStore.getLoggedInUser() == null) {
                                       // Intent sender=getIntent();
                                       // name=sender.getExtras().getString("Name");
                                        Intent a = new Intent(getApplicationContext(), First_Screen.class);
                                        startActivity(a);
                                    } else {
                                       // Intent sender=getIntent();
                                       // name=sender.getExtras().getString("Name");

                                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(a);

                                   }
                                }
                            }
                        });
                        try {
                            Thread.sleep(2000);

                        } catch (InterruptedException e) {

                        }
                    }
                }
            }).start();

        }





}
