package test.com.carwash;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nairit on 9/7/15.
 */
public class CaraskInstant1 extends ActionBarActivity implements View.OnClickListener {
    Button  btnTimePicker;
    EditText et,et2;
    String t,tt;
    Spinner spBusinessType;
    String businessType[] = { "A","B","C","D","E","F","G"};
    EditText  txtTime;
    Spinner spCountries;
    String s,Type1,Type2,OILCHANGE,SERVICE,WASHING,POLISHING,AIRCLEANING,EXTRAONDEMAND,currentadd,deladd;
    TextView etdate,etdate2;
    String carmodel="",carbrand="";
    // Adapter
    ArrayAdapter<String> adapterBusinessType;
    String name,phone,username,address;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carask_instant);


       String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Intent intent=getIntent();
        s=intent.getStringExtra("Total Sum");
        // currentadd=intent.getStringExtra("currentadd");
         //deladd=intent.getStringExtra("currentadd");
        Type1=intent.getStringExtra("Instant");
        OILCHANGE=intent.getStringExtra("OILCHANGE");
        SERVICE=intent.getStringExtra("SERVICE");
        WASHING=intent.getStringExtra("WASHING");
        POLISHING=intent.getStringExtra("POLISHING");
        AIRCLEANING=intent.getStringExtra("AIRCLEANING");
        EXTRAONDEMAND=intent.getStringExtra("EXTRAONDEMAND");
        et2= (EditText) findViewById(R.id.deladd);
        et= (EditText) findViewById(R.id.pckadd);
        System.out.println(currentadd+"####################");
        System.out.println(deladd+"####################");
        etdate= (TextView) findViewById(R.id.etdate);
        etdate2= (TextView) findViewById(R.id.etdate2);
        etdate2.setText(date);
        etdate.setText(date);
et.setText("Your GPS Location");
        et2.setText("Your GPS Location");




         t=et.getText().toString();
         tt=et2.getText().toString();
      //  btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);

       // txtDate = (EditText) findViewById(R.id.txtDate);
        txtTime = (EditText) findViewById(R.id.txtTime);

       // btnCalendar.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        spCountries = (Spinner) findViewById(R.id.spCountries);
        spBusinessType = (Spinner) findViewById(R.id.spBussinessType);

        // Initialize and set Adapter
        adapterBusinessType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, businessType);
        spBusinessType.setAdapter(adapterBusinessType);

        // Country Item Selected Listener
        spCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();
                carbrand=item;
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(),
                        "Car Brand : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        // Business Type Item Selected Listener
        spBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();
carmodel=item;
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(),
                        "Car Model : " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onClick(View v) {

     /*   if (v == btnCalendar) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }*/
        if (v == btnTimePicker) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
    }
    public void next(View v){

        t=et.getText().toString();
        tt=et2.getText().toString();
        Intent i=new Intent(this,CartnewInstant.class);
        i.putExtra("Total Sum", s);
        i.putExtra("Instant",Type1);
        i.putExtra("carmodel",carmodel);
        i.putExtra("currentadd",t);
        i.putExtra("deladd",tt);
        i.putExtra("carbrand",carbrand);
        i.putExtra("OILCHANGE",OILCHANGE);
        i.putExtra("SERVICE",SERVICE);
        i.putExtra("WASHING",WASHING);
        i.putExtra("POLISHING",POLISHING);
        i.putExtra("AIRCLEANING",AIRCLEANING);
        i.putExtra("EXTRAONDEMAND",EXTRAONDEMAND);
        startActivity(i);

    }
}
