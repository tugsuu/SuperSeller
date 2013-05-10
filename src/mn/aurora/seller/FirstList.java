package mn.aurora.seller;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleCursorAdapter;

public class FirstList extends Activity{
	SeekBar sb;
	TextView txt3;
	ListView list;
	Cursor cursor;
	SimpleCursorAdapter cursorAdapt;
	SQLiteAdapter sqLiteAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_firstlist);
		
		txt3 = (TextView)findViewById(R.id.text3);
		sb = (SeekBar)findViewById(R.id.sb1);
		list = (ListView)findViewById(R.id.listView1);
		sqLiteAdapter = new SQLiteAdapter(this);
		sqLiteAdapter.openToRead();
//		, R.id.sb1, R.id.tv3
		cursor = sqLiteAdapter.queueProductName();
		String[] from = new String[]{SQLiteAdapter.PRODUCT_ID, SQLiteAdapter.PRODUCT_NAME};
		int[] to = new int[]{R.id.id, R.id.tv1};
		cursorAdapt = new SimpleCursorAdapter(this, R.layout.row_first, cursor, from, to);
		list.setAdapter(cursorAdapt);		
//		list.setOnItemClickListener(listContentOnItemClickListener);
		
	}
	
//	private ListView.OnItemClickListener listContentOnItemClickListener = new ListView.OnItemClickListener(){
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long arg3) {
//			// TODO Auto-generated method stub
////			Cursor cursor = (Cursor) arg0.getItemAtPosition(position);
//			
//			
//		}
//	
//	
//	};
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sqLiteAdapter.close();
	}

	private void updateList(){
		cursor.requery();
  }
}
