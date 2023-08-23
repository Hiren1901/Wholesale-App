package com.example.wholesalewale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import exportkit.figma.R;

public class customerpage extends Fragment {
    View view;
    RecyclerView gridView;
    categoryAdapter categoryAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.mainactivity2, container, false);
        gridView=view.findViewById(R.id.grid);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<categoryItems> items=new ArrayList<>();
        items.add(new categoryItems(R.drawable.toys_icon,"Toys"));
        items.add(new categoryItems(R.drawable.bangles_icon,"Bangles"));
        items.add(new categoryItems(R.drawable.cosmetic_icon,"Cosmetics"));
        items.add(new categoryItems(R.drawable.giftitems_icon,"Gift Items"));
        items.add(new categoryItems(R.drawable.innerwear2_icon,"Inner Garments"));
        items.add(new categoryItems(R.drawable.perfumes,"Perfumes"));
        items.add(new categoryItems(R.drawable.purses2_icon,"Purses"));
        items.add(new categoryItems(R.drawable.sareecover_icon,"Saree and Bangle boxes"));
        items.add(new categoryItems(R.drawable.sports_icon,"Sports"));

        categoryAdapter=new categoryAdapter(getContext(),items);
        gridView.setLayoutManager( new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        gridView.setAdapter(categoryAdapter);
    }
}
