package test.com.carwash;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditProfile extends ActionBarActivity implements View.OnClickListener {
    EditText et1,et2,et3,et4;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initialize();
    }
    private void initialize()
    {

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.b1:
                String username="";
                String name = et1.getText().toString();
                String password = et3.getText().toString();
                String address = et4.getText().toString();
               String phone=et2.getText().toString();
                Intent intent=getIntent();
                int a=0;
                int id=intent.getIntExtra("ID",a);
                User user = new User(id,name, phone,username, address, password);
                editUser(user);
                break;

        }


    }


    private void editUser(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.editUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                Intent loginIntent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}
