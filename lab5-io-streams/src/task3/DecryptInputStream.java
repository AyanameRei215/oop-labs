package task3;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Потік дешифрування: кожен прочитаний байт зменшується на код ключового символу (за модулем 256). */
public class DecryptInputStream extends FilterInputStream {

    private final int shift;

    public DecryptInputStream(InputStream in, char key) {
        super(in);
        this.shift = key & 0xFF;
    }

    @Override
    public int read() throws IOException {
        int b = in.read();
        return b == -1 ? -1 : (b - shift) & 0xFF;
    }

    @Override
    public int read(byte[] buffer, int off, int len) throws IOException {
        int n = in.read(buffer, off, len);
        for (int i = off; n > 0 && i < off + n; i++) {
            buffer[i] = (byte) ((buffer[i] - shift) & 0xFF);
        }
        return n;
    }

    @Override
    public boolean markSupported() {
        return false;
    }
}
