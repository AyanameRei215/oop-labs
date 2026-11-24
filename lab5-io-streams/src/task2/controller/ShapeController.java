package task2.controller;

import task2.io.ShapeFileService;
import task2.model.Circle;
import task2.model.Rectangle;
import task2.model.Shape;
import task2.model.ShapeModel;
import task2.model.Triangle;
import task2.view.ConsoleView;
import util.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/** Контролер: меню, введення з клавіатури та зв'язок моделі, представлення і файлового сервісу. */
public class ShapeController {

    private final ShapeModel model;
    private final ConsoleView view;
    private final ShapeFileService fileService;
    private final Scanner scanner;

    public ShapeController(ShapeModel model, ConsoleView view, ShapeFileService fileService, Scanner scanner) {
        this.model = model;
        this.view = view;
        this.fileService = fileService;
        this.scanner = scanner;
    }

    public void run() {
        view.showTitle("Початковий набір фігур");
        view.showShapes(model.getShapes());

        while (true) {
            view.showMenu();
            if (!scanner.hasNextLine()) {
                return;
            }
            String line = scanner.nextLine().trim();
            int command;
            try {
                command = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                view.showError("команда має бути числом від 0 до 8, а введено: \"" + line + "\"");
                continue;
            }
            if (command == 0) {
                return;
            }
            execute(command);
        }
    }

    private void execute(int command) {
        switch (command) {
            case 1:
                view.showTitle("Набір фігур");
                view.showShapes(model.getShapes());
                break;
            case 2:
                view.showTitle("Малювання фігур");
                view.showDrawing(model.getShapes());
                break;
            case 3:
                view.showArea("Сумарна площа всіх фігур", model.totalArea());
                break;
            case 4:
                showAreaByType();
                break;
            case 5:
                model.sortByArea();
                view.showTitle("Сортування за площею (зростання)");
                view.showShapes(model.getShapes());
                break;
            case 6:
                model.sortByColor();
                view.showTitle("Сортування за кольором");
                view.showShapes(model.getShapes());
                break;
            case 7:
                saveToFile();
                break;
            case 8:
                loadFromFile();
                break;
            default:
                view.showError("невідома команда " + command + " (доступні команди від 0 до 8)");
        }
    }

    private void showAreaByType() {
        String name = Input.ask(scanner, "Введіть вид фігури (Rectangle, Triangle або Circle): ");
        Class<? extends Shape> type = parseType(name);
        if (type == null) {
            view.showError("невідомий вид фігури \"" + name + "\"");
            return;
        }
        int count = model.countByType(type);
        if (count == 0) {
            view.showMessage("Фігур виду " + type.getSimpleName() + " у наборі немає");
            return;
        }
        view.showArea("Сумарна площа фігур виду " + type.getSimpleName() + " (" + count + " шт.)",
                model.totalAreaByType(type));
    }

    private Class<? extends Shape> parseType(String name) {
        switch (name.toLowerCase()) {
            case "rectangle":
                return Rectangle.class;
            case "triangle":
                return Triangle.class;
            case "circle":
                return Circle.class;
            default:
                return null;
        }
    }

    private void saveToFile() {
        String path = Input.ask(scanner, "Введіть шлях і ім'я файлу для збереження (наприклад, data/shapes.dat): ");
        if (path.isEmpty()) {
            view.showError("шлях до файлу не може бути порожнім");
            return;
        }
        try {
            fileService.save(model.getShapes(), path);
            view.showMessage("Набір із " + model.getShapes().length + " фігур збережено у файл "
                    + new File(path).getAbsolutePath());
        } catch (IOException e) {
            view.showError("не вдалося зберегти файл: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        String path = Input.ask(scanner, "Введіть шлях і ім'я файлу для читання (наприклад, data/shapes.dat): ");
        if (path.isEmpty()) {
            view.showError("шлях до файлу не може бути порожнім");
            return;
        }
        try {
            Shape[] loaded = fileService.load(path);
            model.setShapes(loaded);
            view.showMessage("Прочитано " + loaded.length + " фігур з файлу " + new File(path).getAbsolutePath());
            view.showShapes(loaded);
        } catch (FileNotFoundException e) {
            view.showError("файл не знайдено або недоступний: " + path);
        } catch (IOException e) {
            view.showError("не вдалося прочитати файл: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            view.showError("у файлі є об'єкти невідомого класу: " + e.getMessage());
        }
    }
}
