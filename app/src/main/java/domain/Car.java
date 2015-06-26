package domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Haydar on 29-05-15.
 */
public class Car implements Serializable {
    private long id;


    private String Eerstekleur;

    private String Wachtopkeuren;

    private String Maximumtetrekkenmassamiddenasgeremd;

    private String Catalogusprijs;

    private String Maximaleconstructiesnelheid;

    private String Vermogenbromsnorfiets;

    private String Milieuclassificatie;

    private String Handelsbenaming;

    private String Toegestanemaximummassavoertuig;

    private String Brandstofverbruikgecombineerd;

    private String Hoofdbrandstof;

    private String Voertuigsoort;

    private String WAMverzekerdgeregistreerd;

    private String Nevenbrandstof;

    private String Vermogen;

    private String Merk;

    private String Aantalstaanplaatsen;

    private String Massaleegvoertuig;

    private Date DatumeersteafgifteNederland;

    private String Kenteken;

    private String Retrofitroetfilter;

    private String CO2uitstootgecombineerd;

    private String Maximumtetrekkenmassaopleggergeremd;

    private Date VervaldatumAPK;

    private String Maximumtetrekkenmassageremd;

    private String Laadvermogen;

    private String Zuinigheidslabel;

    private String Cilinderinhoud;

    private String Maximumtetrekkenmassaautonoomgeremd;

    private String Aantalcilinders;

    private String Inrichting;

    private String Aantalzitplaatsen;

    private Date Datumeerstetoelating;

    private String Maximumtetrekkenmassaongeremd;

    private String Brandstofverbruikstad;

    private String Brandstofverbruikbuitenweg;

    private String Tweedekleur;

    private String BPM;

    private Date Datumaanvangtenaamstelling;

    private String G3installatie;

    private String Massarijklaar;

    private String imageUrl;

    private CarMeta __metadata;

    public Car(String Kenteken, String Merk, String imageUrl) {
        this.Kenteken = Kenteken;
        this.Merk = Merk;
        this.imageUrl = imageUrl;

    }

    public Car() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEerstekleur() {
        return Eerstekleur;
    }

    public void setEerstekleur(String Eerstekleur) {
        this.Eerstekleur = Eerstekleur;
    }

    public String getWachtopkeuren() {
        return Wachtopkeuren;
    }

    public void setWachtopkeuren(String Wachtopkeuren) {
        this.Wachtopkeuren = Wachtopkeuren;
    }

    public String getMaximumtetrekkenmassamiddenasgeremd() {
        return Maximumtetrekkenmassamiddenasgeremd;
    }

    public void setMaximumtetrekkenmassamiddenasgeremd(String Maximumtetrekkenmassamiddenasgeremd) {
        this.Maximumtetrekkenmassamiddenasgeremd = Maximumtetrekkenmassamiddenasgeremd;
    }

    public String getCatalogusprijs() {
        return Catalogusprijs;
    }

    public void setCatalogusprijs(String Catalogusprijs) {
        this.Catalogusprijs = Catalogusprijs;
    }

    public String getMaximaleconstructiesnelheid() {
        return Maximaleconstructiesnelheid;
    }

    public void setMaximaleconstructiesnelheid(String Maximaleconstructiesnelheid) {
        this.Maximaleconstructiesnelheid = Maximaleconstructiesnelheid;
    }

    public String getVermogenbromsnorfiets() {
        return Vermogenbromsnorfiets;
    }

    public void setVermogenbromsnorfiets(String Vermogenbromsnorfiets) {
        this.Vermogenbromsnorfiets = Vermogenbromsnorfiets;
    }

    public String getMilieuclassificatie() {
        return Milieuclassificatie;
    }

    public void setMilieuclassificatie(String Milieuclassificatie) {
        this.Milieuclassificatie = Milieuclassificatie;
    }

    public String getHandelsbenaming() {
        return Handelsbenaming;
    }

    public void setHandelsbenaming(String Handelsbenaming) {
        this.Handelsbenaming = Handelsbenaming;
    }

    public String getToegestanemaximummassavoertuig() {
        return Toegestanemaximummassavoertuig;
    }

