package base;

public class ApplicationConfig {

    public static String getEnvironment() {
        String environment = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        return environment;
    }

}
