package ru.osetsky.search.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.sql.*;


public class DbConnect {

    private static final Logger LOG = LoggerFactory.getLogger(DbConnect.class);

    private static final String URL = "jdbc:sqlite:D:/codeStudies/developer/search/sqlite/search.db";

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

    public void addIntoTable(String name, String href) {
        try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO PARCE(name, href) values(?,?)");
                st.setString(1, name);
                st.setString(2, href);
                st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
