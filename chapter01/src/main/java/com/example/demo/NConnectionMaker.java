package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:mem:testdb", "sa", "");

        return c;
    }
}
