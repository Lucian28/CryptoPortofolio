package com.myapplication.cryptoportofolio.models;

public class CoinPortofolio {
    String crypto;
    String fiat;
    String amount;

    public String getCrypto() {
        return crypto;
    }

    public String getFiat() {
        return fiat;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public CoinPortofolio(String crypto, String amount) {
        this.crypto = crypto;
        this.amount = amount;
    }

    public CoinPortofolio(String crypto, String fiat, String amount) {
        this.crypto = crypto;
        this.fiat = fiat;
        this.amount = amount;
    }
}
