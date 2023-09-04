package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import exportkit.figma.R;

public class Splash_screen extends AppCompatActivity {
        TextView Whole,Sale,Wale;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_splash_screen);
            Whole = findViewById(R.id.whole);
            Sale = findViewById(R.id.sale);
            Wale = findViewById(R.id.wale);
            Animation frm_bottom = AnimationUtils.loadAnimation(getApplication(), R.anim.up);
            Animation right = AnimationUtils.loadAnimation(getApplication(), R.anim.right);
            Animation btm = AnimationUtils.loadAnimation(getApplication(), R.anim.btm);
            Sale.setAnimation(btm);
            btm.start();
            Wale.setAnimation(right);
            right.start();
            Whole.setAnimation(frm_bottom);
            frm_bottom.start();

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                        int secondsDelayed = 7;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (user != null) {
                                if (user.getDisplayName().contains("user ")) {
                                    Intent intent = new Intent(getApplication(), CustomerDashboard.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getApplication(), Dashboard.class);
                                    startActivity(intent);
                                }
                            } else {
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                            }

                        }
                    }, secondsDelayed * 1200);


                }

            };


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "This is my message to be reloaded");
        Toast.makeText(this,"dsd", Toast.LENGTH_LONG).show();

    }


}