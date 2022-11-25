package main;

import modeles.Sauvegarde;

import java.util.ArrayList;
import java.util.List;

public class Test {

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

    public static List<Sauvegarde> ordonnerPrioriteEtablissement(Sauvegarde[] sauvegardes) {
        List<Sauvegarde> result = new ArrayList<>();
        if (sauvegardes.length == 2) {
            if (sauvegardes[0].getDegreSatisfactionEtablissement() > sauvegardes[1].getDegreSatisfactionEtablissement()) {
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

    public static void main(String[] args) {
        /*PlateformeCandidatures plateformeCandidatures= new PlateformeCandidatures(3, 1, 1, 3);
        System.out.println(plateformeCandidatures.toString());

        plateformeCandidatures.mariageStableEtablissements();
        plateformeCandidatures.affichage();*/

//        PlateformeCandidatures testCours = new PlateformeCandidatures();
        PlateformeCandidatures testCours = new PlateformeCandidatures(5, 1, 1, 5);

        System.out.println(testCours);

        testCours.mariageStableEtablissements();
        //testCours.affichageAffectations();
        Sauvegarde sauvegardeCoursEtab = testCours.sauvegarder(Sauvegarde.Priorite.ETABLISSEMENT);

//        System.out.println(sauvegardeCoursEtab);

        testCours.restaurer();
        testCours.mariageStableEtudiants();
        //testCours.affichageAffectations();
        Sauvegarde sauvegardeCoursEtu = testCours.sauvegarder(Sauvegarde.Priorite.ETUDIANT);

//        System.out.println(sauvegardeCoursEtu);

        Sauvegarde[] sauvegardes = new Sauvegarde[2];
        sauvegardes[0] = sauvegardeCoursEtab;
        sauvegardes[1] = sauvegardeCoursEtu;

        List<Sauvegarde> sauvegardesPrioriteEtudiant = ordonnerPrioriteEtudiant(sauvegardes);
        System.out.println("Sauvegardes priorité étudiant : ");
        for (Sauvegarde sauvegarde :
                sauvegardesPrioriteEtudiant) {
            System.out.println(sauvegarde);
        }

        List<Sauvegarde> sauvegardesPrioriteEtablissement = ordonnerPrioriteEtablissement(sauvegardes);
        System.out.println("Sauvegardes priorité établissement : ");
        for (Sauvegarde sauvegarde :
                sauvegardesPrioriteEtablissement) {
            System.out.println(sauvegarde);
        }
    }
}
