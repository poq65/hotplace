package com.example.jihyun.hotplace_ansan;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;


/**
 * Created by jihyun on 2017-03-12.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
        TextView TextView_Title;
        TextView TextView_Description;

                public ViewHolder(View itemView) {
                super(itemView);
                TextView_Title = (TextView) itemView.findViewById(R.id.txtTitle_item);
                TextView_Description = (TextView) itemView.findViewById(R.id.txtDescription_item);
                cardView=(CardView)itemView.findViewById(R.id.cardView);
            }


}