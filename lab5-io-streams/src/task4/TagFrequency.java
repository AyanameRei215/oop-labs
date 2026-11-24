package task4;

import util.Input;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Завдання 4: підрахунок частоти тегів на сторінці за URL. */
public class TagFrequency {

    public static final String DEFAULT_URL = "https://uk.wikipedia.org/wiki/Java";

    private static final Pattern COMMENT = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
    private static final Pattern SCRIPT = Pattern.compile("(<script\\b[^>]*>).*?(</script\\s*>)",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern STYLE = Pattern.compile("(<style\\b[^>]*>).*?(</style\\s*>)",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    /** Початок відкриваючого тегу: "<" і ім'я тегу. Закриваючі теги (</...>), коментарі та <!DOCTYPE> не підходять. */
    private static final Pattern TAG = Pattern.compile("<([a-zA-Z][a-zA-Z0-9-]*)");

    /** Завантажує текст сторінки за URL. */
    public static String download(String url) throws IOException, InterruptedException {
        URI uri = URI.create(url);
        if (uri.getScheme() == null || !(uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
            throw new IllegalArgumentException("підтримуються лише адреси http:// та https://");
        }
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(20))
                .header("User-Agent", "Mozilla/5.0 (lab5 tag counter)")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("сервер повернув код " + response.statusCode());
        }
        return response.body();
    }

    /** Підраховує, скільки разів зустрічається кожен тег (без урахування регістру). */
    public static Map<String, Integer> countTags(String html) {
        String text = COMMENT.matcher(html).replaceAll("");
        text = SCRIPT.matcher(text).replaceAll("$1$2");
        text = STYLE.matcher(text).replaceAll("$1$2");

        Map<String, Integer> counts = new TreeMap<>();
        Matcher matcher = TAG.matcher(text);
        while (matcher.find()) {
            counts.merge(matcher.group(1).toLowerCase(), 1, Integer::sum);
        }
        return counts;
    }

    /** Теги в лексикографічному порядку (за зростанням назви). */
    public static List<Map.Entry<String, Integer>> sortedByName(Map<String, Integer> counts) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort(Map.Entry.comparingByKey());
        return entries;
    }

    /** Теги за зростанням частоти появи (при однаковій частоті — за назвою). */
    public static List<Map.Entry<String, Integer>> sortedByFrequency(Map<String, Integer> counts) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()));
        return entries;
    }

    private static void print(List<Map.Entry<String, Integer>> entries) {
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.printf("%-14s %d%n", entry.getKey(), entry.getValue());
        }
    }

    public static void run(Scanner scanner) {
        String url = Input.ask(scanner, "Введіть URL (Enter — " + DEFAULT_URL + "): ");
        if (url.isEmpty()) {
            url = DEFAULT_URL;
        } else if (!url.contains("://")) {
            url = "https://" + url;
        }
        try {
            String html = download(url);
            Map<String, Integer> counts = countTags(html);
            if (counts.isEmpty()) {
                System.out.println("На сторінці не знайдено жодного тегу");
                return;
            }
            int total = counts.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Сторінка: " + url);
            System.out.println("Різних тегів: " + counts.size() + ", усього тегів: " + total);

            System.out.println();
            System.out.println("--- а) У лексикографічному порядку ---");
            print(sortedByName(counts));

            System.out.println();
            System.out.println("--- б) За зростанням частоти появи ---");
            print(sortedByFrequency(counts));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: некоректний URL (" + e.getMessage() + ")");
        } catch (ConnectException e) {
            System.out.println("Помилка: не вдалося підключитися до сервера (перевірте адресу та інтернет-з'єднання)");
        } catch (IOException e) {
            String reason = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            System.out.println("Помилка підключення або читання сторінки: " + reason);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Завантаження перервано");
        }
    }
}
