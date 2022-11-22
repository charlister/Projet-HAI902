package main;

import modeles.Etablissement;
import modeles.Etudiant;

import java.util.ArrayList;
import java.util.List;

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

    /* **** */
    public boolean allAffected()
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

    public void mariagemixte()
    {
        while (!allAffected())
        {
            for (Etudiant etudiant : this.candidats)
            {
                System.out.println("ETAPE1 - Etudiant"+etudiant.getId()+ ", premiervoeu : Etablissement" + etudiant.getPremierVoeux().getId() + ", voeux restants : ["  );
                for (Etablissement etablissement : etudiant.getListeVoeux())
                {
                    System.out.print(etablissement.getId() + ",");
                }
                System.out.print("]\n");
                if (!etudiant.estAffecte()) {
                    Etablissement voeu = etudiant.getPremierVoeux();
                    System.out.println("ETAPE PAS AFFECTE - Etablissement" + voeu.getId() + ", candidatsAcceptes : ");
                    for (Etudiant e : voeu.getCandidatsAcceptes())
                    {
                        System.out.print(e.getId() + ",");
                    }
                    if (voeu.peutAccueillir()) {
                        System.out.println(voeu.getCandidatsAcceptes());
                        voeu.ajoutCandidat(etudiant);
                        etudiant.setAffectation(voeu);
                    } else {
                        System.out.println("ETAPE PEUT PAS ACCUEILLIR - Etablissement : " + voeu.getId() + ";" + voeu.getCandidatsAcceptes());
                        Etudiant cmp = voeu.moinsBon();
                        System.out.println("Etudiant à comparer (moins bon) : Etudiant" + cmp.getId() + ", position : " + voeu.getPreferences().indexOf(cmp));
                        if (voeu.getPreferences().indexOf(cmp) > voeu.getPreferences().indexOf(etudiant)) {
                            System.out.println("CAS NUMERO 2");
                            voeu.getCandidatsAcceptes().remove(cmp);
                            cmp.desaffecter();
                            voeu.ajoutCandidat(etudiant);
                            etudiant.setAffectation(voeu);
                            System.out.println("Voeu"+ voeu.getId() + ", etu :" + voeu.getCandidatsAcceptes().get(0));
                        }
                        else {
                            System.out.println("CAS NUMERO 1");
                            etudiant.desaffecter();
                        }
                    }
                }
            }
        }
    }

    public void affichage()
    {
        for (Etudiant etudiant : candidats)
        {
            if (etudiant.estAffecte())
            {
                System.out.println("Etudiant"+etudiant.getId() + ", affectation : Etablissement|" + etudiant.getEtablissementAffecte().getCapaciteAccueil()+ "]" + etudiant.getEtablissementAffecte().getId());
            }
            else {
                System.out.println("Etudiant"+etudiant.getId()+"sans affectation");
            }

            if (etudiant.getPremierVoeux().getId() != etudiant.getListeVoeux().get(0).getId())
            {
                System.out.println("problème");
            }
        }
    }

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
