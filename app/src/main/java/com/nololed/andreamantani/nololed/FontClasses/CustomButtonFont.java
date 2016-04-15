package com.nololed.andreamantani.nololed.FontClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import org.w3c.dom.Attr;

/**
 * Created by andreamantani on 29/03/16.
 */
public class CustomButtonFont extends Button {
    public CustomButtonFont(Context context, AttributeSet attrs){
        super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bariol_italic.ttf"));
    }
}
