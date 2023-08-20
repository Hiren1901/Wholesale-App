package com.example.wholesalewale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import exportkit.figma.R;

public class firebasePersitence extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}