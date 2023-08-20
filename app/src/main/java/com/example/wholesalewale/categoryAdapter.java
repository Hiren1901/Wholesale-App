package com.example.wholesalewale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import exportkit.figma.R;

public class categoryAdapter extends BaseAdapter {
Context context;
ArrayList arrayList;
categoryAdapter(Context context, ArrayList arrayList){
    this.context=context;
    this.arrayList=arrayList;
}
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       view =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.images, viewGroup, false);


        return view;
    }
}
