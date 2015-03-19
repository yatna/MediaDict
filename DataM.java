package yatna.mediadict;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataM {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "song_name";
	public static final String KEY_ARTIST = "artist_name";
	public static final String KEY_DATE = "date_name";
	public static final String KEY_GENRE = "genre_name";
	public static final String KEY_COUNTRY = "country_name";
	public static final String KEY_LENGHT = "lenght_name";
	public static final String KEY_EXPLICITNESS = "exp_name";
	public static final String KEY_LYRICS = "lyrics_name";
	public static final String KEY_TAG = "tag_name";
	public static final String KEY_SCORE = "score_sum";
//	public static final String KEY_COMMENT = "com_sum";
//	public static final String KEY_SUM5 = "mem5_sum";
//	public static final String KEY_SUM6 = "mem6_sum";
//	public static final String KEY_DES  = "group_des";
	
	
	private static final String DATABASE_NAME = "MainDbThree";
	private static final String DATABASE_TABLE = "mainTableThree";
	private static final int DATABASE_VERSION = 1;
	
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+ DATABASE_TABLE + " (" +
                     KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					 KEY_NAME + " TEXT NOT NULL, " +
				     KEY_ARTIST + " TEXT NOT NULL, " +
					 KEY_DATE + " TEXT NOT NULL, " +
					 KEY_GENRE + " TEXT NOT NULL, " +
					 KEY_COUNTRY + " TEXT NOT NULL, " +
					 KEY_LENGHT + " TEXT NOT NULL, " +
					 KEY_EXPLICITNESS + " TEXT NOT NULL, " +
					 KEY_LYRICS + " TEXT NOT NULL, " +
					 KEY_TAG + " TEXT NOT NULL, " +
					 KEY_SCORE + " INTEGER);"
//					 KEY_COMMENT + " TEXT NOT NULL, " +
//					 KEY_SUM5 + " INTEGER, " +
//					 KEY_SUM6 + " INTEGER, " +
//					 KEY_DES + " TEXT NOT NULL);"
                     );
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public DataM(Context c){
		ourContext = c;
	}
	public DataM open() throws SQLException{
		ourHelper= new DBHelper(ourContext);
		ourDatabase=ourHelper.getWritableDatabase();
		return this;
		
	}
	public void close(){
		ourHelper.close();
	}
	public long createEntry(String name, String artist, String date, String genre, String country, String lenght, String ex, String lyrics, String tag, int score) {
		// TODO Auto-generated method stub
		ContentValues cv=new ContentValues();
		int zero=0;
		String des="";
		cv.put(KEY_NAME, name);
		cv.put(KEY_ARTIST, artist);
		cv.put(KEY_DATE,date);
		cv.put(KEY_GENRE, genre);
		cv.put(KEY_COUNTRY,country);
		cv.put(KEY_LENGHT, lenght);
		cv.put(KEY_EXPLICITNESS,ex);
		cv.put(KEY_LYRICS, lyrics);
		cv.put(KEY_TAG,tag);
		cv.put(KEY_SCORE, score);
//		cv.put(KEY_COMMENT,mem5);
//		cv.put(KEY_SUM6,zero);
//		cv.put(KEY_MEM6,mem6);
//		cv.put(KEY_DES, des);
    	return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}
	public String getData() throws SQLException {
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_ARTIST, KEY_DATE, KEY_GENRE, KEY_COUNTRY, KEY_LENGHT, KEY_EXPLICITNESS, KEY_LYRICS, KEY_TAG, KEY_SCORE};
		Cursor c= ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		 String result="";
		 int iRow=c.getColumnIndex(KEY_ROWID);
		 int iName=c.getColumnIndex(KEY_NAME);
		 int iMem1=c.getColumnIndex(KEY_ARTIST);
		 int iMem2=c.getColumnIndex(KEY_DATE);
		 int iMem3=c.getColumnIndex(KEY_GENRE);
		 int iMem4=c.getColumnIndex(KEY_COUNTRY);
		 int iMem5=c.getColumnIndex(KEY_LENGHT);
		 int iMem6=c.getColumnIndex(KEY_EXPLICITNESS);
		 int iSum1=c.getColumnIndex(KEY_LYRICS);
		 int iSum2=c.getColumnIndex(KEY_TAG);
		 int iSum3=c.getColumnIndex(KEY_SCORE);
