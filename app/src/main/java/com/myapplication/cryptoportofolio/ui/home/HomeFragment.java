package com.myapplication.cryptoportofolio.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.models.Coin;
import com.myapplication.cryptoportofolio.models.CoinDetailed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView deviceList;
    CoinDetailed[] lista=new CoinDetailed[100];

    MutableLiveData<Integer> verif = new MutableLiveData<>();
    RecyclerViewCrypto adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        CoinDetailed coinDetailed = new CoinDetailed("wait","wait","wait");

        deviceList = (RecyclerView) root.findViewById(R.id.reciclerViewCrypto);
        deviceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        for(int i=0;i<100;i++)
        lista[i]=coinDetailed;
        adapter = new RecyclerViewCrypto(lista);
        deviceList.setAdapter(adapter);

        timer();
         timer2();
        setDetailedCoins();
        getDetailedCoins();
        verif.setValue(0);
        verif.observe(getActivity(),new Observer<Integer>() {
            @Override
            public void onChanged(Integer changedValue) {
                if(verif.getValue()>0)
                   adapter.notifyDataSetChanged();

              //  Log.e("VERIF = ",verif.getValue().toString());
            }
        });

        return root;
    }

    public void setALLCoins() {
        getAllCoinsAsync task = new getAllCoinsAsync();
        task.execute();
    }
    private class getAllCoinsAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.coingecko.com/api/v3/coins/list")
                    .get()
                    .build();
            String responseBody = "gol";
            try {
                Response response = client.newCall(request).execute();
                responseBody = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("LISTA MONEDE: ", s);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            try {
                LinkedList<Coin> listaMonede = JSONtoLinkedList(s);

                for (int i = 0; i < listaMonede.size(); i++) {

                    String id = listaMonede.get(i).getId();
                    String simbol = listaMonede.get(i).getSymbol();
                    String nume = listaMonede.get(i).getName();

                    myRef = database.getReference("Coins/" + i);
                    Coin coin = new Coin(id, simbol, nume);
                    myRef.setValue(coin);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private LinkedList<Coin> JSONtoLinkedList(String JsonBody) throws JSONException {
        LinkedList<Coin> listaMonede = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(JsonBody);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String id = json.getString("id");
            String symbol = json.getString("symbol");
            String name = json.getString("name");
            Coin coin = new Coin(id, symbol, name);
            listaMonede.add(coin);
        }
        return listaMonede;
    }
    private void setDetailedCoins() {
        getDetailedCoinsAsync task = new getDetailedCoinsAsync();
        task.execute();
    }
    private class getDetailedCoinsAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=1h%2C24h%2C7d%2C30d%2C1y")
                    .get()
                    .build();
            String responseBody = "gol";
            try {
                Response response = client.newCall(request).execute();
                responseBody = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;

            try {
                LinkedList<CoinDetailed> listaMonede = JSONtoLinkedListDetailed(s);
                for (int i = 0; i < listaMonede.size(); i++) {
                    String id = listaMonede.get(i).getId();
                    String simbol = listaMonede.get(i).getSymbol();
                    String nume = listaMonede.get(i).getName();
                    String imagine = listaMonede.get(i).getImageURL();
                    String pret = listaMonede.get(i).getPrice();

                    myRef = database.getReference("Coins/" + i);
                    CoinDetailed coin = new CoinDetailed(id, simbol, nume, imagine, pret);
                    myRef.setValue(coin);
                }
                Log.e("SETARE VALORI NOI",listaMonede.get(0).getPrice());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private LinkedList<CoinDetailed> JSONtoLinkedListDetailed(String JsonBody) throws JSONException {
        LinkedList<CoinDetailed> listaMonede = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(JsonBody);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            String id = json.getString("id");
            String symbol = json.getString("symbol");
            String name = json.getString("name");
            String imageURL = json.getString("image");
            String price = json.getString("current_price");

            CoinDetailed coin = new CoinDetailed(id, symbol, name, imageURL, price);
            listaMonede.add(coin);
        }
        return listaMonede;
    }
    private void getDetailedCoins() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Coins").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        int i=0;
                                                                        String name = null; String imageURL= null; String price= null;
                                                                        for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                                                                            for (DataSnapshot propertySnapshot : entrySnapshot.getChildren()) {
                                                                                if(propertySnapshot.getKey().equals("name"))
                                                                                    name=propertySnapshot.getValue().toString();
                                                                                if(propertySnapshot.getKey().equals("price"))
                                                                                    price=propertySnapshot.getValue().toString();
                                                                                if(propertySnapshot.getKey().equals("imageURL"))
                                                                                    imageURL=propertySnapshot.getValue().toString();
                                                                            }
                                                                            CoinDetailed coinDetailed = new CoinDetailed(name,imageURL,price);
                                                                            lista[i]=coinDetailed;
                                                                            verif.setValue(i);
                                                                            i++;
                                                                        }


                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                    }
                                                                }
        );

    }

    private void timer(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

               setDetailedCoins();
                Log.e("SET A INCEPUT","SET");
            }
        }, 0, 5000);
    }
    private void timer2(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

               getDetailedCoins();
            }
        }, 0, 5000);
    }
}