package cadastro.model.util;

import java.sql.*;

public class ConectorBD {

    public Connection con;
    public PreparedStatement stmt;
    public ResultSet rs;

    public Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "mtzhsg7b";
        con = DriverManager.getConnection(url, user, password);
        return con;
    }

    public ResultSet getSelect(String sql) throws SQLException {
        stmt = getConnection().prepareStatement(sql);
        rs = stmt.executeQuery();
        return rs;
    }

    public int insert(String sql) throws SQLException {
        stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.executeUpdate();
        rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Falha ao inserir dados.");
        }
    }

    public boolean update(String sql) throws SQLException {
        stmt = getConnection().prepareStatement(sql);
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
    }

    public void close() throws SQLException {
        if (rs != null && !rs.isClosed()) rs.close();
        if (stmt != null && !stmt.isClosed()) stmt.close();
        if (con != null && !con.isClosed()) con.close();
    }
}