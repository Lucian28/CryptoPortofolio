package com.myapplication.cryptoportofolio.ui.home;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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



