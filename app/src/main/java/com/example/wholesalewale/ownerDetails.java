package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import exportkit.figma.R;

public class ownerDetails extends AppCompatActivity {
    TextView heading;
    AutoCompleteTextView category;
    String path;
    FloatingActionButton adduserImg;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final int SELECT_PICTURES =1 ;
    TextView name;
    EditText address;
    TextView phototext;
    EditText Editname;
    EditText Editmobile;
    TextView mobile;
    TextView dot_1;
    TextView dot_2;
    Button save;
    Spinner spinner;
    TextView textView;
       ImageView photo,camera;
       owners owner;


    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        Glide.with(this).load(selectedImage).circleCrop().into(photo);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(TextUtils.isEmpty(Editname.getText().toString())){
            Editname.setError("Please enter Name");
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthStateListener);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createshop);
        heading=findViewById(R.id.Heading);
        category=findViewById(R.id.spinner);
        firebaseAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.textView3);
        dot_1=findViewById(R.id.textView9);
        dot_2=findViewById(R.id.textView6);
        Editname=findViewById(R.id.editname);
        phototext=findViewById(R.id.textView);
        adduserImg=(FloatingActionButton) findViewById(R.id.addpic);
        Editmobile=findViewById(R.id.editmobile);
        save=findViewById(R.id.button);
        photo=findViewById(R.id.textView7);
        firebaseDatabase=FirebaseDatabase.getInstance();
       // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference=firebaseDatabase.getReference().child("Shops");
         owner=new owners();
        textView=findViewById(R.id.textView2);
        address=findViewById(R.id.editTextTextPostalAddress);
        category.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.list, android.R.layout.simple_spinner_item);

       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        category.setAdapter(arrayAdapter);
        category.setThreshold(0);
        category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    category.showDropDown();
            }
        });
        String tempname=Editname.getText().toString();
        CharSequence tempmob=Editmobile.getText();
        String tempaddress=address.getText().toString();

        adduserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                ImagePicker.Companion.with(ownerDetails.this)
////                            .crop()        //Crop image(Optional), Check Customization for more option
////                            .cropOval()       //Allow dimmed layer to have a circle inside
////                            .cropFreeStyle()     //Let the user to resize crop bounds
////                            .galleryOnly()          //We have to define what image provider we want to use
////                            .maxResultSize(1080, 1080) //Final image resolution will be less than 1080 x 1080(Optional)
//                        .start();

                try {
                    if (ActivityCompat.checkSelfPermission(ownerDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ownerDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURES);
                    } else {
////                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
//                        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
////**These following line is the important one!
//
//                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult( ImagePicker.Companion.with(ownerDetails.this).crop().galleryOnly().createIntent(), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });




        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){

                    save.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            if(TextUtils.isEmpty(Editname.getText().toString())){
                                Editname.setError("Please enter Name");
                                return;
                            }
                            if(TextUtils.isEmpty(Editmobile.getText())){
                                Editmobile.setError("Please enter your contact number");
                                return;
                            }
                            if(TextUtils.isEmpty(address.getText().toString())){
                                address.setError("Please enter you address");
                                return;
                            }

                            if(save.getText().toString() != "Save")   {

                                owner=new owners(Editname.getText().toString(),Editmobile.getText().toString(),address.getText().toString());
                                category.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                Editname.setHint("");
                                Editname.setText("");
                                Editmobile.setText("");
                                Editmobile.setHint("Business number");
                                Editname.setHint("shop name");
                                phototext.setText("Add Shop Image");
                                address.setText("");
                                save.setText("Save");
                                heading.setText("Shop's Details");
                                name.setText("Shop's Name");
                                address.setHint("Shop Address");
                                dot_1.setBackground(getDrawable(R.drawable.circle_2));
                                dot_2.setBackground(getDrawable(R.drawable.circle_1));
                            }else{
                                shopdetails shopdetails=new shopdetails(Editname.getText().toString(),category.getText().toString(),address.getText().toString(),Editmobile.getText().toString());
                                databaseReference.child(user.getUid()).child("owner details").setValue(owner);
                                databaseReference.child(user.getUid()).child("shop details").setValue(shopdetails);
                                Intent intent = new Intent(ownerDetails.this, Dashboard.class);
                                //intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);

                            }

                        }
                    });
                }
            }
        };

    }
}