package net.winnerawan.anekaresep.model;

/**
 * Created by winnerawan on 8/13/16.
 */
public class KueBasah {

    String id,gambar,judul,awal,bahan,cara,keterangan,akhir,rate;

    public KueBasah(String id, String gambar, String judul, String awal, String bahan, String cara, String keterangan, String akhir,String rate) {
        this.id=id;
        this.gambar=gambar;
        this.judul=judul;
        this.awal=awal;
        this.bahan=bahan;
        this.cara=cara;
        this.keterangan=keterangan;
        this.akhir=akhir;
        this.rate=rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAwal() {
        return awal;
    }

    public void setAwal(String awal) {
        this.awal = awal;
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
}
