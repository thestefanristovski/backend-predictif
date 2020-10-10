package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.ConsultationDao;
import fr.insalyon.dasi.dao.EmployeDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.dao.MediumDao;
import fr.insalyon.dasi.dao.UtilisateurDao;
import fr.insalyon.dasi.metier.modele.Astrologue;
import fr.insalyon.dasi.metier.modele.Cartomancier;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import static fr.insalyon.dasi.metier.modele.EnumModele.Genre.HOMME;
import fr.insalyon.dasi.metier.modele.EnumModele.statutConsultation;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Profil;
import fr.insalyon.dasi.metier.modele.Spirite;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.util.AstroTest;
import fr.insalyon.dasi.util.Message;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.Date;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DASI Team
 */
public class Service {

    
 //////////////////////////SERVICE UTILISATEUR////////////////////////////////////////////////   
    protected UtilisateurDao utilisateurDao = new UtilisateurDao();

    public Long inscrireUtilisateur(Utilisateur utilisateur) throws IOException {
        Long resultat = null;
        JpaUtil.creerContextePersistance(); // creation de l'entity manager entityManagerFactory.createEntityManager(),
        // la création de la factory est faite dans le main
        try {
            JpaUtil.ouvrirTransaction(); // em.getTransaction().begin();
            utilisateurDao.creer(utilisateur);
            JpaUtil.validerTransaction(); // em.getTransaction().commit();
            resultat = utilisateur.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireUtilisateur(utilisateur)", ex);
            JpaUtil.annulerTransaction(); //  em.getTransaction().rollback();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();// em.close();
        } 
        
        
        if (utilisateur instanceof Client)
        {
            Client nouveauClient = (Client) utilisateur;
            String prenomClient = nouveauClient.getPrenom();
            String mailClient = nouveauClient.getAdresseElectronique();
            Genre genreClient = nouveauClient.getGenre();
            Date dateDeNaissanceClient= nouveauClient.getDateDeNaissance();
            String contactPred = "contact@predictif.fr";
          
            if (resultat !=null)
            {
                AstroTest astroTest = new AstroTest();
                List<String> profilc1 = astroTest.getProfil(prenomClient, dateDeNaissanceClient);
                Profil p1 = new Profil(profilc1.get(0), profilc1.get(1), profilc1.get(2), profilc1.get(3));

                nouveauClient.setProfilAstral(p1);
                modifierUtilisateur(nouveauClient);

                String gClient=null;

                if(genreClient == Genre.FEMME)
                {
                    gClient = "Bienvenue";
                }

                if(genreClient == Genre.HOMME)
                {
                    gClient = "Bienvenu";
                }

                if(genreClient == Genre.AUTRE)
                {
                    gClient = "Bienvenu.e";
                }           


                String inscriptionOk =  "Corps : Bonjour " + prenomClient + ",  nous vous confirmons votre inscription au service PREDICT’IF."
                        + "Rendezvous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums \n";
                String objet = gClient +  "chez PREDICT'IF";
                Message.envoyerMail(contactPred, mailClient, objet, inscriptionOk);
            } else
            {
                String inscriptionNonOk =  "Corps : Bonjour " + prenomClient + ",   votre inscription au service PREDICT’IF a malencontreusement échoué...\n"
                    + "Merci de recommencer ultérieurement. \n";  
                String objet = "Echec de l'inscription chez PREDICT'IF";
                Message.envoyerMail(contactPred, mailClient, objet, inscriptionNonOk);
            }
        }
        return resultat;
    }

