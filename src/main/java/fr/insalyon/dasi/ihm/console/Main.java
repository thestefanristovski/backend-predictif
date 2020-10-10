package fr.insalyon.dasi.ihm.console;

import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.EnumModele.Genre;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.metier.service.Service;
import fr.insalyon.dasi.util.AstroTest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DASI Team
 */
public class Main {

    public static void main(String[] args) throws IOException {

        JpaUtil.init();

        /////////////----test initialisation de la base------------////////
        //attention il faut peupler la base pour que les tests fonctionnent
        testerInitialisationBase();

        ////////-----------------tests liées aux client---------------- /////////
     /*    testerCreerClient();
       testerListerClients();
       testerRechercheClientParMail();
       testerRechercheClientParId();
       testerListeConsultationClient() ;
       testerInscriptionClientAvecMailExistant(); 
        
        
        //////////////--------------tests liés aux employes---------------//
       testerCreerEmploye(); 
         testerListerEmployes();
         testerRechercheEmployeParMail();
         testerRechercheEmployeParId();
         testerListeConsultationEmploye();
         testerInscriptionEmployeAvecMailExistant();
        
        
        ////////-----------------tests liées aux utilisateur---------------- /////////
         testAuthentifierUtilisateur(); 
        
        
        ////////------------ tests liés aux consultations -------------/////
        testerCreationDemande();
       testerProchaineConsultation();
       testerCommencerConsultation();
       testerTerminerConsultation(); 
       testerLaisserCommentaire();
       testerGenererPrediction();
        
        
        ///////---------------tests liées aux medium ----------------/////
        testerListerMediums();
       testerRechercheMediumParId();
       testerNbConsultationsParMedium();
        
        
        /////////------ tests liés à l'agence------------------/////
        testerTop5Medium();*/
        JpaUtil.destroy();
    }

    ///méthode d'affichage de l'utilisateur
    public static void afficherUtilisateur(Utilisateur utilisateur) {
        System.out.println("-> " + utilisateur);
    }

    //méthode d'afficahge du medium
    public static void afficherMedium(Medium medium) {
        System.out.println("-> " + medium);
    }

