package org.dhbw.swe.main;

import org.dhbw.swe.board.ControlMechanismFour;
import org.dhbw.swe.board.Graph;

public class Main {

    public static void main (String[] args){

        // Graph.INSTANCE.four.stream().forEach(x -> System.out.println(x));

        System.out.println(new ControlMechanismFour().dice());

    }

}
