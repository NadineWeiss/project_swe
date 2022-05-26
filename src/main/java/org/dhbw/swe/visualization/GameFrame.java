package org.dhbw.swe.visualization;

import org.dhbw.swe.game.Context;
import org.dhbw.swe.game.Observer;
import org.dhbw.swe.game.ObserverContext;
import org.dhbw.swe.graph.Direction;
import org.dhbw.swe.graph.FieldType;
import org.dhbw.swe.graph.Graph;
import org.dhbw.swe.graph.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameFrame extends JFrame implements Observable {

    private List<FieldButton> btns = new ArrayList<>();
    private DiceButton btnNewDice = new DiceButton();
    private DiceButton btnPreviousDice = new DiceButton();
    private JPanel panel = new JPanel();
    private JPanel panelField = new JPanel();

    private List<Observer> observers = new ArrayList<>();
    private Color turnColor;

    public GameFrame() throws HeadlessException {

        btnNewDice.addActionListener(e -> {

            DiceButton btn = ((DiceButton)e.getSource());
            if(btn.isClickable())
                notifyObservers(new ObserverContext(Context.DICE));

        });

    }

    public void newGame(List<Optional<Color>> field){

        panelField.removeAll();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(setPanelCoordinate(), BorderLayout.NORTH);
        panel.add(setPanelField(), BorderLayout.CENTER);

        setGamePieces(field);

        setTitle("Mensch Ärgere Dich Nicht");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        setSize(800, 900);
        setVisible(true);

    }

    public JPanel setPanelField(){

        btns.clear();

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

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton newGameBtn = new JButton("Neues Spiel");
        newGameBtn.addActionListener(e -> notifyObservers(new ObserverContext(Context.NEW)));
        newGameBtn.setFocusPainted(false);
        panelMenu.add(newGameBtn);

        JButton saveGameBtn = new JButton("Spiel speichern");
        saveGameBtn.addActionListener(e -> notifyObservers(new ObserverContext(Context.SAVE)));
        saveGameBtn.setFocusPainted(false);
        panelMenu.add(saveGameBtn);

        JButton loadGameBtn = new JButton("Spiel laden");
        loadGameBtn.addActionListener(e -> notifyObservers(new ObserverContext(Context.LOAD)));
        loadGameBtn.setFocusPainted(false);
        panelMenu.add(loadGameBtn);

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

    public String getSelectedFile(List<String> fileChoices){

        String selectedFileName = (String) JOptionPane.showInputDialog(null, "Wähle das Spiel, das geladen werden soll:",
                "Auswahl eines Spiels", JOptionPane.QUESTION_MESSAGE, null, fileChoices.toArray(), fileChoices.toArray()[0]);

        return selectedFileName;

    }

    public void winner(Color color){

        JOptionPane.showMessageDialog(this, "Farbe " + color.toString() + " hat gewonnen.",
                "Glückwunsch :)", JOptionPane.INFORMATION_MESSAGE);

    }

    public void diced(int diceValue, Color turnColor){

        btnPreviousDice.setDice(diceValue, turnColor);
        btnNewDice.setClickable(false);

        btns.stream().filter(x -> x.getPieceColor() != null && x.getPieceColor().equals(turnColor))
                .forEach(x -> x.setClickable(true));

        this.revalidate();
        this.repaint();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private ActionListener clicked = e -> {

        FieldButton btn = ((FieldButton)e.getSource());

        if(btn.isClickable()){
            if(btn.getPieceColor() != null && btn.getPieceColor().equals(turnColor)){

                btns.parallelStream().forEach(x -> x.setBackgroundImage(false));
                btn.setBackgroundImage(true);

                int fieldIndex = ((FieldButton) e.getSource()).getIndex();
                notifyObservers(new ObserverContext(Context.CALCULATE, Optional.of(fieldIndex)));

            }else{

                btns.parallelStream().forEach(x -> x.setBackgroundImage(false));

                notifyObservers(new ObserverContext(Context.MOVE));

            }
        }

    };

    public void markAdditionalField(int index){

        btns.get(index).setBackgroundImage(true);
        btns.get(index).setClickable(true);

    }

    public void setTurn(Color color, boolean algo){

        if(!algo)
            btnNewDice.setClickable(true);
        btnNewDice.setDice(color);

        turnColor = color;
        btns.stream().forEach(x -> x.setClickable(false));

        if(algo)
            notifyObservers(new ObserverContext(Context.DICE));

    }

    public void setGamePieces(List<Optional<Color>> field) {

        btns.stream().forEach(x -> x.removePiece());

        for(int i = 0; i < field.size(); i++){

            if(!field.get(i).isEmpty()){

                btns.get(i).setPiece(field.get(i).get());

            }

        }

        panelField.revalidate();
        panelField.repaint();

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

    @Override
    public void register(Observer observer) {

        observers.add(observer);

    }

    @Override
    public void remove(Observer observer) {

        observers.remove(observer);

    }

    @Override
    public void notifyObservers(ObserverContext observerContext) {

        observers.forEach(x -> x.update(observerContext));

    }

}
