package mn.aurora.seller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SellProduct extends Activity implements OnClickListener{
	private SQLiteAdapter sqLiteDatabase;
	String formattedDate;
	Cursor cursor;
	SimpleCursorAdapter cursorAdapter;
	EditText et_count;
	Spinner spinShop, spinProduct;
	TextView tv_date;
	Button btnAdd, btnBack;
	Date date;

	CharSequence s;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_borluulah);
		
		sqLiteDatabase = new SQLiteAdapter(this);
		sqLiteDatabase.openToWrite();
		
		spinProduct = (Spinner)findViewById(R.id.spin_pro);
		spinShop = (Spinner)findViewById(R.id.spin_shop);
		tv_date = (TextView)findViewById(R.id.textView9);
		btnAdd = (Button)findViewById(R.id.btn_addToList);
		et_count = (EditText)findViewById(R.id.et_count);
		list = (ListView)findViewById(R.id.list); 
		btnBack = (Button)findViewById(R.id.btnBackBor);
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => "+c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = df.format(c.getTime());
		tv_date.setText(formattedDate);
	
		addValueToShop();
		addValueToProduct();
		
		
//		list.setVisibility(View.GONE);
        
        btnBack.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnAdd)
		{	
			DataManager.setDate(tv_date.getText().toString());
			DataManager.setShop(spinShop.getSelectedItem().toString());
			Toast.makeText(this, DataManager.getDate(), Toast.LENGTH_SHORT).show();
			Integer cost = Integer.parseInt(et_count.getText().toString()); 
			String shname = spinShop.getSelectedItem().toString();
			String pname = spinProduct.getSelectedItem().toString();
			sqLiteDatabase.insertToSecond(shname, pname, cost, formattedDate);
			updateList(); 
			et_count.setText("");
			
			
//	        list.setVisibility(View.VISIBLE);
	       
		}
		if (v==btnBack) {
			if(DataManager.getForm()==1)
			{	
				Intent main = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(main);
				finish();
			}
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
		sqLiteDatabase.close();
	}

	private void updateList(){
		cursor.requery();
  }
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cursor = sqLiteDatabase.queueSecondTable();
        String[] from = new String[]{SQLiteAdapter.SECOND_ID, SQLiteAdapter.SECOND_SHOP, SQLiteAdapter.SECOND_PNAME, SQLiteAdapter.SECOND_COUNT,"Une", SQLiteAdapter.SECOND_DATE};
        int[] to = new int[]{R.id.id, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5};
        cursorAdapter =	new SimpleCursorAdapter(this, R.layout.row_second, cursor, from, to);
        list.setAdapter(cursorAdapter);
	}
	
}
