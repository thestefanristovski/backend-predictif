/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.metier.modele;

import fr.insalyon.dasi.metier.modele.EnumModele.statutConsultation;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author zss
 */
@Entity
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateConsultation;
    private String commentaireEmploye;
    private statutConsultation statusConsultation;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDemande;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFin;
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Medium medium;

    protected Consultation() {
    }

    public Consultation(Date dateConsultation, String commentaireEmploye, statutConsultation statusConsultation, Date dateDemande, Date dateFin) {
        this.dateConsultation = dateConsultation;
        this.commentaireEmploye = commentaireEmploye;
        this.statusConsultation = statusConsultation;
        this.dateDemande = dateDemande;
        this.dateFin = dateFin;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public String getCommentaireEmploye() {
        return commentaireEmploye;
    }

    public statutConsultation getStatusConsultation() {
        return statusConsultation;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public void setCommentaireEmploye(String commentaireEmploye) {
        this.commentaireEmploye = commentaireEmploye;
    }

    public void setStatusConsultation(statutConsultation statusConsultation) {
        this.statusConsultation = statusConsultation;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Client getClient() {
        return client;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Consultation{" + "consultationId=" + consultationId + ", dateConsultation=" + dateConsultation + ", commentaireEmploye=" + commentaireEmploye + ", statusConsultation=" + statusConsultation + ", dateDemande=" + dateDemande + ", dateFin=" + dateFin + ", employe=" + employe + ", client=" + client + ", medium=" + medium + '}';
    }

}
