package test.com.carwash;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class First_Screen extends ActionBarActivity implements View.OnClickListener{
   // TextView tv;
    EditText et11,et22;
    Button b,c;
    UserLocalStore userLocalStore;
String name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        initialize();
       // tv.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
       // Intent sender=getIntent();
        // name=sender.getExtras().getString("Name");
       //  phone=sender.getExtras().getString("Phone");


        userLocalStore = new UserLocalStore(this);

    }

    private void initialize()
    {
        //tv=(TextView)findViewById(R.id.tv);
        et11=(EditText)findViewById(R.id.et11);
        et22=(EditText)findViewById(R.id.et22);

        b=(Button)findViewById(R.id.b1);
        c=(Button)findViewById(R.id.b2);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.b1:
                String username = et11.getText().toString();
                String password = et22.getText().toString();

                Intent i=new Intent(this,MainActivity.class);
                //i.putExtra("Name",username);
               // i.putExtra("Phone",phone);
                User user = new User(username, password);
                if (username.matches("")||password.matches("")) {
                    Toast.makeText(this, "You did not enter a username or password.Please enter correct details and try to login", Toast.LENGTH_LONG).show();
                    return;
                }else {

                    authenticate(user);
                }
                break;
        }
    }
public void doSomething(View v){
    startActivity(new Intent(this, ForgotPass.class));

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(First_Screen.this);
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
