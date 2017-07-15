package com.example.jihyun.hotplace_ansan;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jihyun on 2017-03-12.
 */
public class ItemData extends RealmObject {

    @PrimaryKey
    int id;
    String Title;
    String Description;

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String Title){
        this.Title = Title;
    }

    public String getTitleName(){
        return this.Title;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    public String getDescription(){
        return this.Description;
    }

}