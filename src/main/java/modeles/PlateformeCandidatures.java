package modeles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateformeCandidatures {
    /* ATTRIBUTS */
    private String nom = "Plateforme de Candidatures";
    private final List<Etablissement> etablissements;
    private List<Etudiant> candidats;

    /* CONSTRUCTEURS */
    public PlateformeCandidatures(int nombreEtudiants, int nombreDePlacesMin, int nombreDePlacesMax, int nombreEtablissements) {
        Etudiant.reinitialiserCompteur();
        Etablissement.reinitialiserCompteur();
        this.etablissements = new ArrayList<Etablissement>();
        this.candidats = new ArrayList<Etudiant>();
        this.genererCandidats(nombreEtudiants);
        this.genererEtablissements(nombreDePlacesMin, nombreDePlacesMax, nombreEtablissements);
        this.genererVoeux();
        this.genererClassement();
    }

    public PlateformeCandidatures() {
        Etudiant.reinitialiserCompteur();
        Etablissement.reinitialiserCompteur();
        this.etablissements = new ArrayList<Etablissement>();
        this.candidats = new ArrayList<Etudiant>();
        exempleDuCours();
    }

    public List<Etablissement> getEtablissements() {
        return etablissements;
    }

    public List<Etudiant> getCandidats() {
        return candidats;
    }

    public String getNom() {
        return nom;
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
     * Génère pour chaque étudiant sa liste de vœux
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

    /* ==========================================================================================
     *  Méthodes implémentées dans la cadre de la mise en place de l'algorithme du mariage stable
     *  =========================================================================================
     */

    /**
     * Met en application l'algorithme du mariage stable sur notre jeu de données.
     * Priorité : donnée aux établissements.
     * @return une sauvegarde représentant les couples d'associations obtenues à la fin de l'algorithme.
     */
    public Sauvegarde mariageStableEtablissements()
    {
        while (!acceptationsTerminees())
        {
            for (Etudiant etudiant : this.candidats) {
                // Rien à faire si le candidat est déjà affecté temporairement.
                if (!etudiant.estAffecte()) {
                    Etablissement voeu = etudiant.voeuActuel();
                    // On ajoute l'étudiant à la liste des candidats acceptés temporairements si l'établissement a de
                    // la place.
                    if (voeu.accueilPossible()) {
                        voeu.ajoutCandidat(etudiant);
                        etudiant.setAffectation(voeu);
                    }
                    // Sinon, on ...
                    else {
                        Etudiant cmp = voeu.dernierCandidat();
                        // ... voit si le candidat étudié actuellement est plus souhaité que le moins souhaité des
                        // candidats acceptés par l'établissement. Si oui, on désaffecte l'étudiant le moins bon et on
                        // affecte le candidat étudié.
                        if (voeu.getPreferences().indexOf(cmp) > voeu.getPreferences().indexOf(etudiant)) {
                            voeu.getCandidatsAcceptes().remove(cmp);
                            cmp.desaffecter();
                            voeu.ajoutCandidat(etudiant);
                            etudiant.setAffectation(voeu);
                        }
                        // Sinon, on refuse l'étudiant.
                        else {
                            etudiant.refuser();
                        }
                    }
                }
            }
        }
        return new Sauvegarde(Sauvegarde.Priorite.ETABLISSEMENT, this.etablissements, degreSatisfactionEtudiants(),
                degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes(),
                degreSatisfactionEtablissementsSelonCapaciteAccueil());
    }

    /**
     * Met en application l'algorithme du mariage stable sur notre jeu de données.
     * Priorité : donnée aux étudiants.
     * @return une sauvegarde représentant les couples d'associations obtenues à la fin de l'algorithme.
     */
    public Sauvegarde mariageStableEtudiants()
    {
        while (!acceptationsTerminees())
        {
            for (Etablissement etablissement : etablissements)
            {
                if (etablissement.accueilPossible())
                {
                    Etudiant etudiantSouhaite = etablissement.getEtudiantSouhaite();
                    // Si on propose un meilleur voeu à un étudiant
                    if(!etudiantSouhaite.estAffecte())
                    {
                        etudiantSouhaite.setAffectation(etablissement);
                        etablissement.ajoutCandidat(etudiantSouhaite);
                    }
                    else if(etudiantSouhaite.getListeVoeux().indexOf(etablissement) > etudiantSouhaite.getListeVoeux().indexOf(etudiantSouhaite.voeuActuel()))
                    {
                        Etablissement etablissementAComparer = etudiantSouhaite.voeuActuel();
                        etablissementAComparer.desaffecter(etudiantSouhaite);
                        etablissementAComparer.incrementerCandidatEtudie();
                        etudiantSouhaite.setAffectation(etablissement);
                        etablissement.ajoutCandidat(etudiantSouhaite);
                    } else {
                        etablissement.incrementerCandidatEtudie();
                    }
                }
            }
        }
        return new Sauvegarde(Sauvegarde.Priorite.ETUDIANT, this.etablissements, degreSatisfactionEtudiants(),
                degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes(),
                degreSatisfactionEtablissementsSelonCapaciteAccueil());
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

    /**
     * Calcule le degré de satisfaction globale des étudiants.
     * @return une mesure comprise dans l'intervalle [0 ; 1].
     */
    public float degreSatisfactionEtudiants() {
        float satisfactionGlobale = 0;
        if (this.candidats.isEmpty())
            return 0.f;
        for (Etudiant etudiant :
                this.candidats) {
            satisfactionGlobale += etudiant.degreSatisfaction();
        }
        return satisfactionGlobale/this.candidats.size();
    }


    public float degreSatisfactionEtablissements() {
        float satisfactionGlobale = 0;
        if (this.etablissements.isEmpty())
            return 0.f;
        for (Etablissement etablissement :
                this.etablissements) {
            satisfactionGlobale += etablissement.degreSatisfaction();
        }
        return satisfactionGlobale/this.etablissements.size();
    }

    /**
     * Calcule le degré de satisfaction globale des établissements selon le nombre de candidats acceptés.
     * @return une mesure comprise dans l'intervalle [0 ; 1].
     */
    public float degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes() {
        float satisfactionGlobale = 0;
        if (this.etablissements.isEmpty())
            return 0.f;
        for (Etablissement etablissement :
                this.etablissements) {
            satisfactionGlobale += etablissement.degreSatisfactionSelonNombreCandidatsAcceptes();
        }
        return satisfactionGlobale/this.etablissements.size();
    }

    /**
     * Calcule le degré de satisfaction globale des établissements selon la capacité d'accueil.
     * @return une mesure comprise dans l'intervalle [0 ; 1].
     */
    public float degreSatisfactionEtablissementsSelonCapaciteAccueil() {
        float satisfactionGlobale = 0;
        if (this.etablissements.isEmpty())
            return 0.f;
        for (Etablissement etablissement :
                this.etablissements) {
            satisfactionGlobale += etablissement.degreSatisfactionSelonCapaciteAccueil();
        }
        return satisfactionGlobale/this.etablissements.size();
    }

    /*public Sauvegarde sauvegarder(Sauvegarde.Priorite priorite)
    {
        return new Sauvegarde(priorite, this.etablissements, degreSatisfactionEtudiants(), degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes(), degreSatisfactionEtablissementsSelonCapaciteAccueil());
    }*/

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
        Etudiant etudiant1 = new Etudiant(1, preferencesEtudiant1, false);
        Etudiant etudiant2 = new Etudiant(2, preferencesEtudiant2, false);
        Etudiant etudiant3 = new Etudiant(3, preferencesEtudiant3, false);
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
