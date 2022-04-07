package com.company.figures;

import java.awt.*;

public class Rect extends Figure{

    public Rect(Color col, int number, int speed) {
        super(col, number, speed);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), 30, 20);
    }
}
