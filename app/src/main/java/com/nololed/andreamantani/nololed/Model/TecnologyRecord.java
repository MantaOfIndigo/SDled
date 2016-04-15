package com.nololed.andreamantani.nololed.Model;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.R;

import java.io.File;

/**
 * Created by andreamantani on 21/03/16.
 */
public class TecnologyRecord extends LinearLayout {

    Tecnology values;
    LinearLayout main;
    LinearLayout delete;

    public TecnologyRecord(Context context, AttributeSet attrs, Tecnology values, OnClickListener mainListener, OnClickListener deleteListener){
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tec_record, this);
        this.values = values;
        ImageView img = (ImageView) findViewById(R.id.record_img);
        TextView txtTitle = (TextView) findViewById(R.id.record_title);
        TextView txtDesc = (TextView) findViewById(R.id.record_desc);
        this.main = (LinearLayout) findViewById(R.id.record_main);
        this.delete = (LinearLayout) findViewById(R.id.record_delete);
        this.main.setOnClickListener(mainListener);
        this.delete.setOnClickListener(deleteListener);

        txtTitle.setText(String.valueOf(values.getQta()));
        txtDesc.setText(values.getDesc());
        //img.setImageURI(values.getPhoto());
        Log.v("tecnology", "image: " + values.getPhoto().getPath());


        Log.v("check", "scale: " + img.getWidth() + " " + img.getHeight());

        File sd = new File(values.getPhoto().getPath());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(sd.getAbsolutePath(), bmOptions);

        bitmap = Bitmap.createScaledBitmap(bitmap, 90, 90, true);
        img.setImageBitmap(bitmap);
        //bitmap.recycle();

    }

    public LinearLayout getMainLayout(){
        return this.main;
    }
    public LinearLayout getDeleteLayout(){
        return this.delete;
    }


    public void setTitle(String value){
        TextView txt = (TextView) findViewById(R.id.record_title);
        txt.setText(value);
    }


    public Tecnology getTecnology(){
        return this.values;
    }

}
