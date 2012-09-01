package go.run;

import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class HellochartsActivity extends Activity {
	/** Called when the activity is first created. */
	private int type = 1;
	// type: 0= 2:weeks 1:4 weeks 2:4months 3:8months
	private XYPlot mySimpleXYPlot;
	Number[] numSightings = new Number[4];
	Number[] timestamps = new Number[4];
	private float percnetage;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);

		SharedPreferences pref = getSharedPreferences("abc",0);
		Editor editor = pref.edit();
		editor.putString("fbPage", "charts");
		editor.commit();
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(facebookListener);
		ImageView chartButton = (ImageView) findViewById(R.id.charts);
		ImageView settingsButton = (ImageView) findViewById(R.id.settings);
		ImageView helpButton = (ImageView) findViewById(R.id.help);

		chartButton.setOnClickListener(chartListener);
		settingsButton.setOnClickListener(settingsListener);
		helpButton.setOnClickListener(helpListener);
		
		final DBAdapter dbAdapter = DBAdapter
				.getInstance(HellochartsActivity.this);
		synchronized (dbAdapter) {
			try {
				dbAdapter.openDataBase();
				Calendar calendar = Calendar.getInstance();
				calendar.clear();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);
				calendar.set(Calendar.WEEK_OF_YEAR,
						Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
				calendar.set(Calendar.YEAR,
						Calendar.getInstance().get(Calendar.YEAR));

				// dbAdapter.insertexcerxisetime((new
				// Timestamp(statetimeDate.getTime())).toString(), (new
				// Timestamp(endtimeDate.getTime())).toString(), 10);
				// Log.e("##", " "+dbAdapter.getavg(calendar, DBAdapter.WEEK));

				int complete = dbAdapter.getsum(calendar, DBAdapter.WEEK);
				int target = 150;
				 percnetage = (float) (complete * 100.0 / target);
				((TextView) findViewById(R.id.text))
						.setText("% weekely target completed "
								+ percnetage );

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dbAdapter.close();
			}
		}

		findViewById(R.id.a4week).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setValues(0);
				type = 0;
				// initialize our XYPlot reference:
				mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot0);
				mySimpleXYPlot.setVisibility(View.VISIBLE);
				findViewById(R.id.mySimpleXYPlot1).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot2).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot3).setVisibility(View.GONE);
				showgraph();
			}
		});
		findViewById(R.id.a8week).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setValues(1);
				type = 1;
				// initialize our XYPlot reference:
				mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot1);
				mySimpleXYPlot.setVisibility(View.VISIBLE);
				findViewById(R.id.mySimpleXYPlot0).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot2).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot3).setVisibility(View.GONE);
				showgraph();

			}
		});
		findViewById(R.id.a4months).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setValues(2);
				type = 2;
				// initialize our XYPlot reference:
				mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot2);
				mySimpleXYPlot.setVisibility(View.VISIBLE);
				findViewById(R.id.mySimpleXYPlot1).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot0).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot3).setVisibility(View.GONE);
				showgraph();
			}
		});
		findViewById(R.id.a8months).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setValues(3);
				type = 3;
				// initialize our XYPlot reference:
				mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot3);
				mySimpleXYPlot.setVisibility(View.VISIBLE);
				findViewById(R.id.mySimpleXYPlot1).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot2).setVisibility(View.GONE);
				findViewById(R.id.mySimpleXYPlot0).setVisibility(View.GONE);
				showgraph();
			}
		});

		findViewById(R.id.a4week).performClick();

	}

	XYSeries series2 = null;

	private void showgraph() {

		Log.e("hours", numSightings + "");
		Log.e("hours", timestamps[0] + " " + timestamps[1] + " "
				+ timestamps[2] + " " + timestamps[3] + " ");
		// create our series from our array of nums:

		series2 = new SimpleXYSeries(Arrays.asList(timestamps),
				Arrays.asList(numSightings), "min of excersize");
		// Paint lineFill = new Paint();
		// lineFill.setAlpha(200);
		// lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE,
		// Color.GREEN, Shader.TileMode.MIRROR));
		// // add a new series
		// LineAndPointFormatter formatter = new
		// LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED);
		// formatter.setFillPaint(lineFill);

		mySimpleXYPlot.addSeries(
				series2,
				LineAndPointRenderer.class,
				new LineAndPointFormatter(Color.rgb(0, 200, 0), Color.rgb(200,
						0, 0), 0));
		mySimpleXYPlot.setDomainStepMode(XYStepMode.SUBDIVIDE);
		mySimpleXYPlot.setDomainStepValue(series2.size());
		// reduce the number of range labels
		// mySimpleXYPlot.getGraphWidget().setRangeTicksPerLabel(4);

		// // reposition the domain label to look a little cleaner:
		// Widget domainLabelWidget = mySimpleXYPlot.getDomainLabelWidget();
		//
		// mySimpleXYPlot.position(domainLabelWidget, // the widget to position
		// 45, // x position value, in this case 45 pixels
		// XLayoutStyle.ABSOLUTE_FROM_LEFT, // how the x position value is
		// applied, in this case from the left
		// 0, // y position value
		// YLayoutStyle.ABSOLUTE_FROM_BOTTOM, // how the y position is applied,
		// in this case from the bottom
		// AnchorPosition.LEFT_BOTTOM); // point to use as the origin of the
		// widget being positioned
		mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, timestamps.length);
		mySimpleXYPlot.setDomainValueFormat(new MyDateFormat());
		mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
		// get rid of the visual aids for positioning:
		mySimpleXYPlot.disableAllMarkup();
	}

	private void setValues(int typereq) {
		// TODO Auto-generated method stub

		if (typereq == 0) {
			final DBAdapter dbAdapter = DBAdapter
					.getInstance(HellochartsActivity.this);
			synchronized (dbAdapter) {
				try {
					dbAdapter.openDataBase();
					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR));
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[3] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[3] = calendar.getTime().getTime();
					Log.e("month", calendar.get(Calendar.DATE) + "");
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 1);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[2] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[2] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 2);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[1] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[1] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 3);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[0] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[0] = calendar.getTime().getTime();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dbAdapter.close();
				}
			}
		} else if (typereq == 1) {
			final DBAdapter dbAdapter = DBAdapter
					.getInstance(HellochartsActivity.this);
			synchronized (dbAdapter) {
				try {
					dbAdapter.openDataBase();
					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR));
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[3] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[3] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 2);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[2] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[2] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 4);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[1] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[1] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.WEEK_OF_YEAR, Calendar.getInstance()
							.get(Calendar.WEEK_OF_YEAR) - 6);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[0] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[0] = calendar.getTime().getTime();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dbAdapter.close();
				}
			}
		} else if (typereq == 2) {
			final DBAdapter dbAdapter = DBAdapter
					.getInstance(HellochartsActivity.this);
			synchronized (dbAdapter) {
				try {
					dbAdapter.openDataBase();
					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH));
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[3] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[3] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 1);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[2] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[2] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 2);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[1] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[1] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 3);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[0] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[0] = calendar.getTime().getTime();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dbAdapter.close();
				}
			}
		} else if (typereq == 3) {
			final DBAdapter dbAdapter = DBAdapter
					.getInstance(HellochartsActivity.this);
			synchronized (dbAdapter) {
				try {
					dbAdapter.openDataBase();
					Calendar calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH));
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[3] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[3] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 2);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[2] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[2] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 4);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[1] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[1] = calendar.getTime().getTime();
					calendar = Calendar.getInstance();
					calendar.clear();
					calendar.setFirstDayOfWeek(Calendar.MONDAY);
					calendar.set(Calendar.MONTH,
							Calendar.getInstance().get(Calendar.MONTH) - 6);
					calendar.set(Calendar.YEAR,
							Calendar.getInstance().get(Calendar.YEAR));
					numSightings[0] = dbAdapter
							.getsum(calendar, DBAdapter.WEEK);
					timestamps[0] = calendar.getTime().getTime();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					dbAdapter.close();
				}
			}
		}

	}

	private class MyDateFormat extends Format {

		// create a simple date format that draws on the year portion of our
		// timestamp.
		// see
		// http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
		// for a full description of SimpleDateFormat.
		private SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
		private SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd");

		@Override
		public StringBuffer format(Object obj, StringBuffer toAppendTo,
				FieldPosition pos) {
			long timestamp = ((Number) obj).longValue();
			Date date = new Date(timestamp);
			if (type == 2 || type == 3)
				return dateFormat1.format(date, toAppendTo, pos);
			else
				return dateFormat2.format(date, toAppendTo, pos);
		}

		@Override
		public Object parseObject(String source, ParsePosition pos) {
			return null;

		}

	}

	View.OnClickListener facebookListener = new OnClickListener() {
		public void onClick(View v) {

			Intent intent = new Intent(HellochartsActivity.this, FBLogin.class);
			intent.putExtra("shareExtra", "Hey!! I am done with " + percnetage + "% of my required Minutes!!!");
			intent.putExtra("comment", "");
			((Activity) HellochartsActivity.this).startActivityForResult(
					intent, 1);
			finish();
		}
	};
	
	View.OnClickListener chartListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(HellochartsActivity.this, HellochartsActivity.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener settingsListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(HellochartsActivity.this, Settings.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener helpListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(HellochartsActivity.this, Help.class);
			startActivity(SS);
			finish();
		}
	};
}