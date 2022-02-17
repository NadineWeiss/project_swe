package org.dhbw.swe.visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameButton extends JButton {

    private boolean isClickable;

    public BufferedImage combineImages(BufferedImage imgA, BufferedImage imgB, int width, int height){

        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(imgB, 0, 0, null);
        g.drawImage(imgA, 0, 0, null);


        g.dispose();

        return combined;

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;

    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }


}
