package com.nololed.andreamantani.nololed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nololed.andreamantani.nololed.Model.GalleryItem;
import com.nololed.andreamantani.nololed.Utils.Constants;
import com.nololed.andreamantani.nololed.Utils.GalleryItems;
import com.nololed.andreamantani.nololed.Utils.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 18/04/16.
 */
public class ImagesListActivity extends AppCompatActivity {



    ImageView imageShow;
    List<GalleryItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageShow = (ImageView) findViewById(R.id.gallery_image_showed);
        items = new ArrayList<>();

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            GalleryItems.addImage(extras.getString("url_image"));
            showImageInGallery(extras.getString("url_image"));
        }

        populateScroll();
    }

    public void populateScroll(){
        LinearLayout content = (LinearLayout) findViewById(R.id.gallery_content);

        content.removeAllViews();
        items.clear();


        content.addView(new GalleryItem(this, null, addPhoto));

        for(int i = 0 ; i < GalleryItems.getImages().size(); i++){
            GalleryItem newItem = new GalleryItem(this,null,showImage,GalleryItems.getImages().get(i), delete);
            content.addView(newItem);
            items.add(newItem);
        }


    }

    private View.OnClickListener addPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photo = new Intent(ImagesListActivity.this, TakePhotoActivity.class);
            photo.putExtra("is_more_photo", true);
            photo.putExtra("origin", "gallery");
            startActivity(photo);
        }
    };

    private View.OnClickListener delete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < items.size();i++){
                if(v == items.get(i).getDeleteLayout()){
                    GalleryItems.getImages().remove(i);
                    items.remove(i);
                    populateScroll();
                }
            }
        }
    };

    private View.OnClickListener showImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LinearLayout content = (LinearLayout) findViewById(R.id.gallery_content);
            String url = ((GalleryItem) v).getImagePath();

            showImageInGallery(url);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_email_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mail:
                Constants.setEstimate(Utilities.getSystem());
                startActivity(new Intent(ImagesListActivity.this, PreviewEstimateActivity.class));
        }

        return true;
    }

    public void showImageInGallery(String url){
        LinearLayout content = (LinearLayout) findViewById(R.id.gallery_content);

        File sd = new File(url);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(url, bmOptions);

        float imageHeight = (float) bmOptions.outHeight;
        float imageWidth = (float) bmOptions.outWidth;

        int scaled = Math.round(imageWidth * (450 / imageHeight));

        bitmap = Bitmap.createScaledBitmap(bitmap, scaled, 450, true);
        imageShow.setImageBitmap(bitmap);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Uscita")
                .setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ImagesListActivity.this.finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
