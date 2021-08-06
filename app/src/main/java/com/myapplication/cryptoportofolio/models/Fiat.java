package com.myapplication.cryptoportofolio.models;

public class Fiat {
    String cod;
    String descriere;

    public Fiat(String cod, String descriere) {
        this.cod = cod;
        this.descriere = descriere;
    }

    public String getCod() {
        return cod;
    }

    public String getDescriere() {
        return descriere;
    }
}
