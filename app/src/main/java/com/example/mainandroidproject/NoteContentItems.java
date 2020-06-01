package com.example.mainandroidproject;

import android.view.View;

import java.util.ArrayList;

public class NoteContentItems {

    private ArrayList<View> items;

    public NoteContentItems() {
        items = new ArrayList<View>();
    }
    public void addItem(View itemInput) {
        items.add(itemInput);
    }
}


