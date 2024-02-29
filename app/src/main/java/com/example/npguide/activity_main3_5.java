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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;
import com.example.npguide.MyAdapter.InnerItemOnclickListener;


import java.util.ArrayList;
import java.util.List;

public class activity_main3_5 extends AppCompatActivity implements InnerItemOnclickListener, OnItemClickListener {
    ListView lv;
    List<Boolean> listShow;    // 這個用來記錄哪幾個 item 是被打勾的
    List<String> name_list;
    int lib_num = 9; //library數目
    String account;
    boolean[] mFavorite = new boolean[lib_num]; // 最愛清單(之後更改數量)

    public DBHelper DH = null;
    private SQLiteDatabase db;  // 資料庫物件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main35);


        // 建立資料庫，若存在就將之開啟
        DH = new DBHelper(this,"Login_data.db",null, 2);

        //得到資料
        //1,得到SharedPreferences物件
        SharedPreferences preferences = getSharedPreferences("config", 0);
        //2,取出資料
        account = preferences.getString("Account","");

        ini_all();  //!!! 之後把裡面的add改成library的資訊

        MyAdapter a = new MyAdapter(this.getApplicationContext(),this, name_list, listShow); //!
        a.setOnInnerItemOnClickListener(this);
        lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(a);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.checkBox2:
                //紀錄打勾狀態
                mFavorite[position] = !mFavorite[position];
                update_data(); // 只有勾選方格才可以存到資料庫
                break;
            case R.id.textview_item:
                //.makeText(getApplicationContext(), "textView : "+ position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(this, MainActivity10.class);
                        break;
                    case 1:
                        intent.setClass(this, MainActivity12.class);
                        break;
                    case 2:
                        intent.setClass(this, MainActivity13.class);
                        break;
                    case 3:
                        intent.setClass(this, MainActivity4.class);
                        break;
                    case 4:
                        intent.setClass(this, MainActivity5.class);
                        break;
                    case 5:
                        intent.setClass(this, MainActivity6.class);
                        break;
                    case 6:
                        intent.setClass(this, MainActivity7.class);
                        break;
                    case 7:
                        intent.setClass(this, MainActivity8.class);
                        break;
                    case 8:
                        intent.setClass(this, MainActivity9.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                activity_main3_5.this.finish();
                break;
            default:
                break;
        }
    }

    // 儲存最愛清單
    private void update_data(){
        String s="";
        for(int i=0;i<lib_num;i++){
            s += (mFavorite[i]?"1":"0") + " "; //以空白分隔
        }

        //資料庫
        db = DH.getWritableDatabase();
        Cursor cursor = db.query("TB_Login_data", null,"_Account=?",new String[]{account},null,null,null);
        cursor.moveToNext();

        //載入資料
        ContentValues values = new ContentValues();
        values.put("_MFavorite", s);

        db.update("TB_Login_data", values, "_Account='"+account.toString()+"'", null);
        //Toast.makeText(getApplicationContext(), "更新:" + s,Toast.LENGTH_SHORT).show();

        cursor.close();
        db.close();
    }

    private void ini_all(){
        listShow = new ArrayList<Boolean>();
        name_list = new ArrayList<String>();

        // listview顯示
        name_list.add("NP Hard                           ");
        name_list.add("Subset sum problem             ");
        name_list.add("Travelling salesman problem    ");
        name_list.add("Boolean Satisfiability         ");
        name_list.add("Three-Satisfiability           ");
        name_list.add("Clique                             ");
        name_list.add("Hamiltonian Path and Cycle     ");
        name_list.add("Vertex Cover                    ");
        name_list.add("Three Dimensional Matching     ");

        //資料庫
        db = DH.getWritableDatabase();
        Cursor cursor = db.query("TB_Login_data", null,"_Account=?",new String[]{account},null,null,null);
        cursor.moveToNext();
        String s = cursor.getString(4).toString();
        if(!s.matches("")){ //有勾選資料
            String[] ss = s.split(" ");
            String tt ="";
            for(int i=0; i<lib_num; i++){
                if(ss[i].matches("1")){
                    mFavorite[i] = true;
                }
                else{
                    mFavorite[i] = false;
                }
            }
        }
        else{
            for(int i=0;i<lib_num;i++){
                mFavorite[i] = false;
            }
        }

        // 取出勾選紀錄
        for(int i=0;i < lib_num;i++){
            listShow.add(mFavorite[i]);
        }

        db.close();
        cursor.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ; //整體的，用不到
    }

    public void logout(View v){
        Intent intent = new Intent(this, activity_login.class);
        startActivity(intent);
        activity_main3_5.this.finish();
    }

    public void gotoMynote(View v){
        Intent intent = new Intent(this, MainActivity_note.class);
        startActivity(intent);
    }


    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater. inflate (R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();

        switch (id){

            case R.id.library:
                //Intent intent=new Intent(this,activity_main3_5.class);
                //startActivity(intent);
                break;
            case R.id.back:
                Intent intent1=new Intent(this,MainActivity15.class);
                startActivity(intent1);
                break;
            case R.id.home:
                Intent intent2=new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.note:
                Intent intent3=new Intent(this,MainActivity_note.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}