//		 int iSum4=c.getColumnIndex(KEY_COMMENT);
//		 int iSum5=c.getColumnIndex(KEY_SUM5);
//		 int iSum6=c.getColumnIndex(KEY_SUM6);
//		 int iDes=c.getColumnIndex(KEY_DES);
		 
		 for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			 result=result+ c.getString(iRow) +"  "+ c.getString(iName)+ "    " +
					 c.getString(iMem1)+ " " +
					 c.getString(iSum1)+ "  " +
					 c.getString(iMem2)+ " " +
					 c.getString(iSum2)+ "  " +
					 c.getString(iMem3)+ " " +
					 c.getInt(iSum3)+ "  " +
					 c.getString(iMem4)+ " " +
					 //c.getInt(iSum4)+ "  " +
					 c.getString(iMem5)+ " " +
					// c.getInt(iSum5)+ "  " +
					 c.getString(iMem6)+ "\n" ;
					// c.getInt(iSum6) + "\n";
			 
		 }
		 
		 return result;
	}
	public String[] getDetails(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME,  KEY_ARTIST, KEY_DATE, KEY_GENRE, KEY_COUNTRY, KEY_LENGHT, KEY_EXPLICITNESS, KEY_LYRICS, KEY_TAG, KEY_SCORE};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String s1=c.getString(1);
			String s2=c.getString(2);
			String s3=c.getString(3);
			String s4=c.getString(4);
			String s5=c.getString(5);
			String s6=c.getString(6);
			String s7=c.getString(7);
			String s8=c.getString(8);
			String s9=c.getString(9);
			String s10=c.getString(10);
			String[] arr={s1,s2,s3,s4,s5,s6,s7,s8,s9,s10};
			
			return arr;
		}
		return null;
	}
	/*public String getMem1(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(2);
			return Mem1;
		}
		return null;
	}
	public String getMem2(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(3);
			return Mem1;
		}
		return null;
	}
	public String getMem3(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(4);
			return Mem1;
		}
		return null;
		
	}
	public String getMem4(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(5);
			return Mem1;
		}
		return null;
	}
	public String getMem5(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(6);
			return Mem1;
		}
		return null;
	}
	public String getMem6(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(7);
			return Mem1;
		}
		return null;
	}
	
	
	public int getSum1(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6,KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(8);
			return Mem1;
		}
		return (Integer) null;
	}
	

	public int getSum2(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(9);
			return Mem1;
		}
		return (Integer) null;
	}
	

	public int getSum3(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(10);
			return Mem1;
		}
		return (Integer) null;
	}

	public int getSum4(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(11);
			return Mem1;
		}
		return (Integer) null;
	}

	public int getSum5(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(12);
			return Mem1;
		}
		return (Integer) null;
	}

	public int getSum6(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			int Mem1=c.getInt(13);
			return Mem1;
		}
		return (Integer) null;
	}
	
	public String getComment(long l) throws SQLException{
		// TODO Auto-generated method stub
		String[] columns =new String[]{KEY_ROWID, KEY_NAME, KEY_MEM1, KEY_MEM2, KEY_MEM3, KEY_MEM4, KEY_MEM5, KEY_MEM6, KEY_SUM1, KEY_SUM2, KEY_SUM3, KEY_SUM4, KEY_SUM5, KEY_SUM6, KEY_DES};
		Cursor c=ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String Mem1=c.getString(14);
			return Mem1;
		}
		return null;
	}*/
	public void updateEntry(long lRow,int score) throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cvUpdate= new ContentValues();
		cvUpdate.put(KEY_SCORE, score);
//		cvUpdate.put(KEY_MEM2, mMem2);
//		cvUpdate.put(KEY_MEM3, mMem3);
//		cvUpdate.put(KEY_MEM4, mMem4);
//		cvUpdate.put(KEY_MEM5, mMem5);
//		cvUpdate.put(KEY_MEM6, mMem6);
		
		ourDatabase.update(DATABASE_TABLE,cvUpdate,KEY_ROWID +"="+ lRow,null);
		
		
	}
	
	/*public void updateSum(long lRow, int mMem1, int mMem2,int mMem3,int mMem4,int mMem5,int mMem6, String s) throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cvUpdate= new ContentValues();
		cvUpdate.put(KEY_SUM1, mMem1);
		cvUpdate.put(KEY_SUM2, mMem2);
		cvUpdate.put(KEY_SUM3, mMem3);
		cvUpdate.put(KEY_SUM4, mMem4);
		cvUpdate.put(KEY_SUM5, mMem5);
		cvUpdate.put(KEY_SUM6, mMem6);
		cvUpdate.put(KEY_DES, s);
		
		ourDatabase.update(DATABASE_TABLE,cvUpdate,KEY_ROWID +"="+ lRow,null);
		
		
	}*/
	public void deleteEntry(long lRow1) throws SQLException{
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
		
	}
	


}

