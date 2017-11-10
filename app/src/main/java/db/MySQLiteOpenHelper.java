package db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * SQLiteOpenHelperを継承したクラス
 * Created by Kyoko1 on 2016/11/10.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    /** DB名 */
    static final String DB = "dailymap.db";
    /** DBバージョン */
    static final int DB_VERSION = 1;
    /** create table文 */
    static final String CREATE_TABLE = "create table t_diary (store_id text, seq_no integer, reg_date text, title text, comment text, evaluation integer, primary key(store_id, seq_no));";
    /** drop table文 */
    static final String DROP_TABLE = "drop table t_diary;";

    /** コンストラクタ */
    public MySQLiteOpenHelper(Context c) {
        super(c, DB, null, DB_VERSION);
    }

    /** onCreate */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /** onUpdate */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}