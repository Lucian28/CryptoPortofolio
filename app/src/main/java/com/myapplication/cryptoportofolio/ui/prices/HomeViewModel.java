package com.myapplication.cryptoportofolio.ui.prices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mEthereum;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mEthereum = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        mEthereum.setValue("Hatz");
       // getALLCoins();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getETH() {
        return mEthereum;
    }


    }



