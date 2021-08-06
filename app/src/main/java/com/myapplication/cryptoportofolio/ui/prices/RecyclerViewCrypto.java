package com.myapplication.cryptoportofolio.ui.prices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.models.CoinDetailed;
import com.squareup.picasso.Picasso;

public class RecyclerViewCrypto extends RecyclerView.Adapter<RecyclerViewCrypto.ViewHolder>{

   public static CoinDetailed[] dataCrypto;
    public RecyclerViewCrypto(CoinDetailed[] dataCrypto){
        this.dataCrypto = dataCrypto;

    }
    @NonNull
    @Override
    public RecyclerViewCrypto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.crypto_row_list,parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCrypto.ViewHolder holder, int position) {
        String name = dataCrypto[position].getName();
        String imageURL = dataCrypto[position].getImageURL();
        String pret = dataCrypto[position].getPrice();

        if(!imageURL.equals("wait"))
        Picasso.get().load(imageURL).into(holder.cryptoImage);

        holder.cryptoName.setText(name);
        holder.cryptoPrice.setText("$"+pret);
    }

    @Override
    public int getItemCount() {
        return dataCrypto.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cryptoName;
        ImageView cryptoImage;
        TextView cryptoPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.cryptoName);
            cryptoPrice = itemView.findViewById(R.id.cryptoPrice);
            cryptoImage = itemView.findViewById(R.id.cryptoImage);
        }
    }
}
