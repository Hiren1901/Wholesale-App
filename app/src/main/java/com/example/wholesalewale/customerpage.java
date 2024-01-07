package com.example.wholesalewale;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import exportkit.figma.R;

public class customerpage extends Fragment implements categoryAdapter.onItemclick, shoplistadapter.onclick  {
    View view;
    RecyclerView gridView;
    Thread t;
    ImageButton logout;
    TextView name,Address;
    Query q;
    SearchView search;
    uploadDetails uploadDetail;
    shopdetails shopdetail;
    RecyclerView shoplist;
    ArrayList<uploadDetails> k;
    ArrayList<userviewShops> shopItem;
    ArrayList<userviewShops> shopItems;
    ImageView cImage;
    Boolean exist=false;
    ArrayList<categoryItems> items;
    DatabaseReference databaseReference,databaseReference2;
    String category,Cname;
    FirebaseDatabase firebaseDatabase;
    categoryAdapter categoryAdapter;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    public void onResume() {
        super.onResume();
    firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {

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
        view=inflater.inflate(R.layout.mainactivity2, container, false);
        gridView=view.findViewById(R.id.grid);
        shoplist=view.findViewById(R.id.shopsList);
        search=view.findViewById(R.id.searchView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items=new ArrayList<>();
        k=new ArrayList<>();
        shopItem=new ArrayList<>();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();
            }
        });
        logout=view.findViewById(R.id.logoutC);
        cImage=view.findViewById(R.id.image);
        Address=view.findViewById(R.id.A1);
        name=view.findViewById(R.id.Customername);
        firebaseAuth=FirebaseAuth.getInstance();
        shoplist.setVisibility(View.INVISIBLE);
        items.add(new categoryItems(R.drawable.toys_icon,"Toys"));
        items.add(new categoryItems(R.drawable.bangles_icon,"Bangles"));
        items.add(new categoryItems(R.drawable.cosmetic_icon,"Cosmetics"));
        items.add(new categoryItems(R.drawable.giftitems_icon,"Gift Items"));
        items.add(new categoryItems(R.drawable.innerwear2_icon,"Inner Garments"));
        items.add(new categoryItems(R.drawable.perfumes,"Perfumes"));
        items.add(new categoryItems(R.drawable.purses2_icon,"Purses"));
        items.add(new categoryItems(R.drawable.sareecover_icon,"Saree and Bangle boxes"));
        items.add(new categoryItems(R.drawable.sports_icon,"Sports"));
        items.add(new categoryItems(R.drawable.artificialjewllery_icon,"Artificial jewellery"));
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Shops");
        databaseReference2=firebaseDatabase.getReference().child("Users");
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("Customers_photos");
        categoryAdapter=new categoryAdapter(getContext(),items);
        shoplist.addItemDecoration(new SpacesItemDecoration(4));
        gridView.setLayoutManager( new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        categoryAdapter.onclick(customerpage.this);
        gridView.setAdapter(categoryAdapter);
        onClick(0);


                mAuthStateListener=new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        if(user!=null){
                            Query q=databaseReference2;
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                                        if(snapshot1.getKey().contains(user.getUid())){
                                            customerclass Customerclass=snapshot1.getValue(customerclass.class);
                                            name.setText(Customerclass.getName());
                                            Cname=Customerclass.getName();

                                            Address.setText(Customerclass.getAddress());

                                            Task<Uri> t= storageReference.child(Customerclass.getName()).getDownloadUrl();
                                            t.addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    Uri pic=t.getResult();
                                                    Glide.with(getActivity()).load(pic).into(cImage);
                                                }
                                            });





                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                };



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                q=firebaseDatabase.getReference().child("Shops");
                query(q,s);

                return true;
            }
        });



    }

    @Override
    public void onClick(int position) {
         category=items.get(position).getName();
        Query query=databaseReference;
        shopItem.clear();

     query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             for(DataSnapshot snap:snapshot.getChildren()) {
                 if (snap.getKey().contains(category)) {
                exist=true;
                for(DataSnapshot snapshot1:snap.getChildren()){
                    for (DataSnapshot d : snapshot1.getChildren()){
                        if (d.getKey().equals("shop details")) {
                            shopdetail = d.getValue(shopdetails.class);

                        }
                        if (d.getKey().equals("Products")) {
                            int count =0;
                            for (DataSnapshot snapshot3 : d.getChildren()) {

                                if(count==3)
                                    break;
                                uploadDetail = snapshot3.getValue(uploadDetails.class);
                                k.add(uploadDetail);
                                count++;
                            }

                        }
                    }
                                    for(int i=0;i< k.size();i++){
                                        userviewShops u=new userviewShops(shopdetail.getShopname(),k.get(i).getProductname(),k.get(i).getQuantity(),k.get(i).getPrice(),k.get(i).getColor(),k.get(i).getMaterial(),k.get(i).getMaterial(),k.get(i).getLinks());
                                        shopItem.add(u);
                                    }
                                    k.clear();
                }
                break;
                 } else {
                     exist=false;
                 }
             }
             shopItems=shopItem;
             shoplist.setVisibility(View.VISIBLE);
             shoplistadapter shoplistadapter=new shoplistadapter(getContext(),shopItem);
             shoplist.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
             shoplistadapter.klick(customerpage.this);
             shoplist.setAdapter(shoplistadapter);

             if(!exist){
                 Toast.makeText(getContext(), "Sorry! there is no shop in this category", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });




    }

    @Override
    public void click(int position) {
        Intent intent=new Intent(getActivity(),buyerVisitshop.class);
        intent.putExtra("Category",category);
        intent.putExtra("SName",shopItem.get(position).getShopname());
        intent.putExtra("cname",Cname);
        startActivity(intent);

    }
    void query(Query query,String s){
        ArrayList<userviewShops> searchItem=new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()) {

                        exist=true;
                        for(DataSnapshot snapshot1:snap.getChildren()){
                            if(snapshot1.getKey().toLowerCase().contains(s.toLowerCase())) {
                                for (DataSnapshot d : snapshot1.getChildren()) {
                                    if (d.getKey().equals("shop details")) {
                                        shopdetail = d.getValue(shopdetails.class);

                                    }
                                    if (d.getKey().equals("Products")) {
                                        for (DataSnapshot snapshot3 : d.getChildren()) {
                                            uploadDetail = snapshot3.getValue(uploadDetails.class);
                                            k.add(uploadDetail);
                                        }

                                    }
                                }
                                for (int i = 0; i < k.size(); i++) {
                                    userviewShops u = new userviewShops(shopdetail.getShopname(), k.get(i).getProductname(), k.get(i).getQuantity(), k.get(i).getPrice(), k.get(i).getColor(), k.get(i).getMaterial(), k.get(i).getMaterial(), k.get(i).getLinks());
                                   searchItem.add(u);
                                }
                                k.clear();
                            }
                        }


                }
                shoplist.setVisibility(View.VISIBLE);
                shoplistadapter shoplistadapter=new shoplistadapter(getContext(),searchItem);
                shoplistadapter.klick(customerpage.this);
                shoplist.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
              //  shoplist.addItemDecoration(new SpacesItemDecoration(4));
                shoplist.setAdapter(shoplistadapter);

                if(!exist){
                    Toast.makeText(getContext(), "Sorry! there is no shop in this category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
