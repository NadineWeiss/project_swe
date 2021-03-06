package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;
import org.dhbw.swe.visualization.GameFrame;
import org.dhbw.swe.visualization.GameFrameInterface;
import org.dhbw.swe.visualization.GameFrameMock;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GameService implements Observer{

    private GameParameterInterface gameParameter;
    private GameFrameInterface gameVisualization;
    private int currentDice;
    private int moveFrom;
    private int moveTo;
    private int redice;

    public GameService(GameFrameInterface gameFrame) {

        gameVisualization = gameFrame;
        ((GameFrame)gameVisualization).register(this);

        newGame();

    }

    public GameService(GameFrameInterface gameFrame, GameParameterInterface gameParameter){

        gameVisualization = gameFrame;
        ((GameFrameMock)gameVisualization).register(this);

        this.gameParameter = gameParameter;

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
            GameIO.saveGame((GameParameter) gameParameter);
        }
        else if (observerContext.getContext().equals(Context.LOAD)) {
            loadGame();
        }

    }

    private void newGame() {

        int playerNumber = gameVisualization.getPlayerNumber();
        int algoNumber = gameVisualization.getAlgoNumber(playerNumber);
        List<Player> players = getPlayerNames(playerNumber - algoNumber);

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

        gameParameter.setPlayerColors(getPlayerColors(players, playerNumber, gameParameter.getAlgoColors()));

        gameVisualization.newGame(gameParameter.getBoard().getColorBoard());
        gameVisualization.setTurn(gameParameter.getTurn(), false);
        gameVisualization.setPlayerNames(gameParameter.getPlayerNameColors(), gameParameter.getAlgoColors());

    }

    private void loadGame(){

        List<String> filenames = GameIO.getFileNames();
        if(filenames.isEmpty()) {
            return;
        }
        String fileName = gameVisualization.getSelectedFile(filenames);
        gameParameter = GameIO.loadGame(fileName);

        gameVisualization.newGame(gameParameter.getBoard().getColorBoard());
        gameVisualization.setTurn(gameParameter.getTurn(), false);
        gameVisualization.setPlayerNames(gameParameter.getPlayerNameColors(), gameParameter.getAlgoColors());

    }

    private void makeMove() {

        gameParameter.getBoard().makeMove(moveFrom, moveTo);
        gameVisualization.setGamePieces(gameParameter.getBoard().getColorBoard());

        final Color winner = gameParameter.getBoard().checkWin();

        if (winner != null) {
            gameParameter.getPlayerColors().entrySet().stream()
                    .filter(x -> x.getValue().equals(winner))
                    .forEach(x -> x.getKey().addWin());
            //Todo: update players.json
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

                gameParameter.getPlayerColors().entrySet().stream()
                        .filter(x -> x.getValue().equals(winner))
                        .forEach(x -> x.getKey().addWin());
                //Todo: update players.json
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

    private List<Player> getPlayerNames(int playerNumber) {

        List<Player> existingPlayers = PlayerIO.loadPlayers();
        List<String> existingPlayerIds = new ArrayList<>();
        for(Player player: existingPlayers){
            existingPlayerIds.add(player.getId());
        }

        Map<String, Boolean> playerMap = gameVisualization.getPlayers(playerNumber, existingPlayerIds);

        List<Player> players = new ArrayList<>();

        for(Map.Entry<String, Boolean> entry : playerMap.entrySet()){

            if(entry.getValue()){

                Player player = new Player(entry.getKey());
                PlayerIO.savePlayer(player);

                players.add(player);

            }else{

                int index = existingPlayerIds.indexOf(entry.getKey());
                Player player = existingPlayers.get(index);

                players.add(player);

            }

        }

        return players;

    }

    private Map<Player, Color> getPlayerColors(List<Player> players, int playerNumber, List<Color> algoColors){

        Map<Player, Color> playerColors = new HashMap<>();
        List<Color> colors = new ArrayList<>();

        if (playerNumber >= 2) {
            colors.add(Color.RED);
            colors.add(Color.GREEN);
        }
        if (playerNumber >= 3) {
            colors.add(Color.YELLOW);
        }
        if(playerNumber >= 4) {
            colors.add(Color.BLUE);
        }

        colors = colors.stream()
                .filter(x -> !algoColors.contains(x))
                .collect(Collectors.toList());

        for(int i = 0; i < colors.size(); i++){

            playerColors.put(players.get(i), colors.get(i));

        }

        return playerColors;

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

    public GameParameterInterface getGameParameter() {
        return gameParameter;
    }
}
