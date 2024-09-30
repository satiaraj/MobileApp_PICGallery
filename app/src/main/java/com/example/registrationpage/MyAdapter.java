package com.example.registrationpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<DataClass> dataList;
    Context context;

    public MyAdapter(ArrayList<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getGambar()).into(holder.ImagePlaceGallery);
        holder.TextTitlePlaceGallery.setText(dataList.get(position).getJudul());
        holder.TextDescriptionPlaceGallery.setText(dataList.get(position).getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ImagePlaceGallery;
        TextView TextTitlePlaceGallery, TextDescriptionPlaceGallery;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ImagePlaceGallery = itemView.findViewById(R.id.ImagePlaceGallery);
            TextTitlePlaceGallery = itemView.findViewById(R.id.TextTitlePlaceGallery);
            TextDescriptionPlaceGallery = itemView.findViewById(R.id.TextDescriptionPlaceGallery);

        }
    }
}
