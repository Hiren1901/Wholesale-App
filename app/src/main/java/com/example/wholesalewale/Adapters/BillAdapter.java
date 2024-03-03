package com.example.wholesalewale.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wholesalewale.oderdetails;

import java.util.ArrayList;

import exportkit.figma.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewModel>{
Context context;
    ArrayList<oderdetails> details;

    public BillAdapter(Context context, ArrayList<oderdetails> details) {
        this.context = context;
       this. details=details;
    }

    @NonNull
    @Override
    public viewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.billadapterlayout,parent,false);
       return  new viewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewModel holder, int position) {
        if(details!=null){
       holder.itemname.setText(details.get(position).getItemName().toString());
       if(details.get(position).getShopName()!=null)
       holder.shopname.setText(details.get(position).getShopName().toString());
       else
           holder.shopname.setVisibility(View.GONE);

       holder.Price.setText(String.valueOf(details.get(position).getPrice()));
        holder.Quantity.setText(String.valueOf(details.get(position).getQnt()));
        double tt=details.get(position).getQnt()*details.get(position).getPrice();
       holder.total.setText(String.valueOf(tt));
            }



    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class viewModel extends RecyclerView.ViewHolder {
        TextView itemname;
        TextView shopname;
        TextView Price;
        TextView Quantity;
        TextView total;

        public viewModel(@NonNull View itemView) {
            super(itemView);
            itemname= itemView.findViewById(R.id.Name);
            shopname=itemView.findViewById(R.id.Sname);
            Price=itemView.findViewById(R.id.Pri);
            Quantity=itemView.findViewById(R.id.Qnt);
            total=itemView.findViewById(R.id.TT);
            shopname.setSelected(true);
            Price.setSelected(true);
            Quantity.setSelected(true);
            total.setSelected(true);

        }
    }
}
