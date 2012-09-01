package go.run;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class popup extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup);
		SharedPreferences pref = getSharedPreferences("abc",0);
		Editor editor = pref.edit();
		editor.putString("fbPage", "popUp");
		editor.commit();
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(facebookListener);

	}

	View.OnClickListener facebookListener = new OnClickListener() {
		public void onClick(View v) {
			SharedPreferences pref = getSharedPreferences("abc",0);
			Intent intent = new Intent(popup.this, FBLogin.class);
			intent.putExtra("shareExtra", "Yeppie.... I have completed my arobic activity goal. which was whopping " + pref.getInt("target", -1) + " Minutes!!!");
			intent.putExtra("comment", "");
			((Activity) popup.this).startActivityForResult(intent, 1);
			finish();
		}
	};
}