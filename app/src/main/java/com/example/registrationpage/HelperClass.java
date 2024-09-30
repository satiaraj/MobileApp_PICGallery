package com.example.registrationpage;

import java.util.Date;

public class HelperClass {
    String nama, email, pass, tanggal;


    public HelperClass() {
    }

    public HelperClass(String nama, String email, String pass, String tanggal) {
        this.nama = nama;
        this.email = email;
        this.pass = pass;
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
