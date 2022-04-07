package com.company.figures;

import com.company.Main;
import com.company.WindowDimensions;

import java.awt.*;
import java.util.Observable;

public class Triangle extends Observable implements Runnable, IFigure{
    Thread thr;
    private boolean xplus;
    private boolean yplus;

    private int number;
    private int speed;
    private int[] x = new int[3];
    private int[] y = new int[3];
    private int x1;
    private int y1;
    private Color color;
    public Triangle (Color col, int number, int speed) {
        xplus = true; yplus = true;
        x[0] = 0; x[1] = 30; x[2] = 0;
        y[0] = 30; y[1] = 60; y[2] = 60;
        this.color = col;
        this.number = number;
        this.speed = speed;
        Main.count++;
        thr = new Thread(this,Main.count+":"+number+":");
        thr.start();
    }
    public void run(){
        while (true){
            if (x[1] >= WindowDimensions.width)
                xplus = false;
            if (x[0] <= -1)
                xplus = true;
            if (y[1] >= WindowDimensions.height)
                yplus = false;
            if (y[0] <= 30)
                yplus = true;
            if (xplus)
                for (int i = 0; i < 3; i++){
                    x[i] += speed;
                }
            else
                for (int i = 0; i < 3; i++){
                    x[i] -= speed;
                }
            if (yplus)
                for (int i = 0; i < 3; i++){
                    y[i] += speed;
                }
            else
                for (int i = 0; i < 3; i++){
                    y[i] -= speed;
                }
            setChanged();
            notifyObservers (this);
            try{Thread.sleep (10);}
            catch (InterruptedException e){}
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillPolygon(x, y, 3);
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public Color getColor() {
        return color;
    }
}
