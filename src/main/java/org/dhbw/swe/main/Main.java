package org.dhbw.swe.main;

import org.dhbw.swe.game.GameIO;
import org.dhbw.swe.game.GameService;

public class Main {

    public static void main (String[] args){

        new GameService();

        //GameIO.getFileNames().forEach(System.out::println);

    }

}
