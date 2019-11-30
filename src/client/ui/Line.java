package client.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Line {
    private int[] startPoint;
    private int[] endPoint;
    private int width;
    private Color color;


    public Line(int[] startPoint, int[] endPoint, int width, Color color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.width = width;
        this.color = color;
    }

    public int[] getStartPoint() {
        return startPoint;
    }

    public int[] getEndPoint() {
        return endPoint;
    }

    public int getWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }
}