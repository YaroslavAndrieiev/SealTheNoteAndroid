package com.example.mainandroidproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

//Класс представляет заметку
public class Note {
    private String title;
    private String text;
    private boolean enabledState;//Состояние выделения для удаления true-выделен
    private ArrayList<Object> noteContentList;//Елементы заметки
    private String someItemInJson;

    public ArrayList<Object> getNoteContent() {
        return noteContentList;
    }
    public void addItemToContentList(Object item) {
        noteContentList.add(item);
    }

    public void setContent(ArrayList<Object> content) {
       this.noteContentList = content;
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.enabledState = false;
        this.noteContentList =  new ArrayList<>();
    }

    public Note(String title, String text, ArrayList<Object> contentList) {
        this.title = title;
        this.text = text;
        this.enabledState = false;
        this.noteContentList = contentList;
    }
    public Note(String title, String text,String jsonItem) {
        this.title = title;
        this.text = text;
        this.enabledState = false;
        this.someItemInJson = jsonItem;
    }

    public String getSomeItemInJson() {
        return someItemInJson;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnabledState() {
        return this.enabledState;
    }

    public void setEnabledState(boolean enabledState) {
        this.enabledState = enabledState;
    }

}