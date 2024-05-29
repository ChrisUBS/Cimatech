package database;

// Librerías
import java.sql.*;

public class DataBase {

    // Atributos
    final String DB_URL = "jdbc:mysql://localhost:3306/CimaTech";
    final String USER = "root";
    final String PASS = "Lataro_2";
    private Connection connection;
    private Statement statement;
    public ResultSet resultSet;
    public ResultSetMetaData resultSetMetaData;
    
    // Constructor
    public DataBase() {
        try {
            // Conectar a la base de datos
            while (this.connection == null) {
                this.connection = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
            }
            
            // Crear objeto de la clase Statement para ejecutar consultas
            this.statement = connection.createStatement();
            
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("Error al conectar a la base de datos");
        }
    }
    
    // ** Metodos **

    public boolean obtener(String QUERY) {
        try {
            this.resultSet = this.statement.executeQuery(QUERY);
            this.resultSetMetaData = this.resultSet.getMetaData();
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean poner(String QUERY) {
        try {
            this.statement.executeUpdate(QUERY);
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public void cerrar() {
        try {
            if (this.resultSet != null) resultSet.close();
            if (this.statement != null) statement.close();
            if (this.connection != null) connection.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }
}
