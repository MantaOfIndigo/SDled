package com.nololed.andreamantani.nololed.Utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreamantani on 18/04/16.
 */
public class GalleryItems extends Application {

    static private List<String> images = new ArrayList<>();

    public static void addImage(String value){
        images.add(value);
    }
    public static List<String> getImages(){
        return images;
    }
}
