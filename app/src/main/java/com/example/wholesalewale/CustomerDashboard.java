package com.example.wholesalewale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import exportkit.figma.R;

public class CustomerDashboard extends AppCompatActivity {
        ImageButton home,like,cart,list;
        CardView cardView,Buy ;

    ImageButton[] imageButtons;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        home=findViewById(R.id.homego);
        like=findViewById(R.id.liked);
        cart=findViewById(R.id.cart);
        list=findViewById(R.id.orderList);
        cardView=findViewById(R.id.card);
        Buy=findViewById(R.id.buybutton);
        imageButtons=new ImageButton[]{home,cart,like,list};
        Intent intent=getIntent();
        String user=intent.getStringExtra("User");
        setButtonAttributes(home);

Buy.setVisibility(View.GONE);
    cardView.setVisibility(View.VISIBLE);


        replaceFragment(new customerpage(user),R.anim.fadein, R.anim.fadeout);
      home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(home);
                Buy.setVisibility(View.GONE);
               replaceFragment(new customerpage(user), R.anim.fadein, R.anim.fadeout);
            }
        });
       cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(cart);
                Buy.setVisibility(View.GONE);
                replaceFragment(new CartList(),R.anim.fadein, R.anim.fadeout);
            }
        });
  like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(like);
                Buy.setVisibility(View.GONE);
                replaceFragment(new LikedItemList(user), R.anim.fadein, R.anim.fadeout);

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonAttributes(list);
                Buy.setVisibility(View.GONE);
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