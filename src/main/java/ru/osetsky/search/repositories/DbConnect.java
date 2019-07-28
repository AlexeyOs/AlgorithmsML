package ru.osetsky.search.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sqlite.JDBC;

import java.sql.*;

@Component
public class DbConnect {

    private static final Logger LOG = LoggerFactory.getLogger(DbConnect.class);

    private static final String URL = "jdbc:sqlite:sqlite/search.db";

    private Connection connection;

    public DbConnect() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        createTableText();
        createTableSites();
        createTableCategories();
    }

    private void createTableText() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(true);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `TEXT` ( " +
                    "  `ID_TEXT`         INT(10) NOT NULL PRIMARY KEY," +
                    "  `TEXT`            TEXT(1024)," +
                    "  `DATE_TEXT`       DATE" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void createTableSites() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(true);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `SITES` ( \n" +
                    "  URL  VARCHAR(40) NOT NULL PRIMARY KEY,\n" +
                    "  ID_TEXT       INT(10),\n" +
                    "  FOREIGN KEY (ID_TEXT) REFERENCES TEXT(ID_TEXT)\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void createTableCategories() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(true);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `CATEGORIES` ( \n" +
                    "  `ID_CATEGORY`       INT(10) NOT NULL PRIMARY KEY,\n" +
                    "  `NAME`              VARCHAR(40),\n" +
                    "  `DESCRIPTION`       VARCHAR(40),\n" +
                    "  `URL`               VARCHAR(40),\n" +
                    "  FOREIGN KEY (URL) REFERENCES SITES(URL)\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void addIntoTableText(String id, String text) {
        try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO TEXT(ID_TEXT,TEXT, DATE_TEXT) values(?,?,?)");
                st.setString(1, id);
                st.setString(2, text);
                st.setDate(3, null);
                st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTableText(String id, String text) {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE TEXT SET ID_TEXT = ?,TEXT = ?, DATE_TEXT =?");
            st.setString(1, id);
            st.setString(2, text);
            st.setDate(3, null);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIntoTableCategories(String url, String id_text) {
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO SITES(URL, ID_TEXT) values(?,?)");
            st.setString(1, url);
            st.setString(2, id_text);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIntoTableSites(int id, String name, String description, String url) {
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO CATEGORIES(ID_CATEGORY, NAME,DESCRIPTION,URL) " +
                    "values(?,?,?,?)");
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, description);
            st.setString(4, url);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateTableSites(int id, String name, String description, String url) {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE CATEGORIES SET ID_CATEGORY=?, NAME=?,DESCRIPTION=?,URL=?");
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, description);
            st.setString(4, url);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
