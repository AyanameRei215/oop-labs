package task2.io;

import task2.model.Shape;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Робота з файлами: збереження і читання набору фігур об'єктними потоками (серіалізація за замовчуванням). */
public class ShapeFileService {

    /** Записує набір фігур у файл. Відсутні каталоги в шляху створюються. */
    public void save(Shape[] shapes, String path) throws IOException {
        File file = new File(path).getAbsoluteFile();
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeObject(shapes);
        }
    }

    /** Читає набір фігур з файлу. */
    public Shape[] load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Object object = in.readObject();
            if (!(object instanceof Shape[])) {
                throw new InvalidObjectException("файл не містить набору фігур");
            }
            return (Shape[]) object;
        }
    }
}
