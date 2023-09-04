package com.example.wholesalewale;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class customerpage extends Fragment implements categoryAdapter.onItemclick,shoplistadapter.onclick  {
    View view;
    RecyclerView gridView;
    ImageButton logout;
    TextView name,Address;
    Query q;
    SearchView search;
    uploadDetails uploadDetail;
    shopdetails shopdetail;
    RecyclerView shoplist;
    ArrayList<uploadDetails> k;
    ArrayList<userviewShops> shopItem;
    ImageView cImage;
    Boolean exist=false;
    ArrayList<categoryItems> items;
    DatabaseReference databaseReference,databaseReference2;

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
        super.onCreate(savedInstanceState);
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
                                    Address.setText(Customerclass.getAddress());
                                   Task<Uri> t= storageReference.child(Customerclass.getName()).getDownloadUrl();
                                    while(!t.isComplete());
                                    Uri pic=t.getResult();
                                    Glide.with(getContext()).load(pic).into(cImage);
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        gridView.setLayoutManager( new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        categoryAdapter.onclick(customerpage.this);
        gridView.setAdapter(categoryAdapter);
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

        String category=items.get(position).getName().toLowerCase();
        Query query=databaseReference;
        shopItem.clear();
     query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             for(DataSnapshot snap:snapshot.getChildren()) {
                 if (snap.getKey().toLowerCase().contains(category)) {
                exist=true;
                for(DataSnapshot snapshot1:snap.getChildren()){
                    for (DataSnapshot d : snapshot1.getChildren()){
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
                                    for(int i=0;i<k.size();i++){
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
             shoplistadapter shoplistadapter=new shoplistadapter(getContext(),shopItem);
             shoplist.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
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
                shoplistadapter shoplistadapter=new shoplistadapter(getContext(),searchItem);
                shoplist.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
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
