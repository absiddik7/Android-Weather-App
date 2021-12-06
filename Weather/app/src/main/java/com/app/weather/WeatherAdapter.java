package com.app.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<WeatherData> weatherList;

    public WeatherAdapter(Context context, ArrayList<WeatherData> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.w_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {

        WeatherData data = weatherList.get(position);
        long d = Long.parseLong(data.getDate());
         @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("HH:mm aa")
                .format(new java.util.Date (d*1000));

        holder.dateTxt.setText(date);
        holder.tempTxt.setText((data.getTemp()) + " \u2103");
        Glide.with(context)
                .load(data.getImg())
                .into(holder.wImg);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTxt;
        private final TextView tempTxt;
        private final ImageView wImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.w_date);
            tempTxt = itemView.findViewById(R.id.w_temp);
            wImg = itemView.findViewById(R.id.w_icon);
        }
    }
}
