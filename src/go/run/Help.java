package go.run;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Help extends Activity {

	private ExpandableListAdapter mAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("values", "inside help");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		ImageView chartButton = (ImageView) findViewById(R.id.charts);
		ImageView settingsButton = (ImageView) findViewById(R.id.settings);
		ImageView helpButton = (ImageView) findViewById(R.id.help);

		chartButton.setOnClickListener(chartListener);
		settingsButton.setOnClickListener(settingsListener);
		helpButton.setOnClickListener(helpListener);
		
		ImageView home = (ImageView) findViewById(R.id.homeicon);
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent SS = new Intent(Help.this, Mainactivity.class);
				SS.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(SS);
				finish();
			}
		});
		
		LinearLayout ll1 = (LinearLayout) findViewById(R.id.instruction);
		ll1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("inside ","onclick");
				Intent SS = new Intent(Help.this, instruction.class);
				SS.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				SS.putExtra("dontfinish", true);
				startActivity(SS);
				//finish();
			}
		});
		
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.healthbenefit);
		ll2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				findViewById(R.id.healthbenefittext).setVisibility(View.VISIBLE);
				findViewById(R.id.exeriserecommondationtext).setVisibility(View.GONE);
				findViewById(R.id.tipstext).setVisibility(View.GONE);
			}
		});
		LinearLayout ll3 = (LinearLayout) findViewById(R.id.exeriserecommondation);
		ll3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				findViewById(R.id.healthbenefittext).setVisibility(View.GONE);
				findViewById(R.id.exeriserecommondationtext).setVisibility(View.VISIBLE);
				findViewById(R.id.tipstext).setVisibility(View.GONE);
			}
		});
		LinearLayout ll4 = (LinearLayout) findViewById(R.id.tips);
		ll4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				findViewById(R.id.healthbenefittext).setVisibility(View.GONE);
				findViewById(R.id.exeriserecommondationtext).setVisibility(View.GONE);
				findViewById(R.id.tipstext).setVisibility(View.VISIBLE);
			}
		});

	}

	View.OnClickListener chartListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Help.this, HellochartsActivity.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener settingsListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Help.this, Settings.class);
			startActivity(SS);
			finish();
		}
	};
	View.OnClickListener helpListener = new OnClickListener() {
		public void onClick(View v) {

			Intent SS = new Intent(Help.this, Help.class);
			startActivity(SS);
			finish();
		}
	};

}
