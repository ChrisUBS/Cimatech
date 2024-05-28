package usuarios;

// Librerías
import main.*;
import database.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Vendedor extends Usuario {

    // Constructor
    public Vendedor(DataBase database, String username, String password) {
        super(database, username, password);
    }
    
    // ** Menu del vendedor **

    @Override
    public void menu() throws SQLException {

        // Variable de estado
        boolean isRunning = true;

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
                "5. Cerrar sesión\n"+
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

                    if (!imprimirTabla("productos", 2)) {
                        System.out.println("\nNo hay productos");
                    }

                    Funcion.pause();
                    break;

                case "2":
                    // Dar de alta producto
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nAgregar producto");

                    // Leer datos y guardar en lista
                    ArrayList<String> datos = new ArrayList<String>();
                    datos.add("producto");
                    datos.add(Leer.nombreProducto("Nombre: "));
                    datos.add("estatus");
                    datos.add(Leer.status("Estatus: "));
                    datos.add("tipo");
                    datos.add(Leer.tipo("Tipo: "));
                    datos.add("cantidad");
                    datos.add(Leer.cantidad("Cantidad: "));
                    datos.add("precio");
                    datos.add(Leer.precio("Precio: $"));

                    // Insertar producto
                    if (registro("productos", datos)) {
                        System.out.println("\nProducto registrado correctamente");
                    } else {
                        System.out.println("\nEl producto ya existe");
                    }
                    
                    Funcion.pause();
                    break;

                case "3":
                    // Dar de baja producto
                    Funcion.clear();
                    System.out.println("*** CIMATECH ***");
                    System.out.println("Vendedor: " + getUsername());
                    System.out.println("\nDar de baja producto");
                    String nombreBaja = Leer.nombreProducto("Nombre: ");

                    // Dar de baja producto
                    if (baja("productos", "producto", nombreBaja)) {
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
                    String produtoAModificar = Leer.nombreProducto("Nombre: ");

                    // Modificar producto
                    if (modificar("productos", "Vendedor", "producto", produtoAModificar)) {
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
                    System.out.println("\nOpción no válida");
                    Funcion.pause();
                    break;
            }
        }
    }
}
