import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Reader { // класс для считывания информации из файла

    public  List<String> readFileContents(String path) { // метод для считывания файла CSV в список со строками
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            System.out.println("Проверьте путь " + path);
            return Collections.emptyList();
        }
    }
}
