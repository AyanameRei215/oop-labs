package task3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Шифрування і дешифрування потоку: код кожного символу збільшується на код ключового символу. */
public final class Cipher {

    private Cipher() {
    }

    /** Читає дані з in, шифрує їх і записує в out. */
    public static void encrypt(InputStream in, OutputStream out, char key) throws IOException {
        OutputStream encrypting = new EncryptOutputStream(out, key);
        byte[] buffer = new byte[8192];
        int count;
        while ((count = in.read(buffer)) != -1) {
            encrypting.write(buffer, 0, count);
        }
        encrypting.flush();
    }

    /** Читає зашифровані дані з in, дешифрує їх і записує в out. */
    public static void decrypt(InputStream in, OutputStream out, char key) throws IOException {
        InputStream decrypting = new DecryptInputStream(in, key);
        byte[] buffer = new byte[8192];
        int count;
        while ((count = decrypting.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        out.flush();
    }
}
