package util;

import java.util.Scanner;

/** Допоміжні методи для читання рядків з клавіатури. */
public final class Input {

    private Input() {
    }

    /** Виводить підказку і повертає введений рядок без пробілів по краях (порожній, якщо ввід закінчився). */
    public static String ask(Scanner scanner, String prompt) {
        return askRaw(scanner, prompt).trim();
    }

    /** Те саме, але без обрізання пробілів (потрібно, наприклад, для ключового символу). */
    public static String askRaw(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }
}
