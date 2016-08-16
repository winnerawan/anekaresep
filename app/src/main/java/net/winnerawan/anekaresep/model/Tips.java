package net.winnerawan.anekaresep.model;

/**
 * Created by winnerawan on 8/15/16.
 */
public class Tips {

    String id,judul,gambar,rate,tips,keterangan,kategori;

    public Tips(){
    }

    public Tips(String id, String judul, String gambar, String rate, String tips, String keterangan, String kategori) {
        this.id=id;
        this.gambar=gambar;
        this.judul=judul;
        this.rate=rate;
        this.tips=tips;
        this.keterangan=keterangan;
        this.kategori=kategori;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
