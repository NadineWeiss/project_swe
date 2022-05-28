package org.dhbw.swe.game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerIO {

    public static void savePlayer(Player player){

        String filePath = "players.json";

        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {

            String json = appendJSON(player);

            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {

                out.write(json);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{

            try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {

                out.write(createJSON(player));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static List<Player> loadPlayers(){

        List<Player> players = new ArrayList<>();

        String filePath = "players.json";
        File f = new File(filePath);
        if(!f.exists()){
            return players;
        }

        try(BufferedReader in = new BufferedReader(new FileReader(filePath))){

            String line = in.readLine();

            while(line != null){

                if(isNextPlayer(line)){

                    String id = getParameter(line).get("id");
                    line = in.readLine();
                    String name = getParameter(line).get("name");
                    line = in.readLine();
                    int winCount = Integer.parseInt(getParameter(line).get("winCount"));

                    Player player = new Player(id, name, winCount);
                    players.add(player);


                }
                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;

    }

    private static String appendJSON(Player player){

        String json = "";
        String filePath = "players.json";

        try(BufferedReader in = new BufferedReader(new FileReader(filePath))){

            String line = in.readLine();

            while(line != null){

                json += "\n" + line;
                line = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);

        json = addPlayerToJSON(json, player, false);

        return json;

    }

    private static String createJSON(Player player){

        String json = newJsonObject();
        json = addPlayerToJSON(json, player, true);

        return json;

    }

    private static String addPlayerToJSON(String json, Player player, boolean firstPlayer) {

        String playerJSON = newPlayerJsonObject();

        String playerId = player.getId();
        playerJSON = addStringParameterToJSON(playerJSON, "id", playerId);

        String playerName = player.getName();
        playerJSON= addStringParameterToJSON(playerJSON, "name", playerName);

        String winCount = String.valueOf(player.getWinCount());
        playerJSON= addStringParameterToJSON(playerJSON, "winCount", winCount);

        json = appendPlayer(json, playerJSON, firstPlayer);

        System.out.println(json);

        return json;

    }

    private static boolean isNextPlayer(String line){

        //Match Strings for value
        Pattern pattern = Pattern.compile("(\")(.*?)(\" : \")(.*?)(\")");
        Matcher matcher = pattern.matcher(line);

        return matcher.find() && matcher.group(2).equals("id");

    }

    private static Map<String, String> getParameter(String line){

        Map<String, String> parameter = new HashMap<>();

        //Match Strings for value
        Pattern pattern = Pattern.compile("(\")(.*?)(\" : \")(.*?)(\")");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            parameter.put(matcher.group(2), matcher.group(4));
        }

        return parameter;

    }

    private static String newJsonObject(){

        return "{\n\t\"players\": [\n\t]\n}";

    }

    private static String newPlayerJsonObject(){

        return "{\n}";

    }

    private static String addStringParameterToJSON(String json, String name, String value){

        String newParameter = "\t\"" + name + "\" : \"" + value + "\"";

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

    private static String appendPlayer(String json, String playerJSON, boolean firstPlayer){

        if(firstPlayer){

            json = json.substring(0, json.length() - 3);

            playerJSON = playerJSON.replace("\n", "\n\t\t");
            json += "\t" + playerJSON;

            json += "\n\t]\n}";

        }else{

            json = json.substring(0, json.length() - 5);
            json += ",\n\t";

            playerJSON = playerJSON.replace("\n", "\n\t\t");
            json += "\t" + playerJSON;

            json += "\n\t]\n}";

        }

        return json;

    }

}
