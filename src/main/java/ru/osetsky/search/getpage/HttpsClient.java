package ru.osetsky.search.getpage;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.osetsky.search.repositories.DbConnect;

import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
import java.sql.Date;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;

@Component
public class HttpsClient{

    public static String getHttpsClient(String https_url){
        URL url;
        try {
            url = new URL(https_url);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            return print_content(con);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String print_content(HttpsURLConnection con){
        String input = null;
        StringBuilder output = new StringBuilder();
        if(con!=null){
            try {
                System.out.println("****** Content of the URL ********");
                BufferedReader br = new BufferedReader( new InputStreamReader(con.getInputStream()) );
                while ((input = br.readLine()) != null){
                    output.append(Jsoup.parse(input).text());
                }
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        DbConnect dbConnect = null;
        try {
            dbConnect = new DbConnect();
            dbConnect.addIntoTableText("0",output.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    private static void print_https_cert(HttpsURLConnection con){
        if(con!=null){
            try {
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
