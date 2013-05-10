package mn.aurora.seller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SellProduct extends Activity implements OnClickListener{
	private SQLiteAdapter sqLiteDatabase;
	String formattedDate;
	Cursor cursor;
	SimpleCursorAdapter cursorAdapter;
	EditText et_count;
	Spinner spinShop, spinProduct;
	TextView tv_date;
	Button add;
	Date date;
	CharSequence s;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_borluulah);
		
		sqLiteDatabase = new SQLiteAdapter(this);
		sqLiteDatabase.openToWrite();
		spinProduct = (Spinner)findViewById(R.id.spin_pro);
		spinShop = (Spinner)findViewById(R.id.spin_shop);
		tv_date = (TextView)findViewById(R.id.textView3);
		add = (Button)findViewById(R.id.btn_addToList);
		et_count = (EditText)findViewById(R.id.et_count);
		list = (ListView)findViewById(R.id.list); 
		
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => "+c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = df.format(c.getTime());
//		tv_date.setText(formattedDate);
				
		addValueToShop();
		addValueToProduct();
		
		cursor = sqLiteDatabase.queueSecondTable();
        String[] from = new String[]{SQLiteAdapter.SECOND_ID, SQLiteAdapter.SECOND_PNAME, SQLiteAdapter.SECOND_COUNT,"Une", SQLiteAdapter.SECOND_DATE};
        int[] to = new int[]{R.id.id, R.id.text1, R.id.text2, R.id.text3, R.id.text4};
        cursorAdapter =	new SimpleCursorAdapter(this, R.layout.row_second, cursor, from, to);
        list.setAdapter(cursorAdapter);		
		
		add.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==add)
		{	
			Integer cost = Integer.parseInt(et_count.getText().toString()); 
			String shname = spinShop.getSelectedItem().toString();
			String pname = spinProduct.getSelectedItem().toString();
			sqLiteDatabase.insertToSecond(shname, pname, cost, formattedDate);
			et_count.setText("");
			updateList();
			
		}
	}
	
	private void addValueToShop()
	{
				
		List<String> lables = sqLiteDatabase.getAllLabelsShop();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinShop.setAdapter(dataAdapter);
	}
	
	public void addValueToProduct()
	{
		// Spinner Drop down elements
				List<String> product = sqLiteDatabase.getAllLabelsProduct();

				// Creating adapter for spinner
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, product);

				// Drop down layout style - list view with radio button
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				// attaching data adapter to spinner
				spinProduct.setAdapter(dataAdapter);
		
	}


	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sqLiteDatabase.close();
	}

	private void updateList(){
		cursor.requery();
  }
}
