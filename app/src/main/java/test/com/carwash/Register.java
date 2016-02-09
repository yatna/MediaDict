package test.com.carwash;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pushbots.push.Pushbots;


public class Register extends ActionBarActivity implements View.OnClickListener{


    EditText et1,et2,et3,et4,et5;
    Button b1;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       initialize();
        b1.setOnClickListener(this);
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setPushEnabled(true);



    }

    private void initialize()
    {

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        et5= (EditText) findViewById(R.id.et5);
        b1=(Button)findViewById(R.id.b2);
        userLocalStore = new UserLocalStore(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.b2:
                String name = et1.getText().toString();
                String username = et3.getText().toString();
                String password = et4.getText().toString();
                String address=et5.getText().toString();
                String phone = et2.getText().toString();
                Pushbots.sharedInstance().setAlias(name);
                Pushbots.sharedInstance().tag("customer");
                int id=0;
                Intent i=new Intent(this,MainActivity.class);
                i.putExtra("Name",name);
                i.putExtra("Phone", phone);
                i.putExtra("Email",username);
                i.putExtra("address",address);
                User user = new User(id,name, phone, username, password,address);
                if (username.matches("")||password.matches("")||name.matches("")||address.matches("")||phone.matches("")) {
                    Toast.makeText(this, "Enter all details and then register", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    registerUser(user);
                }



               // Bundle bundle = new Bundle();
               // bundle.putString("Name",name );
               // bundle.putString("Phone",age);
// set Fragmentclass Arguments
               // NavigationDrawerFragment fragobj = new NavigationDrawerFragment();
               // fragobj.setArguments(bundle);


               // startActivity(i);
                break;

        }

    }

   private void registerUser(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                String username = et3.getText().toString();
                String password = et4.getText().toString();

                User user = new User(username, password);

                authenticate(user);

            }
        });
    }

    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }
}
