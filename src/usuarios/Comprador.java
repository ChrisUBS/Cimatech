package usuarios;

// Librerías
import main.*;
import java.sql.*;
import database.*;

public class Comprador extends Usuario {

    // Constructor
    public Comprador(String username, String password) {
        super(username, password);
    }

    // ** Metodos **

    public boolean imprimirProductos(DataBase database) throws SQLException{
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {
            database.setRsmd(database.getRs().getMetaData());
            int rowsNumber = database.getRs().getRow();

            // Verificar si hay productos
            if (rowsNumber == 0) return false;
            
            boolean productoActivo = false; // Bandera para verificar si hay productos activos

            while (database.getRs().next()) {
                System.out.println("");

                if (database.getRs().getString("status").equals("1")) {
                    productoActivo = true;
                    
                    System.out.println("Nombre: " + database.getRs().getString("name"));
                    System.out.println("Tipo: " + database.getRs().getString("type"));
                    System.out.println("Cantidad: " + database.getRs().getString("amount"));
                    System.out.println("Precio: $" + database.getRs().getString("price"));
                }
            }

            if (!productoActivo) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }


    public boolean cantidadProductos(DataBase database, String id) throws SQLException{
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {
            database.setRsmd(database.getRs().getMetaData());
            int rowsNumber = database.getRs().getRow();

            // Verificar si hay productos
            if (rowsNumber == 0) return false;
            
            boolean productoActivo = false; // Bandera para verificar si hay productos activos

            while (database.getRs().next()) {
                System.out.println("");

                if (database.getRs().getString("id_producto").equals(id)) {
                    productoActivo = true;
                    
                    System.out.println("Nombre: " + database.getRs().getString("name"));
                    System.out.println("Cantidad: " + database.getRs().getString("amount"));
                }
            }

            if (!productoActivo) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }

    // ** Metodos abstractos **
    
    @Override
    public void menu(){
        String opcion,opcion2;
        
        DataBase database = new DataBase();

        Funcion.clear();
        System.out.print(
            "*** CIMATECH ***\n"+
            "Cliente: "+getUsername()+"\n"+
            "1. Ver productos\n"+
            "2. Ver carrito\n"+
            "3. Cerrar sesión\n"+
            "Opción: "
        );
        opcion = Valida.readString();

        switch (opcion) {
            case "1":
                try {
                    if (!imprimirProductos(database)) {
                        System.out.println("\nNo hay productos");
                    }
                    } catch (SQLException e) {
                        // e.printStackTrace();
                    }
                break;
            case "2":
                System.out.print(
                    "*** CIMATECH ***\n"+
                    "Carrito del cliente: "+getUsername()+"\n"+
                    "1. Agregar productos al carrito\n"+
                    "2. Eliminar productos del carrito\n"+
                    "3. Modificar cantidad de los productos del carrito\n"+
                    "4. Pagar carrito\n"+
                    "0. Regresar al menu principañ\n"+
                    "Opción: "
                );
                    opcion2 = Valida.readString();
                do {
                    switch (opcion2) {
                        case "1":
                            
                            break;
                        case "2":
                            
                            break;
                        case "3":
                            
                            break;
                        case "4":
                            
                            break;
                    
                        default:
                            break;
                    }
                } while (opcion2!="0");
                break;
            default:
                break;
        }
    }


    
}
