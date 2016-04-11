package com.nololed.andreamantani.nololed.FontClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by andreamantani on 29/03/16.
 */
public class CustomItalicFont extends TextView {
    public CustomItalicFont(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bariol_italic.ttf"));
    }
}
