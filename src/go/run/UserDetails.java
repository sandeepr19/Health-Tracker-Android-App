package go.run;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class UserDetails extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userdetails);
        SharedPreferences pref = getSharedPreferences("abc",0);
		if(pref.getInt("age", -1)!=-1){
			Intent SS = new Intent(UserDetails.this,Mainactivity.class);
			startActivity(SS);
			finish();
			return;
		}
		else{
			Log.e("is"," not bypassed");
	        final EditText e1 =(EditText)findViewById(R.id.age);

			Button b = (Button) findViewById(R.id.save);
			ImageView chartButton = (ImageView) findViewById(R.id.charts);
			ImageView settingsButton = (ImageView) findViewById(R.id.settings);
			ImageView helpButton = (ImageView) findViewById(R.id.help);

			chartButton.setOnClickListener(chartListener);
			settingsButton.setOnClickListener(settingsListener);
			helpButton.setOnClickListener(helpListener);
	        b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final DBAdapter dbAdapter = DBAdapter.getInstance(UserDetails.this);
					synchronized (dbAdapter) {
						try {
							Log.e("age", Integer.parseInt(e1.getText().toString()) +"");
							//Log.e("sex",e2.getSelectedItem().toString());
							
							dbAdapter.openDataBase();
							dbAdapter.insertuser(e1.getText().toString());
							SharedPreferences pref = getSharedPreferences("abc",0);;
							Editor editor = pref.edit();
							editor.putBoolean("haveage",true);
							//editor.putInt("age", Integer.parseInt(e1.getText().toString()));
							editor.commit();
							pref = getSharedPreferences("abc",0);;
							 editor = pref.edit();
							//editor.putBoolean("haveage",true);
							editor.putInt("age", Integer.parseInt(e1.getText().toString()));
							editor.commit();
							Log.e("!!!!!!!!!!!!!!!!", ""+pref.getInt("age", -1));
							
							Intent SS = new Intent(UserDetails.this,Mainactivity.class);
							startActivity(SS);
							finish();

						} catch (Exception e) {
							e.printStackTrace();

						} finally {
							dbAdapter.close();
						}
					
				}}}
			);
		}
		
    }
	View.OnClickListener chartListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(UserDetails.this, HellochartsActivity.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener settingsListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(UserDetails.this, Settings.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener helpListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(UserDetails.this, Help.class);
			startActivity(SS);
			finish();
		}
	};
}