package modeles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Etudiant {
    /* ATTRIBUTS */
    private static long compteurEtudiants = 0;
    private long id;
    private List<Etablissement> listeVoeux;
    private boolean estAffecte;
    private Etablissement etablissementAffecte;

    /* CONSTRUCTEUR */
    public Etudiant() {
        this.id = ++compteurEtudiants;
        this.estAffecte = false;
        this.etablissementAffecte = null;
        this.listeVoeux = new ArrayList<Etablissement>();
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

    /* ACCESSEURS */
    public long getId() {
        return id;
    }

    public List<Etablissement> getListeVoeux() {
        return listeVoeux;
    }

    public boolean isEstAffecte() {
        return estAffecte;
    }

    public Etablissement getEtablissementAffecte() {
        return etablissementAffecte;
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
}
