package model;

public class Triangle extends Shape {

    private final double base;
    private final double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Малюю трикутник з основою " + base + " та висотою " + height
                + " кольору " + getShapeColor());
    }

    @Override
    public String toString() {
        return "Triangle [base=" + base + ", height=" + height + ", " + super.toString() + "]";
    }
}
