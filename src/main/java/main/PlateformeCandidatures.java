package main;

import modeles.Etablissement;
import modeles.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class PlateformeCandidatures {
    /* ATTRIBUTS */
    private static final String nom = "APB";
    private List<Etablissement> etablissements;
    private List<Etudiant> candidats;

    /* CONSTRUCTEUR */
    public PlateformeCandidatures(int nombreEtudiants, int nombreDePlacesMin, int nombreDePlacesMax, int nombreEtablissements) {
        this.etablissements = new ArrayList<Etablissement>();
        this.candidats = new ArrayList<Etudiant>();
        this.genererCandidats(nombreEtudiants);
        this.genererEtablissements(nombreDePlacesMin, nombreDePlacesMax, nombreEtablissements);
        this.genererVoeux();
        this.genererClassement();
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

    public void algorithmeMariageStable1()
    {

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
}
