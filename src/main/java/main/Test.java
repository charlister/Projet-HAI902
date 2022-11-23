package main;

public class Test {
    public static void main(String[] args) {
        /*PlateformeCandidatures plateformeCandidatures= new PlateformeCandidatures(3, 1, 1, 3);
        System.out.println(plateformeCandidatures.toString());

        plateformeCandidatures.mariagemixte();
        plateformeCandidatures.affichage();*/

        PlateformeCandidatures testCours = new PlateformeCandidatures();
        System.out.println(testCours);

        testCours.mariageStableEtablissements();
        testCours.affichageAffectations();
    }
}
