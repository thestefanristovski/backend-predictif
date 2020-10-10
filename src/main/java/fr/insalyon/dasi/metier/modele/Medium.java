package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author zss
 */
@Entity
public abstract class Medium implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdMedium;
    private String denomination;
    private Genre genre;
    private String presentation;
    private int nbConsultations;

    protected Medium() {
    }

    public Medium(String denomination, Genre genre, String presentation) {
        this.denomination = denomination;
        this.genre = genre;
        this.presentation = presentation;
        this.nbConsultations = 0;
    }

    public int getNbConsultations() {
        return nbConsultations;
    }

    public void setNbConsultations(int nbConsultations) {
        this.nbConsultations = nbConsultations;
    }

    public Long getIdMedium() {
        return IdMedium;
    }

    public String getDenomination() {
        return denomination;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    @Override
    public String toString() {
        return "Medium{" + "IdMedium=" + IdMedium + ", denomination=" + denomination + ", genre=" + genre + ", presentation=" + presentation + ", nbConsultations=" + nbConsultations + '}';
    }

}
