package com.myapplication.cryptoportofolio.ui.add_coins;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCoinsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddCoinsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}