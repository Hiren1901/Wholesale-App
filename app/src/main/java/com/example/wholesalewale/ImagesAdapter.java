package com.example.wholesalewale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import exportkit.figma.R;

public class ImagesAdapter extends  RecyclerView.Adapter<ImagesAdapter.ViewHolder>{
    static Context context;
    static ArrayList<Uri> hashSet;
    static  ArrayList<Uri> uri;
    static  uploadDetails uploaddetail;
    private ImagesAdapter .onItemclick click;
    static String name;
    public ImagesAdapter (Context context){

        this.context=context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {



        ImageView PImage;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
         PImage=view.findViewById(R.id.productImage);
        }


    }
    public void filterList(ArrayList<Uri> filteredList, Context context){
        this.context=context;
        hashSet=filteredList;
        notifyDataSetChanged();
    }
    public interface onItemclick{
        void onClick(int position);
    }
    public ImagesAdapter (Context context, ArrayList<Uri> k){
        this.context=context;
        hashSet=k;
        if(uri!=null){

            this.hashSet.addAll(uri);
            uri.clear();
            uri=null;

        }

    }
    public ImagesAdapter(Context context,ArrayList<Uri> uri,String s){
        this.context=context;
        this.uri=uri;
        name=s;
        if(s=="set null" &&(hashSet!=null&&uri!=null)){
            hashSet.clear();
            uri.clear();
            this.uri=null;
            hashSet=null;
        }
        if(hashSet!=null) {
            this.uri.addAll(hashSet);
            hashSet.clear();
            hashSet = null;
        }

    }

    @NonNull
    @Override
    public ImagesAdapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.images, parent, false);


        return new ImagesAdapter .ViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull ImagesAdapter .ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(hashSet!=null) {
            Glide.with(context).load(hashSet.get(position)).into(holder.PImage);
        }
       if(uri!=null){

            Glide.with(context).load(uri.get(position)).into(holder.PImage);
        }


    }
    public void setOnClick(ImagesAdapter .onItemclick click){
        this.click=click;
    }


    @Override
    public int getItemCount() {
        if(hashSet!=null){
        int size=hashSet.size();
        return size; }
        else if(uri!=null){

        return uri.size();
        }
        else{
            return 0;
        }
    }
}
