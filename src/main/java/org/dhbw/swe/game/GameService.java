package org.dhbw.swe.game;

import java.util.Random;
import org.dhbw.swe.board.FieldInterface;
import org.dhbw.swe.board.ControlMechanismFour;
import java.beans.PropertyChangeEvent;
import org.dhbw.swe.board.BoardInterface;
import org.dhbw.swe.board.Four;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.util.List;
import org.dhbw.swe.board.GamePieceInterface;
import java.util.Map;
import org.dhbw.swe.visualization.GameFrame;

public class GameService {

    private GameParameter gameParameter;
    private GameFrame gameVisualization;
    private int currentDice;
    private int moveFrom;
    private int moveTo;
    private int redice;
    private Map<GamePieceInterface, Integer> possibleMoves;
    private List<Color> algoColors;

    public GameService() {
        this.gameVisualization = new GameFrame();
        this.newGame();
        this.gameVisualization.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("calculate")) {
                this.calculateTurns(e);
            }
            else if (e.getPropertyName().equals("move")) {
                this.makeMove(e);
            }
            else if (e.getPropertyName().equals("dice")) {
                this.dice();
            }
            else if (e.getPropertyName().equals("new")) {
                this.newGame();
            }
        });
    }

    private void newGame() {
        final int playerNumber = this.gameVisualization.getPlayerNumber();
        final int algoNumber = this.gameVisualization.getAlgoNumber(playerNumber);
        if (algoNumber == 3) {
            this.algoColors = Arrays.asList(Color.GREEN, Color.YELLOW, Color.BLUE);
        }
        else if (algoNumber == 2) {
            this.algoColors = Arrays.asList(Color.GREEN, Color.YELLOW);
        }
        else if (algoNumber == 1) {
            this.algoColors = Arrays.asList(Color.GREEN);
        }
        else {
            this.algoColors = new ArrayList<Color>();
        }
        final BoardInterface board = (BoardInterface)new Four();
        board.initBoard(playerNumber);
        (this.gameParameter = new GameParameter(board, Color.RED, playerNumber)).setPlayerNumber(playerNumber);
        this.gameVisualization.newGame(this.gameParameter.getBoard().getBoard());
        this.gameVisualization.setTurn(this.gameParameter.getTurn(), false);
    }

    private void makeMove(final PropertyChangeEvent e) {
        this.gameParameter.getBoard().makeMove(this.moveFrom, this.moveTo);
        this.gameVisualization.setGamePieces(this.gameParameter.getBoard().getBoard());
        if (this.gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {
            final ControlMechanismFour c = (ControlMechanismFour)this.gameParameter.getBoard().getControlMechanism();
            final Color winner = c.checkWin(this.gameParameter.getBoard().getBoard());
            if (winner != null) {
                this.gameVisualization.winner(winner);
                this.newGame();
            }
            else {
                this.nextTurn();
            }
        }
    }

    private void calculateTurns(final PropertyChangeEvent e) {
        if (this.gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {
            final ControlMechanismFour c = (ControlMechanismFour)this.gameParameter.getBoard().getControlMechanism();
            final GamePieceInterface gamePieceInterface = this.gameParameter.getBoard().getBoard().get((int)e.getNewValue()).getGamePiece();
            this.possibleMoves.entrySet().stream().filter(x -> x.getKey().equals(gamePieceInterface)).forEach(x -> {
                this.gameVisualization.markAdditionalField((int)x.getValue());
                this.moveTo = x.getValue();
                return;
            });
            this.moveFrom = (int)e.getNewValue();
        }
    }

    private void dice() {
        this.currentDice = new Random().nextInt(6) + 1;
        this.gameVisualization.diced(this.currentDice);
        if (this.gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour) {
            final ControlMechanismFour c = (ControlMechanismFour)this.gameParameter.getBoard().getControlMechanism();
            this.possibleMoves = (Map<GamePieceInterface, Integer>)c.calculateTurns(this.gameParameter.getTurn(), this.gameParameter.getBoard().getBoard(), this.currentDice);
            if (this.possibleMoves.isEmpty()) {
                this.nextTurn();
            }
            else if (this.algoColors.contains(this.gameParameter.getTurn())) {
                Algorithm.calculateMove(this.gameParameter.getTurn(), (Map)this.possibleMoves, this.gameParameter.getBoard().getBoard());
                this.gameParameter.getBoard().makeMove(Algorithm.getMoveFrom(), Algorithm.getMoveTo());
                this.gameVisualization.setGamePieces(this.gameParameter.getBoard().getBoard());
                final Color winner = c.checkWin(this.gameParameter.getBoard().getBoard());
                if (winner != null) {
                    this.gameVisualization.winner(winner);
                    this.newGame();
                }
                else {
                    this.nextTurn();
                }
            }
        }
    }

    private void nextTurn() {
        if (this.currentDice == 6) {
            this.redice = 0;
        }
        else if (this.redice < 2 && this.gameParameter.getBoard().getControlMechanism() instanceof ControlMechanismFour && ((ControlMechanismFour)this.gameParameter.getBoard().getControlMechanism()).isAllowedToRedice(this.gameParameter.getTurn(), this.gameParameter.getBoard().getBoard())) {
            ++this.redice;
        }
        else {
            this.redice = 0;
            List<Color> colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
            if (this.gameParameter.getPlayerNumber() == 3) {
                colors = Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN);
            }
            else if (this.gameParameter.getPlayerNumber() == 2) {
                colors = Arrays.asList(Color.RED, Color.GREEN);
            }
            int index = colors.indexOf(this.gameParameter.getTurn()) + 1;
            if (index == this.gameParameter.getPlayerNumber()) {
                index = 0;
            }
            this.gameParameter.setTurn((Color)colors.get(index));
        }
        if (this.algoColors.contains(this.gameParameter.getTurn())) {
            this.gameVisualization.setTurn(this.gameParameter.getTurn(), true);
        }
        else {
            this.gameVisualization.setTurn(this.gameParameter.getTurn(), false);
        }
    }

}
