package com.example.wholesalewale;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import exportkit.figma.R;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
Context context;
ImageView imageView;
TextView textView;
ArrayList<categoryItems> arrayList;
categoryAdapter(Context context, ArrayList<categoryItems> arrayList){
    this.context=context;
    this.arrayList=arrayList;
}
    public static class ViewHolder extends RecyclerView.ViewHolder {



        ImageView CImage;
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            CImage=view.findViewById(R.id.imageView9);
            textView=view.findViewById(R.id.textView32);
        }


    }
    @NonNull
    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoryitems, parent, false);


        return new categoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull categoryAdapter.ViewHolder holder, int position) {
    holder.CImage.setImageResource(arrayList.get(position).getDrawable());
    holder.textView.setSelected(true);
    holder.textView.setText(arrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
