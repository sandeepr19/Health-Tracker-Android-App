package go.run;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
public class Mainactivity extends Activity {
	/** Called when the activity is first created. */
	//Calendar starttime;
	TextView textGoesHere;
	long startTime;
	boolean isrunning =false;
	long countUp;
	int target =150;
	int complete =50;
	int age;
	private int sizeofheart ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main1);
	
	}
	
	protected void onStart() {
		super.onStart();
		SharedPreferences pref = getSharedPreferences("abc",0);
		final DBAdapter dbAdapter = DBAdapter.getInstance(Mainactivity.this);
		if(pref.getInt("target", -1)==-1)
		{
			age= pref.getInt("age", -1);
			
			synchronized (dbAdapter) {
				try {
					dbAdapter.openDataBase();
					target = dbAdapter.getrecoomondedhoursc(35);
					Log.e("target","" + target);
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dbAdapter.close();
				}
			}
			pref = getSharedPreferences("abc",0);;
			 Editor editor = pref.edit();
			//editor.putBoolean("haveage",true);
			editor.putString("fbPage", "default");
			editor.putInt("target", target);
			editor.commit();
		}
		else
			target=pref.getInt("target", -1);
		
		ImageView chartButton = (ImageView) findViewById(R.id.charts);
		ImageView settingsButton = (ImageView) findViewById(R.id.settings);
		ImageView helpButton = (ImageView) findViewById(R.id.help);

		chartButton.setOnClickListener(chartListener);
		settingsButton.setOnClickListener(settingsListener);
		helpButton.setOnClickListener(helpListener);
		synchronized (dbAdapter) {
			try {
				dbAdapter.openDataBase();
				Calendar calendar = Calendar.getInstance();
				calendar.clear();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);
				calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
				calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

				//dbAdapter.insertexcerxisetime((new  Timestamp(statetimeDate.getTime())).toString(), (new  Timestamp(endtimeDate.getTime())).toString(), 10);
				//Log.e("##", " "+dbAdapter.getavg(calendar, DBAdapter.WEEK));
				complete = dbAdapter.getsum(calendar, DBAdapter.WEEK);
				((TextView) findViewById(R.id.timerdone)).setText(complete +"");
				String x ="week: " + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) ;
				calendar.add(Calendar.DATE , 7 );
				x = x + " to " + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) ;
				((TextView)findViewById(R.id.weekinfo)).setText(x);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbAdapter.close();
			}
		}
		//complete =target;
		Log.e (" target " + target , "complete " + complete);
		long ratio = Math.round(complete * 1.0/target);
		sizeofheart = (int) (200 * ratio);
		if(sizeofheart < 20)
			sizeofheart =20;
		if(sizeofheart > 200)
			sizeofheart =200;
		Log.e (" size of heart " + sizeofheart , " " + ratio);
		final TextView i = (TextView) findViewById(R.id.inner);
		i.getLayoutParams().height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());
		i.getLayoutParams().width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());

		i.setOnClickListener(new OnClickListener() {

			private Date statetimeDate;
			private Date endtimeDate;

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Log.e("onclick of", "image123");
				//end=Calendar.getInstance().getTime();
				//i.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
				Chronometer stopWatch = (Chronometer) findViewById(R.id.chrono);
				if(isrunning)
				{
					findViewById(R.id.icons).setVisibility(View.VISIBLE);
					findViewById(R.id.currenttime).setVisibility(View.GONE);
					stopWatch.stop();
					endtimeDate= Calendar.getInstance().getTime();
					// TODO Auto-generated method stub
					final DBAdapter dbAdapter = DBAdapter.getInstance(Mainactivity.this);
					synchronized (dbAdapter) {
						try {
							dbAdapter.openDataBase();
							dbAdapter.insertexcerxisetime((new  Timestamp(statetimeDate.getTime())).toString(), (new  Timestamp(endtimeDate.getTime())).toString(), (endtimeDate.getTime()-statetimeDate.getTime())/(1000*60));
							complete = (int) (complete + (endtimeDate.getTime()-statetimeDate.getTime())/(1000 * 60));
							Log.e("complete ", "complete :" +complete);
							
							if(complete >= target)
							{
								i.setBackgroundResource(R.drawable.heart_glow);
								startActivity(new Intent(Mainactivity.this,popup.class));
							}
								
							((TextView) findViewById(R.id.timerdone)).setText(complete +"");
							long ratio = Math.round(complete * 1.0/target);
							sizeofheart = (int) (200 * ratio);
							if(sizeofheart < 20)
								sizeofheart =20;
							if(sizeofheart > 200)
								sizeofheart =200;
							LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics()));
							i.setLayoutParams(myParams);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							dbAdapter.close();
						}

					}
					isrunning=false; 
					
				}
				else {
					findViewById(R.id.icons).setVisibility(View.GONE);
					findViewById(R.id.currenttime).setVisibility(View.VISIBLE);
					stopWatch.start();
					statetimeDate= Calendar.getInstance().getTime();
					startTime = SystemClock.elapsedRealtime();
					//  stopWatch.setBase(elapsedTimeBeforePause);
					textGoesHere = (TextView) findViewById(R.id.textGoesHere);
					stopWatch.setOnChronometerTickListener(new OnChronometerTickListener(){
						private int tmpsizeofheart;

						@Override
						public void onChronometerTick(Chronometer arg0) {
							countUp = (Calendar.getInstance().getTime().getTime() - statetimeDate.getTime()) / 1000;
							String asText = (countUp / 60) + ":" + (countUp % 60); 
							Log.e("counup",countUp+"");
							if(countUp%2 ==0)
							{
								//Log.e("counup","is even");
								//i.setVisibility(View.INVISIBLE);
								tmpsizeofheart = sizeofheart +5;
								//Log.e("value of heart", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics()) +"");
								//i.getLayoutParams().height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());
								//i.getLayoutParams().width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());
								LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) tmpsizeofheart,getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) tmpsizeofheart,getResources().getDisplayMetrics()));
								i.setLayoutParams(myParams);
								//i.invalidate();
							}
							else
							{
								//Log.e("counup","is odd");
								tmpsizeofheart = sizeofheart;
								//i.setVisibility(View.VISIBLE);
								//Log.e("value of heart", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics()) +"");
								LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) tmpsizeofheart,getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) tmpsizeofheart,getResources().getDisplayMetrics()));
								i.setLayoutParams(myParams);
								//								i.getLayoutParams().height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());
								//								i.getLayoutParams().width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) sizeofheart,getResources().getDisplayMetrics());
								//								i.invalidate();
							}
							textGoesHere.setText(asText);
							isrunning=true;
						}
					});
				}
			}
		});
	}
	View.OnClickListener chartListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(Mainactivity.this, HellochartsActivity.class);
			startActivity(SS);
			//finish();
		}
	};
	View.OnClickListener settingsListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(Mainactivity.this, Settings.class);
			startActivity(SS);
			//finish();
		}
	};
	View.OnClickListener helpListener = new OnClickListener() {
		public void onClick(View v) {

			
			Intent SS = new Intent(Mainactivity.this, Help.class);
			startActivity(SS);
			//finish();
		}
	};
}