package com.toeflassistant.entry;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;



import net.youmi.android.spot.SpotManager;

import com.toeflassistant.question_pool.QuestionTopicListActivity;
import com.toeflassistant.writting.R;




import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

public class WelcomeActivity extends Activity {
	
	public static String dbName = "writepractice.db";
	private static String DATABASE_PATH = "/data/data/com.toeflassistant.writting/database/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage_activity);
		
		// 设置有米广告，广告主ID号设置部分。
		AdManager.getInstance(this).init("549e12b2edbfae0c",
						"41c44a2936714c9e", false);
		// 请务必调用以下代码，告诉SDK应用启动，可以让SDK进行一些初始化操作。该接口务必在SDK的初始化接口之后调用。
		// 积分墙设置部分
	    OffersManager.getInstance(this).onAppLaunch();
        //预加载插屏广告数据
	    SpotManager.getInstance(this).loadSpotAds();
//		// 设置友盟“在线参数”功能，这句代码从服务器获取在线参数，并缓存本地。
//		MobclickAgent.updateOnlineConfig(WelcomeActivity.this);
//
//		// 设置确定有没有去广告，因为在Application类中设置的话，可能退出时并没有真的关闭app，导致不能实现设置真价值的情况出现		
//		if (UserStatus.isPremium(getApplicationContext())) {
//					((MyApplication) this.getApplication()).setAdState(true);
//				}
				
		
		////////////////////////////////////复制数据库相关的各种操作///////////////////////////
		
		
		boolean dbExist = checkDatabase();
		if(dbExist){
			
		}else{
			try{
			    copyDatabase();
			} catch (IOException e) {
				throw new Error("Error copying database");
		    }
		}
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
				WelcomeActivity.this.startActivity(in);
				WelcomeActivity.this.finish();

			}

		}).start();
	}
	

	private boolean checkDatabase() {
		// TODO Auto-generated method stub
		SQLiteDatabase checkDB = null;
		try {
			String databaseFilename = DATABASE_PATH + dbName;
			checkDB = SQLiteDatabase.openDatabase(databaseFilename, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDatabase() throws IOException {
		// TODO Auto-generated method stub
		String databaseFilenames = DATABASE_PATH + dbName;
		File dir = new File(DATABASE_PATH);
		if (!dir.exists())// 判断文件夹是否存在，不存在就新建一个
			dir.mkdir();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStream is = WelcomeActivity.this.getResources().openRawResource(
				R.raw.writepractice);// 得到数据库文件的数据流
		byte[] buffer = new byte[8192];
		int count = 0;
		try {

			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (IOException e) {

		}
		try {
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
