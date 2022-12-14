package modeles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Etudiant {
    /* ATTRIBUTS */
    private static long compteurEtudiants = 0;
    private final long id;
    private String nom;
    private final List<Etablissement> listeVoeux;
    private boolean affecte;
    private int voeuEtudie = 0;

    /* CONSTRUCTEURS */
    public Etudiant() {
        this.id = ++compteurEtudiants;
        this.affecte = false;
        this.listeVoeux = new ArrayList<>();
    }

    public Etudiant(long id, List<Etablissement> listeVoeux, boolean estAffecte) {
        this.id = id;
        this.listeVoeux = listeVoeux;
        this.affecte = estAffecte;
    }

    /* ACCESSEURS */
    public long getId() {
        return id;
    }

    public String getNom() {
        return "Etudiant"+this.id;
    }

    public List<Etablissement> getListeVoeux() {
        return listeVoeux;
    }

    public boolean estAffecte() {
        return affecte;
    }

    public Etablissement getEtablissementAffecte()
    {
        return this.getListeVoeux().get(voeuEtudie);
    }

    /**
     * Permet l'affichage d'un étudiant
     * @return l'identifiant de l'étudiant et sa liste ordonnées d'établissements
     */
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Etudiant ").append(this.id);
        res.append(", ses voeux : \n[");
        if (!listeVoeux.isEmpty())
        {
            int index = 0;
            do{
                if(index>0) {
                    res.append(" ; ");
                }
                res.append(this.listeVoeux.get(index).getId());
                index++;
            } while (index < this.listeVoeux.size());
            res.append("]\n");
        }
        return res.toString();
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre de la génération de données
     *  =========================================================================================
     */

    /**
     * sert à la réinitialisation du compteur d'établissements lorsque l'on créera de nouveau une instance de {@link PlateformeCandidatures}
     */
    public static void reinitialiserCompteur()
    {
        compteurEtudiants = 0;
    }

    /**
     * Génère tous les etablissements dans la liste de voeux ordonnés de manière aléatoire
     * @param etablissements liste de tous les établissements, passé en paramètre
     */
    public void genererVoeux(List<Etablissement> etablissements)
    {
        this.listeVoeux.addAll(etablissements);
        Collections.shuffle(this.listeVoeux);
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre de la mise en place de l'algorithme du mariage stable
     *  =========================================================================================
     */

    /**
     * Permet d'obtenir l'établissement le plus souhaité par l'étudiant en prenant en compte ses refus
     * @return etablissement, vœu actuel
     */
    public Etablissement voeuActuel()
    {
        return listeVoeux.get(voeuEtudie);
    }

    /**
     * Permet d'affecter à un étudiant un établissement, et de marquer par la même occasion le drapeau "affecté"
     * @param etablissement,  à affecter
     */
    public void setAffectation(Etablissement etablissement)
    {
        this.voeuEtudie = listeVoeux.indexOf(etablissement);
        this.affecte = true;
    }

    /**
     * Permet de désaffecter un étudiant de son établissement, après avoir été accepté temporairement.
     * Le vœu le plus souhaité devient alors le prochain dans sa liste de vœu, que l'on gère avec le curseur
     * "voeuEtudie"
     */
    public void desaffecter()
    {
        this.affecte = false;
        this.voeuEtudie++;
    }

    /**
     * Permet d'incrémenter le curseur qui gère le voeu le plus souhaité par un étudiant lorsque celui-ci est refusé
     * sans être temporairement accepté.
     */
    public void refuser()
    {
        this.voeuEtudie++;
    }


    /**
     * Permet de restaurer notre jeu de données pour être capable de lui appliquer une nouvelle affectation
     */
    public void restaurer()
    {
        this.voeuEtudie = 0;
        this.affecte = false;
    }

    /* =========================================================================================
     *  Méthodes implémentées dans la cadre du calcul de la satisfaction
     *  =========================================================================================
     */

    /**
     * Mesure le degré de satisfaction pour un étudiant (candidat).
     * Formule : ((nombre de voeux) - (position de l'établissement affecté dans la liste de vœux)) / nombre de voeux.
     * @return le degré de satisfaction pour un étudiant
     */
    public float degreSatisfaction() {
        float nbVoeux = (float) this.listeVoeux.size();
        return (nbVoeux - this.voeuEtudie) / nbVoeux;
    }
}
