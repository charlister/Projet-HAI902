package modeles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Etablissement {
    /* ATTRIBUTES */
    private static long compteurEtablissements = 0;
    private long id;
    private int capaciteAccueil;
    private List<Etudiant> classement, candidatsAcceptes;

    /* CONSTRUCTEURS */
    public Etablissement(int capaciteAccueil) {
        this.id = ++compteurEtablissements;
        this.capaciteAccueil = capaciteAccueil;
        this.classement = new ArrayList<Etudiant>();
        this.candidatsAcceptes = new ArrayList<Etudiant>();
    }

    /**
     * Génère tous les étudiants dans le classement ordonné de manière aléatoire
     * @param etudiants liste de tous les étudiants, passé en paramètre
     */
    public void genererClassement(List<Etudiant> etudiants)
    {
        this.classement.addAll(etudiants);
        Collections.shuffle(this.classement);
    }

    /* ACCESSEURS */
    public static long getCompteurEtablissements() {
        return compteurEtablissements;
    }

    public long getId() {
        return id;
    }

    public int getCapaciteAccueil() {
        return capaciteAccueil;
    }

    public List<Etudiant> getPreferences() {
        return classement;
    }

    public List<Etudiant> getCandidatsAcceptes() {
        return candidatsAcceptes;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Etablissement").append(this.id);
        res.append(", son classement des étudiants : \n[");
        if (!classement.isEmpty())
        {
            int index = 0;
            do{
                if (index > 0) {
                    res.append(" ; ");
                }
                res.append(this.classement.get(index).getId());
                index++;
            }while (index < this.classement.size());
            res.append("]\n");
        }

        return res.toString();
    }
}