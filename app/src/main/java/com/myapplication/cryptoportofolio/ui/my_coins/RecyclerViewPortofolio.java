package com.myapplication.cryptoportofolio.ui.my_coins;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.models.CoinPortofolio;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import static com.myapplication.cryptoportofolio.ui.prices.RecyclerViewCrypto.dataCrypto;


public class RecyclerViewPortofolio extends  RecyclerView.Adapter<RecyclerViewPortofolio.ViewHolder> {
    private final LinkedList<CoinPortofolio> data;

    public RecyclerViewPortofolio(LinkedList<CoinPortofolio> data){
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerViewPortofolio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.portofolio_row_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPortofolio.ViewHolder holder, int position) {
        String name = data.get(position).getCrypto();
        String imageURL="wait";
        String pret="0";
        String symbol;
        for(int i=0;i<dataCrypto.length;i++)
            if(dataCrypto[i].getName().equals(name)){
              imageURL = dataCrypto[i].getImageURL();
                pret = dataCrypto[i].getPrice();
            }
        String amount = data.get(position).getAmount();

        if(!imageURL.equals("wait"))
            Picasso.get().load(imageURL).into(holder.cryptoImage);

        Double pretMoneda = Double.valueOf(pret);
        Double cantitate = Double.valueOf(amount);
        Double valoareMonedeDetinute = pretMoneda*cantitate;

        holder.cryptoName.setText(name);
        holder.cryptoPrice.setText("$"+valoareMonedeDetinute);
        holder.cryptoAmount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cryptoName;
        ImageView cryptoImage;
        TextView cryptoPrice;
        TextView cryptoAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             cryptoName = itemView.findViewById(R.id.cryptoName);
             cryptoImage = itemView.findViewById(R.id.cryptoImage);
             cryptoPrice = itemView.findViewById(R.id.cryptoPrice);
             cryptoAmount = itemView.findViewById(R.id.cryptoAmmount);
        }
    }
}
