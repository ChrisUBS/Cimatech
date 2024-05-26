package main;

// Librerías
import java.util.Scanner;
import database.*;
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

    public static String getRol(DataBase database, String usuario, String contrasena) throws SQLException {
        // Verificar el usuario y contraseña
        if (database.obtener("SELECT * FROM usuarios WHERE username = '" + usuario + "' AND password = '" + contrasena + "'")) {
            if (database.getRs().next() && database.getRs().getString("status").equals("1")) {
                return database.getRs().getString("rol");
            }
        }
        return "";
    }

    public static boolean registrarUsuario(DataBase database, String nombre, String apellido, String usuario, String contrasena) throws SQLException {
        // Verificar usuario
        if (database.obtener("SELECT * FROM usuarios WHERE username = '" + usuario + "'")) {
            if (!database.getRs().next()) {
                // Registrar usuario
                database.poner("INSERT INTO usuarios (rol, name, lastname, username, password) VALUES ('buyer', '" + nombre + "', '" + apellido + "', '" + usuario + "', '" + contrasena + "')");
                return true;
            }
        }
        return false;
    }

}
