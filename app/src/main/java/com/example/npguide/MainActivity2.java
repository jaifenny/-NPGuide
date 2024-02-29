package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView textShow;
    private Button tab,tab2;
    private Button[] bt;
    int counter=0, next = 1, backMethod = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bt = new Button[16];
        //not fixed
        bt[0] = (Button) findViewById(R.id.button1);
        bt[1] = (Button) findViewById(R.id.button2);
        bt[2] = (Button) findViewById(R.id.button4);
        bt[3] = (Button) findViewById(R.id.button5);
        bt[4] = (Button) findViewById(R.id.button6);
        bt[5] = (Button) findViewById(R.id.button7);
        bt[6] = (Button) findViewById(R.id.button11);
        bt[7] = (Button) findViewById(R.id.button12);
        bt[8] = (Button) findViewById(R.id.button13);
        bt[9] = (Button) findViewById(R.id.button15);
        bt[10] = (Button) findViewById(R.id.button16);
        //fixed
        bt[11] = (Button) findViewById(R.id.button3);
        bt[12] = (Button) findViewById(R.id.button8);
        bt[13] = (Button) findViewById(R.id.button9);
        bt[14] = (Button) findViewById(R.id.button10);
        bt[15] = (Button) findViewById(R.id.button14);

        tab = (Button) findViewById(R.id.tab);
        tab2 = (Button) findViewById(R.id.tab2);
        tab2.setVisibility(View.INVISIBLE); // 隱藏

        textShow = (TextView) findViewById(R.id.textView18);

        for(int i=0;i<16;i++){
            bt[i].setEnabled(false);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater. inflate (R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.library:
                Intent intent=new Intent(MainActivity2.this,activity_main3_5.class);
                startActivity(intent);
                break;
            case R.id.back:
                Intent intent1=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.home:
                Intent intent2=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.note:
                Intent intent3=new Intent(this,MainActivity_note.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //窮舉法
    private void exhaustive(){
        if(counter < 11){
            bt[counter].setText("1");
        }
        else{
            int s = Integer.parseInt(bt[0].getText().toString());
            next = 1;
            while(s==4){
                s = Integer.parseInt(bt[next].getText().toString());
                bt[next-1].setText("1");
                next++;
            }
            s++;
            bt[next-1].setText(Integer.toString(s));
        }

        if(counter==25){
            tab2.setVisibility(View.VISIBLE); //顯示"回溯法"按鈕
            textShow.setText("是不是覺得這個方法很爛阿~試試看回溯法吧!");
        }
        else if(counter==40){
            textShow.setText("不要再點了拉，試試看回溯法");
        }
        else if(counter==80){
            tab.setEnabled(false);
            textShow.setText("不給你按哈哈");
        }
        counter++;
    }

    //回溯法
    private void back_method(){
        switch (counter){
            case 1:
                bt[0].setText("1");
                break;
            case 2:
                bt[1].setBackgroundColor(Color.parseColor("#FF0000")); //red
                break;
            case 3:
                bt[1].setBackground(getDrawable(R.drawable.bt_border)); //yellow
                bt[0].setText("3");
                break;
            case 4:
                bt[1].setText("1");
                break;
            case 5:
                bt[2].setBackgroundColor(Color.parseColor("#FF0000")); //red
                break;
            case 6:
                bt[2].setBackground(getDrawable(R.drawable.bt_border)); //yellow
                bt[0].setText("4");
                bt[1].setText("1");
                bt[2].setText("3");
                break;
            case 7:
                bt[3].setText("3");
                break;
            case 8:
                bt[4].setText("2");
                break;
            case 9:
                bt[5].setText("1");
                break;
            case 10:
                bt[6].setText("4");
                break;
            case 11:
                bt[7].setText("1");
                break;
            case 12:
                bt[8].setText("1");
                break;
            case 13:
                bt[9].setText("3");
                break;
            case 14: //end
                bt[10].setText("2");
                backMethod = 2; //準備切換到介紹頁面
                tab.setVisibility(View.INVISIBLE);
                tab2.setText("點我看介紹");
                tab2.setVisibility(View.VISIBLE);
                textShow.setText("恭喜找到答案! 看看解說吧");
                break;
        }
        counter++;
    }

    public void btn_tab(View v){
        if(backMethod==0){
            exhaustive();  //窮舉法
        }
        else{
            back_method(); //回溯法
        }
    }

    public void btn_tab2(View v){
        if(backMethod==0){
            backMethod=1;
            counter=1;
            next=0;

            initial_box();
            textShow.setText("仔細觀察有甚麼規律喔~");
            tab.setEnabled(true);
            tab2.setVisibility(View.INVISIBLE); // 按一下之後隱藏
        }
        else if(backMethod==2){
            //切換到介紹頁面
            Intent intent=new Intent(MainActivity2.this,MainActivity11.class);
            startActivity(intent);
        }
    }

    private void initial_box(){
        for(int i=0;i<11;i++){
            bt[i].setText(""); // 把not finxed的按鈕文字清空
        }
    }
}