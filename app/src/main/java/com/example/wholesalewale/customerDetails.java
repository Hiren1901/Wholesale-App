package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import exportkit.figma.R;

public class customerDetails extends AppCompatActivity {
    ImageView face;
    private StorageReference storageReference;
    private static final int SELECT_PICTURES =1 ;
    FloatingActionButton camera;
    EditText Cname,cno,caddress;
    FirebaseStorage firebaseStorage;
    Button save;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    Uri selectedImage;

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         selectedImage = data.getData();
        Glide.with(this).load(selectedImage).circleCrop().into(face);



    }


    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("Customers_photos");
        face= findViewById(R.id.customerface);
        Cname=findViewById(R.id.cName);
        cno=findViewById(R.id.Cphone);
        save=findViewById(R.id.button4);
        caddress=findViewById(R.id.cAddress);
        camera=findViewById(R.id.addpic2);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    if (ActivityCompat.checkSelfPermission(customerDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(customerDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURES);
                    } else {

                        startActivityForResult( ImagePicker.Companion.with(customerDetails.this).crop().galleryOnly().createIntent(), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty( Cname.getText().toString())){
                            Cname.setError("Please enter Name");
                            return;
                        }
                        if(TextUtils.isEmpty(  cno.getText())){
                            cno.setError("Please enter your contact number");
                            return;
                        }
                        if(TextUtils.isEmpty(caddress.getText().toString())){
                            caddress.setError("Please enter you address");
                            return;
                        }
                        customerclass Customerclass=new customerclass(Cname.getText().toString(),caddress.getText().toString(), cno.getText().toString());
                            databaseReference.child(user.getUid()+"("+Cname.getText().toString().trim()+")").setValue(Customerclass);
                            storageReference.child(Cname.getText().toString()).putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Intent intent=new Intent(customerDetails.this,CustomerDashboard.class);
                            startActivity(intent);
                                }
                            });
                    }
                });

            }
        };

    }
}