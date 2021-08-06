package com.myapplication.cryptoportofolio.ui.add_coins;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.Welcome;
import com.myapplication.cryptoportofolio.models.CoinPortofolio;
import com.myapplication.cryptoportofolio.models.Fiat;
import com.myapplication.cryptoportofolio.ui.prices.PricesFragment;
import com.myapplication.cryptoportofolio.ui.settings.RecyclerViewFiat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

public class AddCoinsFragment extends Fragment {


    private SearchView searchView;
    public static Dialog d;
    public static Dialog dialog;
    private Button addCrypto;
    public static Button selectCrypto;
    public static Button selectFIAT;
    private EditText enterAmount;
    private AddCoinsViewModel addCoinsViewModel;
    public static  RecyclerView recyclerView;
    public static RecyclerView recyclerViewFiat;
    RecyclerViewAddCrypto adapter;
    RecyclerViewFiat adapterFiat;
    public static  LinkedList<Fiat> dataSearch = new LinkedList<>();
    public static  LinkedList<Fiat> data=new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addCoinsViewModel = new ViewModelProvider(this).get(AddCoinsViewModel.class);
        View v = inflater.inflate(R.layout.fragment_slideshow, container, false);
        selectCrypto = v.findViewById(R.id.chooseCrypto);
        selectFIAT = v.findViewById(R.id.chooseCurrency);
        dataSearch.addAll(Welcome.listaFiat);
        data.addAll(Welcome.listaFiat);
        enterAmount = v.findViewById(R.id.chooseAmount);
        addCrypto = v.findViewById(R.id.addCrypto);
        selectFIAT.setVisibility(View.GONE);

        selectCrypto.setOnClickListener(new View.OnClickListener() { // selectare crypto
            @Override
            public void onClick(View v) {
                d = new Dialog(getActivity());
                d.setContentView(R.layout.dialog_select_crypto);
                recyclerView = (RecyclerView) d.findViewById(R.id.reciclerViewAddCrypto);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new RecyclerViewAddCrypto(PricesFragment.listaPentruAdaugare);
                recyclerView.setAdapter(adapter);
                d.show();
            }
        });

        selectFIAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0;i<Welcome.listaFiat.size();i++){
//                    Log.e(Welcome.listaFiat.get(i).getCod(),Welcome.listaFiat.get(i).getDescriere());
//                    Log.e("Marime linkedlist:", String.valueOf(Welcome.listaFiat.size()));
//                }
                dialog  = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_fiat);
                searchView = dialog.findViewById(R.id.searchBarFiat);
                recyclerViewFiat = (RecyclerView) dialog.findViewById(R.id.reciclerViewFiat);
                recyclerViewFiat.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapterFiat = new RecyclerViewFiat(Welcome.listaFiat);
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

        addCrypto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enterAmount.getText().length()==0)
                    enterAmount.setError("Please enter a value !");
                else{
                    String crypto = selectCrypto.getText().toString();
                    String amount = enterAmount.getText().toString();

                    if(fileExist(Welcome.portofolioUser)){
                        LinkedList<CoinPortofolio> coinPortofolios = new LinkedList<>();
                        try {
                            Boolean exista=false;
                            coinPortofolios = ReadFile(Welcome.portofolioUser);
                            for(int i=0;i<coinPortofolios.size();i++)
                                if(coinPortofolios.get(i).getCrypto().equals(crypto)){
                                    float amountOld = Float.parseFloat(coinPortofolios.get(i).getAmount());
                                    float amountPlus = Float.parseFloat(amount);
                                    float amountNew = amountOld+amountPlus;
                                    coinPortofolios.get(i).setAmount(String.valueOf(amountNew));
                                    exista=true;
                                }
                            if(!exista){
                                CoinPortofolio coin = new CoinPortofolio(crypto,amount);
                                coinPortofolios.add(coin);
                                }

                            String content = ArrayToList(coinPortofolios);
                                Log.e("Content:",content);
                                WriteFile(Welcome.portofolioUser,content);
                                showSuccesDialog(crypto,amount);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        String content = crypto+"|"+amount;
                        Log.e("Content:",content);
                        try {
                            WriteFile(Welcome.portofolioUser,content);
                            showSuccesDialog(crypto,amount);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return v;
    }

    private void showSuccesDialog(String moneda, String amount){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Successfully inserted !");
        alertDialog.setMessage(amount+ " " +moneda+ " added to your portfolio !");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


//    private void getAvaiblableCurrencies(){
//        getAvailableCurrenciesAsync task = new getAvailableCurrenciesAsync();
//        task.execute();
//    }
//    private class getAvailableCurrenciesAsync extends AsyncTask<String,String,String>{
//        @Override
//        protected String doInBackground(String... strings) {
//            OkHttpClient client = new OkHttpClient();
//            //c5805e53ad36fe3a8eadb26b46f644b7
//            Request request = new Request.Builder()
//                    .url("https://api.exchangerate.host/symbols")
//                    .get()
//                    .build();
//           String responseBody="gol";
//            try {
//                Response response = client.newCall(request).execute();
//                responseBody = response.body().string();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return responseBody;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.e("JSON: ",s);
//            try {
//                LinkedList<Fiat> currencies = JSONtoLinkedlist(s);
//                inserareFirebase(currencies);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void inserareFirebase(LinkedList<Fiat> linkedList) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef;
//        for (int i = 0; i < linkedList.size(); i++) {
//
//            myRef = database.getReference("Fiat/"+linkedList.get(i).getCod());
//            myRef.setValue(linkedList.get(i).getDescriere());
//        }
//    }
//
//
//    private LinkedList<Fiat> JSONtoLinkedlist(String raspunsJSON) throws JSONException {
//        JSONObject reader = new JSONObject(raspunsJSON);
//        JSONObject reader2 = reader.getJSONObject("symbols");
//        Iterator iteratorCodes = reader2.keys();
//
//        LinkedList<Fiat> arrayList = new LinkedList<>();
//        while (iteratorCodes.hasNext())
//        {
//            String fiatCode = (String)iteratorCodes.next();
//            JSONObject jsonObject =   reader2.getJSONObject(fiatCode);
//
//            String cod = jsonObject.getString("code");
//            String description = jsonObject.getString("description");
//            System.out.println(cod + "------>" + description);
//            Fiat fiat = new Fiat(cod,description);
//            arrayList.add(fiat);
//        }
//        return arrayList;
//    }

    public boolean fileExist(String fname){
        File file = getActivity().getFileStreamPath(fname);
        return file.exists();
    }

    public void WriteFile(String filename, String text) throws IOException {
        FileOutputStream fos = getActivity().openFileOutput(filename, MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
    }

    public LinkedList<CoinPortofolio> ReadFile(String filename) throws IOException {
        LinkedList<CoinPortofolio> lista= new LinkedList<>();
        File file = new File(getActivity().getFilesDir(), filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line=br.readLine();
        while(line != null)
        {
            String[] coinDetails = TrimContact(line);
            Log.e("AAA",coinDetails[0]+coinDetails[1]);
            CoinPortofolio coinPortofolio = new CoinPortofolio(coinDetails[0],coinDetails[1]);
            lista.add(coinPortofolio);
            line = br.readLine();
        }
        return lista;
    }

    public String[] TrimContact(String contact){
        String[] results = contact.split("\\|");
        return results;
    }

    public String ArrayToList(LinkedList<CoinPortofolio> contactList){
        String result="";
        for (int i=0;i<contactList.size();i++)
            result+=contactList.get(i).getCrypto()+"|"+contactList.get(i).getAmount()+"\n";
        return result;
    }
}