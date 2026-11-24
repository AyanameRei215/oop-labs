import task1.MaxWordsLine;
import task2.ShapesApp;
import task3.CipherApp;
import task4.TagFrequency;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {
            System.out.println();
            System.out.println("=== Лабораторна робота 5: потоки введення/виведення ===");
            System.out.println("1 — Рядок з найбільшою кількістю слів у файлі");
            System.out.println("2 — Фігури: збереження та читання набору з файлу");
            System.out.println("3 — Шифрування та дешифрування файлу");
            System.out.println("4 — Частота тегів на веб-сторінці");
            System.out.println("0 — Вихід");
            System.out.print("Ваш вибір: ");
            if (!scanner.hasNextLine()) {
                return;
            }
            switch (scanner.nextLine().trim()) {
                case "1":
                    MaxWordsLine.run(scanner);
                    break;
                case "2":
                    ShapesApp.run(scanner);
                    break;
                case "3":
                    CipherApp.run(scanner);
                    break;
                case "4":
                    TagFrequency.run(scanner);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Помилка: введіть число від 0 до 4");
            }
        }
    }
}
