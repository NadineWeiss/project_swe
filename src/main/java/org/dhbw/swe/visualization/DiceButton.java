package org.dhbw.swe.visualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DiceButton extends GameButton {



    public DiceButton() {

        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);

    }

    public void setDice(int number, Color color) {

        try {

            BufferedImage imageDice = ImageIO.read(getClass().getClassLoader().getResource(getImageName(number)));
            imageDice = resize(imageDice, 80, 80);

            BufferedImage imageColor = ImageIO.read(getClass().getClassLoader().getResource(getImageName(color)));
            imageColor = resize(imageColor, 80, 80);

            this.setIcon(new ImageIcon(combineImages(imageDice, imageColor, 80, 80)));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDice(Color color) {

        try {

            BufferedImage imageDice = ImageIO.read(getClass().getClassLoader().getResource("DiceQuestionmark.png"));
            imageDice = resize(imageDice, 80, 80);

            BufferedImage imageColor = ImageIO.read(getClass().getClassLoader().getResource(getImageName(color)));
            imageColor = resize(imageColor, 80, 80);

            this.setIcon(new ImageIcon(combineImages(imageDice, imageColor, 80, 80)));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setColor(Color color) {

        try {

            BufferedImage imageColor = ImageIO.read(getClass().getClassLoader().getResource(getImageName(color)));
            imageColor = resize(imageColor, 80, 80);

            this.setIcon(new ImageIcon(imageColor));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private String getImageName(int number){

        if(number == 1){
            return "DiceOne.png";
        }
        if(number == 2){
            return "DiceTwo.png";
        }
        if(number == 3){
            return "DiceThree.png";
        }
        if(number == 4){
            return "DiceFour.png";
        }
        if(number == 5){
            return "DiceFive.png";
        }
        if(number == 6){
            return "DiceSix.png";
        }
        return "DiceQuestionmark.png";
    }

    private String getImageName(Color color){

        if(color.equals(Color.RED)){
            return "FieldRed.png";
        }
        if(color.equals(Color.BLUE)){
            return "FieldBlue.png";
        }
        if(color.equals(Color.GREEN)){
            return "FieldGreen.png";
        }
        return "FieldYellow.png";
    }

}
