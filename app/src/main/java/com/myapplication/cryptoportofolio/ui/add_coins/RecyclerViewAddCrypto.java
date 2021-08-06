package com.myapplication.cryptoportofolio.ui.add_coins;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.models.CoinDetailed;
import com.myapplication.cryptoportofolio.ui.prices.HomeFragment;
import com.squareup.picasso.Picasso;

public class RecyclerViewAddCrypto extends  RecyclerView.Adapter<RecyclerViewAddCrypto.ViewHolder>{

    private final CoinDetailed[]data;
    public RecyclerViewAddCrypto(CoinDetailed[]data){
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerViewAddCrypto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.crypto_add_row_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = data[position].getName();
        String imageURL = data[position].getImageURL();

        if(!imageURL.equals("wait"))
            Picasso.get().load(imageURL).into(holder.cryptoImage);

        holder.cryptoName.setText(name);
    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cryptoName;
        ImageView cryptoImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoName = itemView.findViewById(R.id.cryptoName);
            cryptoImage = itemView.findViewById(R.id.cryptoImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = SlideshowFragment.recyclerView.getChildLayoutPosition(v);
                    String item = HomeFragment.listaPentruAdaugare[itemPosition].getName();
                    SlideshowFragment.selectCrypto.setText(item);
                    SlideshowFragment.d.hide();
                }
            });
        }
    }
}
