/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author zss
 */
public class ClientDao {

    public List<Client> listerClients() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c ORDER BY c.nom ASC, c.prenom ASC", Client.class);
        return query.getResultList();
    }

    public Client chercherClientParId(Long ClientId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Utilisateur resultat = em.find(Utilisateur.class, ClientId);
        if (resultat instanceof Client) {
            return (Client) resultat; // renvoie null si l'identifiant n'existe pas
        } else {
            return null;
        }
    }

    public Client chercherClientParMail(String mailClient) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.adresseElectronique = :mail", Client.class);
        query.setParameter("mail", mailClient); // correspond au paramètre ":mail" dans la requête
        List<Client> clients = query.getResultList();
        Client result = null;
        if (!clients.isEmpty()) {
            result = clients.get(0); // premier de la liste
        }
        return result;
    }

}
