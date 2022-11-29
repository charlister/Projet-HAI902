package gui;

import modeles.Etablissement;
import modeles.Etudiant;
import modeles.Sauvegarde;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Interface3 extends JFrame {

    GridBagConstraints gbc ;
    Sauvegarde sauvegardeEtab, sauvegardeEtud;

    Boolean etabSelected , etudSelected;

    JButton retourButton, statistiqueButton;
    Interface3(Sauvegarde sauvegardeEtud, Sauvegarde sauvegardeEtab, Boolean etabSelected, Boolean etudSelected ){

        this.sauvegardeEtab = sauvegardeEtab;
        this.sauvegardeEtud = sauvegardeEtud;
        this.etabSelected = etabSelected;
        this.etudSelected = etudSelected;

        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        this.setVisible(true);
        this.setTitle("Resultats d'affectation");
        this.setLayout(new GridBagLayout());

        this.gbc = new GridBagConstraints();
        fillTable();
    }
    public void fillTable(){

        String header[] = {"Etablissements", "Etudiants"};
        gbc.fill = GridBagConstraints.HORIZONTAL;

        statistiqueButton = new JButton("Afficher Statistiques");

        if(etabSelected && etudSelected){
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(new JLabel("Resultats d'affectation Etablissements"), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            this.add(new JLabel("Resultats d'affectation Etudiants"), gbc);

            Object dataEtab[][] = new Object[sauvegardeEtab.getAssociations().size() * sauvegardeEtab.getAssociations().entrySet().iterator().next().getKey().getCapaciteAccueil()][2];

                int i = 0 ;
                for (Map.Entry<Etablissement, List<Etudiant>> map : sauvegardeEtab.getAssociations().entrySet()) {
                    for(int k = 0; k < map.getValue().size(); k++ ) {

                        dataEtab[i][0] = "Etablissement " + map.getKey().getId();
                        dataEtab[i][1] = "Etudiant " + map.getValue().get(k).getId();
                        i++;

                    }
                }
            gbc.gridx = 0;
            gbc.gridy = 1;
            JTable resultats = new JTable(dataEtab, header);
            this.add(new JScrollPane(resultats), gbc);

            Object dataEtud[][] = new Object[sauvegardeEtud.getAssociations().size() * sauvegardeEtud.getAssociations().entrySet().iterator().next().getKey().getCapaciteAccueil()][2];
            int j = 0 ;
            for (Map.Entry<Etablissement, List<Etudiant>> map : sauvegardeEtud.getAssociations().entrySet()) {
                for(int k = 0; k < map.getValue().size(); k++ ) {

                    dataEtud[j][0] = "Etablissement " + map.getKey().getId();
                    dataEtud[j][1] = "Etudiant " + map.getValue().get(k).getId();
                    j++;

                }
            }
            gbc.gridx = 1;
            gbc.gridy = 1;
            JTable res = new JTable(dataEtud, header);
            this.add(new JScrollPane(res), gbc);

            statistiqueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //dispose();
                    new Interface4(sauvegardeEtab, sauvegardeEtud, true, true);
                }
            });

        } else if(etabSelected){
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(new JLabel("Resultats d'affectation Etablissements"), gbc);

            Object dataEtab[][] = new Object[sauvegardeEtab.getAssociations().size() * sauvegardeEtab.getAssociations().entrySet().iterator().next().getKey().getCapaciteAccueil()][2];

            int i = 0 ;
            for (Map.Entry<Etablissement, List<Etudiant>> map : sauvegardeEtab.getAssociations().entrySet()) {
                for(int k = 0; k < map.getValue().size(); k++ ) {

                    dataEtab[i][0] = "Etablissement " + map.getKey().getId();
                    dataEtab[i][1] = "Etudiant " + map.getValue().get(k).getId();
                    i++;

                }

                gbc.gridx = 0;
                gbc.gridy = 1;
                JTable resultats = new JTable(dataEtab, header);
                this.add(new JScrollPane(resultats), gbc);
                statistiqueButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //dispose();
                        new Interface4(sauvegardeEtab, sauvegardeEtud, true, false);
                    }
                });
            }
        }else if(etudSelected){
            gbc.gridx = 0;
            gbc.gridy = 0;
            this.add(new JLabel("Resultats d'affectation Etudiants"), gbc);

            Object dataEtud[][] = new Object[sauvegardeEtud.getAssociations().size() * sauvegardeEtud.getAssociations().entrySet().iterator().next().getKey().getCapaciteAccueil()][2];
            int j = 0 ;
            for (Map.Entry<Etablissement, List<Etudiant>> map : sauvegardeEtud.getAssociations().entrySet()) {
                for(int k = 0; k < map.getValue().size(); k++ ) {

                    dataEtud[j][0] = "Etablissement " + map.getKey().getId();
                    dataEtud[j][1] = "Etudiant " + map.getValue().get(k).getId();
                    j++;

                }
            }
            gbc.gridx = 0;
            gbc.gridy = 1;
            JTable res = new JTable(dataEtud, header);
            this.add(new JScrollPane(res), gbc);

            statistiqueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //dispose();
                    new Interface4(sauvegardeEtab, sauvegardeEtud, false, true);
                }
            });
        }

        gbc.gridx= 0;
        gbc.gridy= 3;
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

        gbc.gridx= 1;
        gbc.gridy= 3;
        gbc.insets=new Insets(10,90,0,90);
        gbc.ipady= 20;
        this.add(statistiqueButton, gbc);

    }

}
