package org.dhbw.swe.visualization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

public class PlayerDialog extends JDialog implements ActionListener {
    private Map<String, Boolean> data = new HashMap<>();
    private Map<JRadioButton, JComboBox> radioComboMap = new HashMap<>();
    private Map<JRadioButton, JTextField> radioTextMap = new HashMap<>();
    private Map<JTextField, JComboBox> textComboMap = new LinkedHashMap<>();

    public PlayerDialog(Frame parent, int playerNumber, List<String> playerSelection) {

        super(parent,"Wähle die Spieler",true);

        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(playerNumber + 1);
        gridLayout.setColumns(1);
        panel.setLayout(gridLayout);

        for(int i = 0; i < playerNumber; i++){

            JPanel panelPlayer = getPlayerPanel(i, playerSelection);
            panel.add(panelPlayer);

        }

        JPanel panelBtn = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        panelBtn.setLayout(flowLayout);
        JButton btnOK = getBtnOK();
        panelBtn.add(btnOK);
        panel.add(panelBtn);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel);
        setSize(800, 500);

    }

    public void actionPerformed(ActionEvent ae) {

        dispose();

    }

    public Map<String, Boolean> run() {

        this.setVisible(true);
        return data;

    }

    private JButton getBtnOK() {

        JButton btnOK = new JButton();
        btnOK.setText("Ok");

        btnOK.addActionListener(e -> {

            data.clear();

            for(Map.Entry<JTextField, JComboBox> entry : textComboMap.entrySet()){

                JTextField txt = entry.getKey();
                JComboBox combo = entry.getValue();

                if(txt.isEditable() && txt.getText().isEmpty()){
                    //Nicht für jeden Spieler einen Namen vergeben

                    JOptionPane.showMessageDialog(this,
                            "Vergebe bitte für jeden Spieler einen Namen",
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;


                }else if(txt.isEditable()){
                    //Neuer Spieler ausgewählt

                    if(data.containsKey(txt.getText())){

                        JOptionPane.showMessageDialog(this,
                                "Vergebe bitte für jeden Spieler einen ANDEREN Namen",
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;

                    }else{

                        data.put(txt.getText(), true);


                    }

                }else{
                    //Existierender Spieler ausgewählt

                    if(data.containsKey(combo.getSelectedItem().toString())){

                        JOptionPane.showMessageDialog(this,
                                "Wähle UNTERSCHIEDLICHE bereits existierende Spieler",
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;

                    }else{

                        data.put(combo.getSelectedItem().toString(), false);

                    }

                }

            }

            dispose();

        });

        return btnOK;

    }


    private JPanel getPlayerPanel(int index, List<String> playerSelection){

        JPanel panel = new JPanel();
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Spieler " + (index + 1));
        panel.setBorder(title);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);
        gridLayout.setColumns(2);
        panel.setLayout(gridLayout);

        JRadioButton btnNewPlayer = new JRadioButton();
        btnNewPlayer.setText("Neuer Spieler");
        btnNewPlayer.setSelected(true);
        JRadioButton btnExistingPlayer = new JRadioButton();
        btnExistingPlayer.setText("Existierender Spieler");
        ButtonGroup group = new ButtonGroup();
        group.add(btnNewPlayer);
        group.add(btnExistingPlayer);

        JPanel panelName = new JPanel();
        FlowLayout flowLayoutName = new FlowLayout();
        panelName.setLayout(flowLayoutName);
        JLabel lblName = new JLabel();
        lblName.setText("Name: ");
        JTextField txtName = new JTextField();
        txtName.setColumns(20);
        panelName.add(lblName);
        panelName.add(txtName);

        JPanel panelChoose = new JPanel();
        FlowLayout flowLayoutChoose = new FlowLayout();
        panelChoose.setLayout(flowLayoutChoose);
        String comboBoxListe[] = playerSelection.toArray(new String[playerSelection.size()]);
        JComboBox comboPlayer = new JComboBox(comboBoxListe);
        comboPlayer.setEnabled(false);
        panelChoose.add(comboPlayer);
        panelChoose.add(comboPlayer);

        radioTextMap.put(btnNewPlayer, txtName);
        radioComboMap.put(btnExistingPlayer, comboPlayer);
        textComboMap.put(txtName, comboPlayer);

        btnNewPlayer.addActionListener(e -> {

            JRadioButton btn = ((JRadioButton)e.getSource());
            JTextField txt = radioTextMap.get(btn);
            JComboBox combo = getCombo(radioTextMap.get(btn));

            if(!btn.isSelected()){
                txt.setEditable(false);
                combo.setEnabled(true);
            }else{
                txt.setEditable(true);
                combo.setEnabled(false);
            }

        });

        btnExistingPlayer.addActionListener(e -> {

            JRadioButton btn = ((JRadioButton)e.getSource());
            JComboBox combo = radioComboMap.get(btn);
            JTextField txt = getTextField(combo);

            if(!btn.isSelected()){
                txt.setEditable(true);
                combo.setEnabled(false);
            }else{
                txt.setEditable(false);
                combo.setEnabled(true);
            }

        });

        panel.add(btnNewPlayer);
        panel.add(btnExistingPlayer);
        panel.add(panelName);
        panel.add(panelChoose);

        //Schauen was ausgewählt wurde und je nachdem uuid oder name in resultListe

        return panel;

    }

    private JComboBox getCombo(JTextField txt){

        for(Map.Entry<JTextField, JComboBox> entry : textComboMap.entrySet()){

            if(entry.getKey().equals(txt)){

                return entry.getValue();

            }

        }

        return null;

    }

    private JTextField getTextField(JComboBox combo){

        for(Map.Entry<JTextField, JComboBox> entry : textComboMap.entrySet()){

            if(entry.getValue().equals(combo)){

                return entry.getKey();

            }

        }

        return null;

    }

}
