package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat; //抓取時間
import java.util.Date;

public class MainActivity_note extends AppCompatActivity {

    EditText note_content;
    TextView display_time;

    public DBHelper DH = null;
    private SQLiteDatabase db;  // 資料庫物件
    String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);

        note_content = (EditText)findViewById(R.id.editTextTextMultiLine); //筆記內容
        display_time = (TextView) findViewById(R.id.edit_time); //編輯時間

        // 建立資料庫，若存在就將之開啟
        DH = new DBHelper(this,"Login_data.db",null, 2);

        //1,得到SharedPreferences物件
        SharedPreferences preferences = getSharedPreferences("config", 0);
        //2,取出資料
        account = preferences.getString("Account","");

        //顯示資料庫內容
        set_all_content();

    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater. inflate (R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent=new Intent();
        switch (id){
            case R.id.library:
                intent.setClass(this,activity_main3_5.class);
                break;
            case R.id.back:
                intent.setClass(this,activity_main3_5.class);
                break;
            case R.id.home:
                intent.setClass(this,MainActivity.class);
                break;
            case R.id.note:
                intent.setClass(this,MainActivity_note.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }



    private void set_all_content(){
        db = DH.getWritableDatabase();
        Cursor c = db.query("TB_Login_data", null, "_Account=?", new String[]{account}, null, null, null);
        c.moveToNext();

        display_time.setText("最後編輯時間:"+c.getString(6).toString());
        note_content.setText(c.getString(5).toString());

        db.close();
        c.close();
    }


    //獲取現在時間
    private String get_currentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        String str  = formatter.format(curDate);
        return str;
    }

    private void save_note_content(){
        db = DH.getWritableDatabase();
        Cursor c = db.query("TB_Login_data", null, "_Account=?", new String[]{account}, null, null, null);
        c.moveToNext();

        String t = note_content.getText().toString();
        String n = get_currentTime();
        ContentValues values = new ContentValues();
        values.put("_Note", t);
        values.put("_Noteedittime", n);
        db.update("TB_Login_data", values, "_Account='"+account.toString()+"'", null);

        db.close();
        c.close();
    }

    //保存當前筆記內容
    public void save_data(View v){
        db = DH.getWritableDatabase();
        Cursor c = db.query("TB_Login_data", null, "_Account=?", new String[]{account}, null, null, null);
        c.moveToNext();

        //按下保存，儲存時間與內容
        save_note_content();

        //跳回library畫面
        Intent intent = new Intent(this, activity_main3_5.class);
        startActivity(intent);
    }
}