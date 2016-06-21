package com.nololed.andreamantani.nololed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyData;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyRecord;
import com.nololed.andreamantani.nololed.InnerDatabase.TableModel.OldTecFamilyTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andreamantani on 10/06/16.
 */
public class SelectFamilyActivity extends AppCompatActivity {


    ImageView currentImage;
    TextView title;
    String familySelected;

    int newOrNot = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_family);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            newOrNot = extra.getInt("new_item");
        }

        currentImage = (ImageView) findViewById(R.id.family_image);
        title = (TextView) findViewById(R.id.family_title_category);

        familySelected = "Neon";
        title.setText(familySelected);
        int resourceId = OldTecFamilyTable.getItemFromName("Neon").getImageResources();

        currentImage.setImageResource(resourceId);

        final ListView listview = (ListView) findViewById(R.id.family_list);
        String[] values = OldTecFamilyData.familyNames;

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.family_record, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                familySelected = item;
                title.setText(familySelected);
                int resourceId = OldTecFamilyTable.getItemFromName(item).getImageResources();

                currentImage.setImageResource(resourceId);

            }

        });

    }

    public void expandFamily(View v){
        Intent intnt = new Intent(SelectFamilyActivity.this, SelectModelActivity.class);
        intnt.putExtra("family", familySelected);
        intnt.putExtra("new_item", newOrNot);
        startActivity(intnt);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
