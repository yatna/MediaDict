package yatna.mediadict;



import yatna.mediadict.MainActivity.Read;
import yatna.mediadict.MainActivity.Read2;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class DatabaseM extends Activity implements OnClickListener, OnRatingBarChangeListener {
  
Button view,edit,delete,viewE;
TextView tv1;
EditText id1;
RatingBar score;
String Score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.databasem);
		initialize();
		
	}

	

	  private void initialize()
{

	id1=(EditText)findViewById(R.id.et1);
	score=(RatingBar)findViewById(R.id.rb1);
	tv1=(TextView)findViewById(R.id.tv1);
	view=(Button)findViewById(R.id.b1);
	viewE=(Button)findViewById(R.id.b2);
	edit=(Button)findViewById(R.id.b3);
	delete=(Button)findViewById(R.id.b4);
	id1.setOnClickListener(this);
	view.setOnClickListener(this);
	viewE.setOnClickListener(this);
	edit.setOnClickListener(this);
	delete.setOnClickListener(this);
	score.setOnRatingBarChangeListener(this);
	
}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.b1:
			Intent q=new Intent("yatna.DATAVIEWM");
			startActivity(q);
		    break;
		case R.id.et1:
			
		    break;
		case R.id.b2:
			try {
				String s = id1.getText().toString();
				long lRow2= Long.parseLong(s);
				if(lRow2>0){
				DataM hon = new DataM(DatabaseM.this);
				hon.open();
				String detail=hon.getDetails(lRow2);
				hon.close();
				tv1.setText(detail);
//				Dialog d1 = new Dialog(this);
//				d1.setTitle("Details");
//				TextView tv1 = new TextView(this);
//				tv1.setText(detail );
//				d1.setContentView(tv1);
//				d1.show();
				}
			   
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Oops!!");
				TextView tv = new TextView(this);
				tv.setText("Enter a valid group Id");
				d.setContentView(tv);
				d.show();
				
			}
			
			
		    break;
		case R.id.b3:
			try {
				String s = id1.getText().toString();
				long lRow = Long.parseLong(s);
				if(lRow>0)
				{
				DataM hon = new DataM(DatabaseM.this);
				hon.open();
				hon.updateEntry(lRow,Score);
				hon.close();
//				Dialog d1 = new Dialog(this);
//				d1.setTitle("Yeah !!");
//				TextView tv1 = new TextView(this);
//				tv1.setText("Score updated");
//				d1.setContentView(tv1);
//				d1.show();
				}
			   
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Oops !!");
				TextView tv = new TextView(this);
				tv.setText("Group Id not present");
				d.setContentView(tv);
				d.show();
				
			}
			
		    break;
		case R.id.b4:
			try {
				String sRow1 = id1.getText().toString();
				long lRow1 = Long.parseLong(sRow1);
				if(lRow1>0){
					DataM ex1 = new DataM(DatabaseM.this);
				
				ex1.open();
				ex1.deleteEntry(lRow1);
				ex1.close();
//				Dialog d = new Dialog(this);
//				d.setTitle("Yeah !!");
//				TextView tv = new TextView(this);
//				tv.setText("Entry Deleted");
//				d.setContentView(tv);
//				d.show();
				}
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Oops !!");
				TextView tv = new TextView(this);
				tv.setText("Group Id not present");
				d.setContentView(tv);
				d.show();
			}
			
		    break;
		
		}
		
	}



	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
		Toast.makeText(DatabaseM.this, String.valueOf(arg1), Toast.LENGTH_SHORT).show();
		if(arg1>0){
			edit.setEnabled(true);
			Score=""+ arg1;
		}
		
	}

}
