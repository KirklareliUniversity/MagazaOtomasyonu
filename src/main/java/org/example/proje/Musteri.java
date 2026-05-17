package org.example.proje;

public class Musteri {
    private String musteriID;
    private String adSoyad;
    private String eposta;
    private String telefon;
    private String notlar;

    public Musteri(String musteriID, String adSoyad) {
        this.musteriID = musteriID;
        this.adSoyad = adSoyad;
    }

    public Musteri(String musteriID, String adSoyad, String eposta, String telefon, String notlar) {
        this.musteriID = musteriID;
        this.adSoyad = adSoyad;
        this.eposta = eposta;
        this.telefon = telefon;
        this.notlar = notlar;
    }

    public String getMusteriID() { return musteriID; }
    public void setMusteriID(String musteriID) { this.musteriID = musteriID; }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getEposta() { return eposta; }
    public void setEposta(String eposta) { this.eposta = eposta; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getNotlar() { return notlar; }
    public void setNotlar(String notlar) { this.notlar = notlar; }

    @Override
    public String toString() {
        return adSoyad;
    }
}