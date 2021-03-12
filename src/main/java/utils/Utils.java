package utils;

public class Utils {

    public static String getFormattedUrl(String url){
        return url.replace("https://", "").replace("http://", "");
    }
}
