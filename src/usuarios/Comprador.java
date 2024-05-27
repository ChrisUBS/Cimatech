package usuarios;

// Librerías
import main.*;
import java.sql.*;
import database.*;
import java.util.ArrayList;

public class Comprador extends Usuario {

    // Constructor
    public Comprador(DataBase database, String username, String password) {
        super(database, username, password);
    }

    // ** Metodos **

    public boolean imprimirProductos(DataBase database, ArrayList<ArrayList<String>> tmpProductos) throws SQLException {
        String nombre,tipo,cantidad,precio;
        
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {

            boolean productoActivo = false; // Bandera para verificar si hay productos activos
            
            int numeroIncremento = 1;
            while (database.resultSet.next()) {
                System.out.println("");

                if (database.resultSet.getString("status").equals("1")) {
                    ArrayList<String> tmp = new ArrayList<String>();
                    productoActivo = true;
                    nombre = database.resultSet.getString("name");
                    tipo = database.resultSet.getString("type");
                    cantidad = database.resultSet.getString("amount");
                    precio = database.resultSet.getString("price");

                    System.out.println("\nProducto #" + numeroIncremento);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Tipo: " + tipo);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Precio: $" + precio);

                    tmp.add(String.valueOf(numeroIncremento));
                    tmp.add(nombre);
                    tmp.add(tipo);
                    tmp.add(cantidad);
                    tmp.add(precio);

                    tmpProductos.add(tmp);
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

    public boolean agragarCarrito(ArrayList<ArrayList<String>> productos , ArrayList<String> id, ArrayList<Integer> cantidad){
        
        return false;
    }


    public int cantidadProductos(DataBase database, String id) throws SQLException{
        
        int cantidad=-10;
        
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {

            // boolean productoActivo = false; // Bandera para verificar si hay productos activos

            while (database.resultSet.next()) {
                if (database.resultSet.getString("id_producto").equals(id)) {
                    // productoActivo = true;
                    
                    cantidad = Integer.parseInt(database.resultSet.getString("amount"));
                }
            }   
        }
        return cantidad;
    }

    public boolean modificarCantidad(DataBase database,String id, int cantidad) throws SQLException{
        int cantidad2 = cantidadProductos(database, id)-cantidad;
        if (database.obtener("SELECT * FROM productos WHERE id_producto = '"  + id + "'")) {
            database.resultSet.next();
            if (database.resultSet.getString("amount").equals("0")) {
                return false;
            }
            database.poner("UPDATE productos SET amount = '" + cantidad2 + "' WHERE id_producto = '"+ id +"' ");
            return true;
            
        }
        else return false;
    }

    // ** Menu del comprador **
    
    @Override
    public void menu(){
        String opcion, opcion2;

        // Variable de estado
        boolean isRunning = true;

        int maximo=0,cantidad=0;
        String id="";
        ArrayList<ArrayList<String>> productos = new ArrayList<ArrayList<String>>();
        ArrayList<String> productos_ID = new ArrayList<String>();
        ArrayList<Integer> cantidad_productos = new ArrayList<Integer>();
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
                        if (!imprimirProductos(database,productos)) {
                            System.out.println("\nNo hay productos");
                        }
                        else{

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
                        "0. Regresar al menu principal\n"+
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

                            // cantidad = Leer.cantidadProducto(maximo);
                            
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
