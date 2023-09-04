package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import exportkit.figma.R;

public class MainActivity extends AppCompatActivity {
Button CreateShop;


    @Override
    protected void onResume() {
        super.onResume();
       // firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    Button User;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        firebaseAuth=FirebaseAuth.getInstance();
        CreateShop=findViewById(R.id.shop);
        User=findViewById(R.id.user);
//        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user= firebaseAuth.getCurrentUser();
//                if(user!=null){
//                  Intent intent=new Intent(MainActivity.this,Dashboard.class);
//                  intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                  startActivity(intent);
//                  finish();
//
//                }
//            }
//        };
       User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                intent.putExtra("customer","land on customer's page");
                startActivity(intent);
                finish();
            }
        });
        CreateShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}