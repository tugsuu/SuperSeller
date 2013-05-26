package mn.aurora.seller;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class MainActivity extends Activity implements OnClickListener{
	
	private GridviewAdapter mAdapter;
	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;
	private GridView gridView;
	Button aboutBtn;
	Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		   
	    gridView = (GridView) findViewById(R.id.gridView1);
		aboutBtn = (Button)findViewById(R.id.button1);

		prepareList();	        
	    // prepared arraylist and passed it to the Adapter class
	    mAdapter = new GridviewAdapter(this,listCountry, listFlag);
	    // Set custom adapter to gridview
	    gridView.setAdapter(mAdapter);
	    aboutBtn.setOnClickListener(this); 
	    gridView.setOnItemClickListener(new OnItemClickListener() 
	        {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					if(position==3){
		        		Intent i = new Intent(getApplicationContext(), ShopActivity.class);
		        		startActivity(i);
		        		DataManager.setForm(1);
		        		finish();
		        	}else if (position==4) {
		        		Intent i = new Intent(getApplicationContext(), ProductActivity.class);
		        		startActivity(i);
		        		DataManager.setForm(1);
		        		finish();
		        	}else if (position==0) {
		        		Intent i = new Intent(getApplicationContext(), SellProduct.class);
		        		startActivity(i);
		        		DataManager.setForm(1);
		        		finish();
		        	}else if (position==1) {
		        		Intent i = new Intent(getApplicationContext(), FirstList.class);
		        		startActivity(i);
		        		DataManager.setForm(1);
		        		finish();
		        	}
				}
			});
	        
	    }
	    
	    public void prepareList()
	    {
	    	  listCountry = new ArrayList<String>();
	    	  
	    	  listCountry.add("Борлуулалт хийх");
	    	  listCountry.add("Бүртгэл");
	          listCountry.add("Борлуулалт");
	          listCountry.add("Дэлгүүр");
	          listCountry.add("Бүтээгдэхүүн");
	          listCountry.add("Захиалга");
	          
	          listFlag = new ArrayList<Integer>();
	          listFlag.add(R.drawable.a);
	          listFlag.add(R.drawable.b);
	          listFlag.add(R.drawable.c);
	          listFlag.add(R.drawable.d);
	          listFlag.add(R.drawable.e);
	          listFlag.add(R.drawable.f);
	       
	    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==aboutBtn) {
			aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_info));
			openDialog();
		}
			
	}
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
//			aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_info));
			dialog.dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void openDialog(){
		 
		 dialog = new Dialog(MainActivity.this);
	     dialog.setTitle("Програмын тухай");
	     dialog.setContentView(R.layout.dialog_layout);
	     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;  
	     dialog.show();
	    }
	 
}
