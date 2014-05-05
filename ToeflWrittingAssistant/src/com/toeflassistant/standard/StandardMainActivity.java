package com.toeflassistant.standard;

import com.toeflassistant.writting.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StandardMainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_main_activity);
		TextView standard1 = (TextView)findViewById(R.id.standard_1);
		TextView standard2 = (TextView)findViewById(R.id.standard_2);
		standard1.setText(getResources().getString(R.string.standard_1).replaceAll("////", "\n\n"));
		standard2.setText(getResources().getString(R.string.standard_3).replaceAll("////", "\n\n"));
	}

}
