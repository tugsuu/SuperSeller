package mn.aurora.seller;

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
import android.widget.ViewAnimator;

public class MainActivity extends Activity implements OnClickListener{
	Button aboutBtn;
	ViewAnimator viewGroup;
	Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		    
		GridView gridview = (GridView)findViewById(R.id.gridView1);
		aboutBtn = (Button)findViewById(R.id.button1);
		gridview.setAdapter(new ImageAdapter(this));
		aboutBtn.setOnClickListener(this);
		gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_info));
		openDialog();	
	}
	 private void openDialog(){
	     		 
		 dialog = new Dialog(MainActivity.this);
	     dialog.setTitle("Програмын тухай");
	     dialog.setContentView(R.layout.dialog_layout);
	     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;  
	     dialog.show();
	    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_info));
			dialog.dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}
	 
}
