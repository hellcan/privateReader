package com.example.toeflreading.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class WelcomeActivity extends Activity {

    public static String dbName = "writepractice.db";
    private static String DATABASE_PATH = "/data/data/com.example.toeflreading.myapplication/database/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage_activity);

        getActionBar().hide();

        ////////////////////////////////////复制数据库相关的各种操作///////////////////////////


        boolean dbExist = checkDatabase();
        if(dbExist){

        }else{
            try{
                copyDatabase();
            }
            catch (IOException e) {
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
                //跳转起始页面
                Intent in = new Intent(WelcomeActivity.this, MainActivity.class);

                WelcomeActivity.this.startActivity(in);
                WelcomeActivity.this.finish();

            }

        }).start();
    }


    private boolean checkDatabase() {
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
            dir.mkdirs();
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
