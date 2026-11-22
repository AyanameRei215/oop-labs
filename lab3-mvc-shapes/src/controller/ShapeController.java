package controller;

import model.Circle;
import model.Rectangle;
import model.ShapeModel;
import model.Triangle;
import view.ConsoleView;

/** Контролер: зв'язує модель і представлення, керує послідовністю дій. */
public class ShapeController {

    private final ShapeModel model;
    private final ConsoleView view;

    public ShapeController(ShapeModel model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.showTitle("Початковий набір фігур");
        view.showShapes(model.getShapes());

        view.showTitle("Малювання фігур");
        view.showDrawing(model.getShapes());

        view.showTitle("Площі");
        view.showArea("Сумарна площа всіх фігур", model.totalArea());
        view.showArea("Сумарна площа прямокутників", model.totalAreaByType(Rectangle.class));
        view.showArea("Сумарна площа трикутників", model.totalAreaByType(Triangle.class));
        view.showArea("Сумарна площа кіл", model.totalAreaByType(Circle.class));

        model.sortByArea();
        view.showTitle("Сортування за площею (зростання)");
        view.showShapes(model.getShapes());

        model.sortByColor();
        view.showTitle("Сортування за кольором");
        view.showShapes(model.getShapes());
    }
}
