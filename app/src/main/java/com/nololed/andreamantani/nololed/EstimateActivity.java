package com.nololed.andreamantani.nololed;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andreamantani on 30/06/16.
 */
public class EstimateActivity extends AppCompatActivity {

    int coyunt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.estimate);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_type_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:

                    createPDF();
                break;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void createPDF() {
        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
        }

        // content view is EditText for my case in which user enters pdf content
        View content = findViewById(R.id.estimate_layout);

        // crate a page info with attributes as below
// page number, height and width
// i have used height and width to that of pdf content view
        int pageNumber = 3;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                content.getHeight(), pageNumber).create();

        // create a new page from the PageInfo
        PdfDocument.Page page = document.startPage(pageInfo);

// repaint the user's text into the page
        content.draw(page.getCanvas());


// do final processing of the page
        document.finishPage(page);

        // saving pdf document to sdcard
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = "pdfdemo"
                + sdf.format(Calendar.getInstance().getTime()) + coyunt + ".pdf";

        coyunt++;
        // all created files will be saved at path /sdcard/PDFDemo_AndroidSRC/

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "PDFCREATOR" + File.separator);


        File outputFile = new File(Environment.getExternalStorageDirectory() + File.separator + "PDFCREATOR" + File.separator, pdfName);




        boolean success = true;

        if (!folder.exists()) {
            success = folder.mkdir();
        }

        if(success){
            try {
                outputFile.createNewFile();
                OutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                document.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
