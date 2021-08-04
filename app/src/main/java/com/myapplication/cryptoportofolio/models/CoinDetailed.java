package com.myapplication.cryptoportofolio.models;

public class CoinDetailed {
    String id;
    String symbol;
    String name;
    String imageURL;
    String price;

    public CoinDetailed(String id, String symbol, String name, String imageURL, String price) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
    }

    public CoinDetailed(String name, String imageURL, String price) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
