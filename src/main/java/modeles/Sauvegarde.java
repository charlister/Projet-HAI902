package modeles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    private String methodeUtilisee;
    private Priorite priorite;
    private Map<Etablissement, List<Etudiant>> associations;
    private float degreSatisfactionEtudiant;
    private float degreSatisfactionEtablissement;

    public  Sauvegarde(Priorite priorite, List<Etablissement> etablissements, float degreSatisfactionEtudiant, float degreSatisfactionEtablissement)
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
        this.degreSatisfactionEtablissement = degreSatisfactionEtablissement;
    }

    public float getDegreSatisfactionEtudiant() {
        return degreSatisfactionEtudiant;
    }

    public float getDegreSatisfactionEtablissement() {
        return degreSatisfactionEtablissement;
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
        res.append("degré satisfaction établissement : "+String.format("%.02f", this.degreSatisfactionEtablissement)+"\n");
        return res.toString();
    }
}