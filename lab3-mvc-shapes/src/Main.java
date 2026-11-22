import controller.ShapeController;
import model.ShapeModel;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ShapeModel model = new ShapeModel(12);
        ConsoleView view = new ConsoleView();
        new ShapeController(model, view).run();
    }
}
