package org.dhbw.swe.visualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FieldButton extends GameButton {

    private BufferedImage backgroundImage;
    private int index;
    private Color pieceColor;


    public FieldButton(BufferedImage img, int index) {

        this.index = index;

        backgroundImage = resize(img, 50, 50);
        setBackgroundImage(false);

        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);

    }

    public void setBackgroundImage(boolean marked){

        try {

            BufferedImage imageMarked = ImageIO.read(getClass().getClassLoader().getResource("FieldMarked.png"));
            imageMarked = resize(imageMarked, 50, 50);

            if(pieceColor != null){

                BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource(getImageName(pieceColor)));
                image = resize(image, 50, 50);

                this.setIcon(new ImageIcon(marked ? combineImages(image, imageMarked, 50, 50) :
                        combineImages(image, backgroundImage, 50, 50)));

            }else{

                this.setIcon(marked ? new ImageIcon(imageMarked) : new ImageIcon(backgroundImage));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPiece(Color color) {

        try {

            BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource(getImageName(color)));
            image = resize(image, 50, 50);

            this.setIcon(new ImageIcon(combineImages(image, backgroundImage, 50, 50)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        pieceColor = color;

    }

    public void removePiece(){

        pieceColor = null;
        setBackgroundImage(false);

    }

    private String getImageName(Color color){

        if(color.equals(Color.RED)){
            return "PieceRed.png";
        }
        if(color.equals(Color.BLUE)){
            return "PieceBlue.png";
        }
        if(color.equals(Color.GREEN)){
            return "PieceGreen.png";
        }
        return "PieceYellow.png";
    }

    public int getIndex() {
        return index;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

}
