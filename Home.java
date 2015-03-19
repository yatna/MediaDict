package yatna.mediadict;
import java.io.IOException;
import java.util.Random;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Home extends Activity implements OnClickListener{
	TabHost th;
	TextView tv1,tv2;
	HttpClient client;
	String returned=" ";
	String ultimate="",mausam;
	String longitude="",latitude="",country="",artist;
	JSONObject json;
	JSONArray arr;
	Toast t;
	static String URL= "http://www.telize.com/geoip";
	static String URL2= "http://api.openweathermap.org/data/2.5/weather?";
	static String URL3= "http://api.icndb.com/jokes/random?firstName=Rajnikant&lastName=";
	static String URL4= "https://itunes.apple.com/search?";
	
	Button Music,Movies,TvSeries,Suggestion,Joke;
	TabSpec specs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initialize();
        th.setup();
		specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Home");
		th.addTab(specs);
		 specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("About");
		th.addTab(specs);
		client = new DefaultHttpClient();
		new Read().execute("country","longitude","latitude");
		new Read2().execute();
       
    }
    
    private void initialize()
    {    th=(TabHost)findViewById(R.id.tabhost);
    	 Music=(Button)findViewById(R.id.b1);
         Movies=(Button)findViewById(R.id.b2);
         TvSeries=(Button)findViewById(R.id.b3);
        Suggestion=(Button)findViewById(R.id.b4);
        Joke=(Button)findViewById(R.id.b5);
         tv1=(TextView)findViewById(R.id.tv1);
         tv2=(TextView)findViewById(R.id.tv2);
        Music.setOnClickListener(this);
 		Movies.setOnClickListener(this);
 		TvSeries.setOnClickListener(this);
 		Suggestion.setOnClickListener(this);
		Joke.setOnClickListener(this);
    }
    
    
    public JSONObject lastTweet(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL);
		
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
			t=Toast.makeText(Home.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				json=lastTweet("term=demons&entity=musicVideo");
				String s1=json.getString("country");
				String s2=json.getString("longitude");
				String s3=json.getString("latitude");
				String[] arr={s1,s2,s3};
				return arr;

				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} catch (JSONException e) {
				e.printStackTrace();
				
				
			}
			return null;
		}

		protected void onPostExecute(String[] result) {
			longitude=result[0];
			latitude=result[1];
			country=result[2];
			
		}
    }
    
    
    public JSONArray lastTweet2(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL2);
		url.append(username);
		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

     		JSONObject obj=new JSONObject(data);
     		JSONArray timeline = obj.getJSONArray("weather");
     		return timeline;
     		
			
			
			
			
		}
		else{
			t=Toast.makeText(Home.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read2 extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				arr=lastTweet2("lat="+latitude+"&"+"lon="+longitude);

			 mausam=arr.getJSONObject(0).getString("main");
				String s2=arr.getJSONObject(0).getString("description");
				
				String[] ary={mausam,s2};

				return ary;
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} catch (JSONException e) {
				
				e.printStackTrace();
				
				
			}
			return null;
		}

		protected void onPostExecute(String[] result) {
			
			tv1.setText("You are currently at "+latitude +"latitude and "+longitude +" longitude.\nCurrently the weather is "+ result[1]);
		}
    }
    


  

   

    @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
    	switch(arg0.getId()){
		case R.id.b1:
			Intent q=new Intent("yatna.MUSIC");
			startActivity(q);
			break;
		case R.id.b2:
			Intent w=new Intent("yatna.MOVIES");
    		startActivity(w);
			break;
		case R.id.b3:
			Intent e=new Intent("yatna.TVSERIES");
			startActivity(e);
			break;
		case R.id.b4:
			if(mausam.equals("Clouds"))
				artist="Taylor Swift";
			else if (mausam.equals("Clear"))
				artist="Akon";
			else if(mausam.equals("Rainy"))
				artist="Linkin Park";
			else
				artist="Beyonce";
			new Read4().execute("trackName","trackViewUrl");
			break;
		case R.id.b5:
			new Read3().execute();
			break;
			
		
		}
		
	}
    public JSONObject lastTweet3()throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL3);
		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

     		JSONObject obj=new JSONObject(data);
     		JSONObject timeline = obj.getJSONObject("value");
     		return timeline;
     		
			
			
			
			
		}
		else{
			t=Toast.makeText(Home.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read3 extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				json=lastTweet3();

				String s1=json.getString("joke");
				
				return s1;
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} catch (JSONException e) {
				
				e.printStackTrace();
				
				
			}
			return null;
		}

		protected void onPostExecute(String result) {
			
			tv2.setText(result);
		}
    }
    
    public JSONObject lastTweet4(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL4);
		url.append(username);
		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);
            JSONObject obj=new JSONObject(data);
            JSONArray timeline = obj.getJSONArray("results");
            Random ran = new Random();
            int x = ran.nextInt(50);
            return timeline.getJSONObject(x);
     		
			
		}
		else{
			t=Toast.makeText(Home.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read4 extends AsyncTask<String, Integer, String[]>{

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				json=lastTweet("term="+artist +"&entity=musicVideo");
				String s1=json.getString("trackName");
				String s2=json.getString("trackViewUrl");
				
				String[] arr={s1,s2};
				return arr;

				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			} catch (JSONException e) {
				e.printStackTrace();
				
				
			}
			return null;
		}

		protected void onPostExecute(String[] result) {
			tv1.setText(result[0] + "    "+ result[1]);
			
		}
    }
    
    
    
}
