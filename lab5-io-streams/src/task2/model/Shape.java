package task2.model;

import java.io.Serializable;
import java.util.Locale;

/** Абстрактна фігура: має колір і вміє обчислювати свою площу. */
public abstract class Shape implements Drawable, Serializable {

    private static final long serialVersionUID = 1L;

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
