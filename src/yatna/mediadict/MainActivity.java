package yatna.mediadict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Type;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnRatingBarChangeListener{


 EditText keyword,keyword2;
 RatingBar rate;
 String term="",entity="musicVideo",artistId;
 Button search,play,save,database,lyrics,More;
 WebView ourBrowser;
 TextView tv1,tv2,tv3,tv4;
 HttpClient client;
 String returned=" ";
 String ultimate,song_lyr;
 JSONObject json;
 JSONArray arr;
 Toast t;
 String artist,song,rating;
 String[] strArr;


static String URL= "https://itunes.apple.com/search?";
static String URL2="http://lyrics.wikia.com/api.php?func=getSong&artist=";
static String URL3= "https://itunes.apple.com/lookup?";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();

		//Setting up the web view.
        ourBrowser.getSettings().setJavaScriptEnabled(true);
        ourBrowser.getSettings().setLoadWithOverviewMode(true);
        ourBrowser.getSettings().setUseWideViewPort(true);

	}

	  private void initialize()
  {

  	keyword=(EditText)findViewById(R.id.et1);
  	keyword2=(EditText)findViewById(R.id.et2);
  	tv1=(TextView)findViewById(R.id.tv1);
  	tv2=(TextView)findViewById(R.id.tv2);
  	tv3=(TextView)findViewById(R.id.tv3);
  	tv4=(TextView)findViewById(R.id.tv4);
  	search=(Button)findViewById(R.id.b1);
	save=(Button)findViewById(R.id.b3);
	database=(Button)findViewById(R.id.b4);
	lyrics=(Button)findViewById(R.id.b5);
	More=(Button)findViewById(R.id.b6);
  	ourBrowser=(WebView)findViewById(R.id.wb1);
  	search.setOnClickListener(this);
  	play=(Button)findViewById(R.id.b2);
  	play.setOnClickListener(this);
  	save.setOnClickListener(this);
  	database.setOnClickListener(this);
  	lyrics.setOnClickListener(this);
  	More.setOnClickListener(this);
  	rate=(RatingBar)findViewById(R.id.rb1);
	rate.setOnRatingBarChangeListener(this);

  }
	//Using the iTunes API to get details of song by intaking keyword by user.

	public JSONArray lastTweet(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL);
		url.append(username);

		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

     		JSONObject obj=new JSONObject(data);
     		int check=obj.getInt("resultCount");
     		if(check==0){
     			tv2.setText("No match found");
     			return null;
     		}
     		else{
     		JSONArray timeline = obj.getJSONArray("results");

     		return timeline;
     		}

		}
		else{
			t=Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
                String a="term=" + term + "&entity="+ entity;
				arr=lastTweet(a);

				// Reading the entire JSONArray for required fields and storing them in strings.

				for (int i = 0; i < arr.length(); i++){
				String s1=arr.getJSONObject(i).getString("artistName");
				String s2=arr.getJSONObject(i).getString("trackName");
				String s3=arr.getJSONObject(i).getString("trackViewUrl");

				ultimate=ultimate + s2 + "   performed by  " + s1+ "   " + s3+ "\n";}


				return ultimate;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			tv1.setText(result);
		}
    }

   // Setting up actions for different Buttons.
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		ultimate="";

	    client = new DefaultHttpClient();

		switch(arg0.getId()){
		case R.id.b1:
			term=keyword.getText().toString();
			tv1.setText("Loading");
			new Read().execute("trackName","artistName","trackViewUrl");
		    break;
		case R.id.b2:
			term=keyword2.getText().toString();
			new Read2().execute("artworkUrl100");
			More.setEnabled(true);
			lyrics.setEnabled(true);

		    break;
		case R.id.b3:
			Boolean didItWork = true;
			try {

                // Storing data in database.
				DataM entry = new DataM(MainActivity.this);
				entry.open();
				entry.createEntry(strArr[1],strArr[2],strArr[3],strArr[4],strArr[5],strArr[6],strArr[7],strArr[8],strArr[9],rating);
				entry.close();
			} catch (Exception e) {
				didItWork = false;
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Oops !!");
				TextView tv = new TextView(this);
				tv.setText("Invalid Entry");
				d.setContentView(tv);
				d.show();
			} finally {
				if (didItWork == true) {
					Dialog d = new Dialog(this);
					d.setTitle("Done ");
					TextView tv = new TextView(this);
					tv.setText("New Entry Saved");
					d.setContentView(tv);
					d.show();
				}
			}

		    break;
		case R.id.b4:
			Intent q=new Intent("yatna.DATAPAGE");
			startActivity(q);
		    break;

		case R.id.b5:
			new Read3().execute("lyrics");

		    break;
		case R.id.b6:
			new ReadMore().execute("trackName","releaseDate","trackViewUrl");
		    break;
		}
	}


	public class Read2 extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// Getting required details from the first JSONObjects in the JSONArray.

	            String a="term=" + term + "&entity="+ entity;
				arr=lastTweet(a);
				String s0=arr.getJSONObject(0).getString("artworkUrl100");
				String s1=arr.getJSONObject(0).getString("trackName");
				String s2=arr.getJSONObject(0).getString("artistName");
				String s3=arr.getJSONObject(0).getString("kind");
				String s4=arr.getJSONObject(0).getString("country");
				String s5=arr.getJSONObject(0).getString("trackTimeMillis");
				String s6=arr.getJSONObject(0).getString("primaryGenreName");
				String s7=arr.getJSONObject(0).getString("trackExplicitness");
				String s8=arr.getJSONObject(0).getString("trackViewUrl");
				String s9=arr.getJSONObject(0).getString("releaseDate");
				artistId=arr.getJSONObject(0).getString("artistId");

                String[] Arr={s0,s1,s2,s3,s4,s5,s6,s7,s8,s9};
                strArr=Arr;
				return Arr;


			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}
			return null;
		}

		protected void onPostExecute(String[] result) {
		    String finalResult= "Track Name:" +result[1] + "\n" +
		    		"Performed By:" +result[2] + "\n" +
		    		"Track Type:" +result[3] + "\n" +
		    		"Lenght(in ms):" +result[5] + "\n" +
		    		"Release Date:" +result[9] + "\n" +
		    		"Genre:" +result[6] + "\n" +
		    		"Explicitness:" +result[7] + "\n" +
		    		"Country:" +result[4] + "\n" +
		    		"Track URL:" +result[8] ;
		    artist=result[2];
		    song=result[1];
			tv2.setText(finalResult);
			ourBrowser.loadUrl(result[0]);
			More.setText("More By "+ artist);
		}




	}

