package gui;

import modeles.Sauvegarde;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface4 extends JFrame {

    Sauvegarde sauvegardeEtab, sauvegardeEtud;

    Boolean etabSelected , etudSelected;

    GridBagConstraints gbc ;
    JButton retourButton;

    public Interface4(Sauvegarde sauvegardeEtab, Sauvegarde sauvegardeEtud, Boolean etabSelected ,Boolean etudSelected){
        this.sauvegardeEtab = sauvegardeEtab;
        this.sauvegardeEtud = sauvegardeEtud;
        this.etabSelected = etabSelected;
        this.etudSelected = etudSelected;

        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        this.setVisible(true);
        this.setTitle("Resultats de Statistaiques");
        this.setLayout(new GridBagLayout());
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gbc = new GridBagConstraints();
        fillStatistics();
    }

    public void fillStatistics(){
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label0 = new JLabel("Statistiques");
        label0.setFont(new Font("Dialog", Font.BOLD, 17));
        this.add(label0, gbc);

        if (etabSelected && etudSelected){
            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel label1 = new JLabel("Priorite aux Etablissements");
            this.add(label1, gbc);
            label1.setFont(new Font("Dialog", Font.BOLD, 16));

            gbc.gridx=0;
            gbc.gridy=2;
            this.add(new JLabel(" - Degré de satisfaction d'un étudiant  "), gbc);


            gbc.gridx=1;
            gbc.gridy=2;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtudiant())),gbc);

            gbc.gridx=0;
            gbc.gridy=3;
            this.add(new JLabel(" - Degré satisfaction établissement selon nombre candidats acceptés  "), gbc);
            gbc.gridx=1;
            gbc.gridy=3;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes())),gbc);

            gbc.gridx=0;
            gbc.gridy=4;
            this.add(new JLabel(" - Degré satisfaction établissement selon capacité accueil  "), gbc);
            gbc.gridx=1;
            gbc.gridy=4;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtablissementsSelonCapaciteAccueil())),gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            JLabel label2 = new JLabel("Priorite aux Etudiants");
            this.add(label2, gbc);
            label2.setFont(new Font("Dialog", Font.BOLD, 16));

            gbc.gridx=0;
            gbc.gridy=6;
            this.add(new JLabel(" - Degré de satisfaction d'un étudiant  "), gbc);

            gbc.gridx=1;
            gbc.gridy=6;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtudiant())),gbc);

            gbc.gridx=0;
            gbc.gridy=7;
            this.add(new JLabel(" - Degré satisfaction établissement selon nombre candidats acceptés  "), gbc);
            gbc.gridx=1;
            gbc.gridy=7;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes())),gbc);

            gbc.gridx=0;
            gbc.gridy=8;
            this.add(new JLabel(" - Degré satisfaction établissement selon capacité accueil  "), gbc);
            gbc.gridx=1;
            gbc.gridy=8;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtablissementsSelonCapaciteAccueil())),gbc);
            gbc.gridx=3;
            gbc.gridy=9;
            gbc.ipady= 20;
            JButton classementButton = new JButton("Afficher Classement");
            this.add(classementButton, gbc);
            classementButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Interface5(sauvegardeEtab, sauvegardeEtud, true, true);
                }
            });

        }else if (etabSelected){

            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel label1 = new JLabel("Priorite aux Etablissements");
            this.add(label1, gbc);
            label1.setFont(new Font("Dialog", Font.BOLD, 16));

            gbc.gridx=0;
            gbc.gridy=2;
            this.add(new JLabel(" - Degré de satisfaction d'un étudiant  "), gbc);


            gbc.gridx=1;
            gbc.gridy=2;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtudiant())),gbc);

            gbc.gridx=0;
            gbc.gridy=3;
            this.add(new JLabel(" - Degré satisfaction établissement selon nombre candidats acceptés  "), gbc);
            gbc.gridx=1;
            gbc.gridy=3;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes())),gbc);

            gbc.gridx=0;
            gbc.gridy=4;
            this.add(new JLabel(" - Degré satisfaction établissement selon capacité accueil  "), gbc);
            gbc.gridx=1;
            gbc.gridy=4;
            this.add(new JLabel(Double.toString(sauvegardeEtab.getDegreSatisfactionEtablissementsSelonCapaciteAccueil())),gbc);
        }else if(etudSelected){
            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel label2 = new JLabel("Priorite aux Etudiants");
            this.add(label2, gbc);
            label2.setFont(new Font("Dialog", Font.BOLD, 16));

            gbc.gridx=0;
            gbc.gridy=2;
            this.add(new JLabel(" - Degré de satisfaction d'un étudiant  "), gbc);

            gbc.gridx=1;
            gbc.gridy=2;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtudiant())),gbc);

            gbc.gridx=0;
            gbc.gridy=3;
            this.add(new JLabel(" - Degré satisfaction établissement selon nombre candidats acceptés  "), gbc);
            gbc.gridx=1;
            gbc.gridy=3;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtablissementsSelonNombreCandidatsAcceptes())),gbc);

            gbc.gridx=0;
            gbc.gridy=4;
            this.add(new JLabel(" - Degré satisfaction établissement selon capacité accueil  "), gbc);
            gbc.gridx=1;
            gbc.gridy=4;
            this.add(new JLabel(Double.toString(sauvegardeEtud.getDegreSatisfactionEtablissementsSelonCapaciteAccueil())),gbc);

        }
        gbc.gridx=0;
        gbc.gridy=9 ;
        gbc.insets=new Insets(10,90,0,90);
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
