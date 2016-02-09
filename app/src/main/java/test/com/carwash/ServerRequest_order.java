package test.com.carwash;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;

/**
 * Created by DELL on 6/11/2015.
 */
public class ServerRequest_order {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://yatnaverma.dx.am/";



    public ServerRequest_order(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void orderInBackground(Order order,
                                          GetOrderCallback orderCallBack) {
        progressDialog.show();
        new orderAsyncTask(order, orderCallBack).execute();
    }

    public class orderAsyncTask extends AsyncTask<Void, Void, Void> {
        Order order;
        GetOrderCallback orderCallBack;

        public orderAsyncTask(Order order,
                              GetOrderCallback orderCallBack) {
            this.order = order;
            this.orderCallBack = orderCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", order.user_id + ""));
            dataToSend.add(new BasicNameValuePair("order_type", order.order_type));
            dataToSend.add(new BasicNameValuePair("amount", order.amount));

            dataToSend.add(new BasicNameValuePair("timestamp", order.timestamp));
            dataToSend.add(new BasicNameValuePair("description", order.description));
            dataToSend.add(new BasicNameValuePair("pck_address", order.address1));
            dataToSend.add(new BasicNameValuePair("del_address", order.address2));
            dataToSend.add(new BasicNameValuePair("model", order.model));
            dataToSend.add(new BasicNameValuePair("paymentmode", order.paymentmode));


            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS
                    + "PlaceOrder.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            orderCallBack.done(null);
        }

    }
}
