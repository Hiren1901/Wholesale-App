package com.example.wholesalewale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wholesalewale.categoryItems;

import java.util.ArrayList;

import exportkit.figma.R;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
Context context;
ImageView imageView;
TextView textView;
public onItemclick click;
ArrayList<categoryItems> arrayList;
 public categoryAdapter(Context context, ArrayList<categoryItems> arrayList){
    this.context=context;
    this.arrayList=arrayList;
}
    public interface onItemclick{
        void onClick(int position);
    }
    public void onclick(onItemclick click){
        this.click=click;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categoryitems, parent, false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.CImage.setImageResource(arrayList.get(position).getDrawable());
    holder.textView.setSelected(true);
    holder.textView.setText(arrayList.get(position).getName());
    holder.CImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            click.onClick(position);
        }
    });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
