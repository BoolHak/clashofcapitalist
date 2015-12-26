package com.yumait.clashofcapitalists.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bilel Methnani on 12/26/2015.
 */
public class Utils {


    private static final String TOST_MESSAGE = "done!";

    public static void Toast(String text,Context context){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void Toast(Context context){
        Toast.makeText(context, TOST_MESSAGE,Toast.LENGTH_SHORT).show();
    }
}
