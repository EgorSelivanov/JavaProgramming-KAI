package com.company.figures;

import java.awt.*;

public class Oval extends Figure{

    public Oval(Color col, int number, int speed) {
        super(col, number, speed);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), 35, 25);
    }
}
