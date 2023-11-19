import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CSVManager {
    public static void escribirCSV(String nombreArchivo, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivo, false)) {
            writer.write(contenido);
        }
    }

    public static String leerCSV(String nombreArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }
}
