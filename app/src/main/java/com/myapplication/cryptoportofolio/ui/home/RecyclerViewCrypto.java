package com.myapplication.cryptoportofolio.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RecyclerViewCrypto extends RecyclerView.Adapter<RecyclerViewCrypto.ViewHolder>{

    private CoinDetailed[]data;
    public RecyclerViewCrypto(CoinDetailed[]data){
        this.data = data;

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
        String name = data[position].getName();
        String imageURL = data[position].getImageURL();
        String pret = data[position].getPrice();

        if(!imageURL.equals("wait"))
        Picasso.get().load(imageURL).into(holder.cryptoImage);

        holder.cryptoName.setText(name);
        holder.cryptoPrice.setText("$"+pret);
    }

    @Override
    public int getItemCount() {
        return data.length;
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
