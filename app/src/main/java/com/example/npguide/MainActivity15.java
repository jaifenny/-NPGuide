package com.example.npguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity15 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
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
                intent.setClass(this,MainActivity3.class);
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

    public void go_to_lib(View v){
        Intent intent = new Intent(this, activity_main3_5.class);
        startActivity(intent);
        MainActivity15.this.finish();
    }
}