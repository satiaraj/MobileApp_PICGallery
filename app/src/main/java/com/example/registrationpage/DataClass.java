package com.example.registrationpage;

public class DataClass {
    String gambar, judul, deskripsi, nama;

    public DataClass(String gambar, String judul, String deskripsi, String nama) {
        this.gambar = gambar;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.nama = nama;
    }

    public DataClass() {
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
