package com.example.wholesalewale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import exportkit.figma.R;

public class shoplistadapter extends RecyclerView.Adapter<shoplistadapter.ViewHolder> {
    Context context;
    ImageView imageView;
    TextView textView;
    ArrayList<shopdetails> a;
    public onclick Click;

  ArrayList<userviewShops> arrayList;

    shoplistadapter(Context context, ArrayList<userviewShops> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }
    shoplistadapter(){}

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView CImage;
       ImageButton heart;
        TextView itemname;
        TextView price;
        TextView shopname;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            CImage=view.findViewById(R.id.itempic);
            itemname=view.findViewById(R.id.itemName);
            price=view.findViewById(R.id.itemPrice);
            shopname=view.findViewById(R.id.shopName);
            heart=view.findViewById(R.id.imageView12);

        }


    }
    public interface onclick{
        void click(int position);
    }

    @NonNull
    @Override
    public shoplistadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopdisplay, parent, false);
        return new shoplistadapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(arrayList.get(position).getLinks()!=null){
        Glide.with(context).load(arrayList.get(position).getLinks().get(1)).into(holder.CImage);}
    holder.shopname.setText(arrayList.get(position).getShopname());
        holder.price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.itemname.setText(String.valueOf(arrayList.get(position).productname));
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Click.click(position);
     //  holder.heart.setColorFilter(ContextCompat.getColor(context,R.color.lighter_gray));

                Glide.with(context).load(R.drawable.heart).into(holder.heart);
                holder.heart.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}