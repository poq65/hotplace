package com.example.jihyun.hotplace_ansan;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by jihyun on 2017-03-12.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private RealmResults<ItemData> mDataset;
    CardView cardView;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView mName;
        public TextView mDiscription;

        public ViewHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.txtTitle_item);
            mDiscription = (TextView) v.findViewById(R.id.txtDescription_item);
        }
    }

//    public PlaceAdapter(ArrayList<ItemData> itemDatas, Context context) {
//        this.itemDatas = itemDatas;
//        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.context=context;
//    }

    public PlaceAdapter(RealmResults<ItemData> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.viewholder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mName.setText(mDataset.get(position).getTitleName());
        holder.mDiscription.setText(mDataset.get(position).getDescription());


//        holder.cardView.setOnLongClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 Toast.makeText(context, mDataset.get(position).getTitleName(),Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(context,ItemContent.class);
//                intent.putExtra("item",itemDatas.get(position).getTitleName());
//                intent.putExtra("Description",itemDatas.get(position).getDescription());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}