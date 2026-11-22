package view;

import model.Shape;

import java.util.Locale;

/** Представлення: лише виводить дані в консоль, логіки обробки не містить. */
public class ConsoleView {

    public void showTitle(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    public void showShapes(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i + 1) + ". " + shapes[i]);
        }
    }

    public void showDrawing(Shape[] shapes) {
        for (Shape shape : shapes) {
            shape.draw();
        }
    }

    public void showArea(String label, double area) {
        System.out.println(String.format(Locale.US, "%s: %.2f", label, area));
    }
}
