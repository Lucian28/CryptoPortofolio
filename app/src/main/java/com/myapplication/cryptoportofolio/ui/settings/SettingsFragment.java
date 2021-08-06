package com.myapplication.cryptoportofolio.ui.settings;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.Welcome;
import com.myapplication.cryptoportofolio.models.Fiat;
import com.myapplication.cryptoportofolio.ui.add_coins.RecyclerViewAddCrypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    public static MutableLiveData<String> monedaAleasa = new MutableLiveData<>() ;
    public static Dialog dialog;
    SearchView searchView;
    public static Button selectFIAT;
    public static RecyclerView recyclerViewFiat;
    RecyclerViewFiat adapterFiat;
    public static LinkedList<Fiat> dataSearch = new LinkedList<>();
    public static LinkedList<Fiat> data=new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
         selectFIAT = view.findViewById(R.id.chooseCurrency);
        dataSearch.addAll(Welcome.listaFiat);
        data.addAll(Welcome.listaFiat);
//        monedaAleasa.setValue("old");
//        monedaAleasa.observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                if(!Objects.equals(monedaAleasa.getValue(), "old")) {
//                    try {
//                        WriteFile(Welcome.tipFiat, Objects.requireNonNull(monedaAleasa.getValue()));
//                        Welcome.monedaAleasa = monedaAleasa.getValue();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });


        selectFIAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog  = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_fiat);
                searchView = dialog.findViewById(R.id.searchBarFiat);
                recyclerViewFiat = (RecyclerView) dialog.findViewById(R.id.reciclerViewFiat);
                recyclerViewFiat.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapterFiat = new RecyclerViewFiat(data);
                recyclerViewFiat.setAdapter(adapterFiat);
                dialog.show();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapterFiat.filter(newText);
                        return false;
                    }
                });
            }
        });

        return view;
    }

    public void WriteFile(String filename, String text) throws IOException {
        FileOutputStream fos = getActivity().openFileOutput(filename, MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
    }
}