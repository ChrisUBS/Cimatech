package main;

// Librerías
import database.*;
import usuarios.*;

/**
 *
 * @author Christian Uriel Bonilla Suárez
 * @author Jose Eduardo Becerra Flores
 */
public class Main {

    // Variables para la base de datos
    static public DataBase database = null;
    static public String tabla = "usuarios";

    public static void main(String[] args) throws Exception {
        
        // Variable de estado
        boolean isRunning = true;

        // Conectar a la base de datos
        database = new DataBase();

        // Loop principal
        while (isRunning) {
            Funcion.clear();
            System.out.print(
                "*** CIMATECH ***\n"+
                "1. Iniciar sesión\n"+
                "2. Registrarse\n"+
                "3. Salir\n"+
                "Opción: "
            );
            String opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    // Iniciar sesion
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Iniciar sesión\n");
                    String usuario = Leer.username("Usuario: ");
                    String contrasena = Leer.password("Contraseña: ");

                    // Verificar usuario
                    String rol = Funcion.getRol(usuario, contrasena);

                    if (!rol.equals("")) {
                        Usuario actualUsuario = null;

                        // Dar el rol correspondiente
                        if (rol.equals("admin")) actualUsuario = new Admin(database, usuario, contrasena);
                        if (rol.equals("buyer")) actualUsuario = new Comprador(database, usuario, contrasena);
                        if (rol.equals("seller")) actualUsuario = new Vendedor(database, usuario, contrasena);

                        // Mostrar menu del usuario
                        actualUsuario.menu();

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
                    String nombre = Leer.nombre("Nombre: ");
                    String apellido = Leer.apellido("Apellido: ");
                    String user = Leer.username("Usuario: ");
                    String pass = Leer.password("Contraseña: ");

                    // Insertar usuario
                    if (Funcion.registrarUsuario(nombre, apellido, user, pass)) {
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
        database.cerrar();
    }
}