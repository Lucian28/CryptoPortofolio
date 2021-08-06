package com.myapplication.cryptoportofolio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.models.Fiat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Welcome extends  AppCompatActivity{
    public static final String monedeUser="monedeUser.txt";
    public static final String portofolioUser="portofolioUser02.txt";
    public static final String tipFiat="monedaFiat.txt";
    public static String monedaAleasa;

    private Handler mHandler = new Handler();
    public static  LinkedList<Fiat> listaFiat;
    ArrayList<String> listaBruta = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        listaFiat = new LinkedList<>();
         addImportantFiat();
        try {
            checkFiat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!fileExist(monedeUser))
            getAvaiblableCurrencies();
        else {
            try {
                ReadFile(monedeUser,listaBruta);
                for(int i=0;i<listaBruta.size();i++)
                {
                    String[] detalii = TrimContact(listaBruta.get(i));
                    Fiat fiat = new Fiat(detalii[0],detalii[1]);
                    listaFiat.add(fiat);
                }
                handlerFunction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

        private LinkedList<Fiat> JSONtoLinkedlist(String raspunsJSON) throws JSONException {
        JSONObject reader = new JSONObject(raspunsJSON);
        JSONObject reader2 = reader.getJSONObject("symbols");
        Iterator iteratorCodes = reader2.keys();

        LinkedList<Fiat> fiatLinkedList = new LinkedList<>();
        while (iteratorCodes.hasNext())
        {
            String fiatCode = (String)iteratorCodes.next();
            JSONObject jsonObject =   reader2.getJSONObject(fiatCode);

            String cod = jsonObject.getString("code");
            String description = jsonObject.getString("description");
            System.out.println(cod + "------>" + description);
            Fiat fiat = new Fiat(cod,description);
            fiatLinkedList.add(fiat);
        }
        return fiatLinkedList;
    }

        private void getAvaiblableCurrencies(){
        getAvailableCurrenciesAsync task = new getAvailableCurrenciesAsync();
        task.execute();
    }
    private class getAvailableCurrenciesAsync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.exchangerate.host/symbols")
                    .get()
                    .build();
           String responseBody="gol";
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
            Log.e("JSON: ",s);
            try {
                listaFiat = JSONtoLinkedlist(s);
                String content = ArrayToList(listaFiat);
                WriteFile(monedeUser,content);
                handlerFunction();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public  void WriteFile(String filename, String text) throws IOException {
        FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();
    }

    private void handlerFunction(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Welcome.this,MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        },1000);
    }

    public String ArrayToList(LinkedList<Fiat> contactList){
        String result="";
        for (int i=0;i<contactList.size();i++)
            result+=contactList.get(i).getCod()+":"+contactList.get(i).getDescriere()+"\n";
        return result;
    }

    public String[] TrimContact(String contact){
        String[] results = contact.split(":");
        return results;
    }


    public void ReadFile(String filename, ArrayList<String> lista) throws IOException {
        File file = new File(getFilesDir(), filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line=br.readLine();
        while(line != null)
        {
            lista.add(line);
            line = br.readLine();
        }
    }

    public  void addImportantFiat(){
        Fiat euro = new Fiat("EUR","Euro");
        Fiat usd = new Fiat("USD","United States Dollar");
        Fiat ron = new Fiat("RON","Romanian Leu");
        Fiat GBP = new Fiat ("GBP", "British Pound Sterling");
        listaFiat.add(ron);listaFiat.add(usd);listaFiat.add(euro);listaFiat.add(GBP);
    }

    private void checkFiat() throws IOException {
        if(fileExist(Welcome.tipFiat))
            {
                String moneda = ReadFile(Welcome.tipFiat);
                monedaAleasa = moneda;
            }
           else
               monedaAleasa="usd";
    }

    public String ReadFile(String filename) throws IOException {
        File file = new File(getFilesDir(), filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine();

    }
}
