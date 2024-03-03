package com.example.wholesalewale;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wholesalewale.viewmodels.likesmodel;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.wholesalewale.Adapters.buyerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import exportkit.figma.R;

public class LikedItemList extends Fragment implements buyerAdapter.onclick{
    public  LikedItemList(String user){
        this.user=user;
    }
    View view;
int c=0;
boolean isFirstTimeCall=true;
    likesmodel likesmodel;
    ArrayList<showlike> myDataList;
    buyerAdapter b;
    String m_Text;
    boolean first=true;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
   String user;
    int count=5;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;
    Button button;

    @Override
    public void onResume() {
        super.onResume();




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataList=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference().child("Users");
        likesmodel=new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(likesmodel.class)) {
                    return (T) new likesmodel(WorkManager.getInstance(getActivity()));
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(likesmodel.class);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(1));




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




                if(getView()!=null) {
            getData(null);
                }


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isFirstTimeCall) {
                        isFirstTimeCall = false;

                        // Do your stuff here...
                       GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                        int totalItemCount = layoutManager.getItemCount();
                        int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                        if(lastVisibleItem>=count){
                            getData(myDataList.get(lastVisibleItem).getUploadDetails().getProductname());

                        }

                    }
                }

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    isFirstTimeCall = true;
                }

            }
        });
    }
    void getData(String lastItem){
        likesmodel.setUser(user,lastItem);
        likesmodel.getLikeData();

        likesmodel.getWorkInfo().observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if (workInfo == null) {
                    return;
                }


                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                    Data resultData = workInfo.getOutputData();
                    String serializedData = resultData.getString("s");
                    if (serializedData != null) {
                        // Deserialize the JSON string back to ArrayList
                        Gson gson = new Gson();
                        c++;
                        Type listType = new TypeToken<ArrayList<showlike>>() {
                        }.getType();
                     ArrayList<showlike> arrayList = gson.fromJson(serializedData, listType);
                     if(b!=null&&arrayList.size()!=0&&!myDataList.contains(arrayList.get(0))&&!myDataList.get(0).getUploadDetails().getProductname().equals(arrayList.get(0).getUploadDetails().getProductname())){
                     myDataList.add(arrayList.get(0));
                     b.setData(myDataList);
                    b.notifyDataSetChanged();
                    count++;

                     }else if(first) {
                            first=false;

                            myDataList.addAll(arrayList);
                         recyclerView.setVisibility(View.VISIBLE);
                         b = new buyerAdapter(getContext(), myDataList, 'L', "ss");
                         b.initclick(LikedItemList.this);
                         recyclerView.setAdapter(b);
                     }





                        // Handle the ArrayList in your activity
                        // Do something with myDataList
                    }
                }


            }
        });
    }

    @Override
    public void click(int position, String adre) {

    }

    @Override
    public void clickonItem(int position) {

    }

    @Override
    public void clickCart(int position, String name) {
        reference=firebaseDatabase.getReference().child("Users/"+user);
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
                    likeditem l=new likeditem(myDataList.get(position).getSname(),myDataList.get(position).getUploadDetails().getProductname(),myDataList.get(position).getCategory(),myDataList.get(position).getKey(),m_Text);
                    reference.child("cart").child(name).setValue(l);
                 }
                else{
                    reference.child("cart").child(name).removeValue();



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
}
