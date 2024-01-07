package com.example.wholesalewale;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import exportkit.figma.R;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder> {
    Context context;
    ArrayList<Double> amount;
    ArrayList<String> date;
    ArrayList<String> time;

    public orderAdapter(Context context, ArrayList<Double>  amount, ArrayList<String> date, ArrayList<String> time) {
        this.context = context;
        this. amount =  amount;
        this.date=date;
        this.time=time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ordersitemlist,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern(" HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
            holder.Date.setText(date.get(position));
            holder.Time.setText(time.get(position));
            holder.Amount.setText(String.valueOf(amount.get(position)));
            holder.srno.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView Date;
        TextView Time;
        TextView Amount;
        TextView srno;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
         Date=view.findViewById(R.id.date);
         Time=view.findViewById(R.id.time);
         Amount=view.findViewById(R.id.amount);
         srno=view.findViewById(R.id.number);

        }


    }
}
