package ru.osetsky.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.osetsky.search.getpage.HttpClient;
import ru.osetsky.search.getpage.HttpsClient;
import ru.osetsky.search.repositories.DbConnect;

import java.sql.SQLException;

@SpringBootApplication
public class SearchApplication {

	public static void main(String[] args) {
		System.out.println(HttpClient.getHttpClient("http://www.patriarchia.ru/"));
		System.out.println(HttpsClient.getHttpsClient("https://www.google.com/"));
		try {
			DbConnect dbConnect = new DbConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		SpringApplication.run(SearchApplication.class, args);
	}

}
