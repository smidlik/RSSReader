package utils;

import model.RSSSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String CONFIG_FILE = "config.cfg";

    public static String loadStringFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    public static void saveStringToFile(String filePath,byte[] data) throws IOException {

        Path path = Paths.get(filePath);

        Files.write(path,data);

    }

    public static List<RSSSource> loadSorces() throws IOException {
        List<RSSSource> sources = new ArrayList<>();
        new BufferedReader(new StringReader(loadStringFromFile(CONFIG_FILE)))
                .lines().forEach(source ->{
            System.out.println(source);
            String[] parts = source.split(";");
            sources.add(new RSSSource(parts[0],parts[1]));
        });

        return sources;
    }
    public static void saveSources(List<RSSSource> sources){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sources.size(); i++) {
            builder.append(String.format("%s;%s",sources.get(i).getName(),sources.get(i).getSource()));
            builder.append((i!= sources.size()-1) ? "\n" : "");
        }
        try {
            saveStringToFile(CONFIG_FILE, builder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
