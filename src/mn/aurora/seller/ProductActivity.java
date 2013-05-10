package mn.aurora.seller;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductActivity extends Activity{
	
	EditText inputContent1, inputContent2;
	Button buttonAdd, buttonDeleteAll;
	private SQLiteAdapter mySQLiteAdapter;
	ListView listContent;
	ArrayAdapter<String> adapt;
	
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	Spinner spin;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_product);
        addToSpin();
        inputContent1 = (EditText)findViewById(R.id.editText1);
        inputContent2 = (EditText)findViewById(R.id.editText2);
        buttonAdd = (Button)findViewById(R.id.button1);
        listContent = (ListView)findViewById(R.id.listView1);
        
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        
        cursor = mySQLiteAdapter.queueAllProduct();
        String[] from = new String[]{SQLiteAdapter.PRODUCT_ID, SQLiteAdapter.PRODUCT_NAME, SQLiteAdapter.PRODUCT_COST, SQLiteAdapter.PRODUCT_TYPE};
        int[] to = new int[]{R.id.id, R.id.text1, R.id.text2, R.id.text3};
        cursorAdapter =
        	new SimpleCursorAdapter(this, R.layout.row_product, cursor, from, to);
        listContent.setAdapter(cursorAdapter);
        buttonAdd.setOnClickListener(buttonAddOnClickListener);
        listContent.setOnItemClickListener(listContentOnItemClickListener);
        
    }
    
    Button.OnClickListener buttonAddOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Integer cost = Integer.parseInt(inputContent2.getText().toString()); 
			String data1 = inputContent1.getText().toString();
			String data3 = spin.getSelectedItem().toString();
			mySQLiteAdapter.insertProduct(data1, cost, data3);
			updateList();
			clearField();
		}
  	
  };
  
  private ListView.OnItemClickListener listContentOnItemClickListener
  = new ListView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			Cursor cursor = (Cursor) parent.getItemAtPosition(position);
			final int item_id = cursor.getInt(cursor.getColumnIndex(SQLiteAdapter.PRODUCT_ID));
          String item_content1 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.PRODUCT_NAME));
          String item_content2 = cursor.getString(cursor.getColumnIndex(SQLiteAdapter.PRODUCT_COST));
       
          
          AlertDialog.Builder myDialog 
          = new AlertDialog.Builder(ProductActivity.this);
          myDialog.setTitle("Бүтээгдэхүүн.");
          
          TextView dialog_id = new TextView(ProductActivity.this);
          LayoutParams dialog_LayoutParams 
           = new LayoutParams();
          dialog_id.setLayoutParams(dialog_LayoutParams);
          dialog_id.setText("#" + String.valueOf(item_id));
          
          final EditText dialog_product = new EditText(ProductActivity.this);
          LayoutParams dialog1_LayoutParams 
           = new LayoutParams();
          dialog_product.setLayoutParams(dialog1_LayoutParams);
          dialog_product.setText(item_content1);
          
          final EditText dialog_cost = new EditText(ProductActivity.this);
          LayoutParams dialog2_LayoutParams 
           = new LayoutParams();
          dialog_cost.setInputType(InputType.TYPE_CLASS_NUMBER);
          dialog_cost.setLayoutParams(dialog2_LayoutParams);
          dialog_cost.setText(item_content2);
          
//          final Spinner dialog3 = new Spinner(ProductActivity.this);
//          LayoutParams dialog4_LayoutParams
//           = new LayoutParams();
//          dialog3.setLayoutParams(dialog3_LayoutParams);
//          dialog3.setAdapter(adapt);
//          
          LinearLayout layout = new LinearLayout(ProductActivity.this);
          layout.setOrientation(LinearLayout.VERTICAL);
          layout.setPadding(10, 0, 10, 5);
          layout.addView(dialog_id);
          layout.addView(dialog_product);
          layout.addView(dialog_cost);
          myDialog.setView(layout);
          
//          myDialog.setPositiveButton("Устгах", new DialogInterface.OnClickListener() {
//              // do something when the button is clicked
//              public void onClick(DialogInterface arg0, int arg1) {
//              	mySQLiteAdapter.delete_byID(item_id);
//      			updateList();
//               }
//              });
          
          myDialog.setNeutralButton("Засах", new DialogInterface.OnClickListener() {
              // do something when the button is clicked
              public void onClick(DialogInterface arg0, int arg1) {
              	String value1 = dialog_product.getText().toString();
              	String value2 = dialog_cost.getText().toString();
              	mySQLiteAdapter.update_Product(item_id, value1, value2);
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
		inputContent1.setText("");
		inputContent2.setText("");
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
	public void addToSpin()
	{
		spin = (Spinner)findViewById(R.id.spinner1);		
		List<String> list = new ArrayList<String>();
		list.add("Талх");
		list.add("Нарийн боов");
		list.add("Сүү, сүүн бүтээгдэхүүн");
		adapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,list); 
		spin.setAdapter(adapt);
	}
	
}
