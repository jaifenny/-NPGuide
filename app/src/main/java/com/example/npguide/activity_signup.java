package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_signup extends AppCompatActivity {
    private EditText display_account, display_password_1, display_password_2, display_hint;
    private Button btn;
    public DBHelper DH = null;
    private SQLiteDatabase db;  // 資料庫物件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        display_account = (EditText) findViewById(R.id.edittext_account_2);
        display_password_1 = (EditText) findViewById(R.id.edittext_password_2);
        display_password_2 = (EditText) findViewById(R.id.edittext_password_3);
        display_hint = (EditText) findViewById(R.id.edittext_hint);

        // 建立資料庫，若存在就將之開啟
        DH = new DBHelper(this,"Login_data.db",null, 2);
    }


    public void back_btn(View v){
        //跳回登入頁面
        Intent intent = new Intent();
        intent.setClass(this, activity_login.class);
        startActivity(intent);
    }

    public void OK_btn(View v){
        String pas_1 = display_password_1.getText().toString(), pas_2 = display_password_2.getText().toString(),
                account = display_account.getText().toString(), hint = display_hint.getText().toString();
        if(account.matches("")||pas_1.matches("")||pas_2.matches("") ||hint.matches("")){
            Toast.makeText(getApplicationContext(), "資料輸入不完全", Toast.LENGTH_SHORT).show();
        }
        else if(!pas_1.matches(pas_2)){
            Toast.makeText(getApplicationContext(), "請重新確認密碼一致", Toast.LENGTH_SHORT).show();
        }
        else{  //加入資料到資料庫
            db = DH.getWritableDatabase();
            Cursor cursor = db.query("TB_Login_data", null,"_Account=?",new String[]{account},null,null,null);
            cursor.moveToNext();
            if(cursor.getCount()==0) {
                add_data(account, pas_1, hint);

                //跳回登入頁面
                Intent intent = new Intent();
                intent.setClass(this, activity_login.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(), "帳號已存在，請更換帳號或進行登入", Toast.LENGTH_SHORT).show();
        }
    }

    private void add_data(String account, String psw, String hint){
        db = DH.getWritableDatabase();
        Cursor cursor = db.query("TB_Login_data", null,"_Account=?",new String[]{account},null,null,null);
        cursor.moveToNext();

        ContentValues values = new ContentValues();
        values.put("_Account", account);
        values.put("_Password", psw);
        values.put("_Hint", hint);
        String ipsw = "";
        values.put("_Note",ipsw);
        values.put("_Noteedittime",ipsw);
        values.put("_MFavorite", ipsw);  //剛開始最愛清單為空

        long a = db.insert("TB_Login_data",null, values);  //寫入資料
        if(a!=-1)
            Toast.makeText(getApplicationContext(), "註冊成功",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "註冊失敗",Toast.LENGTH_SHORT).show();

        cursor.close();
        db.close();
    }
}