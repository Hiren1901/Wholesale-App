package com.example.wholesalewale;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wholesalewale.Adapters.*;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import exportkit.figma.R;

public class shop extends Fragment implements ItemAdapter.onItemclick{
    onItemclick click;
    public interface onItemclick{
        void onClick(String category,String sname);
    }
    shop(){

    }
    shop(onItemclick itemclick){
        click=itemclick;
    }


        CollapsingToolbarLayout collapsingToolbarLayout;
        boolean founduser=false;
    Query query;
        ItemAdapter itemAdapter;
    padapter p;
    uploadDetails uploadDetail;
    ArrayList<String> photos;
        boolean o=false;
    private ShimmerFrameLayout mFrameLayout;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Button b;
        TextView colors;
    TextView itemprice;
    TextView producName;
    TextView material;
    ImageView close;
       RecyclerView listView;

    TextView Quantity;
    TextView aboutProduct;
        CardView info;
        boolean showinfocard=false;
        RecyclerView recyclerView;
        View view;


        owners owner;
    ArrayList<uploadDetails> k;
        TextView shopName;
        TextView Category;
        TextView Address;
        TextView created;
        TextView ownerName;
        TextView contact;
        shopdetails shopdetail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
        FirebaseAuth firebaseAuth;
        TextView showmore;
        CardView cardView;
    FirebaseAuth.AuthStateListener mAuthStateListener;
        Context myContext;

    public static String currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // get current date time with Date()
        Date date = new Date();
        // System.out.println(dateFormat.format(date));
        // don't print it, but save it!
        return dateFormat.format(date);
    }
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                if (showinfocard) {
                    info.setVisibility(View.GONE);
                    showinfocard=false;
                    b.setVisibility(View.GONE);

                }else{

                AlertDialog alertbox = new AlertDialog.Builder(getActivity())
                        .setMessage("Do you want to exit application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {

                                getActivity().moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                                //close();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
            }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view=inflater.inflate(R.layout.yourshop, container, false);
        shopName=view.findViewById(R.id.shtxt);
        Category=view.findViewById(R.id.cttxt);
        info=view.findViewById(R.id.information);
        info.setVisibility(View.GONE);
        colors=view.findViewById(R.id.colors);
        b=view.findViewById(R.id.button5);
        close=view.findViewById(R.id.imageView10);
        itemprice=view.findViewById(R.id.price1);
        mFrameLayout=view.findViewById(R.id.shimmerLayout);
        producName=view.findViewById(R.id.productname);
        material=view.findViewById(R.id.material);
        Quantity=view.findViewById(R.id.quantity);
        aboutProduct=view.findViewById(R.id.about);
        Address=view.findViewById(R.id.addtxt);
        created=view.findViewById(R.id.createtxt);
        ownerName=view.findViewById(R.id.ownertxt);
        contact=view.findViewById(R.id.contacttxt);
        recyclerView=view.findViewById(R.id.itemsList);
        listView=view.findViewById(R.id.linearLayout2);
        b.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
        mFrameLayout.startShimmer();

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFrameLayout.startShimmer();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mFrameLayout.stopShimmer();
        firebaseAuth.removeAuthStateListener(mAuthStateListener);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shopdetail = new shopdetails();
        // info.setVisibility(View.GONE);
        k = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Product_photos");

        owner = new owners();
        created.setText(currentDate());
        collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.SERIF);
        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.create(collapsingToolbarLayout.getCollapsedTitleTypeface(), Typeface.BOLD));
        Toolbar t = view.findViewById(R.id.app_bar);
        t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        Toast.makeText(getActivity(), "You are logout", Toast.LENGTH_SHORT).show();
                       FirebaseAuth.getInstance().signOut();
                        founduser = false;
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                        return true;
                    default:
                        return false;
                }

            }
        });

        showmore = view.findViewById(R.id.showmore);
        cardView = view.findViewById(R.id.card);
        firebaseAuth = FirebaseAuth.getInstance();
        final String[] shopname = {""};

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user != null) {

                        founduser = true;
                        Query query = firebaseDatabase.getReference().child("Shops/");
                        FirebaseDatabase.getInstance().goOnline();


                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot s:snapshot.getChildren()){

                                for (DataSnapshot snapshot1 : s.getChildren()) {
                                    if (snapshot1.getKey().contains(user.getUid())) {
                                        for (DataSnapshot d : snapshot1.getChildren()) {
                                            founduser = true;
                                            if (d.getKey().equals("owner details")) {
                                                owner = d.getValue(owners.class);
                                                ownerName.setText(owner.getName().toString());
                                            }
                                            if (d.getKey().equals("shop details")) {
                                                shopdetail = d.getValue(shopdetails.class);
                                                Address.setText(shopdetail.getAddress());
                                                Category.setText(shopdetail.getCategory());
                                                collapsingToolbarLayout.setTitle(shopdetail.getShopname());
                                                shopName.setText(shopdetail.getShopname());
                                                shopname[0] = shopdetail.getShopname();
                                                contact.setText(shopdetail.getMob());
                                                click.onClick(shopdetail.getCategory(),shopdetail.getShopname());

                                            }
                                            if (d.getKey().equals("Products")) {
                                                for (DataSnapshot snapshot3 : d.getChildren()) {
                                                    uploadDetail = snapshot3.getValue(uploadDetails.class);
                                                    k.add(uploadDetail);


                                                }

                                            }
                                        }


                                        // recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));

                                    }
                                }
                            }


                                mFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                itemAdapter = new ItemAdapter(getContext(), k, shopname[0]);
                                itemAdapter.setOnClick(shop.this);
                                recyclerView.setAdapter(itemAdapter);

                                if (founduser == false) {
                                    Intent intent = new Intent(getActivity(), ownerDetails.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        // Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_SHORT).show();

                    } else {

//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
                    }
                }
            };



        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
                if (showmore.getText().equals("Show less")) {
                    layoutParams.height = (int) 430;
                    cardView.setLayoutParams(layoutParams);
                    showmore.setText("Show more");

                } else {
                    layoutParams.height = WRAP_CONTENT;
                    cardView.setLayoutParams(layoutParams);
                    showmore.setText("Show less");
                }


            }


        });
    }

    @Override
    public void onClick(int position) {
        Animation animation= AnimationUtils.loadAnimation(getContext(),
                R.anim.fadein);
        info.setAnimation(animation);

        showinfocard=true;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation1= AnimationUtils.loadAnimation(getContext(),
                        R.anim.fadeout);
                info.setAnimation(animation1);
                b.setAnimation(animation1);
                b.setVisibility(View.GONE);
                info.setVisibility(View.GONE);
            }
        });
        info.setVisibility(View.VISIBLE);

        b.setVisibility(View.VISIBLE);
        if( k.get(position).getLinks()!=null) {

             photos = k.get(position).getLinks();
            if (photos.get(0) == null) {
                photos.remove(0);
            }
             p = new padapter(getContext(), photos);
            listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            listView.setAdapter(p);

        }else{
            photos=new ArrayList<>();
            p=new padapter(getContext(),photos);
            listView.setAdapter(p);
        }
        itemprice.setText(String.valueOf( k.get(position).getPrice()));
        colors.setText(String.valueOf(k.get(position).getColor()));
        material.setText(k.get(position).getMaterial());
        Quantity.setText(String.valueOf(k.get(position).getQuantity()));
        producName.setText(k.get(position).getProductname());
        aboutProduct.setText(k.get(position).getAbout());
    }







    }

