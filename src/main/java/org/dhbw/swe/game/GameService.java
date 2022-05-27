package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;
import org.dhbw.swe.visualization.GameFrame;

import java.awt.*;
import java.util.List;
import java.util.*;

public class GameService implements Observer{

    private GameParameter gameParameter;
    private GameFrame gameVisualization;
    private int currentDice;
    private int moveFrom;
    private int moveTo;
    private int redice;

    public GameService() {

        gameVisualization = new GameFrame();
        gameVisualization.register(this);

        newGame();

    }

    @Override
    public void update(ObserverContext observerContext) {

        if (observerContext.getContext().equals(Context.CALCULATE)) {
            calculateMoves(observerContext.getFieldIndex());
        }
        else if (observerContext.getContext().equals(Context.MOVE)) {
            makeMove();
        }
        else if (observerContext.getContext().equals(Context.DICE)) {
            dice();
        }
        else if (observerContext.getContext().equals(Context.NEW)) {
            newGame();
        }
        else if (observerContext.getContext().equals(Context.SAVE)) {
            GameIO.saveGame(gameParameter);
        }
        else if (observerContext.getContext().equals(Context.LOAD)) {
            loadGame();
        }

    }

    private void newGame() {

        final int playerNumber = gameVisualization.getPlayerNumber();
        final int algoNumber = gameVisualization.getAlgoNumber(playerNumber);

        final BoardInterface board = BoardInterface.initBoardInterface(4);
        board.initBoard(playerNumber);
        gameParameter = new GameParameter(board, Color.RED, playerNumber);

        if (algoNumber == 3) {
            gameParameter.setAlgoColors(Arrays.asList(Color.GREEN, Color.YELLOW, Color.BLUE));
        }
        else if (algoNumber == 2) {
            gameParameter.setAlgoColors(Arrays.asList(Color.GREEN, Color.YELLOW));
        }
        else if (algoNumber == 1) {
            gameParameter.setAlgoColors(Arrays.asList(Color.GREEN));
        }
        else {
            gameParameter.setAlgoColors(new ArrayList<>());
        }

        gameVisualization.newGame(gameParameter.getBoard().getColorBoard());
        gameVisualization.setTurn(gameParameter.getTurn(), false);

    }

    private void loadGame(){

        String fileName = gameVisualization.getSelectedFile(GameIO.getFileNames());
        try{
            gameParameter = GameIO.loadGame(fileName);
        }catch(NullPointerException e){
            return;
        }

        gameVisualization.newGame(gameParameter.getBoard().getColorBoard());
        gameVisualization.setTurn(gameParameter.getTurn(), false);

    }

    private void makeMove() {

        gameParameter.getBoard().makeMove(moveFrom, moveTo);
        gameVisualization.setGamePieces(gameParameter.getBoard().getColorBoard());

        final Color winner = gameParameter.getBoard().checkWin();

        if (winner != null) {
            gameVisualization.winner(winner);
            newGame();
        }
        else {
            nextTurn();
        }

    }

    private void calculateMoves(final int fieldIndex) {

        Optional<Integer> moveTo = gameParameter.getBoard().calculateMove(fieldIndex, currentDice);

        if(moveTo.isPresent()){

            this.moveFrom = fieldIndex;
            this.moveTo = moveTo.get();
            gameVisualization.markAdditionalField(this.moveTo);

        }

    }

    private void dice() {

        currentDice = new Random().nextInt(6) + 1;
        gameVisualization.diced(currentDice, gameParameter.getTurn());

        if (!gameParameter.getBoard().isMovePossible(gameParameter.getTurn(), currentDice)) {

            nextTurn();

        } else if (gameParameter.getAlgoColors().contains(gameParameter.getTurn())) {

            gameParameter.getBoard().calculateAlgorithmMove(gameParameter.getTurn(), currentDice);
            int moveFrom = gameParameter.getBoard().getAlgorithmMoveFrom();
            int moveTo = gameParameter.getBoard().getAlgorithmMoveTo();

            gameParameter.getBoard().makeMove(moveFrom, moveTo);
            gameVisualization.setGamePieces(gameParameter.getBoard().getColorBoard());

            final Color winner = gameParameter.getBoard().checkWin();
            if (winner != null) {

                gameVisualization.winner(winner);
                newGame();

            }
            else {

                nextTurn();

            }

        }

    }

    private void nextTurn() {

        if (currentDice == 6) {

            redice = 0;

        }else if (redice < 2 && gameParameter.getBoard().isAllowedToRedice(gameParameter.getTurn())) {

            ++redice;

        } else {

            redice = 0;

            List<Color> colors = getCurrentColors();
            int index = colors.indexOf(gameParameter.getTurn()) + 1;
            if (index == gameParameter.getPlayerNumber())
                index = 0;
            gameParameter.setTurn(colors.get(index));

        }

        if (gameParameter.getAlgoColors().contains(gameParameter.getTurn())) {

            gameVisualization.setTurn(gameParameter.getTurn(), true);

        } else {

            gameVisualization.setTurn(gameParameter.getTurn(), false);

        }
    }

    private List<Color> getCurrentColors(){

        List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        if (gameParameter.getPlayerNumber() == 3) {
            colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN);
        }
        else if (gameParameter.getPlayerNumber() == 2) {
            colors = Arrays.asList(Color.RED, Color.GREEN);
        }

        return colors;

    }

}
