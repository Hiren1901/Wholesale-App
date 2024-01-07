package com.example.wholesalewale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import exportkit.figma.R;

public class padapter extends RecyclerView.Adapter<padapter.ViewHolder>{
    static Context context;
    static ArrayList<String> hashSet;
    uploadDetails details;
    String chk;

    private ImagesAdapter .onItemclick click;
    static String name;
    public padapter (Context context){


        this.context=context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {



        ImageView PImage;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            PImage=view.findViewById(R.id.itemTextView);

        }


    }

    public interface onItemclick{
        void onClick(int position);
    }
    public padapter (Context context, ArrayList<String> k){
        this.context=context;
        hashSet=k;

    }
    public padapter (Context context, uploadDetails details,String chk){
        this.context=context;
        this.details=details;
        this.chk=chk;


    }

    @NonNull
    @Override
    public padapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top, parent, false);


        return new padapter.ViewHolder(view);

    }


    @Override

    public void onBindViewHolder(@NonNull padapter .ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(chk!=null){
            if(details.getLinks()!=null)
            Glide.with(context).load(details.getLinks().get(position)).into(holder.PImage);

        }else {
            Glide.with(context).clear(holder.PImage);
            if (!hashSet.isEmpty() && hashSet.get(position) != null) {
                Glide.with(context).load(hashSet.get(position)).into(holder.PImage);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(chk!=null){
            if(details.getLinks()!=null)
            return details.getLinks().size();
            else
                return 0;
        }
        else
        return hashSet.size();
    }





}
