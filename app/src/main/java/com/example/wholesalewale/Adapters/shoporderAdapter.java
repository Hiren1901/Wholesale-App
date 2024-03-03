package com.example.wholesalewale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import exportkit.figma.R;
import com.example.wholesalewale.*;

public class shoporderAdapter extends RecyclerView.Adapter<shoporderAdapter.holder> {
    Context context;
    ArrayList<ArrayList<String>> arrayList;
    public click onclick;
    public shoporderAdapter(Context context,ArrayList<ArrayList<String>> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.shoporderadapter,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.name.setText(arrayList.get(position).get(0));
        holder.number.setText(arrayList.get(position).get(2));
        holder.address.setText(arrayList.get(position).get(1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onorderClick();
            }
        });

    }
    public void OnCLICK(click c){
        onclick=c;
    }
public interface click{
        void onorderClick();
}
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        TextView name;
        TextView number;
        TextView address;
        TextView date;
        CardView cardView;
        public holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);
            number=itemView.findViewById(R.id.cnumber);
            address=itemView.findViewById(R.id.caddress);
            cardView=itemView.findViewById(R.id.cd);
        }
    }

}
