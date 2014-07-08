package com.toeflassistant.standard;

import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.notebook.QuickContactFragment;
import com.toeflassistant.writting.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

public class StandardMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.standard_main_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Score Table");

		TextView standard1 = (TextView) findViewById(R.id.standard_1);
		TextView standard2 = (TextView) findViewById(R.id.standard_2);
		standard1.setText(getResources().getString(R.string.standard_1)
				.replaceAll("////", "\n\n"));
		standard2.setText(getResources().getString(R.string.standard_3)
				.replaceAll("////", "\n\n"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.from(this)
				// ��������кܶ�ԭʼ��Activity,����Ӧ�ñ����������
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

}
