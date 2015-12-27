package com.yumait.clashofcapitalists.activity;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.dialog.SimpleDialog;

public class MainActivity extends AppCompatActivity {

    public static Typeface robotoLight;
    public static Typeface dolar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        robotoLight = Typeface.createFromAsset(this.getAssets(),
                "font/dolar.ttf");
        dolar = Typeface.createFromAsset(this.getAssets(),
                "font/light.ttf");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String message = getResources().getString(R.string.wellcome);
        SimpleDialog dialog = new SimpleDialog(this,message);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
