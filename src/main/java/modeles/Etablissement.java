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

    /* =========================================================================================
    *  Méthodes implémentées dans la cadre de la mise en place de l'algorithme du mariage stable
    *  =========================================================================================
    */

    /**
     * Permet de confirmer, ou non, la capacité à accueillir d'un établissement
     * @return la comparaison entre la taille de la liste de candidats acceptés et sa capacité d'accueil.
     */
    public boolean accueilPossible()
    {
        /* TRACAGE
        int places = this.capaciteAccueil - candidatsAcceptes.size();
        System.out.println("Nombre de places : " + places);*/
        return candidatsAcceptes.size() < this.capaciteAccueil;
    }

    /**
     * Permet d'ajouter un étudiant à la liste de candidats acceptés par l'établissement
     * @param etudiant, étudiant à ajouter dans la liste des candidats acceptés
     */
    public void ajoutCandidat(Etudiant etudiant)
    {
        this.candidatsAcceptes.add(etudiant);
    }

    /**
     * Permet de parcourir le liste des candidats acceptés par l'établissement, et de retourner l'étudiant dont la
     * position dans le classement de l'établissement est le moins bon
     * @return
     */
    public Etudiant dernierCandidat()
    {
        int classementCandidat = -1;
        Etudiant dernier = null;
        for (Etudiant etudiant : candidatsAcceptes)
        {
            int classementEtudiant = this.classement.indexOf(etudiant);
            if(classementCandidat == -1)
            {
                classementCandidat = classementEtudiant;
                dernier = etudiant;
            } else if (classementCandidat > classementEtudiant) {
                classementCandidat = classementEtudiant;
                dernier = etudiant;
            }
        }
        return dernier;
    }

    /**
     * Permet de restaurer notre jeu de données pour être capable de lui appliquer une nouvelle affectation
     */
    public void restaurer()
    {
        this.candidatsAcceptes.clear();
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre du calcul de la satisfaction
     *  =========================================================================================
     */

    /**
     * mesure le degré de satisfaction pour un établissement.
     * Formule : ( (nombre de candidats + 1) - (position candidat accepté dans le classement de l'établissement) ) / nombre de candidats.
     * /!\ cette formule est valide dans le cas où chaque établissement n'accepte qu'un candidat.
     * @return le degré de satisfaction d'un établissement
     */
    public float degreSatisfaction() {
        float nbCandidats = this.classement.size();
        return ((float) (nbCandidats - this.classement.indexOf(this.candidatsAcceptes.get(0)))) / nbCandidats;
    }
}