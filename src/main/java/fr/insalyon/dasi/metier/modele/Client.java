package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Embedded;

/**
 *
 * @author zss
 */
@Entity
public class Client extends Utilisateur implements Serializable {

    @Embedded
    Profil profilAstral;

    protected Client() {
    }

    public Client(String nom, String prenom, Date dateDeNaissance, String adressePostale, String adresseElectronique, String numeroTelephone, String motDePasse, Genre genre) {
        super(nom, prenom, dateDeNaissance, adressePostale, adresseElectronique, numeroTelephone, motDePasse, genre);
        //this.profilAstral = profilAstral;
    }

    public Profil getProfilAstral() {
        return profilAstral;
    }

    public void setProfilAstral(Profil profilAstral) {
        this.profilAstral = profilAstral;
    }

    @Override
    public String toString() {
        return "Client{" + "profilAstral=" + profilAstral + '}' + super.toString();
    }

}
