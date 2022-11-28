package gui;

import modeles.Etablissement;
import modeles.Etudiant;
import modeles.PlateformeCandidatures;
import modeles.Sauvegarde;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Interface2 extends JFrame{
    List<Etudiant> etudiants= new ArrayList<>();
    List<Etablissement> etablissements=new ArrayList<>();

    JButton buttonAffectation ;
    JCheckBox cbPrioriteEtab;
    JCheckBox cbPrioriteStud;

    PlateformeCandidatures plateformeCandidatures;

    public Interface2(PlateformeCandidatures plateformeCandidatures) {
         this.plateformeCandidatures = plateformeCandidatures;
        this.buttonAffectation = new JButton("Lancer Mariage Stable");
        this.cbPrioriteEtab = new JCheckBox("Priorite aux Etablissements");
        this.cbPrioriteStud = new JCheckBox("Priorite aux Etudiants");
        this.setTitle("Affichage");
        this.setLayout(new GridBagLayout());
        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fillList();
    }

    public void fillList(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label1 = new JLabel("Liste des étudiants avec leurs voeux");
        label1.setFont(new Font("Dialog", Font.BOLD, 17));
        this.add(label1, gbc);
        gbc.ipady = 20;
        gbc.ipadx=20;
        etudiants=plateformeCandidatures.getCandidats();
        for(int i=0;i<etudiants.size();i++){
            gbc.gridx = 0;
            gbc.gridy=i+1;
            this.add(new JLabel("Etudiant "+ Long.toString(etudiants.get(i).getId())), gbc);
            for(int j=0;j<etudiants.get(i).getListeVoeux().size();j++){
                gbc.gridx=j+1;
                gbc.gridy = i+1;
                this.add(new JLabel("Etablissement "+Long.toString(etudiants.get(i).getListeVoeux().get(j).getId())), gbc);
            }
        }

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = etudiants.size()+1;
        JLabel label2 = new JLabel("Liste des établissements avec classements");
        label2.setFont(new Font("Dialog", Font.BOLD, 17));
        this.add(label2, gbc);

        int k=0, y = 0;
        etablissements = plateformeCandidatures.getEtablissements();
        for(int i=etudiants.size()+1;i<etablissements.size()+etudiants.size()+1;i++){
            if (k < etablissements.size()) {
                gbc.gridx = 0;
                gbc.gridy = i + 1;
                this.add(new JLabel("Etablissement " + Long.toString(etablissements.get(k).getId())), gbc);

                for (int l = 0; l < etablissements.get(k).getPreferences().size() ; l++) {
                    gbc.gridx = l + 1;
                    gbc.gridy = i + 1;
                    y = gbc.gridy;
                    this.add(new JLabel("Etudiant " + Long.toString(etablissements.get(k).getPreferences().get(l).getId())), gbc);
                }
                k++;
            }

        }
        int y1 = 0;
        gbc.gridx = 0;
        gbc.gridy = y+1;
        y1 = y+1;
        JLabel label3 = new JLabel("Choisir au moins une option ");
        label3.setFont(new Font("Dialog", Font.BOLD, 17));
        this.add(label3, gbc);

        gbc.gridx = 0;
        gbc.gridy = y1+1;
        int y2 = y1+1;
        this.add(cbPrioriteEtab, gbc);

        gbc.gridx = 1;
        gbc.gridy = y1+1;
        int y3 = y2+1;
        this.add(cbPrioriteStud, gbc);

        gbc.gridx = 0;
        gbc.gridy = y3+1;
        this.add(buttonAffectation, gbc);

        buttonAffectation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbPrioriteEtab.isSelected() && cbPrioriteStud.isSelected()){
                     Sauvegarde sauvegardeEtud = plateformeCandidatures.mariageStableEtudiants();
                     plateformeCandidatures.restaurer();
                    Sauvegarde sauvegardeEtab = plateformeCandidatures.mariageStableEtablissements();
                    new Interface3(sauvegardeEtud, sauvegardeEtab,true,true);
                }
                else if (cbPrioriteEtab.isSelected()){
                    plateformeCandidatures.restaurer();
                    Sauvegarde sauvegardeEtab = plateformeCandidatures.mariageStableEtablissements();
                    new Interface3(null, sauvegardeEtab,true,false);
                }
                else if (cbPrioriteStud.isSelected()) {
                    plateformeCandidatures.restaurer();
                     Sauvegarde sauvegardeEtud = plateformeCandidatures.mariageStableEtudiants();
                    new Interface3(sauvegardeEtud, null,false,true);
                }
                //dispose();
            }
        });

    }


}