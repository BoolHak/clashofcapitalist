package com.yumait.clashofcapitalists.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.yumait.clashofcapitalists.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private CircleProgress circleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface dolarTypeFace = Typeface.createFromAsset(this.getAssets(),
                "font/dolar.ttf");

        TextView spalshText = (TextView) findViewById(R.id.splash_text);
        spalshText.setTypeface(dolarTypeFace);

        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(circleProgress.getProgress() == 99){
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            timer.cancel();
                        }
                        circleProgress.setProgress(circleProgress.getProgress() + 1);
                    }
                });
            }
        }, 1000, 100);
    }
}
