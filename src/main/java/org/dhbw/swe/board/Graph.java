package org.dhbw.swe.board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Graph {

    INSTANCE;

    private Map<Integer, Direction> dirs = Map.of(0, Direction.UP, 90, Direction.RIGHT,
            180, Direction.DOWN, 270, Direction.LEFT);

    public List<Node> four = initFour();

    private List<Node> initFour() {

        List<Node> result = new ArrayList<>();

        result.addAll(getInit());

        result.addAll(getQuarter(Color.RED, 0, 16, 56));
        result.addAll(getQuarter(Color.BLUE, 90, 30, 60));
        result.addAll(getQuarter(Color.GREEN, 180, 44, 64));
        result.addAll(getQuarter(Color.YELLOW, 270, 58, 68));

        result.addAll(getTarget(Color.RED, 0,  56));
        result.addAll(getTarget(Color.BLUE, 90, 60));
        result.addAll(getTarget(Color.GREEN, 180, 64));
        result.addAll(getTarget(Color.YELLOW, 270, 68));

        return result;
    }

    private List<Node> getQuarter(Color color, int rotation, int startEnd, int startTarget){

        List<Node> result = new ArrayList<>();

        //Nodes except the target
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(startEnd + 1, rotateDir(Direction.RIGHT, rotation)));
        for(int i = 0; i < 4; i++) {
            edges.add(new Edge(startEnd + i + 2, rotateDir(Direction.DOWN, rotation)));
        }
        for(int i = 0; i < 4; i++) {
            edges.add(new Edge(startEnd + i + 6, rotateDir(Direction.RIGHT, rotation)));
        }
        if(rotation == 270){
            edges.add(new Edge(16, rotateDir(Direction.DOWN, rotation)));
        }else{
            edges.add(new Edge(startEnd + 10, rotateDir(Direction.DOWN, rotation)));
        }

        for(Edge edge : edges){
            result.add(new Node(FieldType.NEUTRAL, Arrays.asList(edge)));
        }
        result.get(0).setType(getEndType(color));
        result.get(1).setType(getStartType(color));

        result.get(0).addEdge(new Edge(startTarget, rotateDir(Direction.DOWN, rotation)));

        return result;

    }

    private List<Node> getTarget(Color color, int rotation, int startTarget){

        List<Node> result = new ArrayList<>();

        result.add(new Node(getTargetType(color), Arrays.asList(
                new Edge(startTarget + 1, rotateDir(Direction.DOWN, rotation)))));
        result.add(new Node(getTargetType(color), Arrays.asList(
                new Edge(startTarget + 2, rotateDir(Direction.DOWN, rotation)))));
        result.add(new Node(getTargetType(color), Arrays.asList(
                new Edge(startTarget + 3, rotateDir(Direction.DOWN, rotation)))));
        result.add(new Node(getTargetType(color), new ArrayList<>()));

        return result;

    }

    private List<Node> getInit(){

        List<Node> result = new ArrayList<>();

        List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        colors.stream().forEach(color -> {
            for(int i = 0; i < 4; i++){
                result.add(new Node(getInitType(color), new ArrayList<>()));
            }
        });

        return result;

    }

    private Direction rotateDir(Direction direction, int rotation){

        int degree = direction.getDegree() + rotation;
        if(degree >= 360)
            degree = degree - 360;

        return dirs.get(degree);

    }

    private FieldType getInitType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDINIT;
        if(color.equals(Color.BLUE))
            return FieldType.BLUEINIT;
        if(color.equals(Color.GREEN))
            return FieldType.GREENINIT;
        return FieldType.YELLOWINIT;

    }

    private FieldType getStartType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDSTART;
        if(color.equals(Color.BLUE))
            return FieldType.BLUESTART;
        if(color.equals(Color.GREEN))
            return FieldType.GREENSTART;
        return FieldType.YELLOWSTART;

    }

    private FieldType getEndType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDEND;
        if(color.equals(Color.BLUE))
            return FieldType.BLUEEND;
        if(color.equals(Color.GREEN))
            return FieldType.GREENEND;
        return FieldType.YELLOWEND;

    }

    private FieldType getTargetType(Color color){

        if(color.equals(Color.RED))
            return FieldType.REDTARGET;
        if(color.equals(Color.BLUE))
            return FieldType.BLUETARGET;
        if(color.equals(Color.GREEN))
            return FieldType.GREENTARGET;
        return FieldType.YELLOWTARGET;

    }

}
