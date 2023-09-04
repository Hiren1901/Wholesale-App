package com.example.wholesalewale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

import java.net.URL;
import java.util.ArrayList;

import exportkit.figma.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
static Context context;
static ArrayList<uploadDetails> hashSet;

static  uploadDetails uploaddetail;
    private  onItemclick click;
    static String name;
    public ItemAdapter(Context context){

        this.context=context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView c;

          ImageView storeic;
          ImageView photo_;
        TextView shopname;
         TextView item;
         TextView price;
        TextView qty;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            storeic= view.findViewById(R.id.imageView11);
            shopname=view.findViewById(R.id.shopname);
            price=view.findViewById(R.id.itemprice);
            qty=view.findViewById(R.id.qty);
            c=view.findViewById(R.id.card);
            photo_=view.findViewById(R.id.imageView6);
            item=view.findViewById(R.id.itemname);
            shopname.setText(name);
            photo_.setImageResource(R.drawable.polar_bear);
            if(context!=null){
            storeic.setColorFilter(
                    ContextCompat.getColor(context,
                    R.color.light_grey), android.graphics.PorterDuff.Mode.SRC_IN);
            }

        }


    }
    public void filterList(ArrayList<uploadDetails> filteredList,Context context){
        this.context=context;
        hashSet=filteredList;
        notifyDataSetChanged();
    }
    public interface onItemclick{
         void onClick(int position);
    }
public ItemAdapter(Context context, ArrayList<uploadDetails> k, String shopname){
        this.context=context;

        hashSet=k;
        name=shopname;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);


        return new ViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


          holder. price.setText(String.valueOf(hashSet.get(position).getPrice())+"/-");
           holder.qty.setText(String.valueOf(hashSet.get(position).getQuantity()));
          holder. item.setText(hashSet.get(position).getProductname());
          holder.photo_.setImageResource(R.drawable.polar_bear);

          if(hashSet.get(position).getLinks()!=null) {
              if(hashSet.get(position).getLinks().get(0)==null){
                  hashSet.get(position).getLinks().remove(0);
              }
              Glide.with(context).load(hashSet.get(position).getLinks().get(0)).into(holder.photo_);
          }



        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(position);
            }
        });



    }
    public void setOnClick(onItemclick click){
        this.click=click;
    }


    @Override
    public int getItemCount() {
        return hashSet.size();
    }
}
