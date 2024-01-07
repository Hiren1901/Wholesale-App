package com.example.wholesalewale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import exportkit.figma.R;

public class buyerAdapter extends RecyclerView.Adapter<buyerAdapter.ViewHolder> {
    Context context;
    ArrayList<uploadDetails> arrayList;
    String sname;
    onclick Click;
    HashMap<String,String> liked;
    ArrayList<showlike> likes;
    char forLikeOnly;
    buyerAdapter(Context context,ArrayList<showlike> likes,char forLikeOnly,String sname) {
        this.context = context;
        this.likes=likes;
        this.forLikeOnly=forLikeOnly;
        this.sname=sname;

    }


    buyerAdapter(Context context, ArrayList<uploadDetails> arrayList,String sname) {
        this.context = context;
        this.arrayList = arrayList;
        this.sname=sname;

    }
    buyerAdapter(Context context, ArrayList<uploadDetails> arrayList,String sname,HashMap<String,String> liked) {

        this.context = context;
        this.arrayList = arrayList;
        this.sname=sname;
        this.liked=liked;


    }
    interface  onclick{
        void click(int position,String adre);
        void clickonItem(int position);
        void clickCart(int position,String name);
    }
    buyerAdapter(){}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shopforbuyer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (forLikeOnly=='L') {

            if(likes.get(position).getQnt()!=null){
                holder.number.setVisibility(View.VISIBLE);
            holder.number.setText(likes.get(position).getQnt().toString());
            }


            holder.price.setText(String.valueOf(likes.get(position).getUploadDetails().getPrice()));
            holder.itemname.setText(likes.get(position).getUploadDetails().getProductname());
            holder.shopname.setText(likes.get(position).getSname());
            holder.cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Click.clickCart(position,likes.get(position).getUploadDetails().getProductname());
                }
            });
            holder.heart.setColorFilter(context.getResources().getColor(R.color.red));
            if (likes.get(position).getUploadDetails().getLinks() != null) {
                for (int i = 0; i < likes.get(position).getUploadDetails().getLinks().size(); i++) {
                    if (likes.get(position).getUploadDetails().getLinks().get(i) != null) {
                        Glide.with(context).load(likes.get(position).getUploadDetails().getLinks().get(i)).into(holder.CImage);
                        break;
                    }
                }

            }
        }

        else{
        holder.price.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.itemname.setText(arrayList.get(position).getProductname());
        holder.shopname.setText(sname);
        holder.heart.setColorFilter(context.getResources().getColor(R.color.light_grey));
        if (arrayList.get(position).getLinks() != null) {
            for (int i = 0; i < arrayList.get(position).getLinks().size(); i++) {
                if (arrayList.get(position).getLinks().get(i) != null) {
                    Glide.with(context).load(arrayList.get(position).getLinks().get(i)).into(holder.CImage);
                    break;
                }
            }

        }


        if (liked != null && liked.containsKey(arrayList.get(position).getProductname())) {
            holder.heart.setColorFilter(context.getResources().getColor(R.color.red));
        }
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Click.clickonItem(position);
                    }
                });
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click.clickCart(position,null);
            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (liked != null && liked.containsKey(arrayList.get(position).getProductname())) {
                    Click.click(position, "remove");
                    holder.heart.setColorFilter(context.getResources().getColor(R.color.light_grey));
                    liked.remove(arrayList.get(position).getProductname());

                } else {
                    holder.heart.setColorFilter(context.getResources().getColor(R.color.red));
                    if (liked == null) {
                        liked = new HashMap<>();
                    }
                    liked.put(arrayList.get(position).getProductname(), sname);
                    Click.click(position, "add");

                }
                //  holder.heart.setColorFilter(ContextCompat.getColor(context,R.color.lighter_gray));

                //Glide.with(context).load(R.drawable.heart).into(holder.heart);


            }
        });
    }


    }
    public void initclick(onclick c)
    {
        Click=c;
    }

    @Override
    public int getItemCount() {
        if(arrayList!=null){
        return arrayList.size();}
        else{
            return likes.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView CImage;
        ImageButton heart,cart;
        TextView number;
        TextView itemname;
        TextView price;
        TextView shopname;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            CImage=view.findViewById(R.id.itempic);
            itemname=view.findViewById(R.id.itemName);
            price=view.findViewById(R.id.itemPrice);
            shopname=view.findViewById(R.id.shopName);
            heart=view.findViewById(R.id.imageView12);
            cardView=view.findViewById(R.id.ca);
            cart=view.findViewById(R.id.Cart);
            number=view.findViewById(R.id.itemNO);
            number.setVisibility(View.GONE);

        }


    }
}
