package org.example.proje;

public class Satis {
    private String islemId;
    private String musteri;
    private String urun;
    private int adet;
    private double toplamTutar;
    private String tarih;

    public Satis(String islemId, String musteri, String urun, int adet, double toplamTutar, String tarih) {
        this.islemId = islemId;
        this.musteri = musteri;
        this.urun = urun;
        this.adet = adet;
        this.toplamTutar = toplamTutar;
        this.tarih = tarih;
    }

    public String getIslemId() { return islemId; }
    public void setIslemId(String islemId) { this.islemId = islemId; }

    public String getMusteri() { return musteri; }
    public void setMusteri(String musteri) { this.musteri = musteri; }

    public String getUrun() { return urun; }
    public void setUrun(String urun) { this.urun = urun; }

    public int getAdet() { return adet; }
    public void setAdet(int adet) { this.adet = adet; }

    public double getToplamTutar() { return toplamTutar; }
    public void setToplamTutar(double toplamTutar) { this.toplamTutar = toplamTutar; }

    public String getTarih() { return tarih; }
    public void setTarih(String tarih) { this.tarih = tarih; }

    @Override
    public String toString() {
        return islemId + " - " + urun + " (" + musteri + ")";
    }
}