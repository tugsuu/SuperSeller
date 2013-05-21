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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateUserActivity extends Activity{

	private EditText newUsername;
	private EditText newPassword;
	private EditText newConfiPass;
	private Button registerButton;
	private ImageButton clearButton;
	private SQLiteAdapter dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.MY_PREFS, 0);
        Editor editor = settings.edit();
        editor.putLong("uid", 0);
        editor.commit();
        
        dbHelper = new SQLiteAdapter(this);
        dbHelper.openToWrite();
        setContentView(R.layout.activity_newuser);
        initControls();
	}
	private void initControls()
    {
	 	newUsername = (EditText) findViewById(R.id.createuser_et_name);
	 	newPassword = (EditText) findViewById(R.id.createuser_et_pass);
	 	newConfiPass = (EditText) findViewById(R.id.createuser_pass2);
	 	registerButton = (Button) findViewById(R.id.createuser_btn_create);

    	
	 	registerButton.setOnClickListener(new Button.OnClickListener() { 
	 		public void onClick (View v){ 
	 			RegisterMe(v); }});
	 	
    }
    	
    	private void RegisterMe(View v)
        {
    		//Get user details. 
        	String username = newUsername.getText().toString();
        	String password = newPassword.getText().toString();
        	String confirmpassword = newConfiPass.getText().toString();
        	
       
        	if (username.equals("") || password.equals("")){
        		Toast.makeText(getApplicationContext(), 
        				"Та бүх формыг гүйцэт бөглөнө үү!",
    			          Toast.LENGTH_SHORT).show();
      		return;
        	}
        	
        
        	if (!password.equals(confirmpassword)) {
        		Toast.makeText(getApplicationContext(), 
        				"Нууц үг тохирохгүй байна.",
    			          	Toast.LENGTH_SHORT).show();
        					newPassword.setText("");
        					newConfiPass.setText("");
        		return;
        	}
        	
        	//Encrypt password with MD5.
        	password = md5(password);
        	
        	//Check database for existing users.
        	Cursor user = dbHelper.fetchUser(username, password);
        	if (user == null) {
        		Toast.makeText(getApplicationContext(), "Database query error",
    			          Toast.LENGTH_SHORT).show();
        	} else {
        		startManagingCursor(user);
        		
        		//Check for duplicate usernames
        		if (user.getCount() > 0) {
        			Toast.makeText(getApplicationContext(), "Энэ хэрэглэгч бүртгэлттэй байна.Та өөр нэр оруулна уу!",
      			          Toast.LENGTH_SHORT).show();
        			stopManagingCursor(user);
            		user.close();
        			return;
        		}
        		stopManagingCursor(user);
        		user.close();
        		user = dbHelper.fetchUser(username, password);
            	if (user == null) {
            		Toast.makeText(getApplicationContext(), "Database query error",
      			          Toast.LENGTH_SHORT).show();
            		return;
            	} else {
            		startManagingCursor(user);
            		
            		if (user.getCount() > 0) {
            			Toast.makeText(getApplicationContext(), "Энэ хэрэглэгч бүртгэлттэй байна.Та өөр нэр оруулна уу!",
            			          Toast.LENGTH_SHORT).show();
            			stopManagingCursor(user);
                		user.close();
            			return;
            		}
            		stopManagingCursor(user);
            		user.close();
            	}
            	//Create the new username.
        		long id = dbHelper.createUser(username, password);
        		if (id > 0) {
        			Toast.makeText(getApplicationContext(), "Шинэ хэрэглэгч үүсгэгдлээ.",
        			          Toast.LENGTH_SHORT).show();
        			saveLoggedInUId(id, username, newPassword.getText().toString());
        			Intent i = new Intent(v.getContext(), LoginActivity.class);
    	    		startActivity(i);
    	    		
    	    		finish();
        		} else {
        			Toast.makeText(getApplicationContext(), "Шинэ хэрэглэгч үүсгэхэд алдаа гарлаа.",
        			          Toast.LENGTH_SHORT).show();
        		}
        	}
        }
     
     private void saveLoggedInUId(long id, String username, String password) {
    		SharedPreferences settings = getSharedPreferences(LoginActivity.MY_PREFS, 0);
    		Editor editor = settings.edit();
    		editor.putLong("uid", id);
    		editor.putString("username", username);
    		editor.putString("password", password);
    		editor.commit();
    }
    /**
     * Hashes the password with MD5.  
     * @param s
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

        } catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
  }

