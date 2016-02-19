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
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Following extends Activity implements OnClickListener {
 TextView tv1,tv2,tv3,tv4,tv5,tv6;
 Button following,f_more,f_less;
 EditText et1,et2;
 HttpClient client;
 JSONObject json;
 JSONArray arr;
 Toast t;
 int i,size;
 String artist="",previous="";
 static String URL= "http://content.guardianapis.com/search?q=";
 String s="";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow);
		initialize();
		client = new DefaultHttpClient();
		
		FollowM infoo =new FollowM(Following.this);
	     infoo.open();
	     String follows= infoo.getNames();
	     infoo.close();
	     String[] art=follows.split("&");
	     size=art.length;
	    artist=follows;
	   // tv1.setText(follows+ "  "+ size);
	    
//		for(int i=1;i<size;i++)
//		{artist=art[i];
//		new Read9().execute("webtitle","webPublicationDate","webUrl");
//		if(i==1)
//			tv2.setText(previous);
//		if(i==2)
//			tv2.setText(previous);
//		}
	 //   artist=art[1];
	 tv1.setText(artist);  
	new Read9().execute("webtitle","webPublicationDate","webUrl");
		
//		 artist=art[2];
//		 i=2;
//			new Read9().execute("webtitle","webPublicationDate","webUrl");
				
	}
  private void initialize()
  {
	  tv1=(TextView)findViewById(R.id.tv1);
	  tv2=(TextView)findViewById(R.id.tv2);
//	  tv3=(TextView)findViewById(R.id.tv3);
//	  tv4=(TextView)findViewById(R.id.tv4);
//	  tv5=(TextView)findViewById(R.id.tv5);
//	  tv6=(TextView)findViewById(R.id.tv6);
	 
	  et1=(EditText)findViewById(R.id.et1);
	  et2=(EditText)findViewById(R.id.et2);
	  following=(Button)findViewById(R.id.b1);
	  f_more=(Button)findViewById(R.id.b2);
	  f_less=(Button)findViewById(R.id.b3);
	  following.setOnClickListener(this);
	  f_more.setOnClickListener(this);
	  f_less.setOnClickListener(this);
	 
	  
	  
				
				
  }
  public JSONArray lastTweet1(String username)throws ClientProtocolException,IOException,JSONException{
		StringBuilder url= new StringBuilder(URL);
		url.append(username);
		
		HttpGet get =new HttpGet(url.toString());
		HttpResponse r= client.execute(get);
		int status=r.getStatusLine().getStatusCode();
		if(status==200){
			HttpEntity e=r.getEntity();
			String data=EntityUtils.toString(e);

   		JSONObject obj1=new JSONObject(data);
   		JSONObject obj2=obj1.getJSONObject("response");
   		JSONArray timeline = obj2.getJSONArray("results");

   		return timeline;
   		
   	
		}
		else{
			t=Toast.makeText(Following.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
  public class Read9 extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
              String a= artist+  "from-date=2015-02-15&api-key=n5k29rny9d485gsd56ehephr";
				
				arr=lastTweet1(a);
				for (int i = 0; i < size; i++){
					String s1=arr.getJSONObject(i).getString("webTitle");
					String s2=arr.getJSONObject(i).getString("webPublicationDate");
					String s3=arr.getJSONObject(i).getString("webUrl");
					
					
				 s= s+s1+ "  on  "+ s2+  "\n"+s3+"\n";}
				
					
				
				return s;
				
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
		switch(arg0.getId()){
		case R.id.b1:
			FollowM info =new FollowM(this);
		     info.open();
		     String data= info.getData();
		     info.close();
		     tv2.setText(data);
		    break;
        case R.id.b2:
        	
			String s=et1.getText().toString();
        	FollowM entry = new FollowM(Following.this);
			entry.open();
			entry.createEntry(s);
			entry.close();
			FollowM info1 =new FollowM(this);
		     info1.open();
		     String data1= info1.getData();
		     info1.close();
		     tv2.setText(data1);
		    break;
        case R.id.b3:
        	try {
				String sRow1 = et2.getText().toString();
				long lRow1 = Long.parseLong(sRow1);
				if(lRow1>0){
					FollowM ex1 = new FollowM(Following.this);
				
				ex1.open();
				ex1.deleteEntry(lRow1);
				ex1.close();
				FollowM info2 =new FollowM(this);
			     info2.open();
			     String data2= info2.getData();
			     info2.close();
			     tv2.setText(data2);

				}
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Oops !!");
				TextView tv = new TextView(this);
				tv.setText("Artist Id not present");
				d.setContentView(tv);
				d.show();
			}
			
		    break;   
		}
	}

	
	
}
