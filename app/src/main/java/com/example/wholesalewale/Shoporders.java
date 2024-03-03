package com.example.wholesalewale;

import android.content.Context;
import android.content.Intent;
import com.example.wholesalewale.Adapters.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wholesalewale.Adapters.ItemAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.wholesalewale.Adapters.*;
import org.w3c.dom.Comment;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import exportkit.figma.R;

public class Shoporders extends Fragment implements shoporderAdapter.click{
    private Shoporders(){}
    private static Shoporders instance=null;
    public static Shoporders getInstance(){
        if(instance==null){
            instance=new Shoporders();
            return instance;
        }
        else{

            return instance;
        }
    }
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    FirebaseUser user;
    owners owner;
    ArrayList<oderdetails> orderdetails;
   ArrayList< ArrayList<String> >details;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "on create", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("yes",true);
        Toast.makeText(getActivity(), "in saved", Toast.LENGTH_SHORT).show();
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getActivity(), "I'm in stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();Toast.makeText(getActivity(), "I'm in destroy", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
//       savedInstanceState.containsKey()
        Toast.makeText(getContext(), "on Viewcreate", Toast.LENGTH_SHORT).show();
       recyclerView= view.findViewById(R.id.Re);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        details=new ArrayList<>();
        orderdetails=new ArrayList<>();
        reference=firebaseDatabase.getReference().child("Shops");

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if (user != null) {


                    // Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_SHORT).show();

                }
            }
        };

        Query query = firebaseDatabase.getReference().child("Shops/");
        FirebaseDatabase.getInstance().goOnline();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){

                    for (DataSnapshot snapshot1 : s.getChildren()) {
                        if (snapshot1.getKey().contains(user.getUid())) {
                            for (DataSnapshot d : snapshot1.getChildren()) {

                                if (d.getKey().equals("orders")) {
                                    for(DataSnapshot k:d.getChildren()){
                                        for(DataSnapshot x:k.getChildren()){
                                            if(x.getKey().equals("Cdetails"))
                                            {
                                                ArrayList<String> a= (ArrayList<String>) x.getValue();

                                                details.add(new ArrayList<>(a));
                                            }else{
                                                oderdetails order=x.getValue(oderdetails.class);
                                                orderdetails.add(order);
                                            }
                                        }
                                    }

                                }


                            }




                        }
                    }
                }



                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                shoporderAdapter adapter  = new shoporderAdapter(getContext(), details);
                adapter.OnCLICK(Shoporders.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.shoporders,container,false);
        return view;
    }

    @Override
    public void onorderClick() {
        Bundle args=new Bundle();
        args.putSerializable("list",(Serializable) orderdetails);
        Intent intent=new Intent(getActivity(),itemPurchasing_Bill.class);
        intent.putExtra("de",args);
        intent.putExtra("shopowner",true);
        startActivity(intent);
    }
}
