import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.IOException;

public class getProperties {

    Properties settings = new Properties();
    String Url;
    String Username;
    String Password;
    {
        try {
            Url = getProps(settings).getProperty("url");
            Username = getProps(settings).getProperty("username");
            Password = getProps(settings).getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Properties getProps(Properties setting) throws IOException {
        InputStream in = Files.newInputStream(Paths.get("local.properties"));
        settings.load(in);
        return settings;

    }

}
