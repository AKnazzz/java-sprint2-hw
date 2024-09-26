import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Reader {

    /**
     * Считывает содержимое файла по указанному пути и возвращает его в виде списка строк.
     *
     * @param path путь к файлу
     * @return список строк, содержащих содержимое файла или пустой список в случае ошибки
     */

    public List<String> readFileContents(String path) {
        Path filePath = Path.of(path); // Преобразуем строку пути в объект Path
        if (!Files.exists(filePath)) { // Проверяем существование файла
            System.out.println("Файл не найден: " + path);
            return Collections.emptyList();
        }

        try {
            return Files.readAllLines(filePath); // Читаем строки из файла
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return Collections.emptyList(); // Возвращаем пустой список в случае ошибки
        }
    }
}
