package yatna.mediadict;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;



public class Splash extends Activity{
     MediaPlayer song;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

    // Play music on starting of app.
		song=MediaPlayer.create(Splash.this, R.raw.music);
		song.start();

		Thread timer=new Thread(){

			public void run(){
				try{ sleep(1500);
			        }
				catch(InterruptedException e){
					e.printStackTrace();
		         }
				finally{
					Intent openStartingPoint=new Intent("yatna.HOME");
					startActivity(openStartingPoint);}
	           }

		};
		timer.start();
          }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		song.release();
		finish();
	}
	}


