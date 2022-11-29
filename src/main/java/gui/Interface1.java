package gui;



import modeles.PlateformeCandidatures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;


public class Interface1 extends JFrame {

    GridBagConstraints gbc;
    JButton buttonValider ;

    JTextField textField1, textField2, textField3;

    public static void main(String[] args) {
        Interface1  i = new Interface1();
    }
    public Interface1() {

        setLayout(new GridBagLayout());
        gbc  = new GridBagConstraints();
        buttonValider = new JButton("Valider");
        textField1 =new JTextField("0");
        textField2 =new JTextField("0");
        textField3 =new JTextField("1");
        afficherFrame();
        setTitle("Configuration");
        Toolkit outil = getToolkit();
        this.setSize(outil.getScreenSize());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void afficherFrame(){
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.ipadx=20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label1 = new JLabel("Configuration");
        label1.setFont(new Font("Dialog", Font.BOLD, 20));
        this.add(label1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Nombre d'établissements"), gbc);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.ipady = 10;
        gbc.ipadx=40;
       // JTextField textField1 =new JTextField("0");
        this.add(textField1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipady = 20;
        gbc.ipadx=20;
        this.add(new JLabel("Nombre d'étudiants"), gbc);
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.ipady = 10;
        gbc.ipadx=40;
        //JTextField textField2 =new JTextField("0");
        this.add(textField2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipady = 20;
        gbc.ipadx=20;
        this.add(new JLabel("Capacité d'acceuil"), gbc);
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.ipady = 10;
        gbc.ipadx=40;
        //JTextField textField3 =new JTextField("1");
        this.add(textField3, gbc);
        gbc.gridx=0;
        gbc.gridy=4;
        this.add(buttonValider,gbc);


        buttonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String nbreEtablissement = textField1.getText();
                String nbreEtudiant = textField2.getText();
                String capacite = textField3.getText();
               if(parseInt(capacite) * parseInt(nbreEtablissement) >= parseInt(nbreEtudiant)){
                   PlateformeCandidatures plateformeCandidatures = new PlateformeCandidatures(parseInt(nbreEtudiant), parseInt(capacite), parseInt(capacite), parseInt(nbreEtablissement));
                   new Interface2(plateformeCandidatures);
                } else {
                   JOptionPane.showMessageDialog(new Interface1(), "Veuillez entrer des effectifs tels que :\nLe nombre d'étudiants soit inférieur ou égal à la multiplication de la capacité des établissements par le nombre d'établissement");
                }

            }
        });

    }

}