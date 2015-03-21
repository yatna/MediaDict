package yatna.mediadict;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FollowM {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "song_name";
	
	
	
	private static final String DATABASE_NAME = "Dbtwo";
	private static final String DATABASE_TABLE = "Tabletwo";
	private static final int DATABASE_VERSION = 1;
	
	
	private DBHelper ourHelper1;
	private final Context ourContext1;
	private SQLiteDatabase ourDatabase1;
	
	private static class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			arg0.execSQL("CREATE TABLE "+ DATABASE_TABLE + " (" +
	                 KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					 KEY_NAME + " TEXT NOT NULL);"
				    
	                 );	
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			arg0.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
			onCreate(arg0);	
		}
	}
	

	
	


public FollowM(Context c){
	ourContext1 = c;
}
public FollowM open() throws SQLException{
	ourHelper1= new DBHelper(ourContext1);
	ourDatabase1=ourHelper1.getWritableDatabase();
	return this;
	
}
public void close(){
	ourHelper1.close();
}
public long createEntry(String name) {
	// TODO Auto-generated method stub
	ContentValues cv=new ContentValues();
	
	cv.put(KEY_NAME, name);
	

	return ourDatabase1.insert(DATABASE_TABLE, null, cv);
	
}
public String getData() throws SQLException {
	// TODO Auto-generated method stub
	String[] columns =new String[]{KEY_ROWID, KEY_NAME};
	Cursor c= ourDatabase1.query(DATABASE_TABLE, columns, null, null, null, null, null);
	 String result="";
	 int iRow=c.getColumnIndex(KEY_ROWID);
	 int iName=c.getColumnIndex(KEY_NAME);
	 
	 
	 for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
		 result=result+ c.getString(iRow) +"  "+ c.getString(iName)+ " \n" ;
				 
		
	 }
	 
	 return result;
}

public void deleteEntry(long lRow1) throws SQLException{
	// TODO Auto-generated method stub
	ourDatabase1.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	
}
}
