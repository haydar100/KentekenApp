package com.example.berkan.kentekenapp;

/**
 * Created by Haydar on 28-05-15.
 */
public class Auto {
    public String kenteken;
    public String merk;
    public String imageUrl;

    public Auto(String kenteken, String merk, String imageUrl) {
        this.kenteken = kenteken;
        this.merk = merk;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "kenteken='" + kenteken + '\'' +
                ", merk='" + merk + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }
}
