package com.company.figures;

import com.company.Main;
import com.company.WindowDimensions;

import java.awt.*;
import java.util.Observable;

public abstract class Figure extends Observable implements Runnable, IFigure{
    Thread thr;
    private boolean xplus;
    private boolean yplus;

    private int number;
    private int speed;
    private double incrementX;
    private double incrementY;
    private int x;
    private int y;
    private Color color;
    public Figure (Color col, int number, int speed) {
        xplus = true; yplus = true;
        x = 0; y = 30;
        this.color = col;
        this.number = number;
        this.speed = speed;
        this.incrementX = (Math.random() * (3 - 0.5)) + 0.5;
        this.incrementY = (Math.random() * (3 - 0.5)) + 0.5;

        Main.count++;
        thr = new Thread(this,Main.count+":"+number+":");
        thr.start();
    }
    public void run(){
        while (true){
            if (x >= WindowDimensions.width - 20)
                xplus = false;
            if (x <= -1)
                xplus = true;
            if (y >= WindowDimensions.height - 20)
                yplus = false;
            if (y <= 30)
                yplus = true;
            if (xplus)
                x += speed * incrementX;
            else
                x -= speed * incrementX;
            if (yplus)
                y += speed * incrementY;
            else
                y -= speed * incrementY;
            setChanged();
            notifyObservers (this);
            try{Thread.sleep (10);}
            catch (InterruptedException e){}
        }
    }

    @Override
    public abstract void draw(Graphics g);

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
