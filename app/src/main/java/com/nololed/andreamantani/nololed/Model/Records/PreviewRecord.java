package com.nololed.andreamantani.nololed.Model.Records;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.R;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by andreamantani on 09/05/16.
 */
public class PreviewRecord extends LinearLayout {

    public PreviewRecord(Context context, AttributeSet attrs, String name, double price, int qta) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.preview_record, this);

        TextView txtName = (TextView) findViewById(R.id.preview_name);
        TextView txtPrice = (TextView) findViewById(R.id.preview_cost);
        TextView txtQta = (TextView) findViewById(R.id.preview_qta);

        txtName.setText(name);
        txtQta.setText("" + qta);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        formatter.setRoundingMode(RoundingMode.FLOOR);
        txtPrice.setText(formatter.format(price));

    }
}
