package usuarios;

// Librerias
import java.sql.SQLException;
import database.*;
import main.*;

public class Admin extends Usuario{
    
    // Constructor
    public Admin(String username, String password) {
        super(username, password);
    }

    // ** Metodos **

    public boolean imprimirUsuarios(DataBase database) throws SQLException {
        // Consultar tabla
        if (database.obtener("SELECT * FROM usuarios")) {
            database.setRsmd(database.getRs().getMetaData());
            int columnsNumber = database.getRsmd().getColumnCount();

            boolean hayUsuarios = false; // Bandera para saber si hay usuarios

            while (database.getRs().next()) {
                hayUsuarios = true;
                System.out.println("");

                if (database.getRs().getString("status").equals("1")) {
                    System.out.println("Status: Activo");
                }
                else {
                    System.out.println("Status: Inactivo");
                }

                for (int i = 3; i <= columnsNumber; i++) {
                    String columnValue = database.getRs().getString(i);
                    System.out.println(database.getRsmd().getColumnName(i) + ": " + columnValue);
                }
            }

            if (!hayUsuarios) return false;

            return true;

        } else {
            return false;
        }
    }

    public boolean registrarUsuario(DataBase database, String rol, String nombre, String apellido, String usuario, String contrasena) throws SQLException {
        // Verificar usuario
        if (database.obtener("SELECT * FROM usuarios WHERE username = '" + usuario + "'")) {
            if (!database.getRs().next()) {
                // Registrar usuario
                database.poner("INSERT INTO usuarios (rol, name, lastname, username, password) VALUES ('" + rol + "', '" + nombre + "', '" + apellido + "', '" + usuario + "', '" + contrasena + "')");
                return true;
            }
        }
        return false;
    }

    public boolean bajaUsuario(DataBase database, String username) {
        try {
            // Verificar usuario
            if (database.obtener("SELECT * FROM usuarios WHERE username = '" + username + "'")) {
                database.getRs().next();
                if (database.getRs().getString("status").equals("1")) {
                    // Dar de baja
                    database.poner("UPDATE usuarios SET status = 0 WHERE username = '" + username + "'");
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean modificarUsuario (DataBase database, String username) {
        try {
            // Verificar usuario
            if (database.obtener("SELECT * FROM usuarios WHERE username = '" + username + "'")) {
                database.getRs().next();

                // Modificar
                Funcion.clear();
                System.out.println("*** CIMATECH ***");
                System.out.println("Administrador: " + getUsername()); 
                System.out.println("\nModificar usuario");
                System.out.println("Usuario: " + username);

                System.out.println("Status: " + database.getRs().getString("status"));
                System.out.print("Nuevo status: ");
                String status = Valida.readString();

                System.out.println("Rol: " + database.getRs().getString("rol"));
                System.out.print("Nuevo rol: ");
                String rol = Valida.readString();

                System.out.println("Nombre: " + database.getRs().getString("name"));
                System.out.print("Nuevo nombre: ");
                String nombre = Valida.readString();

                System.out.println("Apellido: " + database.getRs().getString("lastname"));
                System.out.print("Nuevo apellido: ");
                String apellido = Valida.readString();
                
                System.out.println("Contraseña: " + database.getRs().getString("password"));
                System.out.print("Nueva contraseña: ");
                String pass = Valida.readString();

                database.poner("UPDATE usuarios SET status = '" + status + "', rol = '" + rol + "', name = '" + nombre + "', lastname = '" + apellido + "', password = '" + pass + "' WHERE username = '" + username + "'");
                return true;
            }

            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    // ** Metodos abstractos **

    @Override
    public void menu() {

        // Variable de estado
        boolean isRunning = true;

        // Crear objeto de la clase DB y conectar
        DataBase database = new DataBase();;

        // Loop principal
        while (isRunning) {
            Funcion.clear();
            System.out.println("*** CIMATECH ***");
            System.out.println("Administrador: " + getUsername());
            System.out.println("1. Ver usuarios");
            System.out.println("2. Agregar usuario");
            System.out.println("3. Dar de baja usuario");
            System.out.println("4. Modificar usuario");
            System.out.println("5. Cerrar sesion");
            System.out.print("Opcion: ");
            String opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    // Ver usuarios
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nUsuarios");

                    try {
                        if (!imprimirUsuarios(database)) {
                            System.out.println("\nNo hay usuarios registrados");
                        }
                    } catch (SQLException e) {
                        // System.out.println("\nError: " + e.getMessage());
                    }

                    Funcion.pause();
                    break;

                case "2":
                    // Agregar usuario
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nAgregar usuario");
                    System.out.print("Rol: ");
                    String rol = Valida.readString();
                    System.out.print("Nombre: ");
                    String nombre = Valida.readString();
                    System.out.print("Apellido: ");
                    String apellido = Valida.readString();
                    System.out.print("Usuario: ");
                    String user = Valida.readString();
                    System.out.print("Contraseña: ");
                    String pass = Valida.readString();

                    // Insertar usuario
                    try {
                        if (registrarUsuario(database, rol, nombre, apellido, user, pass)) {
                            System.out.println("\nUsuario registrado correctamente");
                        } else {
                            System.out.println("\nEl usuario ya existe");
                        }
                    } catch (SQLException e) {
                        // System.out.println("\nError: " + e.getMessage());
                    }

                    Funcion.pause();
                    break;

                case "3":
                    // Dar de baja usuario
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Administrador: " + getUsername());
                    System.out.println("\nDar de baja usuario");
                    System.out.print("Username: ");
                    String username = Valida.readString();

                    // Dar de baja usuario
                    if (bajaUsuario(database, username)) {
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
                    System.out.print("Username: ");
                    String usernameAModificar = Valida.readString();

                    // Modificar usuario
                    if (modificarUsuario(database, usernameAModificar)) {
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
        // Cerrar conexion
        database.close();
    }
}