package com.ats.bse_tv;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.bse_tv.bean.Item;

import java.util.ArrayList;

/**
 * Created by MIRACLEINFOTAINMENT on 02/01/18.
 */

public class ItemListAdapter extends BaseAdapter {

    private ArrayList<Item> dispArray;
    Context context;
    private LayoutInflater inflater = null;
    String status;

    public ItemListAdapter(Context context, ArrayList<Item> listArray) {
        this.dispArray = listArray;
        this.context = context;
        this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ItemListAdapter(Context context, ArrayList<Item> listArray, String status) {
        this.dispArray = listArray;
        this.context = context;
        this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.status = status;
    }


    @Override
    public int getCount() {
        return dispArray.size();
    }

    @Override
    public Object getItem(int i) {
        return dispArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView tvName, tvPrice, tvLow, tvHigh;
        ImageView ivIcon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.custom_list_layout, null);
        holder.tvName = rowView.findViewById(R.id.tvList_Name);
        holder.tvPrice = rowView.findViewById(R.id.tvList_Price);
        holder.tvLow = rowView.findViewById(R.id.tvList_Low);
        holder.tvHigh = rowView.findViewById(R.id.tvList_High);
        holder.ivIcon = rowView.findViewById(R.id.ivList_Icon);

        holder.tvName.setText("" + dispArray.get(i).getItemName());

        if (status.equalsIgnoreCase("Game")) {
            holder.tvPrice.setText("" + Math.round(dispArray.get(i).getOpeningRate()));
            holder.tvLow.setText("" + Math.round(dispArray.get(i).getMinRate()));
            holder.tvHigh.setText("" + Math.round(dispArray.get(i).getMaxRate()));

            float mean = ((dispArray.get(i).getMaxRate() + dispArray.get(i).getMinRate()) / 2);
            // Log.e("Mean : "," ---- "+mean+ "      Opening Rate : "+dispArray.get(i).getOpeningRate());
            if (dispArray.get(i).getOpeningRate() > mean) {
                holder.tvPrice.setTextColor(Color.parseColor("#96EA19"));
                holder.ivIcon.setImageResource(R.drawable.ic_arrow_up);
            } else {
                holder.tvPrice.setTextColor(Color.parseColor("#ea4a19"));
                holder.ivIcon.setImageResource(R.drawable.ic_arrow_down);
            }

        } else if (status.equalsIgnoreCase("Special")) {
            holder.tvPrice.setText("" + Math.round(dispArray.get(i).getMrpSpecial()));
            holder.tvLow.setText("");
            holder.tvHigh.setText("");
        } else if (status.equalsIgnoreCase("Regular")) {
            holder.tvPrice.setText("" + Math.round(dispArray.get(i).getMrpRegular()));
            holder.tvLow.setText("");
            holder.tvHigh.setText("");
        }

        Animation anim = AnimationUtils.loadAnimation(
                context, android.R.anim.slide_in_left
        );
        anim.setDuration(700);
        rowView.startAnimation(anim);

        return rowView;
    }
}
