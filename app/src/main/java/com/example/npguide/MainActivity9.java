package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
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
                Intent intent=new Intent(MainActivity9.this,activity_main3_5.class);
                startActivity(intent);
                break;
            case R.id.back:
                Intent intent1=new Intent(MainActivity9.this,activity_main3_5.class);
                startActivity(intent1);
                break;
            case R.id.home:
                Intent intent2=new Intent(MainActivity9.this,MainActivity.class);
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