package com.gmovie.gmovie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseTest {
    public static void main(String[] args) {
        // try (Connection connection = DriverManager.getConnection(url, user,
        // password)) {
        // System.out.println("Database connected!");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // try (Connection connection = DriverManager.getConnection(url, user,
        // password)) {
        // String sql = "INSERT INTO a (userName, userNum, address) VALUES (?, ?, ?)";
        // PreparedStatement statement = connection.prepareStatement(sql);

        // statement.setString(1, "test1");
        // statement.setString(2, "test2");
        // statement.setString(3, "test3");

        // int rowsInserted = statement.executeUpdate();
        // if (rowsInserted > 0) {
        // System.out.println("A new row was inserted successfully!");
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }
}