// Setting up the rating bar.
	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, String.valueOf(arg1), Toast.LENGTH_SHORT).show();
		if(arg1>0){
			save.setEnabled(true);
			rating=""+arg1;
		}

	}

	//Using the lyrics API to find the lyrics of the given song.

	public JSONObject getlyrics(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL2);
		url.append(username);

		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

     		JSONObject obj=new JSONObject(data);
     		return obj;

		}
		else{
			t=Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read3 extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
                String a=artist+ "&song="+ song+"&fmt=realjson";
				json=getlyrics(a);
				song_lyr=json.getString("lyrics");


				return song_lyr;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			strArr[9]=result;
			tv3.setText(result);

		}
    }

    public JSONArray More(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL3);
		url.append(username);

		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

     		JSONObject obj=new JSONObject(data);
     		JSONArray timeline = obj.getJSONArray("results");
     		return timeline;


		}
		else{
			t=Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class ReadMore extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
                String a="id=" + artistId + "&entity=musicVideo&limit=5&sort=recent";
				arr=More(a);
				for (int i = 1; i < arr.length(); i++){
				String s1=arr.getJSONObject(i).getString("trackName");
				String s2=arr.getJSONObject(i).getString("releaseDate");
				String s3=arr.getJSONObject(i).getString("trackViewUrl");

				ultimate=ultimate + s1 + "   performed on  " + s2+ "   " + s3+ "\n";}


				return ultimate;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();


			}
			return null;
		}

		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			tv4.setText(result);
		}
    }

}


