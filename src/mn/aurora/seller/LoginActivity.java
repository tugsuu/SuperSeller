package mn.aurora.seller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	public static final String MY_PREFS = "SharedPreferences";
	private SQLiteAdapter dbHelper;
	TextView txt_forgetPass;
	EditText et_name, et_pass;
	Button btn_login,clearBtn;
	CheckBox rememberDetails;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();
        dbHelper= new SQLiteAdapter(this);
        dbHelper.openToWrite();
		
        setContentView(R.layout.activity_login);
		initControls();
	}

	 private void initControls() {
	    	//Set the activity layout.
		 	et_name = (EditText)findViewById(R.id.editText1);
			et_pass = (EditText)findViewById(R.id.editText2);
			btn_login = (Button)findViewById(R.id.button1);
			txt_forgetPass = (TextView)findViewById(R.id.textView3);
	    	clearBtn = (Button)findViewById(R.id.button1);
	    	rememberDetails = (CheckBox) findViewById(R.id.checkBox1);
	    	
	    	//Create touch listeners for all buttons.
	    	btn_login.setOnClickListener(new Button.OnClickListener(){
	    		public void onClick (View v){
	    			LogMeIn(v);
	    		}
	    	});
	    	
	    	txt_forgetPass.setOnClickListener(new Button.OnClickListener(){
	    		public void onClick (View v){
	    			Register(v);
	    		}
	    	});
	    	
	    	clearBtn.setOnClickListener(new Button.OnClickListener(){
	    		public void onClick (View v){
	    			ClearForm();
	    		}
	    	});
	 
	    	
	    	rememberDetails.setOnClickListener(new CheckBox.OnClickListener(){
	    		public void onClick (View v){
	    			RememberMe();
	    		}
	    	});
	    	
	    	//Handle remember password preferences.
	    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
	    	String thisUsername = prefs.getString("username", "");
	    	String thisPassword = prefs.getString("password", "");
	    	boolean thisRemember = prefs.getBoolean("remember", false);
	    	if(thisRemember) {
	    		et_name.setText(thisUsername);
	    		et_pass.setText(thisPassword);
	    		rememberDetails.setChecked(thisRemember);
	    	}
	    	
	    }
	    
	    
	   private void ClearForm() {
	    	saveLoggedInUId(0,"","");
	    	et_name.setText("");
	    	et_pass.setText("");
	    }
	    
	    /**
	     * Handles the remember password option.
	     */
	    private void RememberMe() {
	    	boolean thisRemember = rememberDetails.isChecked();
	    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
	    	Editor editor = prefs.edit();
	    	editor.putBoolean("remember", thisRemember);
	    	editor.commit();
	    }
	    
	    /**
	     * This method handles the user login process.  
	     * @param v
	     */
	    private void LogMeIn(View v) {
	    	
	    	String thisUsername = et_name.getText().toString();
	    	String thisPassword = et_pass.getText().toString();
	    	
	    	//Assign the hash to the password
	    	thisPassword = md5(thisPassword);
	    	
	    	// Check the existing user name and password database
	    	Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
	    	if (theUser != null) {
	    		startManagingCursor(theUser);
	    		if (theUser.getCount() > 0) {
	    			saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(SQLiteAdapter.USER_ID)), thisUsername, thisPassword);
	    		    stopManagingCursor(theUser);
	    		    theUser.close();
	    		    Intent i = new Intent(v.getContext(), MainActivity.class);
	    		    startActivity(i);
	    		    finish();
	    		}
	    		
	    		//Returns appropriate message if no match is made
	    		else {
	    			Toast.makeText(getApplicationContext(), 
	    					"Таны оруулсан нэр, нууц үг буруу байна.", 
	    					Toast.LENGTH_SHORT).show();
	    			saveLoggedInUId(0, "", "");
	    		}
	    		stopManagingCursor(theUser);
	    		theUser.close();
	    	}
	    	
	    	else {
	    		Toast.makeText(getApplicationContext(), 
	    				"Өгөгдлийн санд боловсруулалт хийгдсэнгүй.", 
	    				Toast.LENGTH_SHORT).show();
	    	}
	    }
	    
	   
	    private void Register(View v)
	    {
	    	Intent i = new Intent(v.getContext(), CreateUserActivity.class);
	    	startActivity(i);
	    	finish();
	    }
	    
	    private void saveLoggedInUId(long id, String username, String password) {
	    	SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
	    	Editor myEditor = settings.edit();
	    	myEditor.putLong("uid", id);
	    	myEditor.putString("username", username);
	    	myEditor.putString("password", password);
	    	boolean rememberThis = rememberDetails.isChecked();
	    	myEditor.putBoolean("rememberThis", rememberThis);
	    	myEditor.commit();
	    }
	    
	    /**
		 * Deals with the password encryption. 
		 * @param s The password.
		 * @return
		 */
	    private String md5(String s) {
	    	try {
	    		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	    		digest.update(s.getBytes());
	    		byte messageDigest[] = digest.digest();
	    		
	    		StringBuffer hexString = new StringBuffer();
	    		for (int i=0; i<messageDigest.length; i++)
	    			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	    		
	    		return hexString.toString();
	    	} 
	    	
	    	catch (NoSuchAlgorithmException e) {
	    		return s;
	    	}
	    }
}
