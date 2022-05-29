package org.dhbw.swe.visualization;

import org.dhbw.swe.game.Observer;
import org.dhbw.swe.game.ObserverContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameFrameMock implements Observable, GameFrameInterface {

    private List<Observer> observers = new ArrayList<>();
    private int diceValue;
    private int markedAdditionalField;

    @Override
    public void newGame(List<Optional<Color>> board) {

    }

    @Override
    public int getPlayerNumber() {
        return 4;
    }

    @Override
    public int getAlgoNumber(int playerNumber) {
        return 0;
    }

    @Override
    public String getSelectedFile(List<String> fileChoices) {
        return null;
    }

    @Override
    public void winner(Color color) {

    }

    @Override
    public Map<String, Boolean> getPlayers(int playerNumber, List<String> playerSelection) {
        return null;
    }

    @Override
    public void setPlayerNames(Map<String, Color> playerNames, List<Color> algoColors) {

    }

    @Override
    public void diced(int diceValue, Color turnColor) {
        this.diceValue = diceValue;
    }

    public int getDiceValue() {
        return diceValue;
    }

    @Override
    public void markAdditionalField(int index) {
        this.markedAdditionalField = index;
    }

    public int getMarkedAdditionalField() {
        return markedAdditionalField;
    }

    @Override
    public void setTurn(Color color, boolean algo) {

    }

    @Override
    public void setGamePieces(List<Optional<Color>> board) {

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
