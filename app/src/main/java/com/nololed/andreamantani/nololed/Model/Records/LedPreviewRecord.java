package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by andreamantani on 12/06/16.
 */
public class LedPreviewRecord extends LinearLayout {

    public LedPreviewRecord(Context context, AttributeSet attrs, String name, double price) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.led_preview_record, this);

        TextView txtName = (TextView) findViewById(R.id.preview_led_name);
        TextView txtPrice = (TextView) findViewById(R.id.preview_led_cost);

        txtName.setText(name);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        formatter.setRoundingMode(RoundingMode.FLOOR);
        txtPrice.setText(formatter.format(price));

    }
}