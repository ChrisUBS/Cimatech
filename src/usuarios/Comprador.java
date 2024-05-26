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

            boolean productoActivo = false; // Bandera para verificar si hay productos activos
            int numeroIncremento = 1;
            while (database.getRs().next()) {
                System.out.println("");

                if (database.getRs().getString("status").equals("1")) {
                    productoActivo = true;
                    
                    System.out.println("Producto no."+numeroIncremento);
                    System.out.println("Nombre: " + database.getRs().getString("name"));
                    System.out.println("Tipo: " + database.getRs().getString("type"));
                    System.out.println("Cantidad: " + database.getRs().getString("amount"));
                    System.out.println("Precio: $" + database.getRs().getString("price"));
                }
                numeroIncremento++;
            }

            if (!productoActivo) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }


    public int cantidadProductos(DataBase database, String id) throws SQLException{
        
        int cantidad=-10;
        
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {
            database.setRsmd(database.getRs().getMetaData());

            boolean productoActivo = false; // Bandera para verificar si hay productos activos

            while (database.getRs().next()) {
                if (database.getRs().getString("id_producto").equals(id)) {
                    productoActivo = true;
                    
                    cantidad = Integer.parseInt(database.getRs().getString("amount"));
                }
            }   
        }
        return cantidad;
    }

    public boolean modificarCantidad(DataBase database,String id, int cantidad) throws SQLException{
        int cantidad2=cantidadProductos(database, id)-cantidad;
        if (database.obtener("SELECT * FROM productos WHERE id_producto = '"  + id + "'")) {
            database.getRs().next();
            if (database.getRs().getString("amount").equals("0")) {
                return false;
            }
            database.poner("UPDATE productos SET amount = '" + cantidad2 + "' WHERE id_producto = '"+ id +"' ");
            return true;
            
        }
        else return false;
    }

    // ** Metodos abstractos **
    
    @Override
    public void menu(){
        String opcion,opcion2;
        // Variable de estado
        boolean isRunning = true;
        int maximo=0,cantidad=0;
        String id="";
        DataBase database = new DataBase();

        while (isRunning) {
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
                        e.printStackTrace();
                    }
                Funcion.pause();
                break;
            case "2":
                do {
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
                    switch (opcion2) {
                        case "0":
                            break;
                        case "1":
                        System.out.print("ID del producto:");  id = Valida.readString();
                        try {
                            maximo = cantidadProductos(database, id);
                            System.out.println(maximo);
                        } catch (SQLException e) {
                            // e.printStackTrace();
                        }

                        cantidad = Leer.cantidadProducto(maximo);
                        
                        break;
                        case "2":
                            
                            break;
                        case "3":
                            
                            break;
                        case "4":
                        try {
                            if (modificarCantidad(database, id, cantidad)) {
                                System.out.println("Carrito pagado con exito.");
                            }
                            else{
                                System.out.println("Producto no encontrado o sin stock.");
                            }
                            Funcion.pause();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    
                        default:
                            break;
                    }
                } while (!opcion2.equals("0"));

                break;
                case "3":
                    isRunning=false;
                    break;
            default:
                break;
        }
        }
        
    }


    
}
