package com.alidemirci.dontbreakthechain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alidemirci.dontbreakthechain.R;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReadMePage extends AppCompatActivity {

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me_page);

        floatingActionButton = findViewById(R.id.floatingActionButtonNext);


    }

    public void next(View view){
        Intent intent=new Intent(ReadMePage.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}