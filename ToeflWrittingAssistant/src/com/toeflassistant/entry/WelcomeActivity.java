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
		
		// �������׹�棬�����ID�����ò��֡�
		AdManager.getInstance(this).init("549e12b2edbfae0c",
						"41c44a2936714c9e", false);
		// ����ص������´��룬����SDKӦ��������������SDK����һЩ��ʼ���������ýӿ������SDK�ĳ�ʼ���ӿ�֮����á�
		// ����ǽ���ò���
	    OffersManager.getInstance(this).onAppLaunch();
        //Ԥ���ز����������
	    SpotManager.getInstance(this).loadSpotAds();
//		// �������ˡ����߲��������ܣ�������ӷ�������ȡ���߲����������汾�ء�
//		MobclickAgent.updateOnlineConfig(WelcomeActivity.this);
//
//		// ����ȷ����û��ȥ��棬��Ϊ��Application�������õĻ��������˳�ʱ��û����Ĺر�app�����²���ʵ���������ֵ���������		
//		if (UserStatus.isPremium(getApplicationContext())) {
//					((MyApplication) this.getApplication()).setAdState(true);
//				}
				
		
		////////////////////////////////////�������ݿ���صĸ��ֲ���///////////////////////////
		
		
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
		if (!dir.exists())// �ж��ļ����Ƿ���ڣ������ھ��½�һ��
			dir.mkdir();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(databaseFilenames);// �õ����ݿ��ļ���д����
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStream is = WelcomeActivity.this.getResources().openRawResource(
				R.raw.writepractice);// �õ����ݿ��ļ���������
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
