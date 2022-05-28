package org.dhbw.swe.game;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Player {

    private String id;
    private String name;
    private int winCount;

    public Player(String name) {

        this.id = name + "_" + getDateTime();
        this.name = name;
        this.winCount = 0;

    }

    public Player(String id, String name, int winCount) {

        this.id = id;
        this.name = name;
        this.winCount = winCount;

    }

    public Player() {
    }

    public void setId(String id) {

        this.id = id;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void addWin() {
        this.winCount += 1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWinCount() {
        return winCount;
    }

    private String getDateTime(){

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

        return formatter.format(date);

    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", winCount=" + winCount +
                '}';
    }
}