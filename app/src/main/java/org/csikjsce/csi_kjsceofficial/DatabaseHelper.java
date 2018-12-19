package org.csikjsce.csi_kjsceofficial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import org.csikjsce.csi_kjsceofficial.POJO.Notification;

import java.io.PipedOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sziraqui on 8/1/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "csikjsce";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NOTIFICATION = "notification";

    private static final String KEY_ID = "id";
    private static final int ID_POSITION = 0;
    private static final String KEY_TIME = "receive_time";
    private static final int TIME_POSITION = 1;
    private static final String KEY_TITLE = "title";
    private static final int TITLE_POSITION = 2;
    private static final String KEY_DESC = "desc";
    private static final int DESC_POSITION = 3;
    private static final String KEY_EXTRA_URL = "extra_url";
    private static final int URL_POSITION = 4;
    private static final String KEY_TYPE = "type";
    private static final int TYPE_POSITION = 5;
    private static final String KEY_EVENT_ID = "event_id";
    private static final int EVENT_ID_POSITON = 6;
    private static final String KEY_ISREAD = "is_read";
    private static final int ISREAD_POSITION = 7;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String NOTIFICATION_CREATE_QUERY = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"  // 0
                + KEY_TIME + " TEXT,"               // 1
                + KEY_TITLE + " TEXT,"              // 2
                + KEY_DESC + " TEXT,"               // 3
                + KEY_EXTRA_URL + " TEXT,"          // 4
                + KEY_TYPE + " INTEGER,"            // 5
                + KEY_EVENT_ID + " INTEGER,"        // 6
                + KEY_ISREAD + " INTEGER"           // 7
                + ")";
        db.execSQL(NOTIFICATION_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);;
    }

    public void insertNotification(Notification notification){

        ContentValues values = new ContentValues();

        values.put(KEY_ID, notification.getId());
        values.put(KEY_TIME, notification.getTime());
        values.put(KEY_TITLE, notification.getTitle());
        values.put(KEY_DESC, notification.getDescription());
        values.put(KEY_TYPE, notification.getType());
        values.put(KEY_EXTRA_URL, notification.getExtraUrl());
        values.put(KEY_ISREAD, Notification.NOT_READ);
        values.put(KEY_EVENT_ID, notification.getEventId());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
    }

    public Notification selectNotificationById(int id){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATION,
                new String[] { KEY_ID, KEY_TITLE, KEY_TIME, KEY_TITLE, KEY_DESC, KEY_EXTRA_URL, KEY_TYPE, KEY_EVENT_ID, KEY_ISREAD},
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        Notification notification = new Notification();
        if(cursor != null){
            cursor.moveToFirst();
            notification = new Notification(
                    Integer.parseInt(cursor.getString(ID_POSITION)),
                    cursor.getString(TIME_POSITION),
                    cursor.getString(TITLE_POSITION),
                    cursor.getString(DESC_POSITION),
                    cursor.getString(URL_POSITION),
                    Integer.parseInt(cursor.getString(TYPE_POSITION)),
                    Integer.parseInt(cursor.getString(EVENT_ID_POSITON)),
                    Integer.parseInt(cursor.getString(ISREAD_POSITION))
            );

            cursor.close();
        }

        db.close();
        return notification;
    }

    public List<Notification> selectAllNotifications(){
        List<Notification> notifications = new ArrayList<>();
        String SELECT_ALL_NOTIFICATION = "SELECT * FROM "+ TABLE_NOTIFICATION + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(SELECT_ALL_NOTIFICATION, null);

        if(cursor.moveToFirst()){
            do {
                Notification notification = new Notification(
                        Integer.parseInt(cursor.getString(ID_POSITION)),
                        cursor.getString(TIME_POSITION),
                        cursor.getString(TITLE_POSITION),
                        cursor.getString(DESC_POSITION),
                        cursor.getString(URL_POSITION),
                        Integer.parseInt(cursor.getString(TYPE_POSITION)),
                        Integer.parseInt(cursor.getString(EVENT_ID_POSITON)),
                        Integer.parseInt(cursor.getString(ISREAD_POSITION))
                );
                notifications.add(notification);
            } while(cursor.moveToNext());
        }

        return notifications;
    }

    public void markRead(int id){

        ContentValues values = new ContentValues();
        values.put(KEY_ISREAD, Notification.IS_READ);

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NOTIFICATION, values, KEY_ID + "=?", new String[]{ String.valueOf(id) } );
        db.close();
    }
    public int getUnreadCount(){
        String SUM_OF_UNREADS_QUERY = "SELECT SUM(" + KEY_ISREAD + ") FROM " + TABLE_NOTIFICATION;
        int unReadCount = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SUM_OF_UNREADS_QUERY, null);
        if(cursor != null){
            cursor.moveToFirst();
            try{
                unReadCount = Integer.parseInt(cursor.getString(0));
            } catch (NumberFormatException nfe) {
                unReadCount = 0;
            }

        }
        return unReadCount;
    }
}
