package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 *
 *
 * @author zss
 */
@Entity
public class Astrologue extends Medium implements Serializable {

    private String promotion;
    private String formation;

    protected Astrologue() {
    }

    public Astrologue(String promotion, String formation, String denomination, Genre genre, String presentation) {
        super(denomination, genre, presentation);
        this.promotion = promotion;
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    @Override
    public String toString() {
        return "Astrologue{" + "promotion=" + promotion + ", formation=" + formation + '}' + super.toString();
    }

}