    //méthode d'affichage de la consultation
    public static void afficherConsultation(Consultation consultation) {
        System.out.println("-> " + consultation);
    }

/////////////////////////PEUPLEMENT INITIAL DE LA BASE//////////////////////////////////
    //-> creation de medium et d'employes 
     public static void testerInitialisationBase() throws IOException {
        
        System.out.println();
        System.out.println("**** testerRechercheClient() ****");
        System.out.println();
        
        Service service = new Service();
        boolean initialisationReussi = false;

        initialisationReussi = service.peuplementBase();


    }
////////////////////////////////////////////////////////////////////////////////////////
    
    
    //////////////////////TEST CLIENT//////////////////////////////
    ///// teste d'inscription client
    public static void testerCreerClient() throws IOException 
    {
        System.out.println();
        System.out.println("---------Test inscription client-------------");
        System.out.println();

        Service s = new Service();

        Client c1 = new Client("ouidan", "saad", new Date(1999, 9, 3, 0, 0, 0),
                "20 insa", "s.ouidan@insa-lyon.fr", "0602040907", "pass1", Genre.HOMME);

        Client c2 = new Client("ristovski", "stefan", new Date(1998, 11, 6, 0, 0, 0),
                "20AE", "s.ristovski@insa-lyon.fr", "0604050703", "pass2", Genre.HOMME);

        Client c3 = new Client("fadili", "zineb", new Date(1999, 4, 2, 0, 0, 0),
                "20AE", "z.fadili@insa-lyon.fr", "0604080607", "pass3", Genre.FEMME);

        Long id1 = s.inscrireUtilisateur(c1);
        if (id1 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(c1);

        Long id2 = s.inscrireUtilisateur(c2);
        if (id2 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(c2);

        Long id3 = s.inscrireUtilisateur(c3);
        if (id3 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(c3);

    }

    ///// test liste de tous les clients
    public static void testerListerClients() { 
        System.out.println();
        System.out.println("---------Test Lister tous les clients-------------");
        System.out.println();
        Service s = new Service();
        List<Client> clients = s.listerClients();
        if (!clients.isEmpty()) {
            clients.forEach((client) -> {
                afficherUtilisateur(client);
            });
        } else {
            System.out.println("Aucun client trouvé");
        }
    }

    ///// test chercher client par mail
    public static void testerRechercheClientParMail() { 
        System.out.println();
        System.out.println("---------Test Rechercher client par Email-------------");
        System.out.println();
        Service s = new Service();
        Client client = s.rechercherClientParMail("a.ouidan@insa-lyon.fr");
        if (client != null) {
            System.out.println("client trouvé :");
            afficherUtilisateur(client);
        } else {
            System.out.println("client non trouvé");
        }
    }

    ////// test chercher client par id
    public static void testerRechercheClientParId() 
    {
        System.out.println();
        System.out.println("---------Test Rechercher client par Id-------------");
        System.out.println();
        Service s = new Service();
        Client client = s.rechercherClientParId((long) 5);
        if (client != null) {
            System.out.println("client trouvé :");
            afficherUtilisateur(client);
        } else {
            System.out.println("client non trouvé");
        }
    }

    /// test inscrire client avec mail déjà existant 
    public static void testerInscriptionClientAvecMailExistant() throws IOException 
    {
        System.out.println();
        System.out.println("---------testerInscriptionClientAvecMailExistant-------------");
        System.out.println();

        Service s = new Service();
        AstroTest astroTest = new AstroTest();

        Client c1 = new Client("kardashian", "kim", new Date(1990, 9, 3, 0, 0, 0),
                "LA", "k.kardashian@insa-lyon.fr", "0602040907", "pass1", Genre.FEMME);

        Client c2 = new Client("kardashian", "kourtney", new Date(1989, 11, 6, 0, 0, 0),
                "LA", "k.kardashian@insa-lyon.fr", "0604050703", "pass2", Genre.FEMME);

        Long id1 = s.inscrireUtilisateur(c1);
        if (id1 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(c1);

        Long id2 = s.inscrireUtilisateur(c2);
        if (id2 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(c2);
    }

    
    //////////////////////////////////////////////////////////////////////////
    
    
//////////////////TEST EMPLOYE////////////////
    ////// test inscription employe
    public static void testerCreerEmploye() throws IOException 
    {
        System.out.println();
        System.out.println("---------Test inscription Employe-------------");
        System.out.println();

        Service s = new Service();

        Employe e1 = new Employe("fa", "zi", new Date(1991, 0, 0, 4, 2), "alinsa", "zinebfad@insa.fr", "065", "motDePasse", Genre.FEMME, (long) 0);
        Employe e2 = new Employe("ri", "ste", new Date(1991, 0, 0, 4, 2), "alinsa", "stefanrist@insa.fr", "064", "motDePasse", Genre.HOMME, (long) 5);
        Employe e3 = new Employe("oui", "sa", new Date(1991, 0, 0, 4, 2), "alinsa", "saadoui@insa.fr", "063", "motDePasse", Genre.HOMME, (long) 4);

        Long id1 = s.inscrireUtilisateur(e1);
        if (id1 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(e1);

        Long id2 = s.inscrireUtilisateur(e2);
        if (id2 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(e2);

        Long id3 = s.inscrireUtilisateur(e3);
        if (id3 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(e3);

    }

    ///// test lister tous les clients
    public static void testerListerEmployes() {
        System.out.println();
        System.out.println("---------Test Lister tous les employes-------------");
        System.out.println();
        Service s = new Service();
        List<Employe> employes = s.listerEmployes();
        if (!employes.isEmpty()) {
            employes.forEach((employe) -> {
                afficherUtilisateur(employe);
            });
        } else {
            System.out.println("Aucun client trouvé");
        }
    }

    ///// test chercher client par mail
    public static void testerRechercheEmployeParMail() {
        System.out.println();
        System.out.println("---------Test Rechercher employe par Email-------------");
        System.out.println();
        Service s = new Service();
        Employe employe = s.rechercherEmployeParMail("zineb@insa.fr");
        if (employe != null) {
            System.out.println("employe trouvé :");
            afficherUtilisateur(employe);
        } else {
            System.out.println("employe non trouvé");
        }
    }

    ////// test chercher employe par id
    public static void testerRechercheEmployeParId() 
    {
        System.out.println();
        System.out.println("---------Test Rechercher employe par Id-------------");
        System.out.println();
        Service s = new Service();
        Employe employe = s.rechercherEmployeParId((long) 10);
        if (employe != null) {
            System.out.println("employe trouvé :");
            afficherUtilisateur(employe);
        } else {
            System.out.println("employe non trouvé");
        }

        employe = s.rechercherEmployeParId((long) 7);
        if (employe != null) {
            System.out.println("employe trouvé :");
            afficherUtilisateur(employe);
        } else {
            System.out.println("employe non trouvé");
        }
    }

    ///test tentative de creation d'employe avec le meme mail
    public static void testerInscriptionEmployeAvecMailExistant() throws IOException 
    {

        System.out.println();
        System.out.println("---------testerInscriptionEmployeAvecMailExistant-------------");
        System.out.println();

        Service s = new Service();

        Employe e1 = new Employe("jenner", "kylie", new Date(1998, 0, 0, 4, 2), "alinsa", "k.jenner@insa.fr", "065", "motDePasse", Genre.FEMME, (long) 0);
        Employe e2 = new Employe("jenner", "kendall", new Date(1996, 0, 0, 4, 2), "alinsa", "k.jenner@insa.fr", "064", "motDePasse", Genre.HOMME, (long) 4);

        Long id1 = s.inscrireUtilisateur(e1);
        if (id1 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(e1);

        Long id2 = s.inscrireUtilisateur(e2);
        if (id2 != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherUtilisateur(e2);

    }
//////////////////////////////////////////////////////////////////////////////////////:
    
    
    /////////////////// TESTS UTILISATEUR/////////////////////////
    // authentifier Utilisateur
    public static void testAuthentifierUtilisateur() 
    {
        System.out.println();
        System.out.println("---------testAuthentifierUtilisateur-------------");
        System.out.println();

        Service s = new Service();

        Utilisateur utilisateurOk = s.authentifierUtilisateur("zinebfad@insa.fr", "motDePasse");
        if (utilisateurOk != null) {
            if (utilisateurOk instanceof Client) {
                System.out.println("Client trouvé : ");
            }
            if (utilisateurOk instanceof Employe) {
                System.out.println("Employe trouvé : ");
            }
            afficherUtilisateur(utilisateurOk);
        } else {
            System.out.println("Utilisateur non trouvé");
        }

        //// test avec utilisateur non existant
        Utilisateur utilisateurNonOk = s.authentifierUtilisateur("zinebfad@insa.fr", "motDePasseNON");
        if (utilisateurNonOk != null) {
            if (utilisateurNonOk instanceof Client) {
                System.out.println("Client trouvé : ");
            }
            if (utilisateurNonOk instanceof Employe) {
                System.out.println("Employe trouvé : ");
            }
            afficherUtilisateur(utilisateurNonOk);
        } else {
            System.out.println("Utilisateur non trouvé");
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////:
    
    ////////////////TEST CONSULTATION//////////////
    ////////test de creation de demande de consultation
    public static void testerCreationDemande() { 
        System.out.println();
        System.out.println("---------Test Creation de Demande de Consultation-------------");
        System.out.println();
        Service s = new Service();
        Medium medium = s.rechercherMediumParId((long) 1);
        afficherMedium(medium);
        Client client = s.rechercherClientParId((long) 4);
        afficherUtilisateur((Utilisateur) client);

        Long idConsultation = s.creerDemande(client, medium);
        if (idConsultation != null) {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            afficherConsultation(consultation);
        } else {
            System.out.println("Error idConsultation null");
        }
        
        
        medium = s.rechercherMediumParId((long) 2);
        afficherMedium(medium);
        client = s.rechercherClientParId((long) 4);
        afficherUtilisateur((Utilisateur) client);

        idConsultation = s.creerDemande(client, medium);
        if (idConsultation != null) {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            afficherConsultation(consultation);
        } else {
            System.out.println("Error idConsultation null");
        }
        
        medium = s.rechercherMediumParId((long) 3);
        afficherMedium(medium);
        client = s.rechercherClientParId((long) 4);
        afficherUtilisateur((Utilisateur) client);

        idConsultation = s.creerDemande(client, medium);
        if (idConsultation != null) {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            afficherConsultation(consultation);
        } else {
            System.out.println("Error idConsultation null");
        }
        
        medium = s.rechercherMediumParId((long) 3);
        afficherMedium(medium);
        client = s.rechercherClientParId((long) 5);
        afficherUtilisateur((Utilisateur) client);

        idConsultation = s.creerDemande(client, medium);
        if (idConsultation != null) {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            afficherConsultation(consultation);
        } else {
            System.out.println("Error idConsultation null");
        }

    }

    // test demarrer consultation
    public static void testerCommencerConsultation() { 
        System.out.println();
        System.out.println("---------Test Commencer Consultation-------------");
        System.out.println();
        Service s = new Service();
        Long idConsultation = s.commencerConsultation((long) 1);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non demarre");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
        
        idConsultation = s.commencerConsultation((long) 2);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non demarre");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
        idConsultation = s.commencerConsultation((long) 3);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non demarre");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
        
        idConsultation = s.commencerConsultation((long) 4);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non demarre");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
    }

    // test terminer consultation
    public static void testerTerminerConsultation() {
        System.out.println();
        System.out.println("---------Test Terminer Consultation-------------");
        System.out.println();
        Service s = new Service();
        Long idConsultation = s.terminerConsultation((long) 1);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non terminee");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
        
        idConsultation = s.terminerConsultation((long) 2);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non terminee");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }
        
        idConsultation = s.terminerConsultation((long) 3);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non terminee");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }

        idConsultation = s.terminerConsultation((long) 4);
        if (idConsultation == null) {
            System.out.println("Erreur! Consultation non terminee");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Consultation non-trouvee");
            } else {
                afficherConsultation(consultation);
            }
        }

    }

    ///// test laisser un commentaire à la consultation
    public static void testerLaisserCommentaire() { 
        System.out.println();
        System.out.println("---------Test laisser commentaire-------------");
        System.out.println();
        Service s = new Service();
        Long idConsultation = s.ajouterCommentaire((long) 1, "un commentaire");
        if (idConsultation == null) {
            System.out.println("Erreur! le commentaire n'a pas pu être laissé");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Le commentaire a bien été enregistré");
            } else {
                afficherConsultation(consultation);
            }
        }
        
    idConsultation = s.ajouterCommentaire((long) 2, "un commentaire");
        if (idConsultation == null) {
            System.out.println("Erreur! le commentaire n'a pas pu être laissé");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Le commentaire a bien été enregistré");
            } else {
                afficherConsultation(consultation);
            }
        }
        idConsultation = s.ajouterCommentaire((long) 3, "un commentaire");
        if (idConsultation == null) {
            System.out.println("Erreur! le commentaire n'a pas pu être laissé");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Le commentaire a bien été enregistré");
            } else {
                afficherConsultation(consultation);
            }
        }
        idConsultation = s.ajouterCommentaire((long) 4, "un commentaire");
        if (idConsultation == null) {
            System.out.println("Erreur! le commentaire n'a pas pu être laissé");
        } else {
            Consultation consultation = s.rechercherConsultationParId(idConsultation);
            if (consultation == null) {
                System.out.println("Le commentaire a bien été enregistré");
            } else {
                afficherConsultation(consultation);
            }
        }

    }

    /// est liste de consultation par employe
    public static void testerListeConsultationEmploye() //OK
    {
        System.out.println();
        System.out.println("---------Test lister consultation par employe-------------");
        System.out.println();

        Service s = new Service();
        Employe employe = s.rechercherEmployeParId((long) 2);
        List<Consultation> consultationsClient = s.listerConsultationParEmploye(employe);
        System.out.println("Liste des consultations de l'employe");
        afficherUtilisateur(employe);
        if (!consultationsClient.isEmpty()) {
            consultationsClient.forEach((consultation) -> {
                afficherConsultation(consultation);
            });
        } else {
            System.out.println("Aucune consultation trouvée pour cet employe");
        }
    }

    ////// test liste de consultation par client
    public static void testerListeConsultationClient() ///OK
    {
        System.out.println();
        System.out.println("---------Test lister consultation par client-------------");
        System.out.println();

        Service s = new Service();
        Client client = s.rechercherClientParId((long) 5);
        List<Consultation> consultationsClient = s.listerConsultationParClient(client);
        System.out.println("Liste des consultations du client");
        afficherUtilisateur(client);
        if (!consultationsClient.isEmpty()) {
            consultationsClient.forEach((consultation) -> {
                afficherConsultation(consultation);
            });
        } else {
            System.out.println("Aucune consultation trouvée pour ce client");
        }
    }

    ///// test trouver la prochaine consultation d'un employé
    public static void testerProchaineConsultation() {
        System.out.println();
        System.out.println("---------Test Prochaine Consultation-------------");
        System.out.println();
        Service s = new Service();
        Employe emp = s.rechercherEmployeParId((long) 1);
        Consultation c = s.obtenirProchaineConsultation(emp);
        if (c == null) {
            System.out.println("Aucune consultation disponible");
        } else {
            afficherConsultation(c);
        }
    }

    //// test generer prediction
    public static void testerGenererPrediction() throws IOException ////OK
    {

        System.out.println();
        System.out.println("---------Test generer Prediction-------------");
        System.out.println();

        Service s = new Service();
        Client client = s.rechercherClientParId((long) 5);
        List<String> predictions = s.genererPrediction(client, 1, 2, 3);
        System.out.println("Prédictions pour le client : ");
        afficherUtilisateur(client);
        if (!predictions.isEmpty()) {
            predictions.forEach((prediction) -> {
                System.out.println(prediction);
            });
        } else {
            System.out.println("Aucune prediction générée");
        }

    }

    
    ////////////////////////////////////////////////////////////////////////
    
    
    /////////////////TESTS MEDIUM////////////////

    ///test obtenir liste medium
    public static void testerListerMediums() { //OK
        System.out.println();
        System.out.println("---------Test Lister tous les mediums-------------");
        System.out.println();
        Service s = new Service();
        List<Medium> mediums = s.listerMediums();
        if (!mediums.isEmpty()) {
            mediums.forEach((medium) -> {
                afficherMedium(medium);
            });
        } else {
            System.out.println("Aucun medium trouvé");
        }
    }

    /// test chercher medium par id
    public static void testerRechercheMediumParId() ///OK
    {
        System.out.println();
        System.out.println("---------Test Rechercher medium par Id-------------");
        System.out.println();
        Service s = new Service();
        Medium medium = s.rechercherMediumParId((long) 1);
        if (medium != null) {
            System.out.println("medium trouvé :");
            afficherMedium(medium);
        } else {
            System.out.println("medium non trouvé");
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////:

    //////////////////TESTS AGENCE//////////////////

    ///// test trouver le top 5 des medium
    public static void testerTop5Medium() //OK
    {

        System.out.println();
        System.out.println("---------Test top 5 medium ------------");
        System.out.println();
        Service s = new Service();
        List<Medium> liste = s.top5Mediums();
        liste.forEach(System.out::println);
    }

    /////test nombre de consultations par medium
    public static void testerNbConsultationsParMedium() /////OK
    {

        System.out.println();
        System.out.println("---------tester NbConsultationsParMedium ------------");
        System.out.println();

        Service s = new Service();
        s.listerMediums().forEach((medium)
                -> {
            System.out.println(medium.getDenomination() + ": " + medium.getNbConsultations());
        });
    }
    
    
    /////////////////////////////////////////////////////////////////////::

}
