package task2.view;

import task2.model.Shape;

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

    public void showMenu() {
        System.out.println();
        System.out.println("=== Меню ===");
        System.out.println("1 — Показати набір фігур");
        System.out.println("2 — Намалювати фігури");
        System.out.println("3 — Сумарна площа всіх фігур");
        System.out.println("4 — Сумарна площа фігур заданого виду");
        System.out.println("5 — Впорядкувати за площею");
        System.out.println("6 — Впорядкувати за кольором");
        System.out.println("7 — Зберегти набір у файл");
        System.out.println("8 — Прочитати набір з файлу");
        System.out.println("0 — Повернутися в головне меню");
        System.out.print("Ваш вибір: ");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Помилка: " + message);
    }
}
