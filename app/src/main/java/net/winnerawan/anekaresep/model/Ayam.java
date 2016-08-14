package net.winnerawan.anekaresep.model;

/**
 * Created by winnerawan on 8/13/16.
 */
public class Ayam {

    String id,awal,gambar,judul,kategori,bahan,cara,keterangan,akhir,rate,time;

    public Ayam(){
    }

    public Ayam(String id, String awal, String gambar, String judul, String kategori, String bahan, String cara, String keterangan, String akhir, String rate, String time) {
        this.id=id;
        this.awal=awal;
        this.gambar=gambar;
        this.judul=judul;
        this.kategori=kategori;
        this.bahan=bahan;
        this.cara=cara;
        this.keterangan=keterangan;
        this.akhir=akhir;
        this.rate=rate;
        this.time=time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAwal() {
        return awal;
    }

    public void setAwal(String awal) {
        this.awal = awal;
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

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getCara() {
        return cara;
    }

    public void setCara(String cara) {
        this.cara = cara;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAkhir() {
        return akhir;
    }

    public void setAkhir(String akhir) {
        this.akhir = akhir;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
