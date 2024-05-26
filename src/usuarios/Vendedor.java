package usuarios;

// Librerías
import main.*;
import java.sql.SQLException;
import database.*;

public class Vendedor extends Usuario{

    // Constructor
    public Vendedor(String username, String password) {
        super(username,password);
    }

    // ** Metodos **

    public boolean imprimirProductos(DataBase database) throws SQLException {
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {
            database.setRsmd(database.getRs().getMetaData());
            
            boolean productoActivo = false; // Bandera para verificar si hay productos activos

            while (database.getRs().next()) {
                productoActivo = true;
                System.out.println("");

                if (database.getRs().getString("status").equals("1")) {
                    System.out.println("Status: Activo");
                }
                else {
                    System.out.println("Status: Inactivo");
                }

                System.out.println("Nombre: " + database.getRs().getString("name"));
                System.out.println("Tipo: " + database.getRs().getString("type"));
                System.out.println("Cantidad: " + database.getRs().getString("amount"));
                System.out.println("Precio: $" + database.getRs().getString("price"));
            }

            if (!productoActivo) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }

    public boolean registrarProducto(DataBase database, String tipo, String nombre, int cantidad, float precio) throws SQLException {
        // Verificar producto
        if (database.obtener("SELECT * FROM productos WHERE name = '" + nombre + "'")) {
            if (!database.getRs().next()) {
                // Registrar producto
                database.poner("INSERT INTO productos (type, name, amount, price) VALUES ('" + tipo +"', '" + nombre + "', '" + cantidad + "', '" + precio + "')");

                return true;
            }
        }
        return false;
    }

    public boolean bajaProducto(DataBase database, String nombre) {
        try {
            // Verificar producto
            if (database.obtener("SELECT * FROM productos WHERE name = '" + nombre + "'")) {
                database.getRs().next();
                if (database.getRs().getString("status").equals("1")) {
                    database.poner("UPDATE productos SET status = 0 WHERE name = '" + nombre + "'");
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean modificarProducto (DataBase database, String nombre) {
        try {
            // Verificar usuario
            if (database.obtener("SELECT * FROM productos WHERE name = '" + nombre + "'")) {
                database.getRs().next();

                // Modificar
                Funcion.clear();
                System.out.println("*** CIMATECH ***");
                System.out.println("Vendedor: " + getUsername());
                System.out.println("\nModificar producto");

                System.out.println("Nombre: " + nombre);
                String nuevoNombre = Leer.nuevoNombre();

                System.out.println("Status: " + database.getRs().getString("status"));
                System.out.print("Nuevo status: ");
                String status = Valida.readString();

                System.out.println("Tipo: " + database.getRs().getString("type"));
                String tipo = Leer.nuevoTipo();

                System.out.println("Cantidad: " + database.getRs().getString("amount"));
                int cantidad = Leer.nuevaCantidad();

                System.out.println("Precio: $" + database.getRs().getString("price"));
                float precio = Leer.nuevoPrecio();


                database.poner("UPDATE productos SET status = '" + status + "', type = '" + tipo + "', name = '" + nuevoNombre + "', amount = '" + cantidad + "', price = '" + precio + "' WHERE name = '" + nombre + "'");
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

        // Crear objeto de la clase DB
        DataBase database = new DataBase();

        // Loop principal
        while (isRunning) {
            Funcion.clear();
            System.out.print(
                "*** CIMATECH ***\n"+
                "Vendedor: " + getUsername()+"\n"+
                "1. Ver productos\n"+
                "2. Agregar producto nuevo\n"+
                "3. Dar de baja producto\n"+
                "4. Modificar producto\n"+
                "5. Cerrar seccion\n"+
                "Opción: "
            );
            String opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    // Ver productos
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nProductos");
                    try {
                        if (!imprimirProductos(database)) {
                            System.out.println("\nNo hay productos");
                        }
                    } catch (SQLException e) {
                        // e.printStackTrace();
                    }

                    Funcion.pause();
                    break;

                case "2":
                    // Dar de alta producto
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nAgregar producto");

                    // Leer datos
                    System.out.print("Nombre: ");
                    String nombre = Valida.readString();
                    String tipo = Leer.tipo();
                    int cantidad = Leer.cantidad();
                    float precio = Leer.precio();

                    // Insertar producto
                    try {
                        if (registrarProducto(database, tipo, nombre, cantidad, precio)) {
                            System.out.println("\nProducto registrado correctamente");
                        } else {
                            System.out.println("\nEl producto ya existe");
                        }
                    } catch (SQLException e) {
                        // e.printStackTrace();
                    }
                    
                    Funcion.pause();
                    break;

                case "3":
                    // Dar de baja producto
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nDar de baja producto");
                    System.out.print("Nombre: ");
                    String nombreBaja = Valida.readString();

                    // Dar de baja producto
                    if (bajaProducto(database, nombreBaja)) {
                        System.out.println("\nProducto dado de baja correctamente");
                    } else {
                        System.out.println("\nProducto no encontrado o ya dado de baja");
                    }

                    Funcion.pause();
                    break;

                case "4":
                    // Modificar producto
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nModificar producto");
                    System.out.print("Nombre: ");
                    String produtoAModificar = Valida.readString();

                    // Modificar producto
                    if (modificarProducto(database, produtoAModificar)) {
                        System.out.println("\nProducto modificado correctamente");
                    } else {
                        System.out.println("\nProducto no encontrado");
                    }

                    Funcion.pause();
                    break;

                case "5":
                    // Cerrar seccion
                    isRunning = false;
                    break;

                default:
                    System.out.println("Opción no válida");
                    Funcion.pause();
                    break;
            }
        }

        // Cerrar la conexion
        database.close();
    }
}
