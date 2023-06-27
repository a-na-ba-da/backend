package kr.anabada.anabadaserver.global.Util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UriValidateUtils {

    public static boolean isExistUrl(String url) {
        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            // set options
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("HEAD");
            connection.setUseCaches(false);

            // Set general request headers
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "ko-KR");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Content-Type", "text/html;charset=UTF-8");

            // Set additional headers
            connection.setRequestProperty("Referer", "https://www.google.com");
            connection.setRequestProperty("Origin", "https://www.google.com");

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP;
        } catch (IOException e) {
            return false;
        }
    }

}
