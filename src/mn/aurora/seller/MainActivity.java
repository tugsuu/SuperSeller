package mn.aurora.seller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity implements OnClickListener{
	Button aboutBtn;
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
	        	}else if (position==4) {
	        		Intent i = new Intent(getApplicationContext(), ProductActivity.class);
	        		startActivity(i);
	        	}else if (position==0) {
	        		Intent i = new Intent(getApplicationContext(), SellProduct.class);
	        		startActivity(i);
	        	}else if (position==1) {
	        		Intent i = new Intent(getApplicationContext(), FirstList.class);
	        		startActivity(i);
	        	}
	        }
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_clicked));
		openDialog();	
	}
	 private void openDialog(){
	     		 
		 final Dialog dialog = new Dialog(MainActivity.this);
	     dialog.setTitle("Програмын тухай");
	     dialog.setContentView(R.layout.dialog_layout);
	     dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
	     Button btnDismiss = (Button)dialog.getWindow().findViewById(R.id.dismiss);
	     btnDismiss.setOnClickListener(new OnClickListener(){

	   @Override
	   public void onClick(View v) {
		   aboutBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.layout_click));
		   dialog.dismiss();
	   }});
	     
	     dialog.show();
	    }
	
}
