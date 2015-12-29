package com.yumait.clashofcapitalists.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.dialog.SimpleDialog;
import com.yumait.clashofcapitalists.fragment.Home;
import com.yumait.clashofcapitalists.ui.SmallProgressModel;
import com.yumait.clashofcapitalists.views.SmallProgress;

public class MainActivity extends AppCompatActivity implements OnTouchListener{

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

        initIndicators();

        String message = getResources().getString(R.string.wellcome);
        SimpleDialog dialog = new SimpleDialog(this,message);
        //dialog.show();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) return;

            Home homeFragment = Home.newInstance();
            fragmentTransaction.add(R.id.fragment_container,homeFragment);
            fragmentTransaction.commit();
        }

    }

    private void initIndicators(){
        SmallProgress money = (SmallProgress) findViewById(R.id.money);
        SmallProgress food = (SmallProgress) findViewById(R.id.food);
        SmallProgress tech = (SmallProgress) findViewById(R.id.tech);
        SmallProgress cogs = (SmallProgress) findViewById(R.id.cogs);

        money.initView(this,new SmallProgressModel(R.mipmap.ic_money,100, 200));
        food.initView(this,new SmallProgressModel(R.mipmap.ic_food,20, 200));
        tech.initView(this,new SmallProgressModel(R.mipmap.ic_tech,50, 200));
        cogs.initView(this, new SmallProgressModel(R.mipmap.ic_cogs, 70, 200));

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_map));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDialog dialog = new SimpleDialog(MainActivity.this,"Should go to the map");
                dialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        return false;
    }
}
