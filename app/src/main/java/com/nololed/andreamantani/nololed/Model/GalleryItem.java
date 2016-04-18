package com.nololed.andreamantani.nololed.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nololed.andreamantani.nololed.R;

import org.w3c.dom.Attr;

import java.io.File;

/**
 * Created by andreamantani on 18/04/16.
 */
public class GalleryItem extends LinearLayout {

    int index;
    FrameLayout deleteLayout;
    String imagePath;

    public GalleryItem(Context context, AttributeSet attrs, OnClickListener listener, String imagePath, OnClickListener delete){
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.gallery_item, this);


        this.index = index;
        this.imagePath = imagePath;


        deleteLayout = (FrameLayout) findViewById(R.id.gallery_item_delete);
        deleteLayout.setOnClickListener(delete);

        this.setOnClickListener(listener);
        ImageView image = (ImageView) findViewById(R.id.gallery_item_image);

        File sd = new File(imagePath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(sd.getAbsolutePath(), bmOptions);

        bitmap = Bitmap.createScaledBitmap(bitmap, 90, 90, true);
        image.setImageBitmap(bitmap);
    }

    public GalleryItem(Context context, AttributeSet attrs, OnClickListener listener){
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.gallery_item, this);


        FrameLayout hider = (FrameLayout) findViewById(R.id.gallery_item_delete);
        hider.setVisibility(View.GONE);

        ImageView image = (ImageView) findViewById(R.id.gallery_item_image);
        image.setImageResource(R.drawable.plusitem);

        image.setOnClickListener(listener);



    }

    public FrameLayout getDeleteLayout(){
        return this.deleteLayout;
    }

    public String getImagePath(){
        return this.imagePath;
    }
}
