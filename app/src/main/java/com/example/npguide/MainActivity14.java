package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity14 extends AppCompatActivity {

    TextView display_stu, display_pro, display_end;
    Button anime_btn, next_page_btn;
    int conver_count = 0;
    String[] s_pro = {"若你答對\n接下來的問題","我就不當你","若你答錯了，\n那你就會被我當"," 你猜猜\n 我是否會當掉你？","","",  "XDD    "};
    String[] s_stu = {"",      "",                         "好!",                    "......", "我猜您會當掉我", " (好像...\n哪裡怪怪的)","0.0???"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);

        display_pro = (TextView) findViewById(R.id.textview_pro);
        display_stu = (TextView) findViewById(R.id.textview_stu);
        display_end = (TextView)findViewById(R.id.textView53);
        anime_btn = (Button) findViewById(R.id.anime_btn);
        next_page_btn = (Button)findViewById(R.id.anime_btn2);

        display_end.setVisibility(View.INVISIBLE);
        next_page_btn.setVisibility(View.INVISIBLE);
    }
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater. inflate (R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id =item.getItemId();
        Intent intent = new Intent();
        switch (id){
            case R.id.library:
                intent.setClass(this,activity_main3_5.class);
                break;
            case R.id.back:
                intent.setClass(this,MainActivity11.class);
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

    public void start_anime(View v){
        anime_btn.setVisibility(View.INVISIBLE);
        display_end.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                //這裡寫入要作的事情
                if(conver_count!=7) { //來回對話次數
                    display_stu.setText(s_stu[conver_count]);
                    display_pro.setText(s_pro[conver_count]);
                    conver_count++;
                    handler.postDelayed(this, 2000);
                }
                else {
                    anime_btn.setText("再看一次");
                    anime_btn.setVisibility(View.VISIBLE);
                    conver_count = 0;
                    display_end.setVisibility(View.VISIBLE);
                    next_page_btn.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(this); //失敗就暫停
                }
            }
        };
        handler.post(runnable); //執行
    }

    public void next_page(View v){
        Intent intent = new Intent(this, MainActivity3.class); //跳到介紹葉面
        startActivity(intent);
        MainActivity14.this.finish();
    }
}