package org.dhbw.swe.game;

import org.dhbw.swe.board.BoardInterface;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameIO {

    public static void saveGame(GameParameter gameParameter){

        String filePath = "gameStatus_" + getDateTime() + ".json";

        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {

            out.write(createJSON(gameParameter));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static GameParameter loadGame(String fileName){

        Map<String, Object> parameters = new HashMap<>();

        try(BufferedReader in = new BufferedReader(new FileReader(fileName))){

            String line = in.readLine();

            while(line != null){

                parameters.putAll(getParameter(line));
                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transformParameters(parameters);

    }

    public static List<String> getFileNames(){

        try {
            return Files.walk(Paths.get(""), 1)
                    .filter(x -> x.getFileName().toString().endsWith(".json"))
                    .filter(x -> !x.getFileName().toString().equals("players.json"))
                    .map(x -> x.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    private static String createJSON(GameParameter gameParameter){

        String json = newJsonObject();
        json = addStringParameterToJSON(json, "PlayerNumber", String.valueOf(gameParameter.getPlayerNumber()));
        json = addStringParameterToJSON(json, "TurnColor", Integer.toString(gameParameter.getTurn().getRGB()));

        List<String> algoColors = gameParameter.getAlgoColors().stream()
                .map(x -> Integer.toString(x.getRGB()))
                .collect(Collectors.toList());
        json = addStringListParameterToJSON(json, "AlgoColors", algoColors);

        json = addPlayersToJSON(json, gameParameter);

        List<Integer> redPositions = gameParameter.getBoard().getGamePiecePositions(Color.RED);
        json = addIntegerListParameterToJSON(json, "RedGamePiecePositions", redPositions);

        List<Integer> yellowPositions = gameParameter.getBoard().getGamePiecePositions(Color.YELLOW);
        json = addIntegerListParameterToJSON(json, "YellowGamePiecePositions", yellowPositions);

        List<Integer> greenPositions = gameParameter.getBoard().getGamePiecePositions(Color.GREEN);
        json = addIntegerListParameterToJSON(json, "GreenGamePiecePositions", greenPositions);

        List<Integer> bluePositions = gameParameter.getBoard().getGamePiecePositions(Color.BLUE);
        json = addIntegerListParameterToJSON(json, "BlueGamePiecePositions", bluePositions);

        return json;

    }

    private static String addPlayersToJSON(String json, GameParameter gameParameter) {

        for(Map.Entry<Player, Color> entry : gameParameter.getPlayerColors().entrySet()){

            String id = entry.getKey().getId();
            String name = "Player";

            if(entry.getValue().equals(Color.RED))
                name += "Red";
            else if(entry.getValue().equals(Color.YELLOW))
                name += "Yellow";
            else if(entry.getValue().equals(Color.GREEN))
                name += "Green";
            else
                name += "Blue";

            json = addStringParameterToJSON(json, name, id);

        }

        return json;

    }

    private static String getDateTime(){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

        return formatter.format(date);

    }

    private static GameParameter transformParameters(Map<String, Object> parameters){

        int playerNumber = Integer.parseInt(parameters.get("PlayerNumber").toString());
        Color turn = new Color(Integer.parseInt(parameters.get("TurnColor").toString()));

        List<Color> algoColors = new ArrayList<>();
        for(String algoColor : (List<String>) parameters.get("AlgoColors")){
            algoColors.add(new Color(Integer.parseInt(algoColor)));
        }

        final BoardInterface board = BoardInterface.initBoardInterface(4);
        board.initBoard(playerNumber);

        List<Integer> redPositions = ((List<String>) parameters.get("RedGamePiecePositions")).stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());
        List<Integer> yellowPositions = ((List<String>) parameters.get("YellowGamePiecePositions")).stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());
        List<Integer> greenPositions = ((List<String>) parameters.get("GreenGamePiecePositions")).stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());
        List<Integer> bluePositions = ((List<String>) parameters.get("BlueGamePiecePositions")).stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());

        for(int i = 3; i >= 0; i--){

            board.makeMove(i, redPositions.get(i));
            board.makeMove(4 + i, yellowPositions.get(i));
            board.makeMove(8 + i, greenPositions.get(i));
            board.makeMove(12 + i, bluePositions.get(i));

        }

        GameParameter gameParameter = new GameParameter(board, turn, playerNumber, algoColors);
        gameParameter.setPlayerColors(getPlayerColors(parameters));


        return gameParameter;

    }

    private static Map<Player, Color> getPlayerColors(Map<String, Object> parameters) {

        List<Player> players = PlayerIO.loadPlayers();
        Map<Player, Color> playerMap = new HashMap<>();

        if(parameters.containsKey("PlayerRed")){
            Player player = players.stream().filter(x -> x.getId().equals(parameters.get("PlayerRed"))).findFirst().get();
            playerMap.put(player, Color.RED);
        }
        if(parameters.containsKey("PlayerYellow")){
            Player player = players.stream().filter(x -> x.getId().equals(parameters.get("PlayerYellow"))).findFirst().get();
            playerMap.put(player, Color.YELLOW);
        }
        if(parameters.containsKey("PlayerGreen")){
            Player player = players.stream().filter(x -> x.getId().equals(parameters.get("PlayerGreen"))).findFirst().get();
            playerMap.put(player, Color.GREEN);
        }
        if(parameters.containsKey("PlayerBlue")){
            Player player = players.stream().filter(x -> x.getId().equals(parameters.get("PlayerBlue"))).findFirst().get();
            playerMap.put(player, Color.BLUE);
        }

        return playerMap;


    }

    private static Map<String, Object> getParameter(String line){

        Map<String, Object> parameter = new HashMap<>();

        //Match Strings for value
        Pattern pattern = Pattern.compile("(\")(.*?)(\" : \")(.*?)(\")");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            parameter.put(matcher.group(2), matcher.group(4));
        }

        //Match List for value
        pattern = Pattern.compile("(\")(.*?)(\" : \\[)((.+?)(,(.+?))?(,(.+?))?(,(.+?))?)?\\]");
        matcher = pattern.matcher(line);
        while (matcher.find()) {

            List<String> values = new ArrayList<>();

            for(int i = 5; i <= matcher.groupCount(); i+=2){
                values.add(matcher.group(i));
            }

            values = values.stream()
                    .filter(x -> x != null)
                    .collect(Collectors.toList());

            parameter.put(matcher.group(2), values);

        }

        return parameter;

    }



    private static String newJsonObject(){

        return "{\n}";

    }

    private static String addStringParameterToJSON(String json, String name, String value){

        String newParameter = "\t\"" + name + "\" : \"" + value + "\"";

        return addParameterToJSON(json, newParameter);

    }

    private static String addIntegerListParameterToJSON(String json, String name, List<Integer> values){

        String jsonValue = "[";

        if(!values.isEmpty()){

            for(Integer i : values){
                jsonValue += i + ",";
            }
            jsonValue = jsonValue.substring(0, jsonValue.length() - 1);

        }
        jsonValue += "]";

        String newParameter = "\t\"" + name + "\" : " + jsonValue;

        return addParameterToJSON(json, newParameter);

    }

    private static String addStringListParameterToJSON(String json, String name, List<String> values){

        String jsonValue = "[";

        if(!values.isEmpty()){

            for(String i : values){
                jsonValue += i + ",";
            }
            jsonValue = jsonValue.substring(0, jsonValue.length() - 1);

        }
        jsonValue += "]";

        String newParameter = "\t\"" + name + "\" : " + jsonValue;

        return addParameterToJSON(json, newParameter);

    }

    private static String addParameterToJSON(String json, String newParameter){

        String firstPart = json.substring(0, json.length() - 2);
        if(!firstPart.equals("{")){
            firstPart = firstPart + ",";
        }
        firstPart = firstPart + "\n";

        String lastPart = "\n}";

        return firstPart + newParameter + lastPart;

    }

}
