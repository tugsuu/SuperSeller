package mn.aurora.seller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;

public class FirstActivity extends Activity{
	
	SQLiteAdapter sqLiteAdapter;
	Cursor cursor;
	Integer i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first);
		sqLiteAdapter = new SQLiteAdapter(this);
		sqLiteAdapter.openToRead();
		cursor = sqLiteAdapter.fetchAllUsers();
		
		Thread logoTimer = new Thread(){
			public void run()
			{
				try {
						sleep(2000);
						Move();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		
		logoTimer.start();
	}
	public void Move(){
		
		if(cursor.getCount()>0){
			
			Intent moveLogin = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(moveLogin);
			finish();
		}else {
			Intent moveCreate = new Intent(getApplicationContext(), CreateUserActivity.class);
			startActivity(moveCreate);
			finish();
		}
	sqLiteAdapter.close();	
	}
}
