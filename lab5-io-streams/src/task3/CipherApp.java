package task3;

import util.Input;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/** Завдання 3: консольний інтерфейс шифрування та дешифрування файлу. */
public class CipherApp {

    public static void run(Scanner scanner) {
        System.out.println("1 — Зашифрувати файл");
        System.out.println("2 — Розшифрувати файл");
        String mode = Input.ask(scanner, "Ваш вибір: ");
        if (!mode.equals("1") && !mode.equals("2")) {
            System.out.println("Помилка: потрібно ввести 1 або 2");
            return;
        }

        String inPath = Input.ask(scanner, "Вхідний файл (наприклад, data/text.txt): ");
        String outPath = Input.ask(scanner, "Вихідний файл (наприклад, data/encrypted.bin): ");
        if (inPath.isEmpty() || outPath.isEmpty()) {
            System.out.println("Помилка: шлях до файлу не може бути порожнім");
            return;
        }
        if (new File(inPath).getAbsoluteFile().toPath().normalize()
                .equals(new File(outPath).getAbsoluteFile().toPath().normalize())) {
            System.out.println("Помилка: вхідний і вихідний файли мають бути різними");
            return;
        }

        String keyText = Input.askRaw(scanner, "Ключовий символ (рівно один символ): ");
        if (keyText.length() != 1) {
            System.out.println("Помилка: ключ має складатися рівно з одного символу");
            return;
        }
        char key = keyText.charAt(0);
        if ((key & 0xFF) == 0) {
            System.out.println("Помилка: код ключа дає нульовий зсув, оберіть інший символ");
            return;
        }

        System.out.println("Ключ: '" + key + "' (код " + (int) key + ", зсув " + (key & 0xFF) + ")");

        File parent = new File(outPath).getAbsoluteFile().getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        try (InputStream in = new BufferedInputStream(new FileInputStream(inPath));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outPath))) {
            if (mode.equals("1")) {
                Cipher.encrypt(in, out, key);
                System.out.println("Файл зашифровано: " + outPath);
            } else {
                Cipher.decrypt(in, out, key);
                System.out.println("Файл розшифровано: " + outPath);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Помилка: файл не знайдено або недоступний: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Помилка роботи з файлом: " + e.getMessage());
        }
    }
}
