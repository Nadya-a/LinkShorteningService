import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LinkService {
    private String fileName = "links.json";
    private Gson gson = new Gson();

    // читаем все ссылки из файла
    public List<ShortLink> readFromFile() throws Exception {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        FileReader reader = new FileReader(fileName);
        Type type = new TypeToken<ArrayList<ShortLink>>(){}.getType();
        List<ShortLink> links = gson.fromJson(reader, type);
        reader.close();

        if (links == null) {
            return new ArrayList<>();
        }
        return links;
    }

    // сохраняем весь список в файл
    public void saveToFile(List<ShortLink> links) throws Exception {
        FileWriter writer = new FileWriter(fileName);
        writer.write(gson.toJson(links));
        writer.close();
    }

    // добавляем одну ссылку
    public void addToFile(ShortLink shortLink) throws Exception {
        List<ShortLink> links = readFromFile();
        links.add(shortLink);
        saveToFile(links);
    }
}
