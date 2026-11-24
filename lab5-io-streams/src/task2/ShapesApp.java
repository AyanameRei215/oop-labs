package task2;

import task2.controller.ShapeController;
import task2.io.ShapeFileService;
import task2.model.ShapeModel;
import task2.view.ConsoleView;

import java.util.Scanner;

/** Завдання 2: застосунок з фігурами (Task 3 Simple OOP) з меню, збереженням і читанням набору з файлу. */
public class ShapesApp {

    public static void run(Scanner scanner) {
        ShapeModel model = new ShapeModel(12);
        new ShapeController(model, new ConsoleView(), new ShapeFileService(), scanner).run();
    }
}
