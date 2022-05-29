package org.dhbw.swe.main;

import org.dhbw.swe.game.GameService;
import org.dhbw.swe.visualization.GameFrame;

public class Main {

    public static void main (String[] args){

        new GameService(new GameFrame());

    }

}
