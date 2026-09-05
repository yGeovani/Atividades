package com.stockmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_manager";

    private static final String USUARIO = "root";

    private static final String SENHA = "geo7754]~ç";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
