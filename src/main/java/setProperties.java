import java.io.*;
import java.util.Properties;
import java.io.IOException;

public class setProperties {
    public static void main(String[] args) throws IOException {
        Properties settings = new Properties();
        settings.setProperty("url", "jdbc:mysql://localhost:3306/commerce?useSSL=false");
        settings.setProperty("username", "root");
        settings.setProperty("password", "1993");
        OutputStream out = new FileOutputStream("Local.properties");
        settings.store(out, "Program Properties");


    }
}
