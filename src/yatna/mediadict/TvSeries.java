package yatna.mediadict;

import java.io.IOException;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.*;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class TvSeries extends Activity implements OnClickListener{

	
	 EditText keyword,keyword2;
	 RatingBar rate;
	 String term="",entity="tvShow";
	 Button search,play;
	 WebView ourBrowser;
	TextView tv1,tv2;
	HttpClient client;
	String returned=" ";
	String ultimate;
	JSONObject json;
	JSONArray arr;
	Toast t;
	static String URL= "https://itunes.apple.com/search?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tvseries);
		initialize();
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
	  	search=(Button)findViewById(R.id.b1);
	  	ourBrowser=(WebView)findViewById(R.id.wb1);
	  	search.setOnClickListener(this);
	  	play=(Button)findViewById(R.id.b2);
	  	play.setOnClickListener(this);
//	  	rate=(RatingBar)findViewById(R.id.rb1);
//		rate.setOnRatingBarChangeListener(this);
	  	
	  }
		
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
	     		JSONArray timeline = obj.getJSONArray("results");

	     		return timeline;
	     	
			}
			else{
				t=Toast.makeText(TvSeries.this,"error",Toast.LENGTH_SHORT);
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
					for (int i = 0; i < arr.length(); i++){
					String s1=arr.getJSONObject(i).getString("artistName");
					String s2=arr.getJSONObject(i).getString("primaryGenreName");
					String s3=arr.getJSONObject(i).getString("artistLinkUrl");
					
					
					ultimate=ultimate + s1 + "    ( " + s2+ " )   " + s3+ "\n";}
					
					
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
		

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			ultimate="";
			
		    client = new DefaultHttpClient();
		   
			switch(arg0.getId()){
			case R.id.b1:
				term=keyword.getText().toString();
				new Read().execute("primaryGenreName","artistName","artistLinkUrl");
			    break;
			case R.id.b2:
				term=keyword2.getText().toString();
				new Read2().execute("artworkUrl100");
			    break;
			}
		}
		
		
		public class Read2 extends AsyncTask<String, Integer, String[]>{

			@Override
			protected String[] doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					 
		            String a="term=" + term + "&entity=tvEpisode";
					arr=lastTweet(a);
					String s0=arr.getJSONObject(0).getString("artworkUrl100");
					
					String s1=arr.getJSONObject(0).getString("collectionName");
					String s2=arr.getJSONObject(0).getString("trackName");
					String s3=arr.getJSONObject(0).getString("longDescription");
					String s4=arr.getJSONObject(0).getString("country");
					String s5=arr.getJSONObject(0).getString("trackTimeMillis");
					String s6=arr.getJSONObject(0).getString("primaryGenreName");
					String s7=arr.getJSONObject(0).getString("trackExplicitness");
					String s8=arr.getJSONObject(0).getString("trackViewUrl");
					String s9=arr.getJSONObject(0).getString("releaseDate");
					
					String[] strArr={s0,s1,s2,s3,s4,s5,s6,s7,s8,s9};
					return strArr;
					
					
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
			    String finalResult= "Season:" +result[1] + "\n" +
			    		"Episode Name:" +result[2] + "\n" +
			    		"Description:" +result[3] + "\n" +
			    		"Lenght(in ms):" +result[5] + "\n" +
			    		"Release Date:" +result[9] + "\n" +
			    		"Genre:" +result[6] + "\n" +
			    		"Explicitness:" +result[7] + "\n" +
			    		"Country:" +result[4] + "\n" +
			    		"Track URL:" +result[8] ;
				tv2.setText(finalResult);
				ourBrowser.loadUrl(result[0]);
			}
			
			
			
			
		}


//		@Override
//		public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
//			// TODO Auto-generated method stub
//			Toast.makeText(TvSeries.this, String.valueOf(arg1), Toast.LENGTH_SHORT).show();
//			
//		}

}
