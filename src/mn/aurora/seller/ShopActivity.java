package mn.aurora.seller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShopActivity extends Activity{

	EditText etName,etAddress;
	Button buttonAdd;
	ListView listContent;
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop);
		
		etName = (EditText)findViewById(R.id.editText1);
		etAddress = (EditText)findViewById(R.id.editText2);
		buttonAdd = (Button)findViewById(R.id.button1);
//      buttonDeleteAll = (Button)findViewById(R.id.deleteall);
      
		listContent = (ListView)findViewById(R.id.listView1);
		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();

		cursor = mySQLiteAdapter.queueAll();
		String[] from = new String[]{SQLiteAdapter.SECOND_ID, SQLiteAdapter.SHOP_NAME, SQLiteAdapter.SHOP_ADDRESS};
		int[] to = new int[]{R.id.id, R.id.text1, R.id.text2, R.id.text3};
		cursorAdapter =	new SimpleCursorAdapter(this,R.layout.row, cursor, from, to);
		listContent.setAdapter(cursorAdapter);
		
		listContent.setOnItemClickListener(listContentOnItemClickListener);
		buttonAdd.setOnClickListener(buttonAddOnClickListener);
		
      
  }
  
  Button.OnClickListener buttonAddOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String name = etName.getText().toString().trim();
			String address = etAddress.getText().toString().trim();
			
			if((etName.toString()!=("")) && (etAddress.toString()!=("")))
			{	
				
				String data1 = etName.getText().toString();
				String data2 = etAddress.getText().toString();
				mySQLiteAdapter.insertShop(data1, data2);
				updateList();
				InputMethodManager imName = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imName.hideSoftInputFromWindow(etName.getWindowToken(), 0);
				InputMethodManager imAddress = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imAddress.hideSoftInputFromWindow(etAddress.getWindowToken(), 0);
				clearField();
			}else{
					if (TextUtils.isEmpty(name)) {
						etName.setError("Дэлгүүрийн нэрийг оруулна уу!");
						etName.requestFocus();
					}else if (TextUtils.isEmpty(address)) {
						etAddress.setError("Дэлгүүрийн хаягийг оруулна уу!");
						etAddress.requestFocus();
					}
				
			}
		}
  };
    
  private ListView.OnItemClickListener listContentOnItemClickListener = new ListView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			Cursor cursor = (Cursor) parent.getItemAtPosition(position);
			final int item_id = cursor.getInt(cursor.getColumnIndex(SQLiteAdapter.SHOP_ID));
          String item_content1 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.SHOP_NAME));
          String item_content2 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.SHOP_ADDRESS));
       
          
          AlertDialog.Builder myDialog 
          = new AlertDialog.Builder(ShopActivity.this);
          
          myDialog.setTitle("Гэрээт дэлгүүрүүд.");
          
          TextView dialogTxt_id = new TextView(ShopActivity.this);
          LayoutParams dialogTxt_idLayoutParams 
           = new LayoutParams();
          dialogTxt_id.setLayoutParams(dialogTxt_idLayoutParams);
          dialogTxt_id.setText("#" + String.valueOf(item_id));
          
          final EditText dialogC1_id = new EditText(ShopActivity.this);
          LayoutParams dialogC1_idLayoutParams 
           = new LayoutParams();
          dialogC1_id.setLayoutParams(dialogC1_idLayoutParams);
          dialogC1_id.setText(item_content1);
          
          final EditText dialogC2_id = new EditText(ShopActivity.this);
          LayoutParams dialogC2_idLayoutParams 
           = new LayoutParams();
          dialogC2_id.setLayoutParams(dialogC2_idLayoutParams);
          dialogC2_id.setText(item_content2);
          
          LinearLayout layout = new LinearLayout(ShopActivity.this);
          layout.setOrientation(LinearLayout.VERTICAL);
          layout.addView(dialogTxt_id);
          layout.addView(dialogC1_id);
          layout.addView(dialogC2_id);
          myDialog.setView(layout);
          
          myDialog.setPositiveButton("Устгах", new DialogInterface.OnClickListener() {
              // do something when the button is clicked
              public void onClick(DialogInterface arg0, int arg1) {
              	mySQLiteAdapter.delete_byID(item_id);
      			updateList();
               }
              });
          
          myDialog.setNeutralButton("Засах", new DialogInterface.OnClickListener() {
              // do something when the button is clicked
              public void onClick(DialogInterface arg0, int arg1) {
              	String value1 = dialogC1_id.getText().toString();
              	String value2 = dialogC2_id.getText().toString();
              	mySQLiteAdapter.update_byID(item_id, value1, value2);
      			updateList();
               }
              });
      
          
          myDialog.setNegativeButton("Болих", new DialogInterface.OnClickListener() {
              // do something when the button is clicked
              public void onClick(DialogInterface arg0, int arg1) {
       
               }
              });
          
          myDialog.show();
           
		}};
		
	public void clearField()
	{
		etName.setText("");
		etAddress.setText("");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mySQLiteAdapter.close();
	}

	private void updateList(){
		cursor.requery();
  }	
 }
