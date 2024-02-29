package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_login extends AppCompatActivity {
    private EditText display_account, display_ps;
    private TextView display_hint;
    private Button login, sign_up, hint;
    public DBHelper DH = null;
    private SQLiteDatabase db;  // 資料庫物件
    private boolean[] mFavorite; //最愛清單
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        display_account = (EditText)findViewById(R.id.edittext_account);
        display_ps = (EditText)findViewById(R.id.edittext_password);
        display_hint = (TextView)findViewById(R.id.textview_hint);
        hint = (Button) findViewById(R.id.button_forgot);
        login = (Button)findViewById(R.id.button_login);
        sign_up = (Button)findViewById(R.id.button_signup);

        // 建立資料庫，若存在就將之開啟
        DH = new DBHelper(activity_login.this,"Login_data.db",null, 2);

        //1,例項化SharedPreferences物件,引數1:儲存的名字,引數2:儲存的模式MODE_PRIVATE私有的
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        //2,讓SharedPreferences處於可編輯狀態
        editor = preferences.edit();
    }


    public void login_btn(View view){
        //先檢查帳號是否存在
        //若存在 : 檢查密碼是否正確，否則toast"密碼錯誤"
        //若不存在 : toast"帳號不存在，請點選註冊"
        String account = display_account.getText().toString();
        if(account.matches("") || account.matches("")){
            Toast.makeText(getApplicationContext(), "帳號或密碼不能為空",Toast.LENGTH_SHORT).show();
        }
        else {
            db = DH.getWritableDatabase();
            Cursor c = db.query("TB_Login_data", null, "_Account=?", new String[]{account}, null, null, null);
            c.moveToNext();
            if(c.getCount() != 0) { // 成功取到資料
                String psw = c.getString(2).toString();
                if(display_ps.getText().toString().matches(psw)){
                    Intent intent = new Intent();
                    final Bundle bundle = new Bundle();
                    intent.setClass(this, MainActivity.class); //!!!
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //跳到別的activity，同時finish掉中間經過的activity

                    String name = c.getString(1).toString();
                    //3,儲存資料,類似於map
                    editor.putString("Account", name);
                    //4,提交
                    editor.commit();

                    startActivity(intent);
                    activity_login.this.finish();
                }
                else {  // 若密碼錯誤
                    Toast.makeText(getApplicationContext(), "帳號或密碼錯誤",Toast.LENGTH_SHORT).show();
                    clean_up_edittext();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "帳號不存在",Toast.LENGTH_SHORT).show();
                clean_up_edittext();
            }
            c.close();
            db.close();
        }
    }

    private void hehe(){
        String account = display_account.getText().toString();
        db = DH.getWritableDatabase();
        Cursor c = db.query("TB_Login_data", null, "_Account=?", new String[]{account}, null, null, null);
        c.moveToNext();

        ContentValues values = new ContentValues();
        values.put("_Noteedittime", "");
        values.put("_Note", "");

        db.update("TB_Login_data", values, "_Account='"+account.toString()+"'", null);
    }

    //忘記密碼
    public void hint_btn(View view){
        String account = display_account.getText().toString();
        if(account.matches("")){
            Toast.makeText(getApplicationContext(), "請輸入帳號",Toast.LENGTH_SHORT).show();
        }
        else{
            db = DH.getWritableDatabase();
            Cursor cursor = db.query("TB_Login_data", null,"_Account=?",new String[]{account},null,null,null);
            cursor.moveToNext();
            if(cursor.getCount()==0){
                Toast.makeText(getApplicationContext(), "查無此帳號",Toast.LENGTH_SHORT).show();
            }
            else {
                //顯示使用者當初註冊的提示訊息
                String hint = cursor.getString(3).toString();
                display_hint.setText("Hint:"+hint);  //顯示提示訊息
            }
        }
    }


    public void sign_up_btn(View view){
        // 跳轉到註冊頁面
        Intent intent = new Intent();
        intent.setClass(this,activity_signup.class);
        startActivity(intent);
    }

    private void clean_up_edittext(){
        display_account.setText("");
        display_ps.setText("");
        display_hint.setText("");
    }

}