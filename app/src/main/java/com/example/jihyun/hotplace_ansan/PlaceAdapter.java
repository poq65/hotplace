package com.example.jihyun.hotplace_ansan;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
public class PlaceAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<ItemData> itemDatas=new ArrayList<ItemData>();
    private LayoutInflater layoutInflater=null;


    public PlaceAdapter(ArrayList<ItemData> itemDatas, Context context) {
        this.itemDatas = itemDatas;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.viewholder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ItemData itemData=itemDatas.get(position);

        TextView TextView_Title=holder.TextView_Title;
        TextView TextView_Description=holder.TextView_Description;

        TextView_Title.setText(itemData.getTitleName());
        TextView_Description.setText(itemData.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, itemDatas.get(position).getTitleName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,ItemContent.class);
                intent.putExtra("item",itemDatas.get(position).getTitleName());
                intent.putExtra("Description",itemDatas.get(position).getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }


}