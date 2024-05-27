package usuarios;

import java.sql.SQLException;
import java.util.ArrayList;

// Librerías
import database.*;
import main.Funcion;
import main.Leer;
import main.Valida;

public abstract class Usuario {
    
    // Atributos
    public DataBase database;
    private String username;
    private String password;

    // Constructor
    public Usuario(DataBase database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    // ** Getters **

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ** Metodos **

    public boolean imprimirTabla(String nombreTabla, int columnaInicio) throws SQLException {
        // Consultar tabla
        database.obtener("SELECT * FROM " + nombreTabla);

        boolean elementoActivo = false; // Bandera para verificar si hay elementos activos

        // Imprimir elementos
        while (database.resultSet.next()) {
            elementoActivo = true;
            System.out.println("");

            for (int i = columnaInicio; i <= database.resultSetMetaData.getColumnCount(); i++) {
                String columnValue = database.resultSet.getString(i);

                if (database.resultSetMetaData.getColumnTypeName(i).equals("FLOAT")) {
                    columnValue = "$" + columnValue;
                } 

                System.out.println(database.resultSetMetaData.getColumnName(i) + ": " + columnValue);
            }
        }

        if (!elementoActivo) return false;

        return true;
    }

    public boolean registro(String tabla, ArrayList<String> datos) throws SQLException {
        // Verificar elemento
        database.obtener("SELECT * FROM " + tabla + " WHERE " + datos.get(0) + " = '" + datos.get(1) + "'");

        // Si no existe el elemento
        if (!database.resultSet.next()) {

            // Crear query
            String query = "INSERT INTO " + tabla + " (";
            for (int i = 0; i < datos.size(); i += 2) {
                query += datos.get(i);
                if (i < (datos.size() - 2)) query += ", ";
            }
            query += ") VALUES (";
            for (int i = 1; i < datos.size(); i += 2) {
                query += "'" + datos.get(i) + "'";
                if (i < (datos.size() - 1)) query += ", ";
            }
            query += ")";

            // Registrar elemento
            database.poner(query);

            return true;
        }

        // Si ya existe el elemento
        return false;
    }


    public boolean baja(String tabla, String tipo, String elemento) throws SQLException {
        // Verificar elemento
        database.obtener("SELECT * FROM " + tabla + " WHERE " + tipo + " = '" + elemento + "'");

        // Si existe y está activo
        if (database.resultSet.next() && database.resultSet.getString("estatus").equals("activo")) {
            database.poner("UPDATE " + tabla + " SET estatus = 'inactivo' WHERE " + tipo + " = '" + elemento + "'");
            return true;
        }

        // Si no existe o ya está dado de baja
        return false;
    }

    public boolean modificar(String tabla, String tipoUsuario, String nombreColumna, String elemento) throws SQLException {
        // Verificar producto
        database.obtener("SELECT * FROM " + tabla + " WHERE " + nombreColumna + " = '" + elemento + "'");

        // Si el producto no existe
        if(!database.resultSet.next()) return false;

        Funcion.clear();
        System.out.println("*** CIMATECH ***");
        System.out.println(tipoUsuario + ": " + getUsername());
        System.out.println("Modificar: " + elemento);

        // ArrayList para guardar los datos
        ArrayList<String> datos = new ArrayList<String>();

        // Obtener datos
        for (int i = 2; i <= database.resultSetMetaData.getColumnCount(); i++) {
            String columnName = database.resultSetMetaData.getColumnName(i);
            String columnValue = database.resultSet.getString(i);

            // Ignorar columna de username
            if (columnName.equals("username"))  continue;

            // Imprimir columna
            if (database.resultSetMetaData.getColumnTypeName(i).equals("FLOAT")) {
                System.out.println("\n" + columnName + ": $" + columnValue);
            } else {
                System.out.println("\n" + columnName + ": " + columnValue);
            }

            // Leer nuevo valor
            System.out.print("Ingresa '0' para modificar: ");
            String nuevoValor = Valida.readString();
            if (!nuevoValor.equals("0")) nuevoValor = columnValue;
            else if (columnName.equals("estatus"))  nuevoValor = Leer.status("Nuevo valor: ");
            else if (columnName.equals("rol")) nuevoValor = Leer.rol("Nuevo valor: ");
            else if (columnName.equals("nombre")) nuevoValor = Leer.nombre("Nuevo valor: ");
            else if (columnName.equals("producto")) nuevoValor = Leer.nombreProducto("Nuevo valor: ");
            else if (columnName.equals("apellido")) nuevoValor = Leer.apellido("Nuevo valor: ");
            else if (columnName.equals("password")) nuevoValor = Leer.password("Nuevo valor: ");
            else if (columnName.equals("tipo")) nuevoValor = Leer.tipo("Nuevo valor: ");
            else if (columnName.equals("cantidad")) nuevoValor = Leer.cantidad("Nuevo valor: ");
            else if (columnName.equals("precio")) nuevoValor = Leer.precio("Nuevo valor: $");

            // Guardar datos
            datos.add(columnName);
            datos.add(nuevoValor);
        }

        // Crear query
        String query = "UPDATE " + tabla + " SET ";
        for (int i = 0; i < datos.size(); i += 2) {
            query += datos.get(i) + " = '" + datos.get(i + 1) + "'";
            if (i + 2 < datos.size()) query += ", ";
        }
        query += " WHERE " + nombreColumna + " = '" + elemento + "'";

        // Modificar producto
        database.poner(query);
        
        return true;
    }

    // ** Metodos abstractos **

    public abstract void menu() throws Exception;

}
