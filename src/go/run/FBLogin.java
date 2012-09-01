package go.run;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.android.Facebook.DialogListener;

/**
 * @author: Infosys Technologies Ltd
 * @className:FBLogin.java
 * @Description:Used to login with facebook
 */
public class FBLogin extends Activity {

	public static final String TAG = FBLogin.class.getName();
	public Facebook facebook = new Facebook("322641341133648");
	// 292595534089609 - PROD
	// 100291733348837 - TEST
	private String accesscode = null;
	public Intent intent;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		facebook.authorize(FBLogin.this, new String[] { "publish_stream",
				"email" }, Facebook.FORCE_DIALOG_AUTH, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
				accesscode = values.getString(Facebook.TOKEN);
				new BackgroundFacebook(FBLogin.this, getIntent()
						.getStringExtra("comment"), getIntent().getStringExtra(
						"shareExtra"), accesscode).execute();
				// add a new class
				SharedPreferences pref = getSharedPreferences("abc", 0);
				if (pref.getString("fbPage", "default").equals("popUp")) {
					Intent SS = new Intent(FBLogin.this, popup.class);
					startActivity(SS);
					finish();
				} else if (pref.getString("fbPage", "default").equals("charts")) {
					Intent SS = new Intent(FBLogin.this,
							HellochartsActivity.class);
					startActivity(SS);
					finish();
				}

			}

			@Override
			public void onCancel() {
				intent = new Intent();
				setResult(1, intent);
				finish();
			}

			@Override
			public void onError(DialogError dialogError) {

				intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}

			@Override
			public void onFacebookError(FacebookError facError) {

				intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
	}

	private class BackgroundFacebook extends AsyncTask<Void, Void, Void> {
		private Context context;
		private String comment, shareText, accesscode;

		public BackgroundFacebook(Context context, String comment,
				String shareText, String accesscode) {
			super();
			BackgroundFacebook.this.comment = comment;
			BackgroundFacebook.this.context = context;
			BackgroundFacebook.this.shareText = shareText;
			BackgroundFacebook.this.accesscode = accesscode;

		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(context, ("sharing:"), Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			updateStatusonWall(context, accesscode, comment, shareText);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}

	}

	public void updateStatusonWall(Context context, String accessToken,
			String comment, String shareText) {
		try {
			Bundle bundle = new Bundle();
			bundle.putString("message", comment + "\n" + shareText);
			bundle.putString(Facebook.TOKEN, accessToken);

			try {

				String response = facebook.request("me/feed", bundle, "POST");
				JSONObject json;
				json = Util.parseJson(response);

			} catch (FacebookError e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}