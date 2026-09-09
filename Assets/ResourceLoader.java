package Assets;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    public static String loadText(String path) throws IOException {

        InputStream input = ResourceLoader.class.getResourceAsStream(path);

        if (input == null) {
            throw new IOException("Resource not found: " + path);
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[1024];
        int n;

        while ((n = input.read(data)) != -1) {
            buffer.write(data, 0, n);
        }

        input.close();

        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }
}
