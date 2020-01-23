package service.config;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static URL resource=Config.class.getClassLoader().getResource("config.properties");
    private static String CONFIG_LOCATION;

    static {
        try {
            CONFIG_LOCATION = Paths.get(resource.toURI()).toFile().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //"D:\\Proiecte MAP + PLF\\MAP\\Lab7Gui\\src\\main\\resources\\config.properties";
    public static Properties getProperties() {
        Properties properties=new Properties();
        try {
            FileReader fr=new FileReader(CONFIG_LOCATION);
            properties.load(fr);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config properties");
        }
    }
}
