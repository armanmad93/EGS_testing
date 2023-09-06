package endpoints;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Routes {

    private Routes() {
        // empty constructor
    }

    public static final String BASE_URL;
    public static final String ACCESS_TOKEN;
    public static final String CREATE_USER_URL;
    public static final String POST_USER_URL;

    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties");
        }

        BASE_URL = properties.getProperty("BASE_URL");
        ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
        CREATE_USER_URL = properties.getProperty("CREATE_USER_URL")
                .replace("${BASE_URL}", BASE_URL);
        POST_USER_URL=properties.getProperty("POST_USER_URL");
    }

}
