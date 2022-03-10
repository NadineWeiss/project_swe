package org.dhbw.swe.visualization;

import org.dhbw.swe.board.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {

    private List<FieldButton> btns = new ArrayList<>();
    private DiceButton btnNewDice = new DiceButton();
    private DiceButton btnPreviousDice = new DiceButton();
    private JPanel panel = new JPanel();
    private JPanel panelField = new JPanel();
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Color turnColor;

    public GameFrame() throws HeadlessException {

        btnNewDice.addActionListener(e -> {

            DiceButton btn = ((DiceButton)e.getSource());
            if(btn.isClickable())
                changes.firePropertyChange( "dice", "x", "y");

        });

    }

    public void newGame(List<FieldInterface> fields){

        panelField.removeAll();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(setPanelCoordinate(), BorderLayout.NORTH);
        panel.add(setPanelField(), BorderLayout.CENTER);

        setGamePieces(fields);

        this.setTitle("Mensch Ärgere Dich Nicht");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.setSize(800, 900);
        this.setVisible(true);

    }

    public JPanel setPanelField(){

        btns.clear();
        turnColor = null;

        panelField.setLayout(new GridLayout(11, 11));

        for(int i = 0; i < 121; i++){
            JPanel p = new JPanel();
            p.setBackground(new Color(127, 127, 127));
            panelField.add(p);
        }

        addInitFields(panelField, Graph.INSTANCE.four.subList(0, 16));
        addRemainigFields(panelField);
        addTargetFields(panelField, Graph.INSTANCE.four.subList(56, 72));

        return panelField;

    }

    public JPanel setPanelCoordinate(){

        JPanel panelCoordinate = new JPanel();
        panelCoordinate.setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton newGameBtn = new JButton("Neues Spiel");
        newGameBtn.addActionListener(e -> changes.firePropertyChange( "new", "x", "y"));
        newGameBtn.setFocusPainted(false);
        panelMenu.add(newGameBtn);

        JPanel panelDice = new JPanel();
        panelDice.add(btnNewDice);
        panelDice.add(btnPreviousDice);
        btnPreviousDice.setClickable(false);
        btnPreviousDice.setColor(Color.RED);

        panelCoordinate.add(panelMenu, BorderLayout.NORTH);
        panelCoordinate.add(panelDice, BorderLayout.CENTER);

        return panelCoordinate;

    }

    public int getPlayerNumber(){

        Object[] possibilities = {"2", "3", "4"};
        int index = JOptionPane.showOptionDialog(this, "Wähle die Anzahl an Spielern: ",
                "Initialisierung", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibilities, "4");

        if(index == -1)
            return 4;

        return index + 2;

    }

    public int getAlgoNumber(int playerNumber){

        Object[] possibilities = new Object[playerNumber];
        for(int i = 0; i < playerNumber; i++){

            possibilities[i] = i;

        }

        int index = JOptionPane.showOptionDialog(this, "Wähle die Anzahl an durch den Algorithmus gespielten Spielern: ",
                "Initialisierung", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibilities, "4");

        if(index == -1)
            return 0;

        return index;

    }

    public void winner(Color color){

        JOptionPane.showMessageDialog(this, "Farbe " + color.toString() + " hat gewonnen.",
                "Glückwunsch :)", JOptionPane.INFORMATION_MESSAGE);

    }

    public void diced(int diceValue){

        btnPreviousDice.setDice(diceValue, turnColor);
        btnNewDice.setClickable(false);

        btns.stream().filter(x -> x.getPieceColor() != null && x.getPieceColor().equals(turnColor))
                .forEach(x -> x.setClickable(true));

        this.revalidate();
        this.repaint();

    }

    private ActionListener clicked = e -> {

        FieldButton btn = ((FieldButton)e.getSource());

        if(btn.isClickable()){
            if(btn.getPieceColor() != null && btn.getPieceColor() == turnColor){

                btns.parallelStream().forEach(x -> x.setBackgroundImage(false));
                btn.setBackgroundImage(true);
                changes.firePropertyChange( "calculate", "", ((FieldButton)e.getSource()).getIndex());

            }else{

                btns.parallelStream().forEach(x -> x.setBackgroundImage(false));
                changes.firePropertyChange( "move", "", ((FieldButton)e.getSource()).getIndex());

            }
        }

    };

    public void markAdditionalField(int index){

        btns.get(index).setBackgroundImage(true);
        btns.get(index).setClickable(true);

    }

    public void setTurn(Color color, boolean algo){

        turnColor = color;

        if(!algo)
            btnNewDice.setClickable(true);
        btnNewDice.setDice(color);

        btns.stream().forEach(x -> x.setClickable(false));

        if(algo)
            changes.firePropertyChange("dice", "x", "y");


        /*panel.revalidate();
        panel.repaint();*/

    }

    public void setGamePieces(List<FieldInterface> fields) {

        btns.stream().forEach(x -> x.removePiece());

        fields.stream().filter(x -> x.getGamePiece() != null).forEach(x -> {

            btns.get(fields.indexOf(x)).setPiece(x.getGamePiece().color());

        });

    }

    public void addPropertyChangeListener( PropertyChangeListener l )
    {
        changes.addPropertyChangeListener( l );
    }

    public void removePropertyChangeListener( PropertyChangeListener l )
    {
        changes.removePropertyChangeListener( l );
    }

    private void addRemainigFields(JPanel panel){

        int row = 0;
        int column = 5;

        for(Node node : Graph.INSTANCE.four.subList(16, 56)) {

            addBtn(panel, node, row, column);

            column = column + updateColumn(node.getDefaultEdge().getDirection());
            row = row + updateRow(node.getDefaultEdge().getDirection());

        };

    }

    private void addTargetFields(JPanel panel, List<Node> nodes){

        addTargetFields(panel, nodes.subList(0, 4), 1, 5, 1, 0);
        addTargetFields(panel, nodes.subList(4, 8), 5, 9, 0, -1);
        addTargetFields(panel, nodes.subList(8, 12), 9, 5, -1, 0);
        addTargetFields(panel, nodes.subList(12, 16), 5, 1, 0, 1);

    }

    private void addTargetFields(JPanel panel, List<Node> nodes, int startRow, int startColumn,
                                 int rowOffset, int columnOffset){

        for(int i = 0; i < 4; i++){

            addBtn(panel, nodes.get(i), startRow, startColumn);

            startRow += rowOffset;
            startColumn += columnOffset;

        }

    }

    private void addInitFields(JPanel panel, List<Node> nodes){

        addInitFields(panel, nodes.subList(0, 4), 0, 9);
        addInitFields(panel, nodes.subList(4, 8), 9, 9);
        addInitFields(panel, nodes.subList(8, 12), 9, 0);
        addInitFields(panel, nodes.subList(12, 16), 0, 0);

    }

    private void addInitFields(JPanel panel, List<Node> nodes, int startRow, int startColumn){

        int count = 0;

        for(int i = 0; i < 2; i++){

            for(int j = 0; j < 2; j++) {

                addBtn(panel, nodes.get(count), startRow + i, startColumn + j);
                count++;

            }

        }

    }

    private void addBtn(JPanel panel, Node node, int row, int column){

        int count = 0;
        int cell = row * 11 + column;

        for (Component c : panel.getComponents()) {

            if (c instanceof JPanel && count == cell)
            {
                JPanel p = (JPanel) c;

                try {
                    String filename = "FieldNeutral.png";
                    if(node.getType().toString().contains("START") || node.getType().toString().contains("INIT") ||
                            node.getType().toString().contains("TARGET")){
                        filename = getImageName(node.getType());
                    }

                    BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource(filename));
                    FieldButton btn = new FieldButton(image, Graph.INSTANCE.four.indexOf(node));
                    btn.setSize(50, 50);
                    btn.addActionListener(clicked);

                    p.add(btn);
                    btns.add(btn);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            count++;
        }

    }

    private String getImageName(FieldType type){

        if(type.toString().contains("RED")){
            return "FieldRed.png";
        }
        if(type.toString().contains("BLUE")){
            return "FieldBlue.png";
        }
        if(type.toString().contains("GREEN")){
            return "FieldGreen.png";
        }
        if(type.toString().contains("YELLOW")){
            return "FieldYellow.png";
        }
        return "FieldNeutral.png";
    }

    private int updateRow(Direction direction) {

        if(direction.equals(Direction.UP)){
            return -1;
        }
        if(direction.equals(Direction.DOWN)){
            return 1;
        }
        return 0;

    }

    private int updateColumn(Direction direction) {

        if(direction.equals(Direction.LEFT)){
            return -1;
        }
        if(direction.equals(Direction.RIGHT)){
            return 1;
        }
        return 0;

    }

}
