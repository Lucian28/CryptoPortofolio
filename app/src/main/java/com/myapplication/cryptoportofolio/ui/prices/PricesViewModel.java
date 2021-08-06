package com.myapplication.cryptoportofolio.ui.prices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PricesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mEthereum;

    public PricesViewModel() {
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



