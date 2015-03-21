package yatna.mediadict;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
 TextView tv1,tv2;
 Button following,f_more,f_less;
 EditText et1,et2;
 HttpClient client;
 JSONObject json;
 JSONArray arr;
 Toast t;
 String artist,previous;
 static String URL= "http://content.guardianapis.com/search?q=beyonce";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow);
		initialize();
	}
  private void initialize()
  {
	  tv1=(TextView)findViewById(R.id.tv1);
	  tv2=(TextView)findViewById(R.id.tv2);
	  et1=(EditText)findViewById(R.id.et1);
	  et2=(EditText)findViewById(R.id.et2);
	  following=(Button)findViewById(R.id.b1);
	  f_more=(Button)findViewById(R.id.b2);
	  f_less=(Button)findViewById(R.id.b3);
	  following.setOnClickListener(this);
	  f_more.setOnClickListener(this);
	  f_less.setOnClickListener(this);
	  artist= "Beyonce";
	  //new Read().execute("webTitle","webPublicationDate","webUrl");
	  
				
				
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
		    break;
        case R.id.b3:
        	try {
				String sRow1 = et1.getText().toString();
				long lRow1 = Long.parseLong(sRow1);
				if(lRow1>0){
					FollowM ex1 = new FollowM(Following.this);
				
				ex1.open();
				ex1.deleteEntry(lRow1);
				ex1.close();

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

	
	public JSONObject lastTweet(String username)throws ClientProtocolException,IOException,JSONException{
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

     		return timeline.getJSONObject(0);
     		
     	
		}
		else{
			t=Toast.makeText(Following.this,"error",Toast.LENGTH_SHORT);
			t.show();
			return null;
		}
	}
    public class Read extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
                String a= artist + "&from-date=2015-02-15&&api-key=test";
				json=lastTweet(a);
				
				String s1=json.getString("webTitle");
				String s2=json.getString("webPublicationDate");
				String s3=json.getString("webUrl");
				String s="( "+ artist+ " ) "+ s1+ "  on  "+ s2+  "\n"+s3;			
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
			previous= previous + "\n\n"+ result;
			tv1.setText(previous);
		}
    }
	

}
