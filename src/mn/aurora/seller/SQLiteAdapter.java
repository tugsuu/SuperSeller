package mn.aurora.seller;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class SQLiteAdapter{

	public static final String MYDATABASE_NAME = "IamSeller";
	public static final int MYDATABASE_VERSION = 1;

	/*----------------------------- SHOP TABLE -------------------------------------------*/
	public static final String SHOP_TABLE = "shop";
	public static final String SHOP_ID = "_id";
	public static final String SHOP_NAME = "shName";
	public static final String SHOP_ADDRESS = "shAddress";
	private static final String SCRIPT_CREATE_SHOP_TABLE =
		"create table if not exists " + SHOP_TABLE + " ("
		+ SHOP_ID + " integer primary key autoincrement, "
		+ SHOP_NAME + " text not null, "
		+ SHOP_ADDRESS + " text not null);";
	
	/*----------------------------- PRODUCT TABLE -------------------------------------------*/
	public static final String PRODUCT_TABLE = "product";
	public static final String PRODUCT_ID = "_id";
	public static final String PRODUCT_NAME = "pName";
	public static final String PRODUCT_COST = "pCost";
	public static final String PRODUCT_TYPE = "pType";
	private static final String SCRIPT_CREATE_PRODUCT_TABLE =
			"create table if not exists " + PRODUCT_TABLE + " ("
					+ PRODUCT_ID + " integer primary key autoincrement, "
					+ PRODUCT_NAME + " text not null, "
					+ PRODUCT_COST + " integer, "
					+ PRODUCT_TYPE + " text not null);";
 
	/*----------------------------- USER TABLE -------------------------------------------*/
	public static final String USER_TABLE = "user";
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "uName";
	public static final String USER_PASS = "uPass";
	private static final String SCRIPT_CREATE_USER_TABLE =
			"create table if not exists " + USER_TABLE + " ("
			+ USER_ID + " integer primary key autoincrement, "
			+ USER_NAME + " text not null, "
			+ USER_PASS + " text not null);";
	
	/*----------------------------- Second TABLE -------------------------------------------*/
	public static final String SECOND_TABLE = "second";
	public static final String SECOND_ID = "_id";
	public static final String SECOND_SHOP = "shName";
	public static final String SECOND_DATE = "date";
	public static final String SECOND_PNAME = "pName";
	public static final String SECOND_COUNT = "count";
	public static final String SECOND_ = "date";
	private static final String SCRIPT_CREATE_SECOND_TABLE =
			"create table if not exists " + SECOND_TABLE + " ("
			+ SECOND_ID + " integer primary key autoincrement, "
			+ SECOND_DATE + " text not null, "
			+ SECOND_SHOP + " text not null, "
			+ SECOND_PNAME + " text not null, "
			+ SECOND_COUNT + " integer not null);";
	
	
	
	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;
	
	public SQLiteAdapter(Context c){
		context = c;
	}
	
	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;	
	}
	
	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;	
	}
	
	public void close(){
		sqLiteHelper.close();
	}
	
	/*----------------------------- SECOND TABLE METHODS -------------------------------------------*/
	public long insertToSecond(String shname, String pname, Integer count, String date){ 
		ContentValues content = new ContentValues();
		content.put(SECOND_SHOP, shname);
		content.put(SECOND_PNAME, pname);
		content.put(SECOND_COUNT, count);
		content.put(SECOND_DATE, date);
		return sqLiteDatabase.insert(SECOND_TABLE, null, content);
	}
	
	public Cursor queueSecondTable(){
		
		String query = "SELECT s._id, s.pName, s.count, s.count*p.pCost as Une, s.date FROM "
				+ "second as s INNER JOIN product as p ON s.pName = p.pName";
		Cursor cursor = sqLiteDatabase.rawQuery(query, null);
		
		return cursor;
	}
	
	/*----------------------------- SHOP TABLE METHODS -------------------------------------------*/
	public long insertShop(String content1, String content2){
		ContentValues contentValues = new ContentValues();
		contentValues.put(SHOP_NAME, content1);
		contentValues.put(SHOP_ADDRESS, content2);
		return sqLiteDatabase.insert(SHOP_TABLE, null, contentValues);
	}
	
	public void delete_byID(int id){
		sqLiteDatabase.delete(SHOP_TABLE, SHOP_ID+"="+id, null);
	}
	
	public void update_byID(int id, String name, String address){
		ContentValues values = new ContentValues();
		values.put(SHOP_NAME, name);
		values.put(SHOP_ADDRESS, address);
		sqLiteDatabase.update(SHOP_TABLE, values, SHOP_ID+"="+id, null);
	}
	
	public Cursor queueAll(){
		String[] columns = new String[]{SHOP_ID, SHOP_NAME, SHOP_ADDRESS};
		Cursor cursor = sqLiteDatabase.query(SHOP_TABLE, columns, 
				null, null, null, null, SHOP_NAME);
		
		return cursor;
	}
	

	public List<String> getAllLabelsShop(){
			
		List<String> value = new ArrayList<String>();
	   	
	        // Select All Query
	        String selectQuery = "SELECT * FROM " + SHOP_TABLE;
	        sqLiteHelper.getReadableDatabase();
	        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	value.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	        
	    	return value;
	}
	/*----------------------------- PRODUCT TABLE METHODS -------------------------------------------*/
	
	public long insertProduct(String content1, Integer content2, String content3){
		ContentValues contentValues = new ContentValues();
		contentValues.put(PRODUCT_NAME, content1);
		contentValues.put(PRODUCT_COST, content2);
		contentValues.put(PRODUCT_TYPE, content3);
		return sqLiteDatabase.insert(PRODUCT_TABLE, null, contentValues);
	}
	
	public void delete_Product(int id){
		sqLiteDatabase.delete(SHOP_TABLE, SHOP_ID+"="+id, null);
	}
	
	public void update_Product(int id, String name, String cost){
		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, name);
		values.put(PRODUCT_COST, cost);
		sqLiteDatabase.update(PRODUCT_TABLE, values, PRODUCT_ID+"="+id, null);
	}
	
	public Cursor queueAllProduct(){
		String[] columns = new String[]{PRODUCT_ID, PRODUCT_NAME, PRODUCT_COST, PRODUCT_TYPE};
		Cursor cursor = sqLiteDatabase.query(PRODUCT_TABLE, columns, 
				null, null, null, null, PRODUCT_NAME);
		
		return cursor;
	}
	public Cursor queueProductName(){
		String[] columns = new String[]{PRODUCT_ID, PRODUCT_NAME};
		Cursor cursor = sqLiteDatabase.query(PRODUCT_TABLE, columns, 
				null, null, null, null, PRODUCT_ID);
		
		return cursor;
	}
	

	public List<String> getAllLabelsProduct(){
		
		List<String> value = new ArrayList<String>();
	   	
	        // Select All Query
	        String selectQuery = "SELECT * FROM " + PRODUCT_TABLE;
	        sqLiteHelper.getReadableDatabase();
	        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	value.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	        
	        // closing connection
	        cursor.close();
//	        sqLiteDatabase.close();
	    	
	    	// returning lables
	    	return value;
	}
	/*----------------------------- USER TABLE METHODS -------------------------------------------*/
	
	public long createUser(String username, String password) {
		ContentValues initialValues = createUserTableContentValues(username, password);
		return sqLiteDatabase.insert(USER_TABLE, null, initialValues);
	}
	
	
	public boolean deleteUser(long rowId) {
		return sqLiteDatabase.delete(USER_TABLE, USER_ID + "=" + rowId, null) > 0;
	}
	
	public boolean updateUserTable(long rowId, String username, String password) {
		ContentValues updateValues = createUserTableContentValues(username, password);
		return sqLiteDatabase.update(USER_TABLE, updateValues, USER_ID + "=" + rowId, null) > 0;
	}

	public Cursor fetchAllUsers() {
		return sqLiteDatabase.query(USER_TABLE, new String[] { USER_ID, USER_NAME, 
				USER_PASS }, null, null, null, null, null);
	}
	
	public Cursor fetchUser(String username, String password) {
		Cursor myCursor = sqLiteDatabase.query(USER_TABLE, 
				new String[] { USER_ID, USER_NAME, USER_PASS }, 
				USER_NAME + "='" + username + "' AND " + 
				USER_PASS + "='" + password + "'", null, null, null, null);
		
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		return myCursor;
	}
	
	public Cursor fetchUserById(long rowId) throws SQLException {
		Cursor myCursor = sqLiteDatabase.query(USER_TABLE, 
				new String[] { USER_ID, USER_NAME, USER_PASS }, 
				USER_ID + "=" + rowId, null, null, null, null);
		if (myCursor != null) {
			myCursor.moveToFirst();
		}
		return myCursor;
	}

	private ContentValues createUserTableContentValues(String username, String password) {
		ContentValues values = new ContentValues();
		values.put(USER_NAME, username);
		values.put(USER_PASS, password);
		return values;
	}
	
	/*----------------------------- SQLiteOpenHelper Methods -------------------------------------------*/
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		
			db.execSQL(SCRIPT_CREATE_SHOP_TABLE);
			db.execSQL(SCRIPT_CREATE_PRODUCT_TABLE);
			db.execSQL(SCRIPT_CREATE_USER_TABLE);
			db.execSQL(SCRIPT_CREATE_SECOND_TABLE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(SQLiteDatabase.class.getName(),
					"Upgrading databse from version" + oldVersion + "to " 
					+ newVersion + ", which will destroy all old data");
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shop");
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS second");
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS product");
			onCreate(sqLiteDatabase);
		}
	
	}

	
}
