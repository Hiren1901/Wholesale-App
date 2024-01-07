package com.example.wholesalewale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import exportkit.figma.R;

public class CustomerDashboard extends AppCompatActivity {
        ImageButton home,like,cart,list;
    ImageButton[] imageButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        home=findViewById(R.id.homego);
      like=findViewById(R.id.liked);
        cart=findViewById(R.id.cart);
        list=findViewById(R.id.orderList);
        imageButtons=new ImageButton[]{home,cart,like,list};
        setButtonAttributes(home);
        replaceFragment(new customerpage(),R.anim.fadein, R.anim.fadeout);
      home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(home);
               replaceFragment(new customerpage(), R.anim.fadein, R.anim.fadeout);
            }
        });
       cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(cart);
                replaceFragment(new CartList(),R.anim.fadein, R.anim.fadeout);
            }
        });
  like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(like);
                replaceFragment(new LikedItemList(), R.anim.fadein, R.anim.fadeout);

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(list);
                replaceFragment(new orderedList(), R.anim.fadein, R.anim.fadeout);
            }
        });

    }
    public void replaceFragment(Fragment fragment, int animEnter, int animExit) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animEnter, animExit);
        fragmentTransaction.replace(R.id.home,fragment);
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
                    if(i==2){

                    imageButtons[i].setColorFilter(getColor(R.color.red));
                    }else{
                        imageButtons[i].setColorFilter(getColor(R.color.black));
                    }
                }
            }
        }

    }
}