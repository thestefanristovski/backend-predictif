package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author zss
 */
@Entity
public class Cartomancier extends Medium implements Serializable {

    public Cartomancier() {
    }

    public Cartomancier(String denomination, Genre genre, String presentation) {
        super(denomination, genre, presentation);
    }

    @Override
    public String toString() {
        return "Cartomancier{" + '}' + super.toString();
    }

}
