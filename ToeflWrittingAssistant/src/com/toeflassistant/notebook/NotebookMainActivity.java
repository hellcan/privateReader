package com.toeflassistant.notebook;

import com.astuetz.PagerSlidingTabStrip;
import com.toeflassistant.entry.MainActivity;
import com.toeflassistant.writting.R;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NotebookMainActivity extends FragmentActivity {

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notebook_main_activity);

		initActionBar();
		tabs = (PagerSlidingTabStrip) findViewById(R.id.notebook_main_tabs);
		pager = (ViewPager) findViewById(R.id.notebook_main_pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		// �����Ϸ���tab
		tabs.setViewPager(pager);

		changeColor(currentColor);
	}

	// ���Ͻ�popup-menu
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	private void initActionBar() {
		// TODO Auto-generated method stub
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("My NoteBook");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_contact:
			QuickContactFragment dialog = new QuickContactFragment();
			dialog.show(getSupportFragmentManager(), "QuickContactFragment");
			break;
		case android.R.id.home:
			Intent upIntent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.from(this)
				// ��������кܶ�ԭʼ��Activity,����Ӧ�ñ�����������
						.addNextIntent(upIntent).startActivities();
				finish();
			} else {
				NavUtils.navigateUpTo(this, upIntent);
			}
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	// �޸���ɫ
	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] {
						oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;
	}

	// public void onColorClicked(View v) {
	//
	// int color = Color.parseColor(v.getTag().toString());
	// changeColor(color);
	//
	// }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	// ����tab����
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Head", "Transition", "Ending",
				"Summarize", "Sentence", "Word", "Other" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return NotebookListFragment.newInstance(position, TITLES);
		}

	}
}