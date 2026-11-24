package task2.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/** Модель: зберігає набір фігур і містить логіку його обробки. */
public class ShapeModel {

    private static final String[] COLORS = {"Red", "Green", "Blue", "Yellow", "Black", "White", "Orange"};
    private static final double[] SIZES = {1.5, 2.0, 3.0, 4.5, 5.0, 6.5, 7.0, 8.0, 10.0};

    private Shape[] shapes;

    /** Створює набір з count фігур (count >= 10) із заздалегідь підготовлених даних. */
    public ShapeModel(int count) {
        Random random = new Random();
        shapes = new Shape[count];
        for (int i = 0; i < count; i++) {
            String color = COLORS[random.nextInt(COLORS.length)];
            double a = SIZES[random.nextInt(SIZES.length)];
            double b = SIZES[random.nextInt(SIZES.length)];
            // фігури чергуються по порядку: прямокутник, трикутник, коло
            switch (i % 3) {
                case 0:
                    shapes[i] = new Rectangle(color, a, b);
                    break;
                case 1:
                    shapes[i] = new Triangle(color, a, b);
                    break;
                default:
                    shapes[i] = new Circle(color, a);
            }
        }
    }

    public Shape[] getShapes() {
        return shapes;
    }

    /** Замінює набір фігур (наприклад, після читання з файлу). */
    public void setShapes(Shape[] shapes) {
        this.shapes = shapes;
    }

    /** Кількість фігур заданого виду. */
    public int countByType(Class<? extends Shape> type) {
        int count = 0;
        for (Shape shape : shapes) {
            if (type.isInstance(shape)) {
                count++;
            }
        }
        return count;
    }

    /** Сумарна площа всіх фігур. */
    public double totalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        return sum;
    }

    /** Сумарна площа фігур заданого виду (Rectangle.class, Triangle.class, Circle.class). */
    public double totalAreaByType(Class<? extends Shape> type) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (type.isInstance(shape)) {
                sum += shape.calcArea();
            }
        }
        return sum;
    }

    /** Впорядкування за зростанням площі (Comparator). */
    public void sortByArea() {
        Comparator<Shape> byArea = new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        };
        Arrays.sort(shapes, byArea);
    }

    /** Впорядкування за кольором (Comparator). */
    public void sortByColor() {
        Comparator<Shape> byColor = new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareTo(s2.getShapeColor());
            }
        };
        Arrays.sort(shapes, byColor);
    }
}
