package test.com.carwash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CardNumber extends ActionBarActivity implements View.OnClickListener{
    EditText CardNo ,year,month,cvv;
    Button addCard;
    UserLocalStore userLocalStore;
    public String MY_PREFS_NAME = "";
    int cn,y,m,cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_number);
        CardNo=(EditText)findViewById(R.id.no);
        year=(EditText)findViewById(R.id.yy);
        month=(EditText)findViewById(R.id.mm);
        cvv=(EditText)findViewById(R.id.cvv);
        addCard=(Button)findViewById(R.id.addCard);
        addCard.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addCard:
                User user = userLocalStore.getLoggedInUser();
                MY_PREFS_NAME= user.id+"";
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
               cn=Integer.parseInt(CardNo.getText().toString());
               m=Integer.parseInt(month.getText().toString());
               y=Integer.parseInt(year.getText().toString());
               cv=Integer.parseInt(cvv.getText().toString());
                editor.putInt("Card Number",cn);
                editor.putInt("Month", m);
                editor.putInt("Year", y);
                editor.putInt("CVV", cv);
                editor.commit();
                startActivity(new Intent(this,MainActivity.class));
                Toast.makeText(getApplicationContext(),"Your Card has been saved for Future Transactions",Toast.LENGTH_LONG).show();


                break;
        }
    }
}
/* to retrieve data
 UserLocalStore userLocalStore;
    public String MY_PREFS_NAME = "";

     userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        MY_PREFS_NAME= user.id+"";

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int cn = prefs.getInt("Card Number", null);
         int m = prefs.getInt("Month", null);
          int y = prefs.getInt("Year", null);
           int cvv = prefs.getInt("CVV", null);

 */