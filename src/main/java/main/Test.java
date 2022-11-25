package main;

import modeles.Sauvegarde;

import java.util.ArrayList;
import java.util.List;

public class Test {

    /**
     * Ordonne de manière décroissante les deux versions de l'algorithme selon la priorité aux étudiants.
     * @param sauvegardes tableau de Sauvegarde contenant l'application des deux versions de l'algorithme du mariage stable.
     * @return une liste où les deux versions de l'algorithme sont classés dans l'ordre décroissant.
     */
    public static List<Sauvegarde> ordonnerPrioriteEtudiant(Sauvegarde[] sauvegardes) {
        List<Sauvegarde> result = new ArrayList<>();
        if (sauvegardes.length == 2) {
            if (sauvegardes[0].getDegreSatisfactionEtudiant() > sauvegardes[1].getDegreSatisfactionEtudiant()) {
                result.add(sauvegardes[0]);
                result.add(sauvegardes[1]);
            }
            else {
                result.add(sauvegardes[1]);
                result.add(sauvegardes[0]);
            }
        }
        return result;
    }

    /**
     * Ordonne de manière décroissante les deux versions de l'algorithme selon la priorité aux établissements et par rapport au nombre de candidats acceptés.
     * @param sauvegardes tableau de Sauvegarde contenant l'application des deux versions de l'algorithme du mariage stable.
     * @return une liste où les deux versions de l'algorithme sont classés dans l'ordre décroissant.
     */
    public static List<Sauvegarde> ordonnerPrioriteEtablissementsSelonNombreCandidatsAcceptes(Sauvegarde[] sauvegardes) {
        List<Sauvegarde> result = new ArrayList<>();
        if (sauvegardes.length == 2) {
            if (sauvegardes[0].getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes() > sauvegardes[1].getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes()) {
                result.add(sauvegardes[0]);
                result.add(sauvegardes[1]);
            }
            else {
                result.add(sauvegardes[1]);
                result.add(sauvegardes[0]);
            }
        }
        return result;
    }

    /**
     * Ordonne de manière décroissante les deux versions de l'algorithme selon la priorité aux établissements et par rapport à la capacité d'accueil des établissements.
     * @param sauvegardes tableau de Sauvegarde contenant l'application des deux versions de l'algorithme du mariage stable.
     * @return une liste où les deux versions de l'algorithme sont classés dans l'ordre décroissant.
     */
    public static List<Sauvegarde> ordonnerPrioriteEtablissementsSelonCapaciteAccueil(Sauvegarde[] sauvegardes) {
        List<Sauvegarde> result = new ArrayList<>();
        if (sauvegardes.length == 2) {
            if (sauvegardes[0].getDegreSatisfactionEtablissementsSelonCapaciteAccueil() > sauvegardes[1].getDegreSatisfactionEtablissementsSelonCapaciteAccueil()) {
                result.add(sauvegardes[0]);
                result.add(sauvegardes[1]);
            }
            else {
                result.add(sauvegardes[1]);
                result.add(sauvegardes[0]);
            }
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        PlateformeCandidatures testCours = new PlateformeCandidatures(5, 1, 1, 5);

        System.out.println(testCours);

        Thread.sleep(2000);

        Sauvegarde sauvegardeCoursEtab = testCours.mariageStableEtablissements();
        System.err.println("Sauvegarde priorité établissement");
        System.out.println(sauvegardeCoursEtab);
        //testCours.affichageAffectations();
        Thread.sleep(2000);

        testCours.restaurer();

        Sauvegarde sauvegardeCoursEtu = testCours.mariageStableEtudiants();
        System.err.println("Sauvegarde priorité étudiant");
        System.out.println(sauvegardeCoursEtu);
        //testCours.affichageAffectations();
        Thread.sleep(2000);

        Sauvegarde[] sauvegardes = new Sauvegarde[2];
        sauvegardes[0] = sauvegardeCoursEtab;
        sauvegardes[1] = sauvegardeCoursEtu;

        List<Sauvegarde> sauvegardesPrioriteEtudiant = ordonnerPrioriteEtudiant(sauvegardes);
        System.err.println("Classement sauvegardes priorité étudiant : ");
        for (Sauvegarde sauvegarde :
                sauvegardesPrioriteEtudiant) {
            System.out.println(sauvegarde);
        }

        Thread.sleep(2000);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes = ordonnerPrioriteEtablissementsSelonNombreCandidatsAcceptes(sauvegardes);
        System.err.println("Classement sauvegardes priorité établissement selon nombre de candidats acceptés : ");
        for (Sauvegarde sauvegarde :
                sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes) {
            System.out.println(sauvegarde);
        }

        Thread.sleep(2000);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonCapaciteAccueil = ordonnerPrioriteEtablissementsSelonCapaciteAccueil(sauvegardes);
        System.err.println("Classement sauvegardes priorité établissement selon capacité d'accueil : ");
        for (Sauvegarde sauvegarde :
                sauvegardesPrioriteEtablissementsSelonCapaciteAccueil) {
            System.out.println(sauvegarde);
        }
    }
}
