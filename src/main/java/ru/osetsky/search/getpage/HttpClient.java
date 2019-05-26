package ru.osetsky.search.getpage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;

public class HttpClient {

    public static String getHttpClient(String http_url) {
        String string = null;
        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new URL(http_url).openStream()));
            string = reader.readLine();
            while (string != null) {
                System.out.println(Jsoup.parse(string).text());
                string = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}