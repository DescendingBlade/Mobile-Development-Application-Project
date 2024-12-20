package com.example.lab4;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModelView extends ViewModel {

    //Declaring the class fields
    public MutableLiveData<Integer> clickedValue;
    public MutableLiveData<String> noteTitle;
    public MutableLiveData<String> noteDetail;
    public boolean edited;

    //Constructor for the class
    public ModelView()
    {
        clickedValue = new MutableLiveData<Integer>();
        noteTitle = new MutableLiveData<String>();
        noteDetail = new MutableLiveData<String>();
        edited = false;
    }

    public void setIsEdited()
    {
        edited = true;
    }

    public void setNotes(String newNotes)
    {
        noteDetail.setValue(newNotes);
    }

    public void setNoteTitle(String newTitle)
    {
        noteTitle.setValue(newTitle);
    }

    public void setClickedValue(int newValue)
    {
        clickedValue.setValue(newValue);
    }

    public int getClickedValue()
    {
        return clickedValue.getValue();
    }

    public String getNoteTitle()
    {
        return noteTitle.getValue();
    }

    public String getNotes()
    {
        return noteDetail.getValue();
    }

    public boolean getIsEdited()
    {
        return edited;
    }
}
