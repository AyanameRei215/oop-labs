package model;

import java.util.Locale;

/** Абстрактна фігура: має колір і вміє обчислювати свою площу. */
public abstract class Shape implements Drawable {

    private final String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    /** Обчислення площі фігури. */
    public abstract double calcArea();

    @Override
    public String toString() {
        return String.format(Locale.US, "color=%s, area=%.2f", shapeColor, calcArea());
    }
}
