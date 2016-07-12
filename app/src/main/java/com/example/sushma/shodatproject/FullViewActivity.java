package com.example.sushma.shodatproject;

/**
 * Created by sushma on 7/11/16.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FullViewActivity extends Activity {

    private ImageView imgView;
    private TextView titleText;
    private TextView id;
    private int position;
    ArrayList<detailObject> fullList = new ArrayList<detailObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        fullList = intent.getParcelableArrayListExtra("list");
        position = intent.getIntExtra("position", 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        imgView = (ImageView) findViewById(R.id.full_view);
        titleText = (TextView)  findViewById(R.id.imageTitle);
        id = (TextView) findViewById(R.id.albumID);
        titleText.setText(fullList.get(position).title);
        id.setText(Integer.toString(fullList.get(position).albumId));
        new DownloadImageTask(imgView).execute(fullList.get(position).url);

    }


}