package test.com.carwash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by nairit on 29/5/15.
 */
public class AdvancedBooking extends Activity {
    private CheckBox box1,box2,box3,box4,box5,box6;
    TextView tv22;
    String OILCHANGE="OIL CHANGE";
    String SERVICE="SERVICE",WASHING="WASHING",POLISHING="POLISHING",AIRCLEANING="AIR CLEANING",EXTRAONDEMAND="EXTRA ON DEMAND";
    String c,advance="Adavanced Booking";
    Double Sum=0.0,b1=0.0,b2=0.0,b3=0.0,b4=0.0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advancebook1);
        tv22= (TextView) findViewById(R.id.tv22);

        addListenerbox1();
        addListenerbox2();
        addListenerbox3();
        addListenerbox4();
        addListenerbox5();
        addListenerbox6();
        // addListenerbox12();
        //Sum=b1+b2+b3+b4;








    }
    public void proceed(View v){
        Intent i=new Intent(this,CaraskAdvance1.class);
        i.putExtra("Total Sum", c);
        i.putExtra("Advance", advance);
        if (box1.isChecked()){
            i.putExtra("OILCHANGE",OILCHANGE);

        }

        if(box2.isChecked()){
            i.putExtra("SERVICE",SERVICE);

        }
        if(box3.isChecked()){
            i.putExtra("WASHING",WASHING);

        }
        if(box4.isChecked()){
            i.putExtra("POLISHING",POLISHING);

        }
        if(box5.isChecked()){
            i.putExtra("AIRCLEANING",AIRCLEANING);

        }
        if(box6.isChecked()){
            i.putExtra("EXTRAONDEMAND",EXTRAONDEMAND);

        }

        startActivity(i);

    }



    public void addListenerbox1() {
        box1 = (CheckBox) findViewById(R.id.checkBox1);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 1000.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked()) != true) {
                    box1.setChecked(false);
//not checked
                    Sum -= 1000.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }



    public void addListenerbox2() {
        box2 = (CheckBox) findViewById(R.id.checkBox2);
        box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 1500.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box2.setChecked(false);
//not checked
                    Sum-=1500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox3() {
        box3 = (CheckBox) findViewById(R.id.checkBox3);
        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox4() {
        box4 = (CheckBox) findViewById(R.id.checkBox4);
        box4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 10000.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=10000.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox5() {
        box5 = (CheckBox) findViewById(R.id.checkBox5);
        box5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    Sum += 2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
//checked
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    Sum-=2500.0;
                    c = Sum.toString();
                    tv22.setText(c);
                }

            }
        });
    }
    public void addListenerbox6() {
        box6 = (CheckBox) findViewById(R.id.checkBox6);
        box6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {

//checkedSys
                    System.out.print("checked");
                } else if ((buttonView.isChecked())!=true) {
                    box1.setChecked(false);
//not checked
                    System.out.print("not checked");
                }

            }
        });
    }
}
