package com.example.sendvis2;

public class OfertaOngoing {
    private String numeVendor;
    private String titlu;
    private String quantificator;
    private String number;

    private String maxNumber;

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    public OfertaOngoing(String numeVendor, String titlu, String quantificator, String number, String maxNumber) {
        this.numeVendor = numeVendor;
        this.titlu = titlu;
        this.quantificator = quantificator;
        this.number = number;
        this.maxNumber = maxNumber;
    }

    public String getNumeVendor() {
        return numeVendor;
    }

    public void setNumeVendor(String numeVendor) {
        this.numeVendor = numeVendor;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getQuantificator() {
        return quantificator;
    }

    public void setQuantificator(String quantificator) {
        this.quantificator = quantificator;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
