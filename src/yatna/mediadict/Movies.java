package yatna.mediadict;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

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


public class Movies extends Activity implements OnClickListener{

	
	 EditText keyword,keyword2;
	 RatingBar rate;
	 String term="",entity="movie";
	 Button search,play,newentry,listen,MediaDict;
	 WebView ourBrowser,ourBrowser2;
	TextView tv1,tv2,tv3,tv4,tv5;
	HttpClient client;
	String returned=" ";
	String ultimate,desc;
	JSONObject json;
	JSONArray arr;
	String r;
	Toast t;
	static String URL= "https://itunes.apple.com/search?";
	static String URL2= "http://www.myapifilms.com/imdb?title=";
	static String URL3="http://www.myapifilms.com/imdb/inTheaters?format=JSON&lang=en-us";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		initialize();
		 ourBrowser.getSettings().setJavaScriptEnabled(true);
	        ourBrowser.getSettings().setLoadWithOverviewMode(true);
	        ourBrowser.getSettings().setUseWideViewPort(true);
	        ourBrowser2.getSettings().setJavaScriptEnabled(true);
	        ourBrowser2.getSettings().setLoadWithOverviewMode(true);
	        ourBrowser2.getSettings().setUseWideViewPort(true);
	}

	  private void initialize()
	  {
	  
	  	keyword=(EditText)findViewById(R.id.et1);
	  	keyword2=(EditText)findViewById(R.id.et2);
	  	tv1=(TextView)findViewById(R.id.tv1);
	  	tv2=(TextView)findViewById(R.id.tv2);
	  
	  	tv4=(TextView)findViewById(R.id.tv4);
	  	tv5=(TextView)findViewById(R.id.tv5);
	  	
	  	ourBrowser=(WebView)findViewById(R.id.wb1);
	  	ourBrowser2=(WebView)findViewById(R.id.wb2);
	  	
	  	search=(Button)findViewById(R.id.b1);
	  	newentry=(Button)findViewById(R.id.b3);
	  	play=(Button)findViewById(R.id.b2);
	  	listen=(Button)findViewById(R.id.b4);
	  	MediaDict=(Button)findViewById(R.id.b5);
	  	play.setOnClickListener(this);
	  	listen.setOnClickListener(this);
	  	newentry.setOnClickListener(this);
	  	search.setOnClickListener(this);
	  	MediaDict.setOnClickListener(this);
	  	
	  	
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
				t=Toast.makeText(Movies.this,"error",Toast.LENGTH_SHORT);
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
					String s2=arr.getJSONObject(i).getString("trackName");
					String s3=arr.getJSONObject(i).getString("collectionViewUrl");
					
					
					ultimate=ultimate + s2 + "   starring  " + s1+ "   " + s3+ "\n";}
					
					
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
				new Read().execute("trackName","artistName","trackViewUrl");
			    break;
			case R.id.b2:
				term=keyword2.getText().toString();
				new Read2().execute("artworkUrl100");
				newentry.setEnabled(true);
				listen.setEnabled(true);
				MediaDict.setEnabled(true);
			    break;
			case R.id.b3:
				new getNew().execute("title","plot","rating");
			    break;
            case R.id.b4:
				
            	try{
             		ourBrowser2.loadUrl("http://tts-api.com/tts.mp3?q="+desc);
             		}
             		catch(Exception e){
             			e.printStackTrace();
             		}
			    break;
            case R.id.b5:
				new getScore().execute("metascore","votes");
            		
			    break;
			
			}
		}
		
		
		public class Read2 extends AsyncTask<String, Integer, String[]>{

			@Override
			protected String[] doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					 
		            String a="term=" + term + "&entity="+ entity;
					arr=lastTweet(a);
					String s0=arr.getJSONObject(0).getString("artworkUrl100");
					
					String s1=arr.getJSONObject(0).getString("trackName");
					String s2=arr.getJSONObject(0).getString("artistName");
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
			    String finalResult= "Track Name:" +result[1] + "\n" +
			    		"Performed By:" +result[2] + "\n" +
			    		"Description:" +result[3] + "\n" +
			    		"Lenght(in ms):" +result[5] + "\n" +
			    		"Release Date:" +result[9] + "\n" +
			    		"Genre:" +result[6] + "\n" +
			    		"Explicitness:" +result[7] + "\n" +
			    		"Country:" +result[4] + "\n" +
			    		"Track URL:" +result[8] ;
				tv2.setText(finalResult);
				desc=result[3];
				term=result[1];
				ourBrowser.loadUrl(result[0]);
			}
			
			
			
			
		}


		
		
		
		public JSONObject lastScore(String username)throws ClientProtocolException,IOException,JSONException{
			StringBuilder url= new StringBuilder(URL2);
			url.append(username);
			
			HttpGet get =new HttpGet(url.toString());
			HttpResponse r= client.execute(get);
			int status=r.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity e=r.getEntity();
				String data=EntityUtils.toString(e);

	     		JSONArray obj=new JSONArray(data);
	     		JSONObject timeline = obj.getJSONObject(0);

	     		return timeline;
	     	
			}
			else{
				t=Toast.makeText(Movies.this,"error",Toast.LENGTH_SHORT);
				t.show();
				return null;
			}
		}
	    public class getScore extends AsyncTask<String, Integer, String[]>{

			@Override
			protected String[] doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
	               
				json=lastScore(term);
					String s1=json.getString("metascore");
					String s2=json.getString("votes");
					String[] r={s1,s2};
					
					
					return r;
					
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
				// TODO Auto-generated method stub
				String[] a=result[0].split("/");
				int meta =Integer.parseInt(a[0]);
				String[] b=result[1].split(",");
				String c=b[0]+b[1];
				int votes =Integer.parseInt(c);

				int final_score=meta +(votes/50000);
				String comment="";
				if (final_score>60)
					comment="MUST WATCH";
				else if (final_score<30 )
					comment="You will waste your time :-(";
				else 
					comment="Average";
				tv5.setText(comment);
				
				
				
			}
	    }
		

	    public JSONArray New()throws ClientProtocolException,IOException,JSONException{
			StringBuilder url= new StringBuilder(URL3);
			//url.append(username);
			
			HttpGet get =new HttpGet(url.toString());
			HttpResponse r= client.execute(get);
			int status=r.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity e=r.getEntity();
				String data=EntityUtils.toString(e);

	     		JSONArray obj=new JSONArray(data);
	     		JSONObject timeline = obj.getJSONObject(0);
	     		

	     		return timeline.getJSONArray("movies");
	     	
			}
			else{
				t=Toast.makeText(Movies.this,"error",Toast.LENGTH_SHORT);
				t.show();
				return null;
			}
		}
	    public class getNew extends AsyncTask<String, Integer, String>{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
	               
				arr=New();
				for (int i=0;i<10;i++)
				{
					 r=r+arr.getJSONObject(i).getString("title")+"\n"+arr.getJSONObject(i).getString("plot")+"\n"+arr.getJSONObject(i).getString("rating")+"\n\n";
					 
				}
					
					return r;
					
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
			tv4.setText(r);	
			}
	    }
		

}
