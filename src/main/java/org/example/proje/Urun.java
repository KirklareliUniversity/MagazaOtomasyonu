package org.example.proje;

public class Urun {
    private String urunAdi;
    private String kategori;
    private int stokMiktari;
    private double birimFiyati;
    private String urunSahibi;

    
    public Urun(String urunAdi, String kategori, int stokMiktari, double birimFiyati, String urunSahibi) {
        this.urunAdi = urunAdi;
        this.kategori = kategori;
        this.stokMiktari = stokMiktari;
        this.birimFiyati = birimFiyati;
        this.urunSahibi = urunSahibi;
    }

   
    public String getUrunAdi() { return urunAdi; }
    public void setUrunAdi(String urunAdi) { this.urunAdi = urunAdi; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public int getStokMiktari() { return stokMiktari; }
    public void setStokMiktari(int stokMiktari) { this.stokMiktari = stokMiktari; }

    public double getBirimFiyati() { return birimFiyati; }
    public void setBirimFiyati(double birimFiyati) { this.birimFiyati = birimFiyati; }

    public String getUrunSahibi() { return urunSahibi; }
    public void setUrunSahibi(String urunSahibi) { this.urunSahibi = urunSahibi; }
}