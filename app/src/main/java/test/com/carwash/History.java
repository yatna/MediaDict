package test.com.carwash;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class History extends ActionBarActivity {

    TextView t;
    HttpClient client;
    ProgressDialog pgb;
    //ArrayList<OrderList> allLocations = new ArrayList<>();
    String id;
    private Toolbar toolbar;
   // ListView list;
    String s1,s2,s3,s4,s5;
    UserLocalStore userLocalStore;
    static String URL= "http://yatnaverma.dx.am/UserSide/History.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
       // list= (ListView) findViewById(R.id.list);
        t=(TextView)findViewById(R.id.h1);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        client = new DefaultHttpClient();
        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        id=user.id+"";
        showPgb();
        new Read().execute();

    }




    public JSONArray history()throws ClientProtocolException,IOException,JSONException {
        StringBuilder url= new StringBuilder(URL);
        HttpPost post =new HttpPost(url.toString());
        ArrayList<NameValuePair> postParameters;

        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("users_id",id));


        post.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse r= client.execute(post);
        int status=r.getStatusLine().getStatusCode();
        if(status==200){
            HttpEntity e=r.getEntity();
            String data= EntityUtils.toString(e);

            JSONArray jArr=new JSONArray(data);

            return jArr;

        }
        else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public class Read extends AsyncTask<String, Integer, String> {
        String history="";
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                JSONArray json=history();
                for(int i=0;i<json.length();i++)
                {

                     s1= json.getJSONObject(i).getString("order_id");
                    s2= json.getJSONObject(i).getString("order_type");
                     s3= json.getJSONObject(i).getString("amount");
                     s4= json.getJSONObject(i).getString("timestamp");
                    s5=json.getJSONObject(i).getString("status");

                   // orderList.setOrder_id(s1);
                    //orderList.setOrder_type(s2);
                    //orderList.setAmount(s3);
                    //orderList.setTimestamp(s4);
                    //orderList.setStatus(s5);
                    //allLocations.add(orderList);
                   // System.out.print("allocation size:: "+allLocations.size());





                    history = history +"Order Id : "+ s1 + "\n\n" +"Type Of Order : "+ s2 + "\n\n" +"Total Amount : Rs"+ s3 + "\n\n" +"Date & Time : "+ s4 + "\n\n" +"Order Status : "+ s5 +  "\n" + "_________________________________" + " " + "\n\n" + " " ;
                }

return history;




            } catch (ClientProtocolException e) {
                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {

                e.printStackTrace();


            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pgb.dismiss();
            t.setText(result);
            //System.out.println("index " + index + "alllcations.size " + (allLocations.size() - 1));
            //if(index==allLocations.size()-1){
              //  list.setVisibility(View.VISIBLE);
               // OrderListAdapter adapter=new OrderListAdapter(History.this,allLocations);
                //list.setAdapter(adapter);
            }



            }






    public void showPgb()
    {
        pgb = new ProgressDialog(History.this);
        pgb.setMessage("Fetching History ...");
        pgb.setTitle("Processing...");
        pgb.setCancelable(true);
        pgb.show();

    }
}
