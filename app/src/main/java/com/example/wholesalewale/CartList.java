package com.example.wholesalewale;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import exportkit.figma.R;

public class CartList extends Fragment implements buyerAdapter.onclick {



    View view;
    TextView heading;
    ArrayList<String> qnt;
    String Cname,m_Text;
    String completeUserId;
 public  Iterator<DataSnapshot>[] iterator;
 FirebaseUser user;
    String id;
    ArrayList<showlike> liked;
    ArrayList<String> cdetails;
    ArrayList<String> keys;
    ArrayList<String> ctg;
    ArrayList<Query> qcount;
    ArrayList<String> sname;
    likeditem l;
     int count=0;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;
    Button buy;
    boolean d=false,k=false;


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
        heading=view.findViewById(R.id.textView36);
        heading.setText("Cart");
        buy=view.findViewById(R.id.button7);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        liked=new ArrayList<>();
        qnt=new ArrayList<>();
        qcount=new ArrayList<>();
        sname=new ArrayList<>();
        keys=new ArrayList<>();
        cdetails=new ArrayList<>();
        ctg=new ArrayList<>();
        iterator=new Iterator[2];
        reference=firebaseDatabase.getReference().child("Users");
        reference1=firebaseDatabase.getReference().child("Shops");

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                id=user.getUid();
                Query q=reference;
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(d){
                         qnt.clear();
                         qcount.clear();
                         sname.clear();

                        }
                     d=true;
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
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
            if (snapshot1.getKey().contains(user.getUid())) {
                customerclass Customerclass = snapshot1.getValue(customerclass.class);
                        completeUserId=snapshot1.getKey();
                Cname = Customerclass.getName();
               cdetails.add(Customerclass.getName());
               cdetails.add(Customerclass.getAddress());
               cdetails.add(Customerclass.getNumber());
               cdetails.add(user.getUid());
                for (DataSnapshot dataSnapshot : snapshot1.getChildren()) {
                    if (dataSnapshot.getKey().contains("cart")) {
                        iterator[0] = dataSnapshot.getChildren().iterator();
                        while (iterator[0].hasNext()) {
                            DataSnapshot s = iterator[0].next();
                            l = s.getValue(likeditem.class);
                            qnt.add(l.getQnt());
                           keys.add( l.getKey());
                          ctg.add( l.getCategory());
                            sname.add(l.getShopname());
                            Query q = reference1.child(l.getCategory() + "/" + l.getKey() + "/Products/" + l.getItemname());
                            qcount.add(q);
                            if (!iterator[0].hasNext()){
                                fetch(qcount, qnt, sname,keys,ctg);
                                break;
                            }


                        }

                        break;

                    }
                }
                iterator=new Iterator[2];

                break;
            }
        }
    }

    @Override
    public void click(int position, String adre) {

    }

    @Override
    public void clickonItem(int position) {

    }

    @Override
    public void clickCart(int position,String name) {
        String s="("+Cname.trim()+")";
        reference=firebaseDatabase.getReference().child("Users/"+id+s);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Quantity");

// Set up the input
        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType( InputType.TYPE_CLASS_NUMBER);
        input.setHint("Quantity 1,8...");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                if(Integer.parseInt(m_Text)!=0){
                    reference.child("cart").child(name).child("qnt").setValue(m_Text);
          Toast.makeText(getContext(), m_Text+" "+l.getItemname()+" added to cart", Toast.LENGTH_SHORT).show();}
                else{
                    reference.child("cart").child(name).removeValue();
                    Toast.makeText(getContext(), l.getItemname()+" has removed from the cart", Toast.LENGTH_SHORT).show();


                }
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
    void fetch(ArrayList<Query> q,ArrayList<String> qnt,ArrayList<String> sname,ArrayList<String> keys,ArrayList<String> ctg){
        count=0;


        if(!liked.isEmpty()){
            liked.clear();
        }
        for( int i=0;i<q.size();i++) {
            q.get(i).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        count++;
                    uploadDetails uploadDetails = snapshot.getValue(com.example.wholesalewale.uploadDetails.class);
                    if(sname.size()>0&&qnt.size()>0)
                     liked.add(new showlike(uploadDetails,sname.get(count-1) , Integer.parseInt(qnt.get(count-1))));

                    if(count==q.size()){
                        buyerAdapter b = new buyerAdapter(getContext(), liked, 'L', l.getShopname());
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
                        b.initclick(CartList.this);
                        recyclerView.setAdapter(b);
                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    placeorder(liked,keys,ctg);
                                }
                            }
                        });

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void placeorder(@NonNull ArrayList<showlike> liked, ArrayList<String> keys, ArrayList<String> ctg){
        //get customers details also
        ArrayList<oderdetails> oderdetails=new ArrayList<>();
        double orderAmount=0;
        for(int i=0;i<liked.size();i++){
            oderdetails details=new oderdetails(liked.get(i).getUploadDetails().getProductname(), liked.get(i).getQnt(),liked.get(i).getUploadDetails().getLinks(), liked.get(i).getUploadDetails().getPrice(),cdetails);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
            LocalDateTime now = LocalDateTime.now();
            orderAmount=orderAmount+liked.get(i).getQnt()*liked.get(i).getUploadDetails().getPrice();
            oderdetails details2=new oderdetails(liked.get(i).getUploadDetails().getProductname(), liked.get(i).getQnt(),liked.get(i).getUploadDetails().getLinks(), liked.get(i).getUploadDetails().getPrice(),liked.get(i).getSname());
            oderdetails.add(details2);
            reference1.child(ctg.get(i)).child(keys.get(i)).child("orders").child(cdetails.get(0)).child("order_Id "+now.hashCode()).setValue(details);

        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyy/MM//dd");
        LocalDateTime now = LocalDateTime.now();
        long code=now.hashCode();
        DatabaseReference newref=reference.child(completeUserId).child("Orders").push();
        newref.child("Amount").setValue(orderAmount);
        newref.child("Date").setValue(now.format(dtf2).toString());
        newref.child("Time").setValue(now.format(dtf).toString());
        newref.child("Details").setValue(oderdetails);
        reference.child(completeUserId).child("cart").removeValue();
        Toast.makeText(getActivity(), "All orders are placed", Toast.LENGTH_SHORT).show();

    }

}
