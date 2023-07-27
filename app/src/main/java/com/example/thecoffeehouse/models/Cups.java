package com.example.thecoffeehouse.models;

import android.widget.ImageView;

import java.io.Serializable;

public class Cups implements Serializable {
    private int cupsResourceId;

    public Cups(int cupsResourceId) {
        this.cupsResourceId = cupsResourceId;
    }



    public void setCupsResourceId(int cupsResourceId) {
        this.cupsResourceId = cupsResourceId;
    }
}
