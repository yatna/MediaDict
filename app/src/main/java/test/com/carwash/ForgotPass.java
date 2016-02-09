package test.com.carwash;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgotPass extends ActionBarActivity implements View.OnClickListener{
    EditText username, phone;
    String usr, phn;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass);
        username = (EditText) findViewById(R.id.et3);
        phone = (EditText) findViewById(R.id.et4);

        b= (Button) findViewById(R.id.b);
        b.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.b:
                usr=username.getText().toString();
                phn=phone.getText().toString();
                Forgot forgot= new Forgot(usr,phn);
                registerUser(forgot);
               // Intent loginIntent = new Intent(this, First_Screen.class);
               // startActivity(loginIntent);
                break;
        }
    }

    private void registerUser(Forgot forgot) {
        ServerRequest_forgot serverRequest = new ServerRequest_forgot(this);
        serverRequest.forgotInBackground(forgot, new GetForgotCallback() {
            @Override
            public void done(Forgot returnedUser) {
                Toast.makeText(getApplicationContext(), "Your New Password will soon be sent!!", Toast.LENGTH_LONG).show();

                final Intent intent = new Intent(ForgotPass.this, First_Screen.class);

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
        });
    }
}


