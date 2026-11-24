package task1;

import util.Input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Завдання 1: знайти у файлі рядок з найбільшою кількістю слів. */
public class MaxWordsLine {

    /** Слово — послідовність літер і цифр (усередині може бути апостроф або дефіс). */
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}]+(?:['’\\-][\\p{L}\\p{N}]+)*");

    /** Результат пошуку: номер рядка, кількість слів і сам рядок. */
    public static class LineInfo {
        private final int number;
        private final int words;
        private final String text;

        public LineInfo(int number, int words, String text) {
            this.number = number;
            this.words = words;
            this.text = text;
        }

        public int getNumber() {
            return number;
        }

        public int getWords() {
            return words;
        }

        public String getText() {
            return text;
        }
    }

    public static int countWords(String line) {
        Matcher matcher = WORD.matcher(line);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * Читає файл рядок за рядком і повертає рядок з найбільшою кількістю слів
     * (перший з них, якщо таких кілька) або null, якщо в файлі немає жодного слова.
     */
    public static LineInfo find(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            LineInfo best = null;
            String line;
            int number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                int words = countWords(line);
                if (words > 0 && (best == null || words > best.getWords())) {
                    best = new LineInfo(number, words, line);
                }
            }
            return best;
        }
    }

    public static void run(Scanner scanner) {
        String path = Input.ask(scanner, "Введіть шлях до файлу (наприклад, data/text.txt): ");
        if (path.isEmpty()) {
            System.out.println("Помилка: шлях до файлу не може бути порожнім");
            return;
        }
        try {
            LineInfo result = find(path);
            if (result == null) {
                System.out.println("У файлі немає жодного слова");
            } else {
                System.out.println("Рядок з найбільшою кількістю слів (рядок №" + result.getNumber()
                        + ", слів: " + result.getWords() + "):");
                System.out.println(result.getText());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Помилка: файл не знайдено або недоступний: " + path);
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
