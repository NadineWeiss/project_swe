package org.dhbw.swe.game;

import java.util.Random;

import org.dhbw.swe.board.ControlMechanismFour;
import org.dhbw.swe.board.BoardInterface;
import org.dhbw.swe.board.Four;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.util.List;
import org.dhbw.swe.board.GamePieceInterface;
import java.util.Map;
import org.dhbw.swe.visualization.GameFrame;

public class GameService implements Observer{

    private GameParameter gameParameter;
    private GameFrame gameVisualization;
    private int currentDice;
    private int moveFrom;
    private int moveTo;
    private int redice;
    private Map<GamePieceInterface, Integer> possibleMoves;
    private List<Color> algoColors;

    public GameService() {

        gameVisualization = new GameFrame();
        gameVisualization.register(this);

        newGame();

    }

    @Override
    public void update(ObserverContext observerContext) {

        if (observerContext.getContext().equals(Context.CALCULATE)) {
            calculateTurns(observerContext.getFieldIndex());
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

    }

    private void newGame() {

        final int playerNumber = gameVisualization.getPlayerNumber();
        final int algoNumber = gameVisualization.getAlgoNumber(playerNumber);

        if (algoNumber == 3) {
            algoColors = Arrays.asList(Color.GREEN, Color.YELLOW, Color.BLUE);
        }
        else if (algoNumber == 2) {
            algoColors = Arrays.asList(Color.GREEN, Color.YELLOW);
        }
        else if (algoNumber == 1) {
            algoColors = Arrays.asList(Color.GREEN);
        }
        else {
            algoColors = new ArrayList<>();
        }

        final BoardInterface board = new Four();
        board.initBoard(playerNumber);

        gameParameter = new GameParameter(board, Color.RED, playerNumber);

        gameVisualization.newGame(gameParameter.getBoard().getBoard());
        gameVisualization.setTurn(gameParameter.getTurn(), false);

    }

    private void makeMove() {

        gameParameter.getBoard().makeMove(moveFrom, moveTo);
        gameVisualization.setGamePieces(gameParameter.getBoard().getBoard());

        if (gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {

            final ControlMechanismFour c = (ControlMechanismFour)gameParameter.getBoard().getControlMechanism();
            final Color winner = c.checkWin(gameParameter.getBoard().getBoard());

            if (winner != null) {
                gameVisualization.winner(winner);
                newGame();
            }
            else {
                nextTurn();
            }
        }

    }

    private void calculateTurns(final int fieldIndex) {

        if (gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {

            final ControlMechanismFour c = (ControlMechanismFour)gameParameter.getBoard().getControlMechanism();
            final GamePieceInterface gamePieceInterface = gameParameter.getBoard().getBoard().get(fieldIndex).getGamePiece();

            possibleMoves.entrySet().stream().filter(x -> x.getKey().equals(gamePieceInterface)).forEach(x -> {
                gameVisualization.markAdditionalField(x.getValue());
                moveTo = x.getValue();
                return;
            });

            moveFrom = fieldIndex;

        }

    }

    private void dice() {

        currentDice = new Random().nextInt(6) + 1;
        gameVisualization.diced(currentDice, gameParameter.getTurn());

        if (gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {

            final ControlMechanismFour c = (ControlMechanismFour)gameParameter.getBoard().getControlMechanism();
            possibleMoves = c.calculateTurns(gameParameter.getTurn(), gameParameter.getBoard().getBoard(), currentDice);

            if (possibleMoves.isEmpty()) {

                nextTurn();

            } else if (algoColors.contains(gameParameter.getTurn())) {

                Algorithm.calculateMove(gameParameter.getTurn(), possibleMoves, gameParameter.getBoard().getBoard());
                gameParameter.getBoard().makeMove(Algorithm.getMoveFrom(), Algorithm.getMoveTo());
                gameVisualization.setGamePieces(gameParameter.getBoard().getBoard());

                final Color winner = c.checkWin(gameParameter.getBoard().getBoard());
                if (winner != null) {

                    gameVisualization.winner(winner);
                    newGame();

                }
                else {

                    nextTurn();

                }
            }
        }

    }

    private void nextTurn() {

        if (currentDice == 6) {

            redice = 0;

        }
        else if (redice < 2 && gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour &&
                ((ControlMechanismFour)gameParameter.getBoard().getControlMechanism()).isAllowedToRedice(gameParameter.getTurn(), gameParameter.getBoard().getBoard())) {
            ++redice;
        }
        else {
            redice = 0;
            List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
            if (gameParameter.getPlayerNumber() == 3) {
                colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN);
            }
            else if (gameParameter.getPlayerNumber() == 2) {
                colors = Arrays.asList(Color.RED, Color.GREEN);
            }
            int index = colors.indexOf(gameParameter.getTurn()) + 1;
            if (index == gameParameter.getPlayerNumber()) {
                index = 0;
            }
            gameParameter.setTurn(colors.get(index));
        }
        if (algoColors.contains(gameParameter.getTurn())) {
            gameVisualization.setTurn(gameParameter.getTurn(), true);
        }
        else {
            gameVisualization.setTurn(gameParameter.getTurn(), false);
        }
    }

}
