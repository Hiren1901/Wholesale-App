package com.example.wholesalewale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import exportkit.figma.R;

public class completeproductview extends AppCompatActivity {
RecyclerView recyclerView;
TextView name,material,quantity,color,price,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completeproductview);
        recyclerView=findViewById(R.id.linearLayout2);
        name=findViewById(R.id.pname);
        price=findViewById(R.id.pprice);
        about=findViewById(R.id.textView52);
        material=findViewById(R.id.mat);
        color=findViewById(R.id.textView51);
        quantity=findViewById(R.id.textView49);

     String data=getIntent().getStringExtra("list");
        Gson gson=new Gson();
        Type type = new TypeToken<uploadDetails>(){}.getType();
        uploadDetails l=gson.fromJson(data,type);
        padapter p=new padapter(completeproductview.this,l,"Y");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false));
       recyclerView.setAdapter(p);
       name.setText(String.valueOf(l.getProductname()));
       price.setText(String.valueOf(l.getPrice()));
       about.setText(String.valueOf(l.getAbout()));
       material.setText(String.valueOf(l.getMaterial()));
       quantity.setText(String.valueOf(l.getQuantity()));
       color.setText(String.valueOf(l.getColor()));

    }
}