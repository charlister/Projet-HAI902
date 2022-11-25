package modeles;

import java.util.*;
import java.util.stream.Collectors;

public class Sauvegarde {
    public enum Priorite {
        ETUDIANT("ETUDIANT"),
        ETABLISSEMENT("ETABLISSEMENT");

        private final String type;

        Priorite(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Priorité aux " + this.type;
        }
    }
    private Priorite priorite;
    private Map<Etablissement, List<Etudiant>> associations;
    private float degreSatisfactionEtudiant;
    private float degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes;
    private float degreSatisfactionEtablissementsSelonCapaciteAccueil;

    public  Sauvegarde(Priorite priorite, List<Etablissement> etablissements, float degreSatisfactionEtudiant, float degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes, float degreSatisfactionEtablissementsSelonCapaciteAccueil)
    {
//        this.methodeUtilisee = methodeUtilisee;
        this.priorite = priorite;
        this.associations = new HashMap<>();
        for(Etablissement etablissement : etablissements)
        {
            List<Etudiant> etudiants = new ArrayList<>();
            etudiants.addAll(etablissement.getCandidatsAcceptes());
            associations.put(etablissement, etudiants);
        }
        this.degreSatisfactionEtudiant = degreSatisfactionEtudiant;
        this.degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes = degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes;
        this.degreSatisfactionEtablissementsSelonCapaciteAccueil = degreSatisfactionEtablissementsSelonCapaciteAccueil;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public Map<Etablissement, List<Etudiant>> getAssociations() {
        return associations;
    }

    /*
    public void lectureEtablissementCandidats() {
        this.associations
                .entrySet()
                .stream()
                .forEach(association -> {
                    association.getKey().getId(); // à remplacer par la méthode de remplissage du tableau de l'interface graphique
                    for (Etudiant candidat :
                            association.getValue()) {
                        candidat.getId(); // à remplacer par la méthode de remplissage du tableau de l'interface graphique
                    }
                });
    }

    public void lectureCandidatEtablissement() {
        this.associations
                .entrySet()
                .stream()
                .forEach(association -> {
                    for (Etudiant candidatAccepte :
                            association.getKey().getCandidatsAcceptes()) {
                        candidatAccepte.getListeVoeux(); // à remplacer par la méthode de remplissage du tableau de l'interface graphique
                    }
                });
    }
    */

    public float getDegreSatisfactionEtudiant() {
        return degreSatisfactionEtudiant;
    }

    public float getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes() {
        return degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes;
    }

    public float getDegreSatisfactionEtablissementsSelonCapaciteAccueil() {
        return degreSatisfactionEtablissementsSelonCapaciteAccueil;
    }

    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(this.priorite.toString()).append('\n');
        for (Etablissement etablissement : associations.keySet())
        {
            res.append("Etablissement").append(etablissement.getId());
            res.append(", candidats acceptés : [");
            List<Etudiant> etudiants = associations.get(etablissement);
            for (Etudiant etudiant : etudiants)
            {
                if(etudiants.indexOf(etudiant) == 0) {
                    res.append("Etudiant").append(etudiant.getId());
                }
                else
                {
                    res.append(", ");
                    res.append("Etudiant").append(etudiant.getId());
                }
            }
            res.append("]\n");
        }
        res.append("degré satisfaction étudiant : "+String.format("%.02f", this.degreSatisfactionEtudiant)+"\n");
        res.append("degré satisfaction établissement selon nombre candidats acceptés : "+String.format("%.02f", this.degreSatisfactionEtablissementsSelonNombreCandidatsAcceptes)+"\n");
        res.append("degré satisfaction établissement selon capacité accueil: "+String.format("%.02f", this.degreSatisfactionEtablissementsSelonCapaciteAccueil)+"\n");
        return res.toString();
    }
}