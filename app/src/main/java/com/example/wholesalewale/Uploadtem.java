package com.example.wholesalewale;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
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
import com.google.firebase.storage.UploadTask;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.controls.Preview;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureAction;
import com.otaliastudios.cameraview.preview.CameraPreview;
import com.otaliastudios.cameraview.preview.GlCameraPreview;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

import exportkit.figma.R;
import okhttp3.internal.cache.DiskLruCache;
import pl.droidsonroids.gif.GifImageView;

public class Uploadtem extends Fragment {
    ArrayList<Uri>  showimagewithuri;
    String name;

    private static final int PICK_FROM_GALLERY = 1;
    private static final int SELECT_PICTURES =1 ;
    BitmapCallback callback;
boolean h=false;
        RecyclerView recyclerView;
    boolean showcamera=false;
    ImageView image;
   public Uploadtem(String name){
        this.name=name;
    }
    public Uploadtem(){}

    View view;
   boolean uploaded=false;
    Context myContext;
    uploadDetails uploadDetail;
    Button button;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImagesAdapter imagesAdapter;
    ImageView preview,capture;


    GifImageView addpicture;
    Button save;
    Button shadow;
    FloatingActionButton touch,gallery,opencamera;
    Animation rotate_o,rotate_c,frm_bottom,to_bottom;
    Uri uri;
    ImageView keep,delete,closeCamera;
    CardView camera;
    CameraView cameraView;
    ActivityResultLauncher<Intent> startActivityIntent ;

    FirebaseAuth firebaseAuth;
    int imgcount=1;
    EditText itemprice;
    EditText producName;
    EditText material;
    EditText colors;
    EditText Quantity;
    ArrayList<Uri> arrayList;

    ArrayList<Uri> showimage;
    EditText aboutProduct;
    boolean clicked;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
        cameraView.close();
    }
    @Override
    public void onPause() {
        super.onPause();

    }



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("Product_photos");
        databaseReference=firebaseDatabase.getReference().child("Shops");
        uploadDetail=new uploadDetails();
        showimage=new ArrayList<>();
        arrayList=new ArrayList<>();
        showimagewithuri=new ArrayList<>();
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (showcamera) {
                    camera.setVisibility(View.GONE);
                    shadow.setVisibility(View.GONE);
                    cameraView.close();
                    showcamera=false;
                } else {

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

        view=inflater.inflate(R.layout.activity_uploadtem, container, false);
        itemprice=view.findViewById(R.id.price);
        producName=view.findViewById(R.id.productname);
        colors=view.findViewById(R.id.colors);
        material=view.findViewById(R.id.material);
        addpicture=view.findViewById(R.id.addimage);
        recyclerView=(RecyclerView)view.findViewById(R.id.pimages);
        shadow=view.findViewById(R.id.button5);
        cameraView=view.findViewById(R.id.cameraView2);
        touch=view.findViewById(R.id.button6);
        gallery=view.findViewById(R.id.gallery_open);
        opencamera=view.findViewById(R.id.camera_open);
        rotate_o=AnimationUtils.loadAnimation(getContext(),R.anim.rotate);
        rotate_c=AnimationUtils.loadAnimation(getContext(),R.anim.rotate_clos);
        frm_bottom=AnimationUtils.loadAnimation(getContext(),R.anim.from_bottom);
        to_bottom=AnimationUtils.loadAnimation(getContext(),R.anim.to_bottom);
        keep=view.findViewById(R.id.save);
        closeCamera=view.findViewById(R.id.closecamera);
        delete=view.findViewById(R.id.delete);
        camera=view.findViewById(R.id.cameraView);
        capture=view.findViewById(R.id.capture);
        Quantity=view.findViewById(R.id.quantity);
        aboutProduct=view.findViewById(R.id.about);
        save=view.findViewById(R.id.button2);
        itemprice.setInputType(InputType.TYPE_CLASS_NUMBER);

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }



    private void setclickable(boolean clicked) {
                if(clicked){
                    opencamera.setClickable(true);
                    gallery.setClickable(true);
                    gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_PICTURES);
                                } else {
//
                                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it


                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    Intent k=Intent.createChooser(intent, "Select Picture");

                                  //startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES); //SELECT_PICTURES is simply a global int used to check the calling intent in onActivityResult
                                   // startActivityForResult(k,SELECT_PICTURES);
                                    startActivityIntent.launch(k);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    opencamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                            showcamera=true;
                camera.setVisibility(View.VISIBLE);
                shadow.setVisibility(View.VISIBLE);
                keep.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                capture.setVisibility(View.VISIBLE);
                cameraView.setLifecycleOwner(getViewLifecycleOwner());
                cameraView.setMode(Mode.PICTURE);
                Animation animation= AnimationUtils.loadAnimation(getContext(),
                        R.anim.fadein);

                Animation animation1= AnimationUtils.loadAnimation(getContext(),
                        R.anim.fadeout);
                capture.setAnimation(animation);
                shadow.setAnimation(animation);
                closeCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        camera.setVisibility(View.GONE);
                        shadow.setVisibility(View.GONE);
                        cameraView.close();
                        capture.setAnimation(animation1);
                    }
                });
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cameraView.takePicture();

                        capture.setAnimation(animation1);
                        capture.setVisibility(View.GONE);
                        keep.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        keep.setAnimation(animation);
                        delete.setAnimation(animation);

                    }
                });
                        }
                    });
                }else{
                    opencamera.setClickable(false);
                    gallery.setClickable(false);
                }
            }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Toast.makeText(myContext, "in", Toast.LENGTH_SHORT).show();
