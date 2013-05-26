package mn.aurora.seller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleCursorAdapter;

public class FirstList extends Activity implements OnClickListener{
	SeekBar sb;
	TextView txt3;
	ListView list;
	Cursor cursor;
	Button btnBack;
	SimpleCursorAdapter cursorAdapt;
	SQLiteAdapter sqLiteAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_firstlist);
		
		txt3 = (TextView)findViewById(R.id.text3);
		sb = (SeekBar)findViewById(R.id.firstSeekbar);
		list = (ListView)findViewById(R.id.listView1);
		btnBack = (Button)findViewById(R.id.backFirstList);
		
		btnBack.setOnClickListener(this);
		sqLiteAdapter = new SQLiteAdapter(this);
		sqLiteAdapter.openToRead();
		cursor = sqLiteAdapter.queueProductName();
		String[] from = new String[]{SQLiteAdapter.PRODUCT_ID, SQLiteAdapter.PRODUCT_NAME};
		int[] to = new int[]{R.id.id, R.id.tv1};
		cursorAdapt = new SimpleCursorAdapter(this, R.layout.row_first, cursor, from, to);
		list.setAdapter(cursorAdapt);		
		
//		list.setOnItemClickListener(listContentOnItemClickListener);
//		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//			}
//			
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress,
//					boolean fromUser) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==btnBack) {
			if(DataManager.getForm()==1)
			{
				Intent main = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(main);
				finish();
			}
		}
	}
	
//	private ListView.OnItemClickListener listContentOnItemClickListener = new ListView.OnItemClickListener(){
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long arg3) {
//			// TODO Auto-generated method stub
////			Cursor cursor = (Cursor) arg0.getItemAtPosition(position);
//			sb.set
//			
//		}
//	
//	
//	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(DataManager.getForm()==1)
		{
			Intent main = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(main);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sqLiteAdapter.close();
	}

	private void updateList(){
		cursor.requery();
  }
}