    public void setToegestanemaximummassavoertuig(String Toegestanemaximummassavoertuig) {
        this.Toegestanemaximummassavoertuig = Toegestanemaximummassavoertuig;
    }

    public String getBrandstofverbruikgecombineerd() {
        return Brandstofverbruikgecombineerd;
    }

    public void setBrandstofverbruikgecombineerd(String Brandstofverbruikgecombineerd) {
        this.Brandstofverbruikgecombineerd = Brandstofverbruikgecombineerd;
    }

    public String getHoofdbrandstof() {
        return Hoofdbrandstof;
    }

    public void setHoofdbrandstof(String Hoofdbrandstof) {
        this.Hoofdbrandstof = Hoofdbrandstof;
    }

    public String getVoertuigsoort() {
        return Voertuigsoort;
    }

    public void setVoertuigsoort(String Voertuigsoort) {
        this.Voertuigsoort = Voertuigsoort;
    }

    public String getWAMverzekerdgeregistreerd() {
        return WAMverzekerdgeregistreerd;
    }

    public void setWAMverzekerdgeregistreerd(String WAMverzekerdgeregistreerd) {
        this.WAMverzekerdgeregistreerd = WAMverzekerdgeregistreerd;
    }

    public String getNevenbrandstof() {
        return Nevenbrandstof;
    }

    public void setNevenbrandstof(String Nevenbrandstof) {
        this.Nevenbrandstof = Nevenbrandstof;
    }

    public String getVermogen() {
        if (Vermogen != null) {
            return Vermogen + " kW";
        } else {
            return "";
        }

    }

    public void setVermogen(String Vermogen) {
        this.Vermogen = Vermogen;
    }

    public String getMerk() {
        return Merk;
    }

    public void setMerk(String Merk) {
        this.Merk = Merk;
    }

    public String getAantalstaanplaatsen() {
        return Aantalstaanplaatsen;
    }

    public void setAantalstaanplaatsen(String Aantalstaanplaatsen) {
        this.Aantalstaanplaatsen = Aantalstaanplaatsen;
    }

    public String getMassaleegvoertuig() {
        if (Massaleegvoertuig != null) {
            return Massaleegvoertuig + " kg";
        } else {
            return "";
        }
    }

    public void setMassaleegvoertuig(String Massaleegvoertuig) {
        this.Massaleegvoertuig = Massaleegvoertuig;
    }

    public Date getDatumeersteafgifteNederland() {
        return DatumeersteafgifteNederland;
    }

    public void setDatumeersteafgifteNederland(Date DatumeersteafgifteNederland) {
        this.DatumeersteafgifteNederland = DatumeersteafgifteNederland;
    }

    public String getKenteken() {
        return Kenteken;
    }

    public void setKenteken(String Kenteken) {
        this.Kenteken = Kenteken;
    }

    public String getRetrofitroetfilter() {
        return Retrofitroetfilter;
    }

    public void setRetrofitroetfilter(String Retrofitroetfilter) {
        this.Retrofitroetfilter = Retrofitroetfilter;
    }

    public String getCO2uitstootgecombineerd() {
        return CO2uitstootgecombineerd;
    }

    public void setCO2uitstootgecombineerd(String CO2uitstootgecombineerd) {
        this.CO2uitstootgecombineerd = CO2uitstootgecombineerd;
    }

    public String getMaximumtetrekkenmassaopleggergeremd() {
        return Maximumtetrekkenmassaopleggergeremd;
    }

    public void setMaximumtetrekkenmassaopleggergeremd(String Maximumtetrekkenmassaopleggergeremd) {
        this.Maximumtetrekkenmassaopleggergeremd = Maximumtetrekkenmassaopleggergeremd;
    }

    public Date getVervaldatumAPK() {
        return VervaldatumAPK;
    }

    public void setVervaldatumAPK(Date VervaldatumAPK) {
        this.VervaldatumAPK = VervaldatumAPK;
    }

    public String getMaximumtetrekkenmassageremd() {
        return Maximumtetrekkenmassageremd;
    }

    public void setMaximumtetrekkenmassageremd(String Maximumtetrekkenmassageremd) {
        this.Maximumtetrekkenmassageremd = Maximumtetrekkenmassageremd;
    }

