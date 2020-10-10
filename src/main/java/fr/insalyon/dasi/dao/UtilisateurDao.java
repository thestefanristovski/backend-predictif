/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Utilisateur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author zss
 */
public class UtilisateurDao {

    public void creer(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(utilisateur);
    }

    public void supprimer(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.remove(utilisateur);
    }

    public void modifier(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.merge(utilisateur);
    }

    // attention au retour il faut tester le instance of
    public Utilisateur chercherParMail(String utilisateurMail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.adresseElectronique = :mail", Utilisateur.class);
        query.setParameter("mail", utilisateurMail); // correspond au paramètre ":mail" dans la requête
        List<Utilisateur> utilisateurs = query.getResultList();
        Utilisateur result = null;
        if (!utilisateurs.isEmpty()) {
            result = utilisateurs.get(0); // premier de la liste
        }
        return result;
    }

}
