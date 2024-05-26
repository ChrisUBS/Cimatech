package database;

// Librerías
import java.sql.*;

public class DataBase {

    // Atributos
    // final String DB_URL = "jdbc:mysql://b9jdlktjjs9eghvwfghv-mysql.services.clever-cloud.com:3306/b9jdlktjjs9eghvwfghv";
    final String DB_URL = "jdbc:mysql://localhost:3306/cimatech";
    final String USER = "root";
    final String PASS = "Lataro_2";
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    
    // Constructor
    public DataBase() {

        // Conectar a la base de datos
        try {
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            this.stmt = conn.createStatement();
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Error al conectar a la base de datos");
        }
    }

    // *** Getters y Setters ***

    public ResultSet getRs() {
        return rs;
    }

    public ResultSetMetaData getRsmd() {
        return rsmd;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void setRsmd(ResultSetMetaData rsmd) {
        this.rsmd = rsmd;
    }
    
    // *** Metodos ***

    public boolean obtener(String QUERY) {
        try {
            this.rs = this.stmt.executeQuery(QUERY);
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean poner(String QUERY) {
        try {
            this.stmt.executeUpdate(QUERY);
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (this.rs != null) rs.close();
            if (this.stmt != null) stmt.close();
            if (this.conn != null) conn.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }
}
