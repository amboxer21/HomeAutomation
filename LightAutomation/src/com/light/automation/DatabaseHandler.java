package com.light.automation;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;
 
import android.content.Context;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
 
  private static int updated;

  private static final int DATABASE_VERSION     = 1;
  private static final String KEY_ID            = "id";
  private static final String KEY_IP            = "ip";
  private static final String KEY_STATE         = "state";
  private static final String TABLE_LIGHT_STATE = "lightState";
  private static final String DATABASE_NAME     = "lightStateManager";
 
  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
 
  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_LIGHT_STATE_TABLE = "CREATE TABLE " + TABLE_LIGHT_STATE + 
      "(" + KEY_ID + " INTEGER PRIMARY KEY,"
      + KEY_STATE  + " INTEGER," 
      + KEY_IP  + " TEXT" + ")";
    db.execSQL(CREATE_LIGHT_STATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGHT_STATE);
    onCreate(db);
  }

  void addLightState(LightState lightState) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(KEY_STATE, lightState.getState()); 
    values.put(KEY_IP, lightState.getIP()); 

    db.insert(TABLE_LIGHT_STATE, null, values);
    db.close();
  }
 
  LightState getLightState(int id) {

    SQLiteDatabase db = this.getReadableDatabase();
 
    Cursor cursor = db.query(TABLE_LIGHT_STATE,
      new String[] { KEY_ID, KEY_STATE, KEY_IP }, KEY_ID + "=?",
      new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }

    LightState lightState = new LightState(Integer.parseInt(cursor.getString(0)), 
      Integer.parseInt(cursor.getString(1)),
      cursor.getString(2));

  return lightState;
  }
     
  public int updateLightState(LightState lightState) {

    ContentValues values = new ContentValues();
    SQLiteDatabase db    = this.getWritableDatabase();

    values.put(KEY_STATE, lightState.getState());
    values.put(KEY_IP, lightState.getIP());
    return db.update(TABLE_LIGHT_STATE, values, KEY_ID + " = ?", 
      new String[] {String.valueOf(lightState.getID()) });
  }
 
  public void deleteLightState(LightState lightState) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_LIGHT_STATE, KEY_ID + " = ?",
      new String[] { String.valueOf(lightState.getID()) });
    db.close();
  }

  public String getLightStateHelper() {
    String countQuery = "SELECT state FROM " + TABLE_LIGHT_STATE;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.moveToPosition(0);
    return cursor.getString(cursor.getColumnIndex("state"));
  }

  public String getLightStateIPHelper() {
    String countQuery = "SELECT state FROM " + TABLE_LIGHT_STATE;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.moveToPosition(0);
    return cursor.getString(cursor.getColumnIndex("ip"));
  }

  public int getLightStateCount() {
    String lightStateQuery = "SELECT  * FROM " + TABLE_LIGHT_STATE;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(lightStateQuery, null);

    return cursor.getCount();
  }
 
}
