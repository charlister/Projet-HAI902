package main;

import modeles.Etablissement;
import modeles.Etudiant;
import modeles.Sauvegarde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateformeCandidatures {
    /* ATTRIBUTS */
    private String nom = "APB";
    private List<Etablissement> etablissements;
    private List<Etudiant> candidats;

    /* CONSTRUCTEUR */
    public PlateformeCandidatures(int nombreEtudiants, int nombreDePlacesMin, int nombreDePlacesMax, int nombreEtablissements) {
        this.nom = nom;
        this.etablissements = new ArrayList<Etablissement>();
        this.candidats = new ArrayList<Etudiant>();
        this.genererCandidats(nombreEtudiants);
        this.genererEtablissements(nombreDePlacesMin, nombreDePlacesMax, nombreEtablissements);
        this.genererVoeux();
        this.genererClassement();
    }

    public PlateformeCandidatures() {
        this.etablissements = new ArrayList<Etablissement>();
        this.candidats = new ArrayList<Etudiant>();
        exempleDuCours();
    }

    /**
     * Génére un ensemble de candidats pour la plateforme de candidature
     * @param nombreEtudiants le nombre d'étudiants inscrits sur la plateforme
     */
    private void genererCandidats(int nombreEtudiants)
    {
        for (long i = 0; i<nombreEtudiants; i++) {
            this.candidats.add(new Etudiant());
        }
    }

    /**
     * Génère un ensemble d'établissements pour la plateforme de candidature
     * @param nombreDePlacesMin est le nombre de places minimales dans un établissement
     * @param nombreDePlacesMax est le nombre de places maximales dans un établissement
     * @param nombreEtablissements est le nombre d'établissements à générer
     */
    private void genererEtablissements(int nombreDePlacesMin, int nombreDePlacesMax, int nombreEtablissements) {
        long i = 0;
        while (i < nombreEtablissements)
        {
            int capacite = nombreDePlacesMin + (int)(Math.random() * (Math.abs(nombreDePlacesMax - nombreDePlacesMin + 1)));
            this.etablissements.add(new Etablissement(capacite));
            i++;
        }
    }

    /**
     * Génère pour chaque étudiant sa liste de voeux
     */
    private void genererVoeux()
    {
        for (Etudiant etudiant : this.candidats)
        {
            etudiant.genererVoeux(this.etablissements);
        }
    }

    /**
     * Génère pour chaque etablissement sa liste d'étudiants par préférence
     */
    private void genererClassement()
    {
        for (Etablissement etablissement : this.etablissements)
        {
            etablissement.genererClassement(this.candidats);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Etudiant candidat : this.candidats) {
            result.append(candidat.toString()+"\n");
        }

        for (Etablissement etablissement : this.etablissements) {
            result.append(etablissement.toString()+"\n");
        }

        return result.toString();
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre de la mise en place de l'algorithme du mariage stable
     *  =========================================================================================
     */

    /**
     * Met en application l'algorithme du mariage stable sur notre jeu de données.
     * Priorité : donnée aux établissements.
     */
    public void mariageStableEtablissements()
    {
        while (!acceptationsTerminees())
        {
            for (Etudiant etudiant : this.candidats)
            {
                /* TRACAGE
                System.out.println("ETAPE1 - Etudiant"+etudiant.getId()+ ", premiervoeu : Etablissement" + etudiant.getPremierVoeux().getId() + ", voeux restants : ["  );
                for (Etablissement etablissement : etudiant.getListeVoeux())
                {
                    System.out.print(etablissement.getId() + ",");
                }
                System.out.print("]\n");*/
                if (!etudiant.estAffecte()) {
                    Etablissement voeu = etudiant.voeuActuel();
                    /* TRACAGE
                    System.out.println("ETAPE PAS AFFECTE - Etablissement" + voeu.getId() + ", candidatsAcceptes : ");
                    for (Etudiant e : voeu.getCandidatsAcceptes())
                    {
                        System.out.print(e.getId() + ",");
                    }*/
                    if (voeu.accueilPossible()) {
                        // TRACAGE : System.out.println(voeu.getCandidatsAcceptes());
                        voeu.ajoutCandidat(etudiant);
                        etudiant.setAffectation(voeu);
                    } else {
                        // TRACAGE : System.out.println("ETAPE PEUT PAS ACCUEILLIR - Etablissement : " + voeu.getId() + ";" + voeu.getCandidatsAcceptes());
                        Etudiant cmp = voeu.dernierCandidat();
                        // TRACAGE : System.out.println("Etudiant à comparer (moins bon) : Etudiant" + cmp.getId() + ", position : " + voeu.getPreferences().indexOf(cmp));
                        if (voeu.getPreferences().indexOf(cmp) > voeu.getPreferences().indexOf(etudiant)) {
                            // TRACAGE : System.out.println("CAS NUMERO 2");
                            voeu.getCandidatsAcceptes().remove(cmp);
                            cmp.desaffecter();
                            voeu.ajoutCandidat(etudiant);
                            etudiant.setAffectation(voeu);
                            // TRACAGE : System.out.println("Voeu"+ voeu.getId() + ", etu :" + voeu.getCandidatsAcceptes().get(0));
                        }
                        else {
                            // TRACAGE : System.out.println("CAS NUMERO 1");
                            etudiant.refuser();
                        }
                    }
                }
            }
        }
    }

    /**
     * Met en application l'algorithme du mariage stable sur notre jeu de données.
     * Priorité : donnée aux étudiants.
     */
    public void mariageStableEtudiants()
    {
        while (!acceptationsTerminees())
        {
            Map<Etablissement, List<Etudiant>> candidaturesTemporaires = new HashMap<Etablissement, List<Etudiant>>();
            
            // On établit, pour chaque établissement, une liste d'étudiants dont le souhait le plus élevé est de
            // rejoindre ce dernier
            for (Etudiant etudiant : this.candidats) {
                /* TRACAGE
                System.out.println("ETAPE1 - Etudiant"+etudiant.getId()+ ", premiervoeu : Etablissement" + etudiant.getPremierVoeux().getId() + ", voeux restants : ["  );
                for (Etablissement etablissement : etudiant.getListeVoeux())
                {
                    System.out.print(etablissement.getId() + ",");
                }
                System.out.print("]\n");*/
                if (!etudiant.estAffecte()) {
                    Etablissement voeu = etudiant.voeuActuel();
                    if (!candidaturesTemporaires.containsKey(voeu)) {
                        List<Etudiant> etudiantsCandidats = new ArrayList<Etudiant>();
                        candidaturesTemporaires.put(voeu, etudiantsCandidats);
                    }
                    /* TRACAGE
                    System.out.println("ETAPE PAS AFFECTE - Etablissement" + voeu.getId() + ", candidatsAcceptes : ");
                    for (Etudiant e : voeu.getCandidatsAcceptes())
                    {
                        System.out.print(e.getId() + ",");
                    }*/
                    if (voeu.accueilPossible()) {
                        candidaturesTemporaires.get(voeu).add(etudiant);
                        // TRACAGE : System.out.println(voeu.getCandidatsAcceptes());

                    } else {
                        // TRACAGE : System.out.println("CAS NUMERO 1");
                        etudiant.refuser();
                    }
                }
            }
            
            // Pour chaque établissement, on sélectionne les meilleurs ayant candidatés à ce tour, et on refuse les
            // autres
            for (Etablissement etablissement : candidaturesTemporaires.keySet())
            {
                List<Etudiant> candidats = candidaturesTemporaires.get(etablissement);
                int placesDisponibles = etablissement.getCapaciteAccueil() - etablissement.getCandidatsAcceptes().size();
                
                while ((placesDisponibles > 0) && (!candidats.isEmpty()))
                {
                    Etudiant meilleur = etudiantChoisi(candidats, etablissement);
                    etablissement.ajoutCandidat(meilleur);
                    candidats.remove(meilleur);
                    meilleur.setAffectation(etablissement);                    
                    placesDisponibles--;
                }

                for (Etudiant etudiant : candidats)
                {
                    etudiant.refuser();
                }                
            }
        }
    }

    /**
     *
     * @param etudiants
     * @param etablissement
     * @return
     */    
    private static Etudiant etudiantChoisi(List<Etudiant> etudiants, Etablissement etablissement)
    {
        Etudiant meilleur = null;
        int classement = -1;
        for (Etudiant etudiant : etudiants)
        {
            int classementEtudiant = etablissement.getPreferences().indexOf(etudiant);
            if (classement == -1)
            {
                meilleur = etudiant;
                classement = etablissement.getPreferences().indexOf(etudiant);
            } else if (classementEtudiant < classement) {
                meilleur = etudiant;
                classement = classementEtudiant; 
            }
        }

        return meilleur;        
    } 

    /**
     * Permet d'afficher pour chaque candidat son affectation
     */
    public void affichageAffectations()
    {
        for (Etudiant etudiant : candidats)
        {
            System.out.println("Etudiant"+etudiant.getId() + ", affectation : Etablissement[capacite : " + etudiant.getEtablissementAffecte().getCapaciteAccueil()+ "]" + etudiant.getEtablissementAffecte().getId() + "\n");
        }
    }

    /**
     * Permet de vérifier que tous les candidats ont eu une affectation
     * @return un booleen, false si un étudiant n'a pas d'affectation, true sinon
     */
    public boolean acceptationsTerminees()
    {
        for (Etudiant e : candidats)
        {
            if (!e.estAffecte())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Permet de restaurer notre jeu de données pour être capable de lui appliquer une nouvelle affectation
     */
    public void restaurer()
    {
        for (Etudiant etudiant : candidats)
        {
            etudiant.restaurer();
        }
        
        for (Etablissement etablissement : etablissements)
        {
            etablissement.restaurer();
        }
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre du calcul de la satisfaction
     *  =========================================================================================
     */
    public float degreSatisfactionEtudiants() {
        float satisfactionGlobale = 0;
        for (Etudiant etudiant :
                this.candidats) {
            satisfactionGlobale += etudiant.degreSatisfaction();
        }
        return satisfactionGlobale/this.candidats.size();
    }

    public float degreSatisfactionEtablissements() {
        float satisfactionGlobale = 0;
        for (Etablissement etablissement :
                this.etablissements) {
            satisfactionGlobale += etablissement.degreSatisfaction();
        }
        return satisfactionGlobale/this.etablissements.size();
    }

    public Sauvegarde sauvegarder(String methode)
    {
        return  new Sauvegarde(methode, this.etablissements, degreSatisfactionEtudiants(), degreSatisfactionEtablissements());
    }

    /* =========================================================================================
     *  Pour mettre en place un jeu de données correspondant à l'exemple du cours
     *  =========================================================================================
     */

    /**
     * Initialise un jeu de données dont les valeurs correspondent à l'exemple du cours
     */
    private void exempleDuCours() {
        Etablissement etablissement1 = new Etablissement(1, 1, null, new ArrayList<>());
        Etablissement etablissement2 = new Etablissement(2, 1, null, new ArrayList<>());
        Etablissement etablissement3 = new Etablissement(3, 1, null, new ArrayList<>());
        etablissements.add(etablissement1);
        etablissements.add(etablissement2);
        etablissements.add(etablissement3);
        List<Etablissement> preferencesEtudiant1 = new ArrayList<>();
        preferencesEtudiant1.add(etablissement2);
        preferencesEtudiant1.add(etablissement1);
        preferencesEtudiant1.add(etablissement3);
        List<Etablissement> preferencesEtudiant2 = new ArrayList<>();
        preferencesEtudiant2.add(etablissement1);
        preferencesEtudiant2.add(etablissement2);
        preferencesEtudiant2.add(etablissement3);
        List<Etablissement> preferencesEtudiant3 = new ArrayList<>();
        preferencesEtudiant3.add(etablissement1);
        preferencesEtudiant3.add(etablissement2);
        preferencesEtudiant3.add(etablissement3);
        Etudiant etudiant1 = new Etudiant(1, preferencesEtudiant1, false, null);
        Etudiant etudiant2 = new Etudiant(2, preferencesEtudiant2, false, null);
        Etudiant etudiant3 = new Etudiant(3, preferencesEtudiant3, false, null);
        candidats.add(etudiant1);
        candidats.add(etudiant2);
        candidats.add(etudiant3);
        List<Etudiant> classementEtablissement1 = new ArrayList<>();
        classementEtablissement1.add(etudiant1);
        classementEtablissement1.add(etudiant3);
        classementEtablissement1.add(etudiant2);
        etablissement1.setClassement(classementEtablissement1);
        List<Etudiant> classementEtablissement2 = new ArrayList<>();
        classementEtablissement2.add(etudiant2);
        classementEtablissement2.add(etudiant1);
        classementEtablissement2.add(etudiant3);
        etablissement2.setClassement(classementEtablissement2);
        List<Etudiant> classementEtablissement3 = new ArrayList<>();
        classementEtablissement3.add(etudiant2);
        classementEtablissement3.add(etudiant1);
        classementEtablissement3.add(etudiant3);
        etablissement3.setClassement(classementEtablissement3);
    }
}
