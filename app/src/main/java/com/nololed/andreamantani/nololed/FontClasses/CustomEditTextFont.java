package com.nololed.andreamantani.nololed.FontClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by andreamantani on 04/04/16.
 */
public class CustomEditTextFont extends EditText {

    public CustomEditTextFont(Context context, AttributeSet attrs){
        super(context, attrs);

        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bariol_regular.ttf"));
    }
}
