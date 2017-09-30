package com.example.toeflreading.database;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class mDatabase {
    final String dbPath = "/data/data/com.example.toeflreading.myapplication/database/writepractice.db";
    final String questionTable = "question";
    final String tagTable = "tag";
    final String sampleTable = "sample";
    final String markTable = "mark";
    SQLiteDatabase mdb = null;
    private static mDatabase instance = null;


    //singleton design pattern, initiate database object
    public static mDatabase getInstance(Context context){
        if(instance == null){
            instance = new mDatabase();
        }
        return instance;
    }

    public void openDatabase() {
        this.mdb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        this.mdb.close();
    }

    public Cursor getTopicList() {
        String sql = "SELECT _id,topic FROM " + questionTable;
        Cursor result = mdb.rawQuery(sql, null);
        return result;
    }

    public String getOneTopic(String id) {
        String oneTopic = null;
        String sql = "SELECT topic FROM " + questionTable + " WHERE _id=" + id;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            oneTopic = result.getString(0);
        }
        return oneTopic;
    }

    public String getOneQuestion(String id) {

        String oneQuestion = null;
        String sql = "SELECT question FROM " + questionTable + " WHERE _id=" + id;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            oneQuestion = result.getString(0);
        }
        return oneQuestion;
    }

    public List<List<String>> getTagTopicList(String tagName) {
        List<List<String>> mList = new ArrayList<List<String>>();
        List<String> topicIdList = new ArrayList<String>();
        List<String> topicList = new ArrayList<String>();
        String sql = "SELECT q.[_id],q.[topic] FROM " + questionTable + " AS q JOIN " + tagTable + " AS t ON q.[_id]=t.[_id] " +
                "WHERE tag=" + "\"" + tagName + "\"";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            topicIdList.add(result.getString(0));
            topicList.add(result.getString(1));
        }

        mList.add(topicIdList);
        mList.add(topicList);
        return mList;

    }

    public Cursor getTagTopic(String tagName) {
        String sql = "SELECT q.[_id],q.[topic] FROM " + questionTable + " AS q JOIN " + tagTable + " AS t ON q.[_id]=t.[_id] " +
                "WHERE tag=" + tagName;
        Cursor result = mdb.rawQuery(sql, null);
        return result;
    }

    public Cursor getSampleList(String id) {
        int questionId = Integer.valueOf(id);
        String sql = "SELECT _id,content FROM " + sampleTable + " WHERE questionId=" + questionId;
        Cursor result = mdb.rawQuery(sql, null);
        return result;
    }

    //--计算sample的数量
    public String sampleTotal(int questionId) {
        String total = null;
        String sql = "SELECT count(content) FROM " + sampleTable + " WHERE questionId=" + questionId;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            total = result.getString(0);
        }
        return total;

    }

    //--计算收藏句子的数量
    public String sentenceTotal(int questionId) {
        String total = null;
        String sql = "SELECT count(content) FROM " + markTable + " WHERE questionId=" + questionId;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            total = result.getString(0);
        }
        return total;
    }

    public List<String> getTag(int questionId) {
        String questionID = String.valueOf(questionId);
        List<String> tagNameList = new ArrayList<String>();
        String sql = "SELECT tag FROM " + tagTable + " WHERE _id=" + questionID;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            tagNameList.add(result.getString(0));
        }

        return tagNameList;

    }

    //添加好句
    public void addSentence(int topicId, int questionId, String sentence, String category) {
        ContentValues mContentValue = new ContentValues();
        mContentValue.put("topicId", topicId);
        mContentValue.put("questionId", questionId);
        mContentValue.put("content", sentence);
        mContentValue.put("category", category);
        this.mdb.insertOrThrow(markTable, null, mContentValue);
    }

    //删除好句
    public void removeSentence(String sentence, String category) {
        String sql = "DELETE FROM " + markTable + " WHERE content=" + "\"" + sentence + "\"" + "AND category=" + "\"" + category + "\"";
        mdb.execSQL(sql);
    }

    //返回好句列表
    public Cursor getStarredQuestions() {
        String sql = "SELECT content FROM " + markTable + "order by topicId DESC";
        Cursor result = mdb.rawQuery(sql, null);
        return result;
    }

    public List<String> getSentenceList(String category) {
        List<String> sentenceList = new ArrayList<String>();
        String sql = "SELECT content FROM " + markTable + " WHERE category=" + "\"" + category + "\"";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            sentenceList.add(result.getString(0));
        }
        return sentenceList;

    }

    public String getAnalysis(String questionId) {
        String analysis = null;
        String sql = "SELECT analysis FROM " + questionTable + " WHERE _id=" + questionId;
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            analysis = result.getString(0);
        }

        return analysis;
    }

    public List<String> searchTopic(String s) {
        List<String> topicList = new ArrayList<String>();
        String sql = "SELECT topic FROM " + questionTable + " WHERE topic LIKE" + "'%" + s + "%'";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            topicList.add(result.getString(0));
        }
        return topicList;
    }

    public List<String> searchQuestion(String s) {
        List<String> questionList = new ArrayList<String>();
        String sql = "SELECT question FROM " + questionTable + " WHERE question LIKE" + "'%" + s + "%'";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            questionList.add(result.getString(0));
        }
        return questionList;
    }

    public List<String> searchSample(String s) {
        List<String> sampleList = new ArrayList<String>();
        String sql = "SELECT content FROM " + sampleTable + " WHERE content LIKE" + "'%" + s + "%'";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            sampleList.add(result.getString(0));
        }
        return sampleList;
    }

    //利用Topic内容查找id
    public String getIdTopicInput(String s) {
        String id = null;
        String sql = "SELECT _id FROM " + questionTable + " WHERE topic=" + "\"" + s + "\"";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            id = result.getString(0);
        }

        return id;
    }

    //利用Question内容查找id
    public String getIdQuestionInput(String s) {
        String id = null;
        String sql = "SELECT _id FROM " + questionTable + " WHERE question=" + "\"" + s + "\"";
        Cursor result = mdb.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            id = result.getString(0);
        }

        return id;
    }
}
