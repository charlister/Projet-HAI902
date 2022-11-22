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

    public void setClassement(List<Etudiant> classement) {
        this.classement = classement;
    }

    public Etablissement(long id, int capaciteAccueil, List<Etudiant> classement, List<Etudiant> candidatsAcceptes) {
        this.id = id;
        this.capaciteAccueil = capaciteAccueil;
        this.classement = classement;
        this.candidatsAcceptes = candidatsAcceptes;
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

    /* ************************************* */

    public boolean peutAccueillir()
    {
        int places = this.capaciteAccueil - candidatsAcceptes.size();
        System.out.println("Nombre de places : " + places);
        return candidatsAcceptes.size() < this.capaciteAccueil;
    }

    public void ajoutCandidat(Etudiant etudiant)
    {
        this.candidatsAcceptes.add(etudiant);
    }

    public Etudiant moinsBon()
    {
        int position = -1;
        Etudiant moinsBon = null;
        for (Etudiant etudiant : candidatsAcceptes)
        {
            int positionTMP = this.classement.indexOf(etudiant);
            if(position == -1)
            {
                position = positionTMP;
                moinsBon = etudiant;
            } else if (position > positionTMP) {
                position = positionTMP;
                moinsBon = etudiant;
            }
        }
        return moinsBon;
    }


}