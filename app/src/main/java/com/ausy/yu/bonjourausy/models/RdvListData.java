package com.ausy.yu.bonjourausy.models;

/**
 * Created by yukaiwen on 18/04/2017.
 */

public class RdvListData {

    private int RDVId;
    private String rdvNom;
    private String rdvPrenom;
    private String contactNom;
    private String contactPrenom;
    private String TelMobile;
    private String Libelle;
    private String HeurePrevu;
    private String HeurePriseEnCharge;
    private boolean isValide;
    private boolean isRelance1;

    public String getRdvNom() {
        return rdvNom;
    }

    public void setRdvNom(String rdvNom) {
        this.rdvNom = rdvNom;
    }

    public String getRdvPrenom() {
        return rdvPrenom;
    }

    public void setRdvPrenom(String rdvPrenom) {
        this.rdvPrenom = rdvPrenom;
    }

    public String getContactNom() {
        return contactNom;
    }

    public void setContactNom(String contactNom) {
        this.contactNom = contactNom;
    }

    public String getContactPrenom() {
        return contactPrenom;
    }

    public void setContactPrenom(String contactPrenom) {
        this.contactPrenom = contactPrenom;
    }

    public String getTelMobile() {
        return TelMobile;
    }

    public void setTelMobile(String telMobile) {
        TelMobile = telMobile;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getHeurePrevu() {
        return HeurePrevu;
    }

    public void setHeurePrevu(String heurePrevu) {
        HeurePrevu = heurePrevu;
    }

    public String getHeurePriseEnCharge() {
        return HeurePriseEnCharge;
    }

    public void setHeurePriseEnCharge(String heurePriseEnCharge) {
        HeurePriseEnCharge = heurePriseEnCharge;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean valide) {
        isValide = valide;
    }

    public boolean isRelance1() {
        return isRelance1;
    }

    public void setRelance1(boolean relance1) {
        isRelance1 = relance1;
    }

    public int getRDVId() {
        return RDVId;
    }

    public void setRDVId(int RDVId) {
        this.RDVId = RDVId;
    }
}
