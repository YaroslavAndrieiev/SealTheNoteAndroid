package com.example.mainandroidproject;

import android.view.View;

import java.util.ArrayList;
//CLASS IS USELESS
public class NoteContentItems {

    private ArrayList<View> items;

    public NoteContentItems() {
        items = new ArrayList<View>();
    }
    public void addItem(View itemInput) {
        items.add(itemInput);
    }
}


