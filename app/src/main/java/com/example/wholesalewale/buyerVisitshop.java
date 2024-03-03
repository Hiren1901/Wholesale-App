package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wholesalewale.Adapters.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import exportkit.figma.R;

public class buyerVisitshop extends AppCompatActivity implements  buyerAdapter.onclick{
        TextView Sname,saddress,mob,ownername;
        owners owner;
        String m_Text="";
        uploadDetails uploadDetail;
        boolean chk=false;
    String shopname,searchitem,cname,key,category;
        SearchView searchView;
        shopdetails shopdetail;
        ArrayList<uploadDetails> k;
        HashMap<String,String> liked;
    FirebaseUser user;
        RecyclerView recyclerView;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference,db;
        FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_visitshop);
        Sname=findViewById(R.id.sname);
        saddress=findViewById(R.id.sadd);
        mob=findViewById(R.id.Mob);
        ownername=findViewById(R.id.oname);
        recyclerView=findViewById(R.id.re);
        searchView=findViewById(R.id.searchView);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        Intent intent=getIntent();
        k=new ArrayList<>();
        liked=new HashMap<>();
        recyclerView.addItemDecoration(new SpacesItemDecoration(2));
         category=intent.getStringExtra("Category");
        shopname=intent.getStringExtra("SName");
        cname=intent.getStringExtra("cname");
        Sname.setText(shopname);
        databaseReference=firebaseDatabase.getReference().child("Shops/"+category+"/");
        Query q=databaseReference;

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               user=firebaseAuth.getCurrentUser();
               if(!chk){
                   chk=true;
                Query(q,false);
               }

            }};
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });



       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               searchitem=newText;
               Query(q,true);
               return true;
           }
       });






    }
    void Query(Query q,Boolean search){
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    if(snapshot1.getKey().contains(shopname)){
                        key=snapshot1.getKey();
                        for(DataSnapshot snapshot2:snapshot1.getChildren()) {
                            if (snapshot2.getKey().equals("owner details")) {
                                owner = snapshot2.getValue(owners.class);
                                ownername.setText(owner.getName().toString());
                            }
                            if (snapshot2.getKey().equals("shop details")) {
                                shopdetail = snapshot2.getValue(shopdetails.class);
                                saddress.setText(shopdetail.getAddress());
                                Toast.makeText(buyerVisitshop.this, shopdetail.getAddress(), Toast.LENGTH_SHORT).show();
                                mob.setText(shopdetail.getMob());
                                // click.onClick(shopdetail.getCategory(),shopdetail.getShopname());

                            }
                            if (search) {

                                if (snapshot2.getKey().equals("Products")) {
                                    k.clear();
                                    for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                        if(snapshot3.getKey().contains(searchitem)) {
                                            uploadDetail = snapshot3.getValue(uploadDetails.class);
                                            k.add(uploadDetail);
                                        }

                                    }

                                }

                            } else {
                                if (snapshot2.getKey().equals("Products")) {
                                for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                    uploadDetail = snapshot3.getValue(uploadDetails.class);
                                    k.add(uploadDetail);
                                }

                            }
                        }
                        }
                        break;

                    }

                }

                Query q=firebaseDatabase.getReference().child("Users/"+user.getUid());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            if(snapshot1.getKey().contains("Liked products")){
                                likeditem l=new likeditem();
                                for(DataSnapshot s:snapshot1.getChildren()){
                                    l=s.getValue(likeditem.class);
                                    liked.put(l.getItemname(),l.getShopname());

                                }
                            }
                        }
                        if(liked.isEmpty()){
                        buyerAdapter b=new buyerAdapter(buyerVisitshop.this,k,shopname);
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false));
                        b.initclick(buyerVisitshop.this);
                        recyclerView.setAdapter(b);
                        }else{
                            buyerAdapter b=new buyerAdapter(buyerVisitshop.this,k,shopname,liked);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false));
                            b.initclick(buyerVisitshop.this);
                            recyclerView.setAdapter(b);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void click(int position,String addre) {
        String s="("+cname.trim()+")";
        db=firebaseDatabase.getReference().child("Users/"+user.getUid());
        likeditem l=new likeditem(shopname,k.get(position).getProductname(),category,key);
        if(addre.equals("add")){
        Toast.makeText(this, "item added in liked section ", Toast.LENGTH_SHORT).show();

        db.child("Liked products").child(k.get(position).getProductname()).setValue(l);
        }else{
           db.child("Liked products/"+k.get(position).getProductname()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(getApplicationContext(), "item removed from liked section ", Toast.LENGTH_SHORT).show();

               }
           });
        }

    }

    @Override
    public void clickonItem(int position) {

        Gson g=new Gson();
Intent intent=new Intent(buyerVisitshop.this,completeproductview.class);
String json=g.toJson(k.get(position));
intent.putExtra("list",json);
startActivity(intent);

    }

    @Override
    public void clickCart(int position,String name) {
        db=firebaseDatabase.getReference().child("Users/"+user.getUid());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Quantity");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType( InputType.TYPE_CLASS_NUMBER);
        input.setHint("Quantity 1,8...");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                likeditem l=new likeditem(shopname,k.get(position).getProductname(),category,key,m_Text);
                db.child("cart").child(k.get(position).getProductname()).setValue(l);
                Toast.makeText(getApplicationContext(), m_Text+" "+k.get(position).getProductname()+" added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }
}