//        if(requestCode == SELECT_PICTURES) {
//            if(resultCode == Activity.RESULT_OK) {
//
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//
//            }
//        }
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query=firebaseDatabase.getReference().child("Shops");
        firebaseAuth=FirebaseAuth.getInstance();
        camera.setVisibility(View.GONE);
        shadow.setVisibility(View.GONE);

        startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Add same code that you want to add in onActivityResult method
                        if(result.getData()!=null) {
                            uploaded=false;
                            Intent data = result.getData();
                            if (data.getClipData() != null) {
                                int count = data.getClipData().getItemCount();
                                int currentItem = 0;
                                while (currentItem < count) {
                                    Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                                    //do something with the image (save it to some directory or whatever you need to do with it here)
                                    arrayList.add(imageUri);
                                    showimagewithuri.add(imageUri);
                                    currentItem++;
                                    Toast.makeText(getContext(), imgcount + " Image added", Toast.LENGTH_SHORT).show();
                                    imgcount++;


                                }
                            } else if (data.getData() != null) {
                                uploaded=false;
                                String imagePath = data.getData().getPath();
                                Uri selectedImage = data.getData();
                                arrayList.add(selectedImage);
                                showimagewithuri.add(selectedImage);
                                //do something with the image (save it to some directory or whatever you need to do with it here)
                            }
                            if (!showimagewithuri.isEmpty()) {
                                imagesAdapter = new ImagesAdapter(getContext(), showimagewithuri, "");
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                recyclerView.setAdapter(imagesAdapter);
                                addpicture.setVisibility(View.GONE);

                            }
                        }

                    }
                });

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked=!clicked;
                setvisibilityoffab(clicked);
                setanimationonfab(clicked);
                setclickable(clicked);

            }

        });

        callback=new BitmapCallback() {
            @Override
            public void onBitmapReady(@Nullable Bitmap bitmap) {
        uploaded=false;
           cameraView.close();
                uri=getImageUri(getContext(),bitmap,bitmap.toString());
                keep.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onClick(View view) {

                        arrayList.add(uri);
                       showimage.add(uri);

                        Toast.makeText(getContext(), imgcount+" Image added", Toast.LENGTH_SHORT).show();
                        imgcount++;
                        cameraView.open();
                        keep.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        capture.setVisibility(View.VISIBLE);

                        if(!showimage.isEmpty()){
                            imagesAdapter=new ImagesAdapter(getContext(),showimage);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                            recyclerView.setAdapter(imagesAdapter);

                        }
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), " Discarded", Toast.LENGTH_SHORT).show();
                        cameraView.open();
                    }
                });

            }
        };
        cameraView.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        cameraView.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS); // Tap to focus!


            cameraView.addCameraListener(new CameraListener() {
                @Override
                public void onCameraOpened(@NonNull CameraOptions options) {
                    super.onCameraOpened(options);

                }

                @Override
                public void onPictureTaken(@NonNull PictureResult result) {
                    super.onPictureTaken(result);
                    addpicture.setVisibility(View.GONE);
                    result.toBitmap((BitmapCallback) callback);
                }
            });

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null) {
                    save.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            String p = itemprice.getText().toString();
                            String c = colors.getText().toString();
                            String q = Quantity.getText().toString();
                            String mat = material.getText().toString();
                            String abt = aboutProduct.getText().toString();
                            String pName = producName.getText().toString();
                                uploaded=false;
                            final boolean[] h = {false};


                                query.addValueEventListener(new ValueEventListener() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (!uploaded) {
                                                Toast.makeText(getContext(), "Wait till upload", Toast.LENGTH_LONG).show();
                                            for (DataSnapshot snap : snapshot.getChildren()){
                                            for (DataSnapshot snapshot1 : snap.getChildren()) {
                                                if (snapshot1.getKey().contains(user.getUid())) {

                                                    uploaded = true;
                                                    uploadDetail = new uploadDetails(pName, Integer.parseInt(q), Double.parseDouble(p), Integer.parseInt(c), mat, abt, null);
                                                    databaseReference.child(snap.getKey()).child(snapshot1.getKey()).child("Products").child(pName).setValue(uploadDetail);

                                                    int i = 1;
                                                    if (!arrayList.isEmpty()) {
                                                        for (Uri u : arrayList) {
                                                            int finalI = i;
                                                            storageReference.child(user.getUid()).child(pName).child("Image_" + i).putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                    while (!urlTask.isSuccessful())
                                                                        ;
                                                                    Uri downloadUrl = urlTask.getResult();
                                                                    uploaded = true;
                                                                    databaseReference.child(snap.getKey()).child(snapshot1.getKey()).child("Products").child(pName).child("links").child(String.valueOf(finalI)).setValue(downloadUrl.toString());
                                                                }
                                                            });


                                                            if (i == arrayList.size() - 1) {
                                                                Toast.makeText(getActivity(), "your items have been  uploaded successfully", Toast.LENGTH_SHORT).show();
                                                            }
                                                            i++;
                                                        }

                                                    }
                                                    h[0] = true;
                                                    break;
                                                }
                                                if(h[0])
                                                    break;

                                            }
                                            if(h[0])
                                                break;
                                        }
                                    }

                                        if (h[0]) {
                                            arrayList.clear();
                                            addpicture.setVisibility(View.VISIBLE);
                                            itemprice.setText("");
                                            producName.setText("");
                                            aboutProduct.setText("");
                                            colors.setText("");
                                            material.setText("");
                                            Quantity.setText("");
                                            showimage.clear();
                                            showimagewithuri.clear();
                                            imagesAdapter=new ImagesAdapter(getContext(),showimagewithuri,"set null");
                                            recyclerView.setAdapter(imagesAdapter);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                        }

                    });

                }

            }
        };
    }

    private void setanimationonfab(boolean clicked) {
        if(clicked){
            touch.startAnimation(rotate_o);
            opencamera.startAnimation(frm_bottom);
            gallery.startAnimation(frm_bottom);
        }else{
            touch.startAnimation(rotate_c);
            opencamera.startAnimation(to_bottom);
            gallery.startAnimation(to_bottom);
        }
    }

    private void setvisibilityoffab(boolean clicked) {
        if(clicked){
            opencamera.setVisibility(View.VISIBLE);
            gallery.setVisibility(View.VISIBLE);
        }else{
            opencamera.setVisibility(View.INVISIBLE);
            gallery.setVisibility(View.INVISIBLE);
        }
    }



    public Uri getImageUri(Context inContext, Bitmap inImage, String i) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,"Image_"+i , null);
        return Uri.parse(path);
    }


}