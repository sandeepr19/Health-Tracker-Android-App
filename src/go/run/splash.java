package go.run;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class splash extends Activity {
	/** Called when the activity is first created. */
	protected boolean active = true;
	protected int splashTime = 2000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
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
					splash.this.runOnUiThread(new Runnable() {
						public void run() {
							startActivity(new Intent(splash.this,instruction.class));
							finish();
						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

				}
			}
		};
		
		splashTread.start();
		
	}
}