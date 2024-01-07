package com.example.wholesalewale;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exportkit.figma.R;

public class LikedItemList extends Fragment {
    View view;
   ArrayList<showlike> liked;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;
    Button button;

    @Override
    public void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                getActivity().moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                //close();

            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.likeditemlist, container, false);
            recyclerView=view.findViewById(R.id.re);
            button=view.findViewById(R.id.button7);
            button.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        liked=new ArrayList<>();
        reference=firebaseDatabase.getReference().child("Users");
        reference1=firebaseDatabase.getReference().child("Shops");
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                Query q=reference;
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().contains(user.getUid())){
                                for(DataSnapshot dataSnapshot:snapshot1.getChildren()){
                                    if(dataSnapshot.getKey().contains("Liked products")){
                                      Iterator<DataSnapshot> it=dataSnapshot.getChildren().iterator();
                                        while(it.hasNext())
                                          {
                                                likeditem l=it.next().getValue(likeditem.class);

                                             Task<DataSnapshot> q=reference1.child(l.getCategory()+"/"+l.getKey()+"/Products/"+l.getItemname()).get();
                                             recyclerView.setVisibility(View.INVISIBLE);
                                             while(!q.isSuccessful());
                                              DataSnapshot snapshotp=  q.getResult();
                                              uploadDetails uploadDetails=snapshotp.getValue(com.example.wholesalewale.uploadDetails.class);
                                                    liked.add(new showlike(uploadDetails,l.getShopname()));
                                                    if(!it.hasNext()){
                                                        recyclerView.setVisibility(View.VISIBLE);
                                                                buyerAdapter b=new buyerAdapter(getContext(),liked,'L',l.getShopname());
                                                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
                                                                recyclerView.addItemDecoration(new SpacesItemDecoration(1));
                                                                recyclerView.setAdapter(b);
                                                    }

//


                                            }

                                            break;

                                    }
                                }
                                break;
                            }
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };

    }
}
