package com.company.figures;

import java.awt.*;

public class Ball extends Figure {

    public Ball(Color col, int number, int speed) {
        super(col, number, speed);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), 20, 20);
    }
}

