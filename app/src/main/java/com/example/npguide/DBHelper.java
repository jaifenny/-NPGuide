package com.example.npguide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB = "Login_data.db"; //資料庫名稱
    private final static String TB = "TB_Login_data"; //資料表
    private final static int VS = 3;

    private static SQLiteDatabase database = null;

    public DBHelper(Context context, String account, SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, name, factory, version);
        super(context, DB, null, VS); //super用來打開或創建資料庫、資料檔案
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //PRIMARY KEY(主索引鍵)
        //AUTOINCREMENT(自動遞增)
        String SQLTable = "CREATE TABLE " + TB + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_Account TEXT NOT NULL, " +
                "_Password TEXT NOT NULL, " +
                "_Hint TEXT NOT NULL, " +
                "_MFavorite TEXT, " + //最愛清單(紀錄點了哪些東西的位置，以空白分隔)
                "_Note TEXT, " +  //筆記內容
                "_Noteedittime TEXT" + //筆記更新時間
                ");";

        sqLiteDatabase.execSQL(SQLTable);
    }

    //使用建構子時如果版本增加,便會呼叫onUpgrade()刪除舊的資料表與其內容,再重新呼叫onCreate()建立新的資料表
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String SQL = "DROP TABLE IF EXISTS " + TB;
        sqLiteDatabase.execSQL(SQL);
        onCreate(sqLiteDatabase);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DBHelper(context,DB, null, VS).getWritableDatabase();
        }
        return database;
    }
}
