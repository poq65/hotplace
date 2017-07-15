package com.example.jihyun.hotplace_ansan;

import android.widget.EditText;

/**
 * Created by jihyun on 2017-04-28.
 */

public class Utility {

    public static boolean isBlankField(EditText etPersonData) {
        return etPersonData.getText().toString().trim().equals("");
    }
}