    private Long modifierUtilisateur(Utilisateur utilisateur) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            utilisateurDao.modifier(utilisateur);
            JpaUtil.validerTransaction();
            resultat = utilisateur.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service modifierUtilisateur(utilisateur)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;       
    }
    
        private Long modifierMedium(Medium medium) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            mediumDao.modifier(medium);
            JpaUtil.validerTransaction();
            resultat = medium.getIdMedium();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service modifierMediul(medium)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;       
    }
    
    public boolean supprimerUtilisateur(Utilisateur utilisateur) {
        boolean succesSuppression = false;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            utilisateurDao.supprimer(utilisateur);
            JpaUtil.validerTransaction();
            succesSuppression = true;
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service supprimerUtilisateur(utilisateur)", ex);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return succesSuppression;
    }

    public Utilisateur authentifierUtilisateur(String mail, String motDePasse) {
        Utilisateur resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche du client
            Utilisateur utilisateur = utilisateurDao.chercherParMail(mail);
            if (utilisateur != null) {
                // Vérification du mot de passe
                if (utilisateur.getMotDePasse().equals(motDePasse)) {
                    resultat = utilisateur;
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierUtilisateur(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

 //////////////////////////SERVICE EMPLOYE//////////////////////////////////////////////// 
   protected EmployeDao employeDao = new EmployeDao();
   
   public List<Employe> listerEmployes() {
        List<Employe> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = employeDao.listerEmployes();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerEmployes()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    private Employe obtenirEmployeDisponible(Genre genre) {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche du client
            Employe employe = employeDao.chercherEmployeDisponible(genre);
            resultat = employe;
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service obtenirEmployeDisponible(genre)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe rechercherEmployeParId(Long id) {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = (Employe) employeDao.chercherEmployeParId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherEmployeParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }
    
    public Employe rechercherEmployeParMail(String mail) {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = (Employe) employeDao.chercherEmployeParEmail(mail);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherEmployeParMail(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }
   
  //////////////////////////SERVICE CLIENT//////////////////////////////////////////////// 
    protected ClientDao clientDao = new ClientDao();
    
        public Client rechercherClientParId(Long id) {
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.chercherClientParId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return resultat;
    }

    public List<Client> listerClients() {
        List<Client> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.listerClients();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerClients()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Client rechercherClientParMail(String mail)
    {
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.chercherClientParMail(mail);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParMail(Mail)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
 
    
   //////////////////////////SERVICE MEDIUM//////////////////////////////////////////////// 

    protected MediumDao mediumDao= new MediumDao();
    
    public long InscrireMedium(Medium medium) throws Exception
    {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            mediumDao.creer(medium);
            JpaUtil.validerTransaction();
            resultat = medium.getIdMedium();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireMedium(medium)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Medium rechercherMediumParId(Long id) {
        Medium resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.chercherParId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherMediumParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Medium> listerMediums() 
    {
        List<Medium> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.listerMediums();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerMediumss()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Medium> top5Mediums()
    {
        List<Medium> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.listerMediums();

            ToIntFunction <Medium> getNbConsultations = medium -> medium.getNbConsultations(); // creation de la function pour recup le nombre de consultations d'un medium,
                                                                                                     //ceci est necessaire psq c le type d'argument pour la prochaine fonction
            Comparator <Medium> cmpParNbConsultations = comparingInt(getNbConsultations); //on crée un comparateur qui se sert de la fonction
                                                                                          //pour comparer les medium par leur nb de consultations
            resultat.sort(Collections.reverseOrder(cmpParNbConsultations)); //on trie les elements en utilisant le comparateur
                                                  //!!!!REMARQUE!!!! a tester si l'ordre donné est le bon ou il faut inverser
            resultat = resultat.subList(0, 5); //on ne garde que les  5 premiers elements (a tester aussi) 

        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service top5Mediums()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    //////////////////////////SERVICE CONSULTATION//////////////////////////////////////////////// 
  
    protected ConsultationDao consultationDao = new ConsultationDao();


    public Consultation rechercherConsultationParId(Long id) {
        Consultation resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.chercherParId(id);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public List<Consultation> listerConsultation() {
        List<Consultation> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.listerConsultations();
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Consultation> listerConsultationParClient(Client client) {
        List<Consultation> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.listerConsultationsParClient(client);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Consultation> listerConsultationParEmploye(Employe employe) {
        List<Consultation> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.listerConsultationsParEmploye(employe);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Consultation> listerConsultationParMedium(Medium medium) {
        List<Consultation> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.listerConsultationsParMedium(medium);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Consultation> listerConsultationParDateDebut(Date date) {
        List<Consultation> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.listerConsultationsParDateDebut(date);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public int nbConsultationParEmploye(Employe employe) {
        int resultat;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.nbConsultationParEmploye(employe);
        } catch (Exception ex) {
            resultat = -1;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public int nbConsultationParMedium(Medium med) {
        int resultat;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.nbConsultationParMedium(med);
        } catch (Exception ex) {
            resultat = -1;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Long creerDemande(Client client, Medium medium) {
        Long resultat = null;
        Date dateDemande=new java.util.Date();  
        Consultation consultation = new Consultation(null, "", statutConsultation.DEMANDEE, dateDemande, null);
        consultation.setClient(client);
        consultation.setMedium(medium);
        
        String nomClient = client.getNom();
        String prenomClient = client.getPrenom();
        Genre genreClient = client.getGenre();
        String denomClient = medium.getDenomination();
        Employe employe = obtenirEmployeDisponible(medium.getGenre());
        if(employe==null) {
            System.out.println("On ne peut pas trouver un employe!");
            resultat = null;
        } else {
            Long nbConsultations = employe.getNombreConsultations();
            employe.setNombreConsultations(nbConsultations+1);
            modifierUtilisateur(employe);
            consultation.setEmploye(employe);
            
            int nbConsultationsM = medium.getNbConsultations();
            medium.setNbConsultations(nbConsultationsM+1);
            modifierMedium(medium);
            consultation.setMedium(medium);
        
            JpaUtil.creerContextePersistance();
                try {
                    JpaUtil.ouvrirTransaction();
                    consultationDao.creer(consultation);
                    JpaUtil.validerTransaction();
                    resultat = consultation.getConsultationId();  
                } catch (Exception ex) {
                    JpaUtil.annulerTransaction();
                    resultat = null;
                } finally {
                    JpaUtil.fermerContextePersistance();
                }
        }    
        
        if (resultat != null)
        {
            ///// Envoie de la notif à l'employe
            String nomEmploye = employe.getNom();
            String prenomEmploye = employe.getPrenom();
            String numTelEmploye = employe.getNumeroTelephone();
            String gClient = null;
            if(genreClient == Genre.FEMME)
            {
                gClient = "Mme";
            }

            if(genreClient == Genre.HOMME)
            {
                gClient = "Mr";
            }

            if(genreClient == Genre.AUTRE)
            {
                gClient = "Mx";
            }

            String notifEmploye =  "Pour : " + nomEmploye + " " +  prenomEmploye + " Tel : " + numTelEmploye + "\n"
              + "Message : Bonjour " + prenomEmploye + ". Consultation requise pour " + gClient + " " + nomClient + " " + prenomClient + ". " 
                + "Medium à incarner : " + denomClient + "\n";
             Message.envoyerNotification(numTelEmploye, notifEmploye);
        }
        else 
        {
            String numeroTelClient = client.getNumeroTelephone();
            
            //// envoie d'une notification sur le telephone du client pour l'informer que le medium n'est pas disponible
            String consultationImpossible =  "Pour : " + nomClient + " " +  prenomClient + " Tel : " + numeroTelClient + "\n"
                  + "Message : Bonjour, malheureusement la consultation demandée a été refusée. Notre Medium" 
                    + denomClient + "n'est pas disponible. Veuillez réessayer ultérieurement ou demander une consultationa avec un autre medium.";
            Message.envoyerNotification(numeroTelClient, consultationImpossible);
        }
        

        return resultat;
    }
    
    private final static SimpleDateFormat HORODATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy à HH:mm:ss");
    
    public Long commencerConsultation(Long id) {
        Consultation consultation = rechercherConsultationParId(id);
        // la méthode service rechercherConsultationParId  ferme le contexte de persistance donc pas de concurrence
        consultation.setStatusConsultation(statutConsultation.ACTIF);
        Date dateConsultation=new java.util.Date();
        consultation.setDateConsultation(dateConsultation);
        
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.miseAJourConsultation(consultation);
            JpaUtil.validerTransaction();
            resultat = consultation.getConsultationId();
   
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        if (resultat != null)
        {
            String nomClient= consultation.getClient().getNom();
            String prenomClient =  consultation.getClient().getPrenom();
            String numeroTelClient = consultation.getClient().getNumeroTelephone();
            String denomMedium = consultation.getMedium().getDenomination(); 
            Date dateDemande = consultation.getDateDemande(); 
            String numeroTelEmploye = consultation.getClient().getNumeroTelephone();
            
            
            String consultationDisponible = "Pour : " + nomClient + " " +  prenomClient
                    + " Tel : " + numeroTelClient + "\n"
                    + "Message : Bonjour "+ prenomClient + ". J'ai bien reçu votre demande de consultation le " + HORODATE_FORMAT.format(dateDemande)
                    + " Vous pouvez dès à présent me contacter au " + numeroTelEmploye + ". A tout de suite ! Mediumiquement, " + denomMedium + "\n";
            
            Message.envoyerNotification(numeroTelClient, consultationDisponible);  
        }
        
        
        return resultat;
    }
    
    public Long terminerConsultation(Long id) {
        Consultation consultation = rechercherConsultationParId(id);
        // la méthode service rechercherConsultationParId  ferme le contexte de persistance donc pas de concurrence
        consultation.setStatusConsultation(statutConsultation.TERMINEE);
        Date dateFin=new java.util.Date();  
        consultation.setDateFin(dateFin);
        
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.miseAJourConsultation(consultation);
            JpaUtil.validerTransaction();
            resultat = consultation.getConsultationId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Long ajouterCommentaire(Long id, String commentaire) {
        Consultation consultation = rechercherConsultationParId(id);
        // la méthode service rechercherConsultationParId  ferme le contexte de persistance donc pas de concurrence
        consultation.setCommentaireEmploye(commentaire);
        
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.miseAJourConsultation(consultation);
            JpaUtil.validerTransaction();
            resultat = consultation.getConsultationId();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Consultation obtenirProchaineConsultation(Employe emp) {
        Consultation resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = consultationDao.obtenirProchaineConsultation(emp);
        } catch (Exception ex) {
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<String> genererPrediction(Client client, int amour, int sante, int travail) throws IOException {
        List<String> prediction = null;
        AstroTest astroTest = new AstroTest();
        Profil profilClient = client.getProfilAstral();
        
        prediction =  astroTest.getPredictions(profilClient.getCouleur(), profilClient.getAnimalTotem(), amour, sante, travail);
        
        return prediction;
        
    }
    
    
    
///////////////////////////INITIALISATION -> PEUPLEMENT DE LA BASE//////////////////////////
    
    public boolean peuplementBase() throws IOException
    {
        boolean peuplementReussi = true;
        JpaUtil.creerContextePersistance();
        Employe e1 = new Employe("Fadili", "Zineb", new Date(1999,0,0,4,2), "24 rue INSA", "zineb@insa.fr", "0610203040",  "motDePasse", Genre.FEMME, (long) 0);
        Employe e2 = new Employe("Ristovski", "Stefan", new Date(1998,0,0,5,4), "26 rue INSA", "stefan@insa.fr", "0650607080",  "motDePasse", Genre.HOMME, (long) 0);
        Employe e3 = new Employe("Ouidan", "Saad", new Date(1999,0,0,6,3), "25 rue INSA", "saad@insa.fr", "0630405060",  "motDePasse", Genre.HOMME, (long) 0);
        
        Spirite s1 = new Spirite("Boule de Cristal", "Gwennaëlle", Genre.FEMME, " Spécialiste des grandes conversations au-delà de TOUTES les frontières");
        Astrologue a1 = new Astrologue("2006", "École Normale Supérieure d’Astrologie (ENS-Astro)", "Serena", Genre.HOMME, "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre\n" +
"passé."); 
        Cartomancier c1 = new Cartomancier("Mme Irma", Genre.FEMME, "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Spirite s2 = new Spirite("Marc de café, boule de cristal, oreilles de lapin", "Professeur Tran", Genre.HOMME, "Votre avenir est devant vous : regardons-le ensemble !");
        Astrologue a2 = new Astrologue("2010", "Institut des Nouveaux Savoirs Astrologiques", "Mr M", Genre.HOMME, "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter!"); 
        Cartomancier c2 = new Cartomancier("Endora", Genre.FEMME, "Mes cartes répondront à toutes vos questions personnelles.");
        
        
        Client cl1 = new Client("Kardashian", "Kim", new Date(1989, 9, 3, 0, 0, 0),
                "Beverly Hills", "kim.kardash@kkw.com", "0602040907", "pass1", Genre.FEMME);
        
                AstroTest astroTest = new AstroTest();
                List<String> profilc1 = astroTest.getProfil("Kim", new Date(1989, 9, 3, 0, 0, 0));
                Profil p1 = new Profil(profilc1.get(0), profilc1.get(1), profilc1.get(2), profilc1.get(3));

                cl1.setProfilAstral(p1);
        
        Client cl2 = new Client("Gaga", "Lady", new Date(1995, 8, 4, 0, 0, 0),
                "Los Angeles", "lady.gaga@littlemonsters.com", "0602047856", "pass2", Genre.FEMME);   
        
                        profilc1 = astroTest.getProfil("Lady", new Date(1995, 8, 4, 0, 0, 0));
                p1 = new Profil(profilc1.get(0), profilc1.get(1), profilc1.get(2), profilc1.get(3));

                cl2.setProfilAstral(p1);
        
        Client cl3 = new Client("Scott", "Michael", new Date(1980, 4, 8, 0, 0, 0),
                "Scranton", "ms@dundermifflin.com", "0623451657", "pass3", Genre.HOMME); 
        
                                profilc1 = astroTest.getProfil("Michael", new Date(1980, 4, 8, 0, 0, 0));
                p1 = new Profil(profilc1.get(0), profilc1.get(1), profilc1.get(2), profilc1.get(3));

                cl3.setProfilAstral(p1);
        
        
        Date d = new Date();
        
        try {
            JpaUtil.ouvrirTransaction();
            utilisateurDao.creer(e1);
            utilisateurDao.creer(e2);
            utilisateurDao.creer(e3);
            mediumDao.creer(s1);
            mediumDao.creer(a1);
            mediumDao.creer(c1);
            mediumDao.creer(s2);
            mediumDao.creer(a2);
            mediumDao.creer(c2);
            utilisateurDao.creer(cl1);
            utilisateurDao.creer(cl2);
            utilisateurDao.creer(cl3);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service peuplementBase", ex);
            JpaUtil.annulerTransaction();
            peuplementReussi = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        
        return peuplementReussi;
    }

}