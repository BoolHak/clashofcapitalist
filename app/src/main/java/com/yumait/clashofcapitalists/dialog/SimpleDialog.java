package com.yumait.clashofcapitalists.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yumait.clashofcapitalists.R;
import com.yumait.clashofcapitalists.activity.MainActivity;

/**
 * Created by Bilel Methnani on 12/25/2015.
 */
public class SimpleDialog extends Dialog {

    private View dialogView;
    private AnimationSet modalInAnim;
    private AnimationSet modalOutAnim;
    private String message;

    MainActivity mainController;
    public SimpleDialog(Context context,String message) {
        super(context, R.style.alert_dialog);
        mainController = (MainActivity) context;
        this.message = message;
    }


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_dialog);
        dialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        TextView title = (TextView) findViewById(R.id.title);
        TextView messageText = (TextView) findViewById(R.id.message);


        ImageButton btnClose = (ImageButton) findViewById(R.id.btn_close);

        title.setTypeface(mainController.robotoLight);
        messageText.setTypeface(mainController.dolar);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.startAnimation(modalOutAnim);
            }
        });

        modalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        modalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        modalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialogView.setVisibility(View.GONE);
                dialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    protected void onStart() {
        dialogView.startAnimation(modalInAnim);
    }


}
