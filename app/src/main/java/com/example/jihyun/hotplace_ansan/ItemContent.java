package com.example.jihyun.hotplace_ansan;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jihyun.hotplace_ansan.R;

/**
 * Created by jihyun on 2017-03-12.
 */
public class ItemContent extends AppCompatActivity {

    TextView tv_title;
    TextView tv_description;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.itemcontent);
        Intent i=getIntent();
        String item=i.getStringExtra("item");
        String description=i.getStringExtra("Description");

        tv_title=(TextView) findViewById(R.id.tv_title);
        tv_description=(TextView)findViewById(R.id.tv_description);

        tv_title.setText(item);
        tv_description.setText(description);


    }


}