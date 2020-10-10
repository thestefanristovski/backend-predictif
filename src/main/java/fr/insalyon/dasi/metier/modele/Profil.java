/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author zss
 */
@Embeddable
public class Profil implements Serializable {

    private String signeZodiac;
    private String signeChinois;
    private String couleur;
    private String animalTotem;

    public Profil() {
    }

    public Profil(String signeZodiac, String signeChinois, String couleur, String animalTotem) {
        this.signeZodiac = signeZodiac;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }

    public String getSigneZodiac() {
        return signeZodiac;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }

    public void setSigneZodiac(String signeZodiac) {
        this.signeZodiac = signeZodiac;
    }

    public void setSigneChinois(String signeChinois) {
        this.signeChinois = signeChinois;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setAnimalTotem(String animalTotem) {
        this.animalTotem = animalTotem;
    }

    @Override
    public String toString() {
        return "Profil{" + "signeZodiac=" + signeZodiac + ", signeChinois=" + signeChinois + ", couleur=" + couleur + ", animalTotem=" + animalTotem + '}';
    }

}
