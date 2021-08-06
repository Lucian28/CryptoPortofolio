package com.myapplication.cryptoportofolio.ui.add_coins;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.cryptoportofolio.R;
import com.myapplication.cryptoportofolio.Welcome;
import com.myapplication.cryptoportofolio.models.Fiat;

import java.util.LinkedList;
import java.util.Locale;

import static com.myapplication.cryptoportofolio.ui.add_coins.SlideshowFragment.dataSearch;

public class RecyclerViewFiat extends  RecyclerView.Adapter<RecyclerViewFiat.ViewHolder>{

    private final LinkedList<Fiat> data;

    public RecyclerViewFiat(LinkedList<Fiat> data){
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerViewFiat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fiat_row_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cod = data.get(position).getCod();
        String descriere = data.get(position).getDescriere();

        holder.codFiat.setText(cod);
        holder.descriereFiat.setText(descriere);

    }
    public void filter(String charText) {
        Log.e("DATASEARCH SIZE ", String.valueOf(dataSearch.size()));
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(dataSearch);
        } else {
            for (int i=0;i<dataSearch.size();i++) {
                if (dataSearch.get(i).getCod().toLowerCase(Locale.getDefault()).contains(charText)) {
                   data.add(dataSearch.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codFiat;
        TextView descriereFiat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codFiat = itemView.findViewById(R.id.fiatCod);
            descriereFiat = itemView.findViewById(R.id.fiatDecriere);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = SlideshowFragment.recyclerViewFiat.getChildLayoutPosition(v);
                    String item = Welcome.listaFiat.get(itemPosition).getCod();
                    SlideshowFragment.selectFIAT.setText(item);
                    SlideshowFragment.dialog.hide();
                }
            });
        }
    }
}


