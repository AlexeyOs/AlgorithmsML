package ru.osetsky.search.getpage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;

public class IfHtml {
    public static void main(String[] args) throws IOException {
        try {
            URL url = new URL("http://www.patriarchia.ru/");

            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                String string = reader.readLine();
                while (string != null) {
//                    System.out.println(string);
                    System.out.println(Jsoup.parse(string).text());
                    string = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

    }
}