package com.myapplication.cryptoportofolio.models;

public class Coin {
    String id;
    String symbol;
    String name;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public Coin(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public Coin(String symbol, String name) {

        this.symbol = symbol;
        this.name = name;
    }

}
