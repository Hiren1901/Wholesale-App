package com.example.wholesalewale.Worker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.wholesalewale.likeditem;
import com.example.wholesalewale.showlike;
import com.example.wholesalewale.uploadDetails;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LikeItemworker extends Worker {

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase firebaseDatabase;
    Query q;

    ArrayList<showlike> liked;
    DatabaseReference reference,reference1;
    public LikeItemworker(@NonNull Context context, @NonNull WorkerParameters workerParams){
        super(context, workerParams);
        liked=new ArrayList<>();



    }



    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        CountDownLatch latch= new CountDownLatch(1);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference().child("Users");
        reference1=firebaseDatabase.getReference().child("Shops");
        Data u=getInputData();//get user from auth method

        String user=  u.getString("user");
        String start=u.getString("lastitem");
        if(start!=null&&!start.equals("")){
            q=reference.child(user).child("Liked products").orderByKey().startAfter(start).limitToFirst(6);
        }else{

       q=reference.child(user).child("Liked products").orderByKey().limitToFirst(6);
        }


  q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                        for(DataSnapshot dataSnapshot:snapshot1.getChildren()){

                                    likeditem l=dataSnapshot.getValue(likeditem.class);
                                    Task<DataSnapshot> q=reference1.child(l.getCategory()+"/"+l.getKey()+"/Products/"+l.getItemname()).get();
                                    while(!q.isSuccessful());
                                    DataSnapshot snapshotp=  q.getResult();
                                    uploadDetails uploadDetails=snapshotp.getValue(com.example.wholesalewale.uploadDetails.class);
                                    liked.add(new showlike(uploadDetails,l.getShopname(),l.getCategory(),l.getKey()));


                        }

            latch.countDown();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        String serializedData = gson.toJson(liked);

Data d=new Data.Builder().put("s",serializedData).build();
        return Result.success(d);
    }
}
