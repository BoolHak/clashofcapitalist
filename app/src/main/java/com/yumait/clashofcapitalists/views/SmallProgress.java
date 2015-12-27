package com.yumait.clashofcapitalists.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.activity.MainActivity;
import com.yumait.clashofcapitalists.ui.SmallProgressModel;

/**
 * Created by Bilel Methnani on 12/27/2015.
 */
public class SmallProgress extends RelativeLayout{

    private static final int MAX_PROGRESS = 100;
    LayoutInflater inflater;
    SmallProgressModel model;
    ImageView image;
    TextView text;
    ProgressBar progressBar;


    public SmallProgress(Context context) {
        super(context);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_small_progess, this);

    }

    public SmallProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_small_progess, this);

    }

    public SmallProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_small_progess, this);
    }

    public void initView(Context context, SmallProgressModel model){
        this.model = model;
        this.setClickable(true);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        image.setImageResource(model.getImage());
        text.setTypeface(((MainActivity) context).dolar);
        changeProgress(model.getValue(), model.getMax());

    }

    private void changeProgress(int value, int max) {
        text.setText("" + value);
        int progressValue = 0;
        if(max > 0 && value > 0) progressValue = (value* MAX_PROGRESS) / max;
        progressBar.setProgress(progressValue);
    }
}
