/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author zss
 */
@Entity
public abstract class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeNaissance;
    private String adressePostale;
    @Column(unique = true)
    private String adresseElectronique;
    private String numeroTelephone;
    private String motDePasse;
    private final Genre genre;

    public Utilisateur() {
        genre = Genre.AUTRE;
    }

    public Utilisateur(String nom, String prenom, Date dateDeNaissance, String adressePostale, String adresseElectronique, String numeroTelephone, String motDePasse, Genre genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adressePostale = adressePostale;
        this.adresseElectronique = adresseElectronique;
        this.numeroTelephone = numeroTelephone;
        this.motDePasse = motDePasse;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public String getAdresseElectronique() {
        return adresseElectronique;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public void setAdresseElectronique(String adresseElectronique) {
        this.adresseElectronique = adresseElectronique;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateDeNaissance=" + dateDeNaissance + ", adressePostale=" + adressePostale + ", adresseElectronique=" + adresseElectronique + ", numeroTelephone=" + numeroTelephone + ", motDePasse=" + motDePasse + '}';
    }

}
