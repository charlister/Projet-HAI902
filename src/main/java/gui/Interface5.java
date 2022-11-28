package gui;

import modeles.Sauvegarde;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Interface5 extends JFrame {
    Sauvegarde sauvegardeEtab, sauvegardeEtud;

    Boolean etabSelected , etudSelected;

    GridBagConstraints gbc ;
    JButton retourButton;

    public Interface5(Sauvegarde sauvegardeEtab, Sauvegarde sauvegardeEtud, Boolean etabSelected , Boolean etudSelected){
        this.sauvegardeEtab = sauvegardeEtab;
        this.sauvegardeEtud = sauvegardeEtud;
        this.etabSelected = etabSelected;
        this.etudSelected = etudSelected;


        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        this.setVisible(true);
        this.setTitle("Classement");
        this.setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        fillClassment();
    }

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

    public void fillClassment(){
        Sauvegarde[] sauvegardes = new Sauvegarde[2];
        sauvegardes[0] = sauvegardeEtab;
        sauvegardes[1] = sauvegardeEtud;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label0 = new JLabel("Classement sauvegardes priorité étudiant");
        label0.setFont(new Font("Dialog", Font.BOLD, 16));
        this.add(label0, gbc);

        List<Sauvegarde> sauvegardesPrioriteEtudiant = ordonnerPrioriteEtudiant(sauvegardes);
        gbc.gridx = 0;
        gbc.gridy = 1;
        JTextArea text1 = new JTextArea(sauvegardesPrioriteEtudiant.get(0).toString());
        text1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.add(text1,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JTextArea(sauvegardesPrioriteEtudiant.get(1).toString()),gbc);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes = ordonnerPrioriteEtablissementsSelonNombreCandidatsAcceptes(sauvegardes);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel label1 = new JLabel("Classement sauvegardes priorité établissement selon nombre de candidats acceptés ");
        label1.setFont(new Font("Dialog", Font.BOLD, 16));
        this.add(label1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes.get(0).toString()),gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes.get(1).toString()),gbc);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonCapaciteAccueil = ordonnerPrioriteEtablissementsSelonCapaciteAccueil(sauvegardes);
        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel label3 = new JLabel("Classement sauvegardes priorité établissement selon la capacité d'acceuil");
        label3.setFont(new Font("Dialog", Font.BOLD, 16));
        this.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonCapaciteAccueil.get(0).toString()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        this.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonCapaciteAccueil.get(1).toString()),gbc);

        gbc.gridx=0;
        gbc.gridy=9 ;
        gbc.weighty = 1;
        gbc.ipady= 20;
        retourButton = new JButton("recommencer");
        this.add(retourButton, gbc);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Interface1();
            }
        });
    }

}
