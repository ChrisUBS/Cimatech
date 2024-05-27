package usuarios;

// Librerias
import java.sql.SQLException;
import java.util.ArrayList;
import database.*;
import main.*;

public class Admin extends Usuario {
    
    // Constructor
    public Admin(DataBase database, String username, String password) {
        super(database, username, password);
    }

    // ** Menu del administrador **

    @Override
    public void menu() throws SQLException {

        // Variable de estado
        boolean isRunning = true;

        // Loop principal
        while (isRunning) {
            Funcion.clear();
            System.out.print(
                "*** CIMATECH ***\n"+
                "Administrador: " + getUsername()+"\n"+
                "1. Ver usuarios\n"+
                "2. Agregar usuario\n"+
                "3. Dar de baja usuario\n"+
                "4. Modificar usuario\n"+
                "5. Cerrar sesión\n"+
                "Opción: "
            );
            String opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    // Ver usuarios
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nUsuarios");

                    if (!imprimirTabla("usuarios", 2)) {
                        System.out.println("\nNo hay usuarios registrados");
                    }

                    Funcion.pause();
                    break;

                case "2":
                    // Agregar usuario
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nAgregar usuario");

                    // Leer datos y guardar en un ArrayList
                    ArrayList <String> datos = new ArrayList<String>();
                    datos.add("rol");
                    datos.add(Leer.rol("Rol: "));
                    datos.add("nombre");
                    datos.add(Leer.nombre("Nombre: "));
                    datos.add("apellido");
                    datos.add(Leer.apellido("Apellido: "));
                    datos.add(0, "username");
                    datos.add(1, Leer.username("Usuario: "));
                    datos.add("password");
                    datos.add(Leer.password("Contraseña: "));

                    // Insertar usuario
                    if (registro("usuarios", datos)) {
                        System.out.println("\nUsuario registrado correctamente");
                    } else {
                        System.out.println("\nEl usuario ya existe");
                    }

                    Funcion.pause();
                    break;

                case "3":
                    // Dar de baja usuario
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nDar de baja usuario");
                    String username = Leer.username("Username: ");

                    // Dar de baja usuario
                    if (baja("usuarios", "username", username)) {
                        System.out.println("\nUsuario dado de baja correctamente");
                    } else {
                        System.out.println("\nUsuario no encontrado o ya dado de baja");
                    }

                    Funcion.pause();
                    break;

                case "4":
                    // Modificar usuario
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nModificar usuario");
                    String usernameAModificar = Leer.username("Username: ");

                    // Modificar usuario
                    if (modificar("usuarios", "Administrador", "username", usernameAModificar)) {
                        System.out.println("\nUsuario modificado correctamente");
                    } else {
                        System.out.println("\nUsuario no encontrado");
                    }

                    Funcion.pause();
                    break;

                case "5":
                    // Regresar
                    isRunning = false;
                    break;

                default:
                    System.out.println("\nOpcion no valida");
                    Funcion.pause();
                    break;
            }
        }
    }
    
}