package utilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    public static boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException malformedURLException) {
            return false;
        }
    }
}
