//package yatna.mediadict;
//
//import java.io.IOException;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class test extends Activity implements OnClickListener {
// TextView tv1,tv2,tv3,tv4,tv5,tv6;
// Button following,f_more,f_less;
// EditText et1,et2;
// HttpClient client;
// JSONObject json;
// JSONArray arr;
// Toast t;
// int i;
// String artist="",previous="";
// static String URL= "http://content.guardianapis.com/search?q=";
// 
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.follow);
//		initialize();
//	}
//	  private void initialize()
//	  {
//		  tv1=(TextView)findViewById(R.id.tv1);
//		  tv2=(TextView)findViewById(R.id.tv2);
//		  tv3=(TextView)findViewById(R.id.tv3);
//		  tv4=(TextView)findViewById(R.id.tv4);
//		  tv5=(TextView)findViewById(R.id.tv5);
//		  tv6=(TextView)findViewById(R.id.tv6);
//		 
//		  et1=(EditText)findViewById(R.id.et1);
//		  et2=(EditText)findViewById(R.id.et2);
//		  following=(Button)findViewById(R.id.b1);
//		  f_more=(Button)findViewById(R.id.b2);
//		  f_less=(Button)findViewById(R.id.b3);
//		  following.setOnClickListener(this);
//		  f_more.setOnClickListener(this);
//		  f_less.setOnClickListener(this);
//		 
//		  
//		  
//					
//					
//	  }
//	  public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			switch(arg0.getId()){
//			case R.id.b1:
//				FollowM info =new FollowM(this);
//			     info.open();
//			     String data= info.getData();
//			     info.close();
//			     tv2.setText(data);
//			    break;
//	        case R.id.b2:
//	        	
//				String s=et1.getText().toString();
//	        	FollowM entry = new FollowM(test.this);
//				entry.open();
//				entry.createEntry(s);
//				entry.close();
//				FollowM info1 =new FollowM(this);
//			     info1.open();
//			     String data1= info1.getData();
//			     info1.close();
//			     tv2.setText(data1);
//			    break;
//	        case R.id.b3:
//	        	try {
//					String sRow1 = et2.getText().toString();
//					long lRow1 = Long.parseLong(sRow1);
//					if(lRow1>0){
//						FollowM ex1 = new FollowM(test.this);
//					
//					ex1.open();
//					ex1.deleteEntry(lRow1);
//					ex1.close();
//					FollowM info2 =new FollowM(this);
//				     info2.open();
//				     String data2= info2.getData();
//				     info2.close();
//				     tv2.setText(data2);
//
//					}
//				} catch (Exception e) {
//					Dialog d = new Dialog(this);
//					String error = e.toString();
//					d.setTitle("Oops !!");
//					TextView tv = new TextView(this);
//					tv.setText("Artist Id not present");
//					d.setContentView(tv);
//					d.show();
//				}
//				
//			    break;   
//			}
//		}
//
//		
//		
//	}