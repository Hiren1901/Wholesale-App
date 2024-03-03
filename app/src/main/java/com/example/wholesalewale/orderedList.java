package com.example.wholesalewale;

import android.content.Intent;
import android.os.Bundle;
import com.example.wholesalewale.Adapters.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import exportkit.figma.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderedList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderedList extends Fragment implements orderAdapter.Click {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Long> Amounts;
    ArrayList<String> date;
    ArrayList<String> time;
    ArrayList<oderdetails> oderdetails;
    ArrayList<ArrayList<oderdetails>> Details;   //ArrayList<orderedList> lists;
    FirebaseUser user;
    boolean chk=false;

    public orderedList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderedList.
     */
    // TODO: Rename and change types and number of parameters
    public static orderedList newInstance(String param1, String param2) {
        orderedList fragment = new orderedList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        //lists=new ArrayList<>();
        Amounts=new ArrayList<>();
        date=new ArrayList<>();
        time=new ArrayList<>();
        Details=new ArrayList<>();
        oderdetails =new ArrayList<>();
        reference=firebaseDatabase.getReference().child("Users");
    }

    @Override
    public void onResume() {
        super.onResume();
        authStateListener.onAuthStateChanged(firebaseAuth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ordered_list, container, false);
        recyclerView=view.findViewById(R.id.re);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();

                Query q=reference;
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        get(snapshot,user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };
    }
    void get(DataSnapshot snapshot,FirebaseUser user){
        if (Amounts.isEmpty()) {

            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                if (snapshot1.getKey().contains(user.getUid())) {
                    for (DataSnapshot dataSnapshot : snapshot1.getChildren()) {
                        if (dataSnapshot.getKey().contains("Orders")) {
                            for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                                for (DataSnapshot k : snapshot2.getChildren()) {
                                    if (k.getKey().contains("Amount"))
                                        Amounts.add(k.getValue(Long.class));
                                    if (k.getKey().contains("Date"))
                                        date.add(k.getValue(String.class));
                                    if (k.getKey().contains("Time"))
                                        time.add(k.getValue(String.class));
                                    if (k.getKey().contains("Details")){
                                        for(DataSnapshot d:k.getChildren()){
                                      oderdetails oderdetail=  d.getValue(oderdetails.class);
                                            oderdetails.add(oderdetail);
                                        }

                                    }

                                }
                                Details.add(new ArrayList<>(oderdetails));
                                oderdetails=new ArrayList<>();
                            }

                        }
                    }


                    break;
                }
            }
            orderAdapter adapter = new orderAdapter(getActivity(), Amounts, date, time);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter.klick(orderedList.this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onClickItem(int position) {
Bundle args=new Bundle();
args.putSerializable("list",(Serializable) Details.get(position));
        Intent intent=new Intent(getActivity(),itemPurchasing_Bill.class);
        intent.putExtra("de",args);
        startActivity(intent);
    }
}