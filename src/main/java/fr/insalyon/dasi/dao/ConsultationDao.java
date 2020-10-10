package fr.insalyon.dasi.dao;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.EnumModele.statutConsultation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author zss
 */
public class ConsultationDao {

    public void creer(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(consultation);
    }

    public void supprimer(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.remove(consultation);
    }

    public void miseAJourConsultation(Consultation consultation) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.merge(consultation);
    }

    public Consultation chercherParId(Long consultationId) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        return em.find(Consultation.class, consultationId); // renvoie null si l'identifiant n'existe pas
    }

    public List<Consultation> listerConsultationsParClient(Client client) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.client = :client ORDER BY c.dateDemande DESC", Consultation.class);
        query.setParameter("client", client);
        return query.getResultList();
    }

    public List<Consultation> listerConsultationsParEmploye(Employe emp) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query;
        query = em.createQuery("SELECT c FROM Consultation c WHERE c.employe = :emp ORDER BY c.dateDemande DESC", Consultation.class);
        query.setParameter("emp", emp);
        return query.getResultList();
    }

    public List<Consultation> listerConsultationsParMedium(Medium med) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.medium = :med ORDER BY c.dateDemande DESC", Consultation.class);
        query.setParameter("med", med);
        return query.getResultList();
    }

    public int nbConsultationParMedium(Medium med) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Query query = em.createQuery("SELECT count(c) FROM Consultation c WHERE c.medium = :med");
        query.setParameter("med", med); // correspond au paramètre ":mail" dans la requête
        int nb = (int) query.getSingleResult();
        return nb;
    }

    public int nbConsultationParEmploye(Employe emp) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Query query = em.createQuery("SELECT count(c) FROM Consultation c WHERE c.employe = :emp");
        query.setParameter("emp", emp); // correspond au paramètre ":mail" dans la requête
        int nb = (int) query.getSingleResult();
        return nb;
    }

    public List<Consultation> listerConsultationsParDateDebut(Date date) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.dateDemande = :date", Consultation.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public List<Consultation> listerConsultations() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c ORDER BY c.dateDemande DESC", Consultation.class);
        return query.getResultList();
    }

public Consultation obtenirProchaineConsultation(Employe emp) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query;
        query = em.createQuery("SELECT c FROM Consultation c WHERE c.employe = :emp ORDER BY c.dateDemande DESC", Consultation.class);
        query.setParameter("emp", emp);
        List<Consultation> listeConsultations = query.getResultList();
        Consultation result = null;
        if (!listeConsultations.isEmpty()) {
            for (int i = 0; i < listeConsultations.size(); i++) {
                result = listeConsultations.get(i);
                if (result.getStatusConsultation() == statutConsultation.DEMANDEE) {
                    break;
                } else {
                    result = null;
                }
            }
            System.out.println(result.toString());
            
        } else {
            System.out.println("RIEN TROUVE");
        }
        return result;
    }
}
