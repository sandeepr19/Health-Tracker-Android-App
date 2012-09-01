package go.run;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Settings extends Activity {
	private ProgressDialog mProgressDialog;
	private int mProgress;
	private Handler mProgressHandler;
	private static final int CHANGE_WEEKLY_GOALS = 1;
	private static final int POP_UP_MESSAGES = 2;
	private static final int RESTORE_DEFAULT = 3;
	private static final int RESET_LISTS = 4;

	

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CHANGE_WEEKLY_GOALS:
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.dialog_box,
					null);
			return new AlertDialog.Builder(Settings.this)
					.setTitle("Confirmation")
					.setView(textEntryView)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									SharedPreferences pref = getSharedPreferences("abc", 0);
									int numberOfHours = Integer
											.parseInt((((EditText) textEntryView
													.findViewById(R.id.numberOfHoursEdit))
													.getText().toString()));
									Editor editor = pref.edit();
									editor.putInt("target", numberOfHours);
									editor.commit();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();

		case RESTORE_DEFAULT:
			return new AlertDialog.Builder(Settings.this)
					.setTitle("Are you sure you want to restore default values")
					.setPositiveButton("yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									SharedPreferences pref = getSharedPreferences("abc", 0);
									int target = pref.getInt("target", -1);
									DBAdapter dbAdapter = DBAdapter.getInstance(Settings.this);
									synchronized (dbAdapter) {
										try {
											dbAdapter.openDataBase();
											target = dbAdapter
													.getrecoomondedhoursc(35);
											Log.e("target", "" + target);

										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											dbAdapter.close();
										}
									}
									pref = getSharedPreferences("abc", 0);
									;
									Editor editor = pref.edit();
									editor.putInt("target", target);
									editor.commit();

								}
							})
					.setNegativeButton("no",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked Cancel so do some stuff */
								}
							}).create();
		case RESET_LISTS:
			return new AlertDialog.Builder(Settings.this)
					.setTitle("Are you sure you want to reset all values")
					.setPositiveButton("yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									DBAdapter dbAdapter = DBAdapter.getInstance(Settings.this);
									synchronized (dbAdapter) {
										try {
											dbAdapter.openDataBase();
											dbAdapter.clearexcercise();
										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											dbAdapter.close();
										}
									}
								}
							})
					.setNegativeButton("no",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked Cancel so do some stuff */
								}
							}).create();

		}
		return null;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("values", "inside settings");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		Button resetStatisticsButton = (Button) findViewById(R.id.resetStatistics);
		resetStatisticsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(RESET_LISTS);
			}
		});

		Button restoreDefaultButton = (Button) findViewById(R.id.restoreDefault);
		restoreDefaultButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(RESTORE_DEFAULT);
			}
		});

		Button changeWeeklyGoalButton = (Button) findViewById(R.id.changeWeeklyGoal);
		changeWeeklyGoalButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(CHANGE_WEEKLY_GOALS);
			}
		});

		ImageView chartButton = (ImageView) findViewById(R.id.charts);
		ImageView settingsButton = (ImageView) findViewById(R.id.settings);
		ImageView helpButton = (ImageView) findViewById(R.id.help);

		chartButton.setOnClickListener(chartListener);
		settingsButton.setOnClickListener(settingsListener);
		helpButton.setOnClickListener(helpListener);

	}

	View.OnClickListener chartListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Settings.this, HellochartsActivity.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener settingsListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Settings.this, Settings.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener helpListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Settings.this, Help.class);
			startActivity(SS);
			finish();
		}
	};

}
