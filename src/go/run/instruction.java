package go.run;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;


public class instruction extends Activity {
	/** Called when the activity is first created. */
	protected boolean active = true;
	protected int splashTime = 20000;
	protected boolean activityskipped=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.instruction);
		SharedPreferences pref = getPreferences(0);
		if(pref.getBoolean("showninstruction", false) && !getIntent().getBooleanExtra("dontfinish", false)){
			Intent SS = new Intent(instruction.this,UserDetails.class);
			startActivity(SS);
			finish();
			return;
		}
		else
		{
			//SharedPreferences pref = getPreferences(0);
			Editor editor = pref.edit().putBoolean("showninstruction",true);
			editor.commit();
		}
		Thread splashTread = new Thread() {

			@Override
			public void run() {
				try {
					int waited = 0;
					while (active && (waited < splashTime)) {
						sleep(100);
						if (active) {
							waited += 100;
						}
					}
					instruction.this.runOnUiThread(new Runnable() {
						public void run() {
							if (!activityskipped)
							{
								startActivity(new Intent(instruction.this,UserDetails.class));
								finish();
							}

						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

				}
			}
		};
		findViewById(R.id.skip).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activityskipped=true;
				startActivity(new Intent(instruction.this,UserDetails.class));
				finish();
			}
		});
		if(!getIntent().getBooleanExtra("dontfinish", false))
		{	
			splashTread.start();
			
		}else
		{
			findViewById(R.id.skip).setVisibility(View.GONE);
		}
	}
}