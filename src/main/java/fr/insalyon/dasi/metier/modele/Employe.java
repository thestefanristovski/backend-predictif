/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author zss
 */
@Entity
public class Employe extends Utilisateur implements Serializable {

    private Boolean disponible;
    private Long nombreConsultations;

    protected Employe() {
    }

    public Employe(String nom, String prenom, Date dateDeNaissance, String adressePostale, String adresseElectronique, String numeroTelephone, String motDePasse, Genre genre, Long nombreConsultations) {
        super(nom, prenom, dateDeNaissance, adressePostale, adresseElectronique, numeroTelephone, motDePasse, genre);
        this.nombreConsultations = nombreConsultations;
        disponible = true;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public Long getNombreConsultations() {
        return nombreConsultations;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void setNombreConsultations(Long nombreConsultations) {
        this.nombreConsultations = nombreConsultations;
    }

    @Override
    public String toString() {
        return "Employe{" + "disponible=" + disponible + ", nombreConsultations=" + nombreConsultations + '}' + super.toString();
    }

}
