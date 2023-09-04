package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import exportkit.figma.R;

public class SignIn extends AppCompatActivity {
    String chk;
        TextView textView;
    String name;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDataBaseReference;
    FirebaseAuth firebaseAuth;
        EditText Name,Password,Email;
        Button signin,create_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Intent intent = getIntent();
         chk = intent.getStringExtra("customer");

            textView = findViewById(R.id.textView13);
        Name = findViewById(R.id.name);
        Password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.email);
        signin = findViewById(R.id.Signin);
        create_account = findViewById(R.id.Createaccount);
        Name.setVisibility(View.GONE);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setVisibility(View.VISIBLE);
                signin.setText("Create");
                create_account.setVisibility(View.GONE);
                signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email1 = Email.getText().toString().trim();
                        String password1 = Password.getText().toString().trim();
                        if(chk!=null){
                         name="user ";}
                        else{
                            name="owner ";
                        }
                        if (TextUtils.isEmpty(email1)) {
                            Toast.makeText(SignIn.this, "Please Enter Emailsss", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(password1)) {
                            Toast.makeText(SignIn.this, "Please Enter Your PASSWORDS", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        //Firebase authentication (account save)
                        firebaseAuth.createUserWithEmailAndPassword(email1, password1)
                                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //user already logged in
                                            //start the activity
                                            FirebaseUser user=firebaseAuth.getCurrentUser();

                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name+Name.getText().toString().trim())
                                                    .build();
                                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    finish();
                                                    onAuthSuccess();

                                                }
                                            });



                                            // startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else {
                                            Toast.makeText(SignIn.this, "Could not registered , bullcraps", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });
                    }
                });
            }
        });
        if(chk!=null){
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email1 = Email.getText().toString().trim();
                    String password1 = Password.getText().toString().trim();

                    if (TextUtils.isEmpty(email1)) {
                        Toast.makeText(SignIn.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password1)) {
                        Toast.makeText(SignIn.this, "Please Enter Your PASSWORDS", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    firebaseAuth.signInWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        finish();
                                        startActivity(new Intent(SignIn.this, CustomerDashboard.class));
                                        Toast.makeText(SignIn.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignIn.this, "Could not login, password or email wrong , bullcraps", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

        }else {
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email1 = Email.getText().toString().trim();
                    String password1 = Password.getText().toString().trim();

                    if (TextUtils.isEmpty(email1)) {
                        Toast.makeText(SignIn.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password1)) {
                        Toast.makeText(SignIn.this, "Please Enter Your PASSWORDS", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    firebaseAuth.signInWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        finish();
                                        startActivity(new Intent(SignIn.this, Dashboard.class));
                                        Toast.makeText(SignIn.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignIn.this, "Could not login, password or email wrong , bullcraps", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void onAuthSuccess() {
        //String username = usernameFromEmail(user.getEmail());
        // Write new user
      //  writeNewUser(user.getUid(), user.getEmail());
        // Go to MainActivity
        //finish();
        if (chk != null) {
            Toast.makeText(this, "welcome customer", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(this,customerDetails.class);
            startActivity(intent1);
        }else {
            Intent intent = new Intent(SignIn.this, ownerDetails.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

    }
//    private void writeNewUser(String userId,  String email) {
//        Patients detail=new Patients(name.getText().toString(),hname.getText().toString());
//        mDataBaseReference.child(userId).child("Hospital Details").setValue(detail);
//    }
}