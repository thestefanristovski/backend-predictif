/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author zss
 */
@Entity
public class Spirite extends Medium implements Serializable {

    private String support;

    public Spirite() {
    }

    public Spirite(String support, String denomination, Genre genre, String presentation) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "Spirite{" + "support=" + support + '}' + super.toString();
    }

}
