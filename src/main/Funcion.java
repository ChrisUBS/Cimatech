package main;

// Librerías
import java.util.Scanner;
import java.sql.SQLException;

public class Funcion {

    // ** Metodos para la consola **

    // Limpiar la consola
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Pausar el programa
    static Scanner pause = new Scanner(System.in);
    public static void pause() {
        System.out.print("\nPresiona Enter para continuar");
        pause.nextLine();
    }

    // ** Metodos para el login **

    public static String getRol(String usuario, String contrasena) throws SQLException {
        // Verificar el usuario y contraseña
        Main.database.obtener("SELECT * FROM " + Main.tabla + " WHERE username = '" + usuario + "' AND password = '" + contrasena + "'");

        // Si el usuario existe y está activo
        if (Main.database.resultSet.next() && Main.database.resultSet.getString("estatus").equals("activo")) {
            return Main.database.resultSet.getString("rol");
        }

        // Si no se encuentra el usuario o está inactivo
        return "";
    }

    public static boolean registrarUsuario(String nombre, String apellido, String usuario, String contrasena) throws SQLException {
        // Verificar usuario
        Main.database.obtener("SELECT * FROM "+ Main.tabla + " WHERE username = '" + usuario + "'");

        // Si no existe el usuario
        if (!Main.database.resultSet.next()) {
            // Registrar usuario
            Main.database.poner("INSERT INTO " + Main.tabla + " (nombre, apellido, username, password) VALUES ('" + nombre + "', '" + apellido + "', '" + usuario + "', '" + contrasena + "')");
            return true;
        }

        // Si ya existe el usuario
        return false;
    }

}
