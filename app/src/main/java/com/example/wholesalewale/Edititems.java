package com.example.wholesalewale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import exportkit.figma.R;

public class Edititems  extends Fragment implements ItemAdapter.onItemclick {
    View view;
    boolean edited;
    Query query;
    Button button;
    uploadDetails uploadDetails;
    FirebaseUser user;
    TextView emptyView;
    FirebaseAuth firebaseAuth;
    Context myContext;
    CardView cardView;
    ItemAdapter itemAdapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    RecyclerView recyclerView;
    android.widget.SearchView searchView;
    ArrayList<uploadDetails> list;
    EditText price;
    EditText about; EditText quantity;
    EditText color;
    EditText pname; EditText material;


void query(Query query,String s){
    button.setVisibility(View.GONE);
    query.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            uploadDetails=snapshot.getValue(uploadDetails.class);
            if(uploadDetails.getProductname().contains(s)) {
                if(s.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);

                }
                else {
                    list.clear();
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    list.add(snapshot.getValue(uploadDetails.class));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    itemAdapter = new ItemAdapter(myContext);
                    itemAdapter.filterList(list,myContext);
                    itemAdapter.setOnClick(Edititems.this);
                    recyclerView.setAdapter(itemAdapter);

                }

            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.edititem, container, false);

        price=view.findViewById(R.id.price);
        pname=view.findViewById(R.id.productname);
        color=view.findViewById(R.id.colors);
        button=view.findViewById(R.id.button3);
        material=view.findViewById(R.id.material);
        quantity=view.findViewById(R.id.quantity);
        about=view.findViewById(R.id.about);
        searchView=view.findViewById(R.id.searchView);
        recyclerView=view.findViewById(R.id.findedit);
        emptyView=view.findViewById(R.id.emptyView);
        cardView=view.findViewById(R.id.cardView2);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        edited=false;
        list=new ArrayList<>();
        cardView.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.onActionViewExpanded();
            }
        });
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 user= firebaseAuth.getCurrentUser();


                if(user!=null){
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            cardView.setVisibility(View.GONE);
                            query=firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products");
                                query(query,s);

                            return true;
                        }
                    });



                    // Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onClick(int position) {
        String s=list.get(list.size()-1).getProductname();
        AlertDialog.Builder builder=new AlertDialog.Builder(myContext);
        builder.setMessage("Pressing delete permanently deletes your item");
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                button.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                price.setText( String.valueOf(list.get(list.size()-1).getPrice()));
                pname.setText(list.get(list.size()-1).getProductname());
                about.setText(list.get(list.size()-1).getAbout());
                material.setText(list.get(list.size()-1).getMaterial());
                quantity.setText(String.valueOf(list.get(list.size()-1).getQuantity()));
                color.setText(String.valueOf(list.get(list.size()-1).getColor()));
                uploadDetails uploadtem=list.get(list.size()-1);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emptyView.setVisibility(View.VISIBLE);
                        list.clear();
                        searchView.clearFocus();
                        cardView.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+s).removeValue();
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()).setValue(uploadtem);
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/productname").setValue(pname.getText().toString());
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/about").setValue(about.getText().toString());
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/color").setValue(Integer.valueOf(color.getText().toString()));
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/price").setValue(Double.parseDouble(price.getText().toString()));
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/quantity").setValue(Integer.parseInt(quantity.getText().toString()));
                        firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+pname.getText().toString()+"/material").setValue(material.getText().toString());
                        searchView.setQuery(pname.getText().toString(), false);
                        query(query,pname.getText().toString());
                    }
                });



            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseDatabase.getReference().child("Shops/"+user.getUid()+"/Products/"+s).removeValue();
                Toast.makeText(myContext, "Item Deleted", Toast.LENGTH_SHORT).show();
                query(query,"");
                list.clear();
            }
        });

        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}
