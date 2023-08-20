package com.example.wholesalewale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import exportkit.figma.R;

public class Dashboard extends AppCompatActivity {

        ImageButton shop,add_item,check_orders,edit;
        ImageButton[] imageButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        shop=findViewById(R.id.shop1);
        add_item=findViewById(R.id.addItem);
        check_orders=findViewById(R.id.delivery);
        edit=findViewById(R.id.editItem);
        imageButtons=new ImageButton[]{shop,add_item,check_orders,edit};
        setButtonAttributes(shop);
        replaceFragment(new shop(),R.anim.fadein, R.anim.fadeout);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(shop);
                replaceFragment(new shop(), R.anim.fadein, R.anim.fadeout);
            }
        });
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(add_item);
                replaceFragment(new Uploadtem(),R.anim.fadein, R.anim.fadeout);
            }
        });
        check_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(check_orders);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(edit);
                replaceFragment(new Edititems(),R.anim.fadein, R.anim.fadeout);
            }
        });

    }

    public void replaceFragment(Fragment fragment, int animEnter, int animExit) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animEnter, animExit);
        fragmentTransaction.replace(R.id.linearLayout,fragment);
        fragmentTransaction.commit();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

       // Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_SHORT).show();
        Fragment fragment=new Uploadtem();
        fragment.onActivityResult(requestCode,resultCode,data);

    }
    private void setButtonAttributes(ImageButton imageButton){

        imageButton.setEnabled(false);

        for(int i=0;i<imageButtons.length;i++){
            if (imageButtons[i]!=imageButton){
                imageButtons[i].setBackground(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageButtons[i].setColorFilter(getColor(R.color.lighter_gray));}

                imageButtons[i].setEnabled(true);
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageButtons[i].setColorFilter(getColor(R.color.accent_blue2));
                }
            }
        }

    }
}