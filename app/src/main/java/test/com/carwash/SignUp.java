package test.com.carwash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by nairit on 30/5/15.
 */
public class SignUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

    }
    public void enter_details(View v){

        startActivity(new Intent(this, SignUpwithDetails.class));
    }
}
