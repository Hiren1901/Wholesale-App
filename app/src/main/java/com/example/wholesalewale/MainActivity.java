package com.example.wholesalewale;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import exportkit.figma.R;

public class MainActivity extends AppCompatActivity {
Button CreateShop;
    FirebaseUser user;

    @Override
    protected void onResume() {
        super.onResume();
firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    Button User;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(user==null)
         this.moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_page);
       this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                //close();

            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        CreateShop=findViewById(R.id.shop);
        User=findViewById(R.id.user);
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
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });
     mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              user= firebaseAuth.getCurrentUser();
              if(user==null)
                Toast.makeText(MainActivity.this,"no user" , Toast.LENGTH_SHORT).show();

            }
        };

    }

}