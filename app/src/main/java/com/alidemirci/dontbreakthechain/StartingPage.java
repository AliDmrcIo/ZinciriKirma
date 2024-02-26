package com.alidemirci.dontbreakthechain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

public class StartingPage extends AppCompatActivity {

    CountDownTimer countDownTimer;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_RUN_KEY = "firstRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean firstRun = settings.getBoolean(FIRST_RUN_KEY, true);

        if (firstRun) { //uygulama ilk defa açılınca
            countDownTimer=new CountDownTimer(1000,1000){ //geri sayan timer'ı koydum
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    Intent intent=new Intent(StartingPage.this,ReadMePage.class);
                    startActivity(intent);
                    finish();
                }
            }.start();

            // İlk açılış işlemleri tamamlandığında durumu güncelle
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_RUN_KEY, false);
            editor.apply();

        }else{
            countDownTimer=new CountDownTimer(1000,1000){ //geri sayan timer'ı koydum
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    Intent intent=new Intent(StartingPage.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }.start();
        }

    }
}