package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zss
 */
public class MediumDao {

    public void creer(Medium m) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(m);
    }

    public void modifier(Medium m) {
        JpaUtil.obtenirContextePersistance().merge(m);
    }

    public Medium chercherParId(Long MediumId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Medium.class, MediumId); // renvoie null si l'identifiant n'existe pas
    }

    public void supprimer(Medium m) {
        JpaUtil.obtenirContextePersistance().remove(m);
    }

    public List<Medium> listerMediums() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Query q = em.createQuery("select m from Medium m order by m.denomination");
        List<Medium> Lm = (List<Medium>) q.getResultList();
        return Lm;
    }

}
