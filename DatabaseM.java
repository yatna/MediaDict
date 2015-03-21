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

public class DatabaseM extends Activity implements OnClickListener {
  
Button view,edit,delete,viewE;
EditText id1,score;
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
	score=(EditText)findViewById(R.id.et2);
	
	view=(Button)findViewById(R.id.b1);
	viewE=(Button)findViewById(R.id.b2);
	edit=(Button)findViewById(R.id.b3);
	delete=(Button)findViewById(R.id.b4);
	view.setOnClickListener(this);
	edit.setOnClickListener(this);
	delete.setOnClickListener(this);
	
}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.b1:
			Intent q=new Intent("yatna.DATAVIEWM");
			startActivity(q);
		    break;
		case R.id.b2:
			try {
				String s = id1.getText().toString();
				long lRow = Long.parseLong(s);
				
				DataM hon = new DataM(DatabaseM.this);
				hon.open();
				String[] detail=hon.getDetails(lRow);
				hon.close();
				Dialog d1 = new Dialog(this);
				d1.setTitle("Yeah !!");
				TextView tv1 = new TextView(this);
				
				tv1.setText(detail[1]+"  " + detail[2] );
				d1.setContentView(tv1);
				d1.show();
			   
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Dang !!");
				TextView tv = new TextView(this);
				tv.setText("Enter a valid group Id");
				d.setContentView(tv);
				d.show();
				Dialog d1 = new Dialog(this);
				d.setTitle("Yeah !!");
				TextView tv1 = new TextView(this);
				tv.setText("Entry Deleted");
				d.setContentView(tv1);
				d.show();
			}
			
			
		    break;
		case R.id.b3:
			try {
				String s = id1.getText().toString();
				long lRow = Long.parseLong(s);
				String a=score.getText().toString();
				int b=Integer.parseInt(a);
				DataM hon = new DataM(DatabaseM.this);
				hon.open();
				hon.updateEntry(lRow,b);
				hon.close();
			   
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Dang !!");
				TextView tv = new TextView(this);
				tv.setText("Enter a valid group Id");
				d.setContentView(tv);
				d.show();
				Dialog d1 = new Dialog(this);
				d.setTitle("Yeah !!");
				TextView tv1 = new TextView(this);
				tv.setText("Entry Deleted");
				d.setContentView(tv1);
				d.show();
			}
			
		    break;
		case R.id.b4:
			try {
				String sRow1 = id1.getText().toString();
				long lRow1 = Long.parseLong(sRow1);
				DataM ex1 = new DataM(DatabaseM.this);
				ex1.open();
				ex1.deleteEntry(lRow1);
				ex1.close();
				Dialog d = new Dialog(this);
				d.setTitle("Yeah !!");
				TextView tv = new TextView(this);
				tv.setText("Entry Deleted");
				d.setContentView(tv);
				d.show();
			} catch (Exception e) {
				Dialog d = new Dialog(this);
				String error = e.toString();
				d.setTitle("Dang !!");
				TextView tv = new TextView(this);
				tv.setText("Group Id not present");
				d.setContentView(tv);
				d.show();
			}
			
		    break;
		
		}
		
	}

}
