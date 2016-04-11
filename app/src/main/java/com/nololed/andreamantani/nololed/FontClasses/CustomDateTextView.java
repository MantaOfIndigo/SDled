package com.nololed.andreamantani.nololed.FontClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by andreamantani on 03/04/16.
 */
public class CustomDateTextView extends TextView {
    public CustomDateTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/regular.ttf"));
    }
}
