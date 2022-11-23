package main;

import modeles.Sauvegarde;

public class Test {
    public static void main(String[] args) {
        /*PlateformeCandidatures plateformeCandidatures= new PlateformeCandidatures(3, 1, 1, 3);
        System.out.println(plateformeCandidatures.toString());

        plateformeCandidatures.mariageStableEtablissements();
        plateformeCandidatures.affichage();*/

//        PlateformeCandidatures testCours = new PlateformeCandidatures();
        PlateformeCandidatures testCours = new PlateformeCandidatures(10, 1, 1, 10);

        System.out.println(testCours);

        testCours.mariageStableEtablissements();
        //testCours.affichageAffectations();
        Sauvegarde sauvegardeCoursEtab = testCours.sauvegarder("Priorité aux établissements");

        System.out.println(sauvegardeCoursEtab);

        testCours.restaurer();
        testCours.mariageStableEtudiants();
        //testCours.affichageAffectations();
        Sauvegarde sauvegardeCoursEtu = testCours.sauvegarder("Priorité aux étudiants");

        System.out.println(sauvegardeCoursEtu);
    }
}
