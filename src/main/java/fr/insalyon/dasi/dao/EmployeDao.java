/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author zss
 */
public class EmployeDao {

    public Employe chercherEmployeParId(Long EmployeId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Employe.class, EmployeId); // renvoie null si l'identifiant n'existe pas
    }

    public Employe chercherEmployeParEmail(String email) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.adresseElectronique = :email", Employe.class);
        query.setParameter("email", email);
        List<Employe> employes = query.getResultList();
        Employe result = null;
        if (!employes.isEmpty()) {
            result = employes.get(0); // premier de la liste
        }
        return result;
    }

    public List<Employe> listerEmployes() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e ORDER BY e.nom ASC, e.prenom ASC", Employe.class);
        return query.getResultList();
    }

    public Employe chercherEmployeDisponible(Genre genre) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.genre = :genre and e.disponible = true order by e.nombreConsultations ASC", Employe.class);
        query.setParameter("genre", genre); // correspond au paramètre ":mail" dans la requête
        List<Employe> employesDisponibles = query.getResultList();
        Employe result = null;
        if (!employesDisponibles.isEmpty()) {
            result = employesDisponibles.get(0); // premier de la liste
        }
        return result;
    }

}
