package main;

// Librerías
import database.*;
import usuarios.*;
import java.sql.SQLException;

/**
 *
 * @author Christian Uriel Bonilla Suárez
 * @author Jose Eduardo Becerra Flores
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        
        // Variable de estado
        boolean isRunning = true;

        // Crear objeto de la clase DB y conectar
        DataBase database = new DataBase();

        // Loop principal
        while (isRunning) {
            Funcion.clear();
            System.out.println("*** CIMATECH ***");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            String opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    // Iniciar sesion
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Iniciar sesion");
                    String usuario = Leer.username();
                    String contrasena = Leer.password();

                    // Verificar usuario
                    String rol = Funcion.getRol(database, usuario, contrasena);

                    if (!rol.equals("")) {
                        Usuario actualUsuario = null;

                        // Dar el rol correspondiente
                        if (rol.equals("admin")) actualUsuario = new Admin(usuario, contrasena);
                        if (rol.equals("buyer")) actualUsuario = new Comprador(usuario, contrasena);
                        if (rol.equals("seller")) actualUsuario = new Vendedor(usuario, contrasena);

                        // Mostrar menu del usuario
                        actualUsuario.menu();

                        // System.out.println("\nUsuario encontrado");
                        // Funcion.pause();

                    } else {
                        System.out.println("\nUsuario o contraseña incorrectos o usuario inactivo");
                        Funcion.pause();
                    }
                    break;

                case "2":
                    // Registrarse
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Registrarse");

                    // Leer datos
                    String nombre = Leer.nombre();
                    String apellido = Leer.apellido();
                    String user = Leer.username();
                    String pass = Leer.password();

                    // Insertar usuario
                    if (Funcion.registrarUsuario(database, nombre, apellido, user, pass)) {
                        System.out.println("\nUsuario registrado correctamente");
                    } else {
                        System.out.println("\nEl usuario ya existe");
                    }

                    Funcion.pause();
                    break;

                case "3":
                    // Salir
                    isRunning = false;
                    break;

                default:
                    System.out.println("\nOpcion no valida");
                    Funcion.pause();
                    break;
            }

        }

        // Cerrar la conexion
        database.close();
    }
}