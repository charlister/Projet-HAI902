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

    JPanel panel;
    JScrollPane scrollpane;

    public Interface5(Sauvegarde sauvegardeEtab, Sauvegarde sauvegardeEtud, Boolean etabSelected , Boolean etudSelected){
        this.sauvegardeEtab = sauvegardeEtab;
        this.sauvegardeEtud = sauvegardeEtud;
        this.etabSelected = etabSelected;
        this.etudSelected = etudSelected;

        this.panel = new JPanel();
        this.scrollpane = new JScrollPane(panel);

        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        this.setTitle("Classement");
        //this.setLayout(new GridBagLayout());
        fillClassment();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
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

        this.panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH ;
        gbc.weightx = 0.5 ;
        gbc.weighty = 0.5 ;

        Sauvegarde[] sauvegardes = new Sauvegarde[2];
        sauvegardes[0] = sauvegardeEtab;
        sauvegardes[1] = sauvegardeEtud;

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label0 = new JLabel("Classement algorithmes selon les différents critères");
        label0.setFont(new Font("Dialog", Font.BOLD, 16));
        this.panel.add(label0, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label00 = new JLabel("Critère : satisfaction des étudiants ");
        label00.setFont(new Font("Dialog", Font.BOLD, 16));
        this.panel.add(label00, gbc);

        List<Sauvegarde> sauvegardesPrioriteEtudiant = ordonnerPrioriteEtudiant(sauvegardes);
        gbc.gridx = 0;
        gbc.gridy = 2;
        JTextArea text1 = new JTextArea(sauvegardesPrioriteEtudiant.get(0).toString());
        text1.setEditable(false);
        this.panel.add(text1,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JTextArea text2 = new JTextArea(sauvegardesPrioriteEtudiant.get(1).toString());
        text2.setEditable(false);
        this.panel.add(text2,gbc);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes = ordonnerPrioriteEtablissementsSelonNombreCandidatsAcceptes(sauvegardes);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel label1 = new JLabel("Critère : satisfaction des établissements");
        label1.setFont(new Font("Dialog", Font.BOLD, 16));
        this.panel.add(label1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.panel.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes.get(0).toString()),gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.panel.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonNombreCandidatsAcceptes.get(1).toString()),gbc);

        List<Sauvegarde> sauvegardesPrioriteEtablissementsSelonCapaciteAccueil = ordonnerPrioriteEtablissementsSelonCapaciteAccueil(sauvegardes);
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel label3 = new JLabel("Critère : satisfaction des établissements par rapport à leur capacité d'accueil");
        label3.setFont(new Font("Dialog", Font.BOLD, 16));
        this.panel.add(label3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.panel.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonCapaciteAccueil.get(0).toString()),gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        this.panel.add(new JTextArea(sauvegardesPrioriteEtablissementsSelonCapaciteAccueil.get(1).toString()),gbc);

        gbc.gridx=0;
        gbc.gridy=10 ;
        gbc.weighty = 1;
        gbc.ipady= 20;
        gbc.insets=new Insets(10,500,10,500);
        retourButton = new JButton("recommencer");
        this.panel.add(retourButton, gbc);
        this.add(this.scrollpane);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Interface1();
            }
        });
    }

}
