package com.myapplication.cryptoportofolio.ui.my_coins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.Welcome;
import com.myapplication.cryptoportofolio.models.Coin;
import com.myapplication.cryptoportofolio.models.CoinPortofolio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class PortfolioFragment extends Fragment {
    private PortfolioViewModel portfolioViewModel;
    public static RecyclerView recyclerViewPortofolio;
    RecyclerViewPortofolio adapterPortofolio;

    public static LinkedList<CoinPortofolio> listaMonede = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        portfolioViewModel = new ViewModelProvider(this).get(PortfolioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        listaMonede.clear();
        if(fileExist(Welcome.portofolioUser)) {
            try {
                ReadFile(Welcome.portofolioUser,listaMonede);
                recyclerViewPortofolio = (RecyclerView) root.findViewById(R.id.recyclerViewPortofolio);
                recyclerViewPortofolio.setLayoutManager(new LinearLayoutManager(getActivity()));
                CoinPortofolio total = new CoinPortofolio("Total: ","","0");
                listaMonede.add(total);
                adapterPortofolio = new RecyclerViewPortofolio(listaMonede);
                recyclerViewPortofolio.setAdapter(adapterPortofolio);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public boolean fileExist(String fname){
        File file = getActivity().getFileStreamPath(fname);
        return file.exists();
    }

    public void ReadFile(String filename, LinkedList<CoinPortofolio> lista) throws IOException {
        File file = new File(getActivity().getFilesDir(), filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line=br.readLine();
        while(line != null)
        {
            String[] coinDetails = TrimContact(line);
            CoinPortofolio coinPortofolio = new CoinPortofolio(coinDetails[0],coinDetails[1]);
            lista.add(coinPortofolio);
            line = br.readLine();
        }
    }

    public String[] TrimContact(String contact){
        String[] results = contact.split("\\|");
        return results;
    }

    public CoinPortofolio adaugareTotal(LinkedList<CoinPortofolio> listaM){

        return null;
    }
}