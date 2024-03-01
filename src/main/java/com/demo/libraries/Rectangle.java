package com.demo.libraries;

import java.lang.Math;

public class Rectangle {
    private int x1, x2, y1, y2;

    public Rectangle(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public long calculateArea() {
        long area = (long) Math.abs(this.x2 - this.x1) * (long) Math.abs(this.y2 - this.y1);
        assert area >= 0;
        return area;
    }
}

