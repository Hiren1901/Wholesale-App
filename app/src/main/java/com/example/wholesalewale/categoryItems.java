package com.example.wholesalewale;

import android.graphics.drawable.Drawable;

public class categoryItems {

    public int getDrawable() {
        return drawable;
    }

    public String getName() {
        return name;
    }

  int drawable;
    String name;
    categoryItems(int drawable,String name){
        this.drawable=drawable;
        this.name=name;
    }


}
