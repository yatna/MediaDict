package test.com.carwash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by nairit on 30/5/15.
 */
public class SignUpwithDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupwithdetails);

    }
    public void submit(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}
