package yatna.mediadict;



import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

public class DataViewM extends Activity {

	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_dataview);
		tv =(TextView)findViewById(R.id.tv);
	     DataM info =new DataM(this);
	     info.open();
	     String data= info.getData();
	     info.close();
	     try{tv.setText(data);
	     }catch(Exception e){
	    	 Dialog d = new Dialog(this);
	         TextView t = new TextView(this);
	         t.setText(e.toString());
	         d.setContentView(t);
	         d.show();
	     }
	}

}
