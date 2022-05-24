package org.dhbw.swe.game;

import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameIO {

    public static void saveGame(GameParameter gameParameter){

        String json = newJsonObject();
        json = addStringParameter(json, "PlayerNumber", String.valueOf(gameParameter.getPlayerNumber()));
        json = addStringParameter(json, "TurnColor", String.valueOf(gameParameter.getTurn()));

        List<String> algoColors = gameParameter.getAlgoColors().stream()
                .map(x -> x.toString())
                .collect(Collectors.toList());
        json = addStringListParameter(json, "AlgoColors", algoColors);

        List<Integer> redPositions = gameParameter.getBoard().getGamePiecePositions(Color.RED);
        json = addIntegerListParameter(json, "RedGamePiecePositions", redPositions);

        List<Integer> yellowPositions = gameParameter.getBoard().getGamePiecePositions(Color.YELLOW);
        json = addIntegerListParameter(json, "YellowGamePiecePositions", yellowPositions);

        List<Integer> greenPositions = gameParameter.getBoard().getGamePiecePositions(Color.GREEN);
        json = addIntegerListParameter(json, "GreenGamePiecePositions", greenPositions);

        List<Integer> bluePositions = gameParameter.getBoard().getGamePiecePositions(Color.BLUE);
        json = addIntegerListParameter(json, "BlueGamePiecePositions", bluePositions);

        System.out.println(json);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String dateTime = formatter.format(date);

        String filePath = "gameStatus_" + dateTime + ".json";
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static GameParameter loadGame(String fileName){

        Map<String, Object> parameters = new HashMap<>();

        try(BufferedReader in = new BufferedReader(new FileReader(fileName))){

            String line = in.readLine();

            while(line != null){

                //Todo: parameters.put()
                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //todo
        return null;

    }

    private static Map<String, Object> getParameter(String line){

        Map<String, Object> parameter = new HashMap<>();

        Pattern pattern = Pattern.compile("(\")(.*?)(\" : \")(.*?)(\")");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            parameter.put(matcher.group(2), matcher.group(3));
        }

        //Todo: Match list

        return parameter;

    }

    public static List<String> getFileNames(){

        try {
            return Files.walk(Paths.get(""), 1)
                    .filter(x -> x.getFileName().toString().endsWith(".json"))
                    .map(x -> x.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    private static String newJsonObject(){

        return "{\n}";

    }

    private static String addStringParameter(String json, String name, String value){

        String newParameter = "\t\"" + name + "\" : \"" + value + "\"";

        return addParameter(json, newParameter);

    }

    private static String addIntegerListParameter(String json, String name, List<Integer> values){

        String jsonValue = "[";

        if(!values.isEmpty()){

            for(Integer i : values){
                jsonValue += i + ",";
            }
            jsonValue = jsonValue.substring(0, jsonValue.length() - 1);

        }
        jsonValue += "]";

        String newParameter = "\t\"" + name + "\" : " + jsonValue;

        return addParameter(json, newParameter);

    }

    private static String addStringListParameter(String json, String name, List<String> values){

        String jsonValue = "[";

        if(!values.isEmpty()){

            for(String i : values){
                jsonValue += i + ",";
            }
            jsonValue = jsonValue.substring(0, jsonValue.length() - 1);

        }
        jsonValue += "]";

        String newParameter = "\t\"" + name + "\" : " + jsonValue;

        return addParameter(json, newParameter);

    }

    private static String addParameter(String json, String newParameter){

        String firstPart = json.substring(0, json.length() - 2);
        if(!firstPart.equals("{")){
            firstPart = firstPart + ",";
        }
        firstPart = firstPart + "\n";

        String lastPart = "\n}";

        return firstPart + newParameter + lastPart;

    }

}