    public String getLaadvermogen() {
        return Laadvermogen;
    }

    public void setLaadvermogen(String Laadvermogen) {
        this.Laadvermogen = Laadvermogen;
    }

    public String getZuinigheidslabel() {
        return Zuinigheidslabel;
    }

    public void setZuinigheidslabel(String Zuinigheidslabel) {
        this.Zuinigheidslabel = Zuinigheidslabel;
    }

    public String getCilinderinhoud() {
        return Cilinderinhoud;
    }

    public void setCilinderinhoud(String Cilinderinhoud) {
        this.Cilinderinhoud = Cilinderinhoud;
    }

    public String getMaximumtetrekkenmassaautonoomgeremd() {
        return Maximumtetrekkenmassaautonoomgeremd;
    }

    public void setMaximumtetrekkenmassaautonoomgeremd(String Maximumtetrekkenmassaautonoomgeremd) {
        this.Maximumtetrekkenmassaautonoomgeremd = Maximumtetrekkenmassaautonoomgeremd;
    }

    public String getAantalcilinders() {
        return Aantalcilinders;
    }

    public void setAantalcilinders(String Aantalcilinders) {
        this.Aantalcilinders = Aantalcilinders;
    }

    public String getInrichting() {
        return Inrichting;
    }

    public void setInrichting(String Inrichting) {
        this.Inrichting = Inrichting;
    }

    public String getAantalzitplaatsen() {
        return Aantalzitplaatsen;
    }

    public void setAantalzitplaatsen(String Aantalzitplaatsen) {
        this.Aantalzitplaatsen = Aantalzitplaatsen;
    }

    public Date getDatumeerstetoelating() {
        return Datumeerstetoelating;
    }

    public void setDatumeerstetoelating(Date Datumeerstetoelating) {
        this.Datumeerstetoelating = Datumeerstetoelating;
    }

    public String getMaximumtetrekkenmassaongeremd() {
        return Maximumtetrekkenmassaongeremd;
    }

    public void setMaximumtetrekkenmassaongeremd(String Maximumtetrekkenmassaongeremd) {
        this.Maximumtetrekkenmassaongeremd = Maximumtetrekkenmassaongeremd;
    }

    public String getBrandstofverbruikstad() {
        return Brandstofverbruikstad;
    }

    public void setBrandstofverbruikstad(String Brandstofverbruikstad) {
        this.Brandstofverbruikstad = Brandstofverbruikstad;
    }

    public String getBrandstofverbruikbuitenweg() {
        return Brandstofverbruikbuitenweg;
    }

    public void setBrandstofverbruikbuitenweg(String Brandstofverbruikbuitenweg) {
        this.Brandstofverbruikbuitenweg = Brandstofverbruikbuitenweg;
    }

    public String getTweedekleur() {
        return Tweedekleur;
    }

    public void setTweedekleur(String Tweedekleur) {
        this.Tweedekleur = Tweedekleur;
    }

    public String getBPM() {
        if (BPM != null) {
            return "€ " + BPM;
        } else {
            return "";
        }
    }

    public void setBPM(String BPM) {
        this.BPM = BPM;
    }

    public Date getDatumaanvangtenaamstelling() {
        return Datumaanvangtenaamstelling;
    }

    public void setDatumaanvangtenaamstelling(Date Datumaanvangtenaamstelling) {
        this.Datumaanvangtenaamstelling = Datumaanvangtenaamstelling;
    }

    public String getG3installatie() {
        return G3installatie;
    }

    public void setG3installatie(String G3installatie) {
        this.G3installatie = G3installatie;
    }

    public String getMassarijklaar() {
        if (Massarijklaar != null) {
            return Massarijklaar + " kg";
        } else {
            return "";
        }
    }

    public void setMassarijklaar(String Massarijklaar) {
        this.Massarijklaar = Massarijklaar;
    }

    public CarMeta get__metadata() {
        return __metadata;
    }

    public void set__metadata(CarMeta __metadata) {
        this.__metadata = __metadata;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Kenteken: " + getKenteken() + "\nMerk: " + getMerk();
    }


}
