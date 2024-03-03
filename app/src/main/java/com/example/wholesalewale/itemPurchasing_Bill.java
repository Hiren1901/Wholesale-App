package com.example.wholesalewale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wholesalewale.Adapters.BillAdapter;

import java.util.ArrayList;

import exportkit.figma.R;

public class itemPurchasing_Bill extends AppCompatActivity {
RecyclerView recyclerView;
TextView sname;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_purchasing_bill);
        recyclerView=findViewById(R.id.re);
        sname=findViewById(R.id.Sname);
        Intent intent=getIntent();
        Bundle args = intent.getBundleExtra("de");
        Boolean owner=intent.getBooleanExtra("shopowner",false);
        if(owner)
        {
            sname.setVisibility(View.GONE);
        }

       ArrayList< oderdetails> oderdetails=(ArrayList<oderdetails>) args.getSerializable("list");
        BillAdapter billAdapter=new BillAdapter(getApplicationContext(),oderdetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(billAdapter);
    }
}