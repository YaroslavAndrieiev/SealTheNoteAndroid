package com.example.mainandroidproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;

//Класс хранит данные которые будут сохраняиться в SharedPreferences, а именно список заметок,
//который в свою очередь я вляется списком Note
public class SharedPreferencesData {
    private ArrayList<Note> notesList;

    public SharedPreferencesData() {
        this.notesList = new ArrayList<>();
//        addNote("textTitle","testText");
    }

    public ArrayList<Note> getNotesList(){
            return this.notesList;
    }
    public void addNote(String titleInp,String textInp) {
        notesList.add(new Note(titleInp,textInp));
    }
    public void addNote(Note newNote) {
        if(!newNote.getTitle().equals("") || !newNote.getText().equals(""))
         notesList.add(newNote);
    }
    public void clearNotesList() {
        notesList.clear();
    }
    //Удаляет все выделенные заметки
    public void deleteAllEnabled()
    {
        //Итератор позволяет редактировать коллекцию во время её перебора
        Iterator<Note> notesIterator = notesList.iterator();
        while(notesIterator.hasNext()) {

            Note nextNote = notesIterator.next();
            if (nextNote.isEnabledState()) {
                notesIterator.remove();
            }
        }
    }
    //Снимает выдиление со всех заметок
    public void setAllNotesUnEnabled()
    {
        for(Note currentNote : notesList)
        {
            currentNote.setEnabledState(false);
        }
    }

}
