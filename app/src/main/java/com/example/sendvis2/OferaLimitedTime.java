package com.example.sendvis2;

import java.time.LocalDate;
import java.time.LocalTime;

public class OferaLimitedTime {
    private String numeVendor;
    private String titlu;
    private String descriere;
    private String ora;
    private String data;

    public OferaLimitedTime(String numeVendor, String titlu, String descriere, String ora, String data) {
        this.numeVendor = numeVendor;
        this.titlu = titlu;
        this.descriere = descriere;
        this.ora = ora;
        this.data = data;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
