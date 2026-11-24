package task3;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/** Потік шифрування: кожен записаний байт збільшується на код ключового символу (за модулем 256). */
public class EncryptOutputStream extends FilterOutputStream {

    private final int shift;

    public EncryptOutputStream(OutputStream out, char key) {
        super(out);
        this.shift = key & 0xFF;
    }

    @Override
    public void write(int b) throws IOException {
        out.write((b + shift) & 0xFF);
    }
}
