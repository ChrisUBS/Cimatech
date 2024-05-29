package usuarios;

// Librerías
import main.*;
import java.sql.*;
import database.*;
import java.util.ArrayList;

public class Comprador extends Usuario {

    // Constructor
    public Comprador(DataBase database,String username, String password) {
        super(database,username, password);
    }

    // ** Metodos **

    //*Metodo que se asegura que la cantidad que se quiere agregar no supera el limite establecido
    public boolean cantidadCorrecta(ArrayList<ArrayList<String>> productos, int cantidad,String nombre){
        boolean formatoCorrecto = false; //Booleano que indicara si la cantidad a agregar es valida o no
        
        for (int i = 0; i < productos.size(); i++) {
            //En caso de que se encuentre el producto que se quiere agregar
            //Y este no supere el limite del almacen, significa que la cantidad es valida
            if (productos.get(i).get(1).equals(nombre)) {
                if ((cantidad>=0)&&(cantidad<=Integer.parseInt(productos.get(i).get(3)))) {
                    formatoCorrecto=true;
                }
            }
        }

        return formatoCorrecto;
    }

    //*Metodo que busca en la lista del carrito de productos para confirmar la existencia de algun producto
    public int existenciaCarrito(ArrayList<String> productoCarrito, String nombre)
    {
        int indice=-2; //Entero que almacenara el indice en donde se encuentra el producto en caso de que exista

        for (int i = 0; i < productoCarrito.size(); i++) {
            //Si el producto se encuentra, el indice tomara el valor del indice donde se encontro el producto y se rompera el ciclo
            if (productoCarrito.get(i).equals(nombre)) {
                indice=i;
                break;
            }
        }

        //Se devuelve el indice en donde se encontro el producto, en caso de no encontrarlo se devuelve un -2
        return indice;
    }

    //*Metodo que verifica que el carrito de productos no se encuentre vacio
    public boolean carritoVacio(ArrayList<String> productoCarrito){
        if(productoCarrito.size()==0){
            System.out.println("\nCarrito electronico vacio.");
            return true;
        }
        else return false;
    }

    //*Metodo que agrega todos los productos activos de la base de datos a un arraylist para mejor manejo de datos
    public void extraerProductos(DataBase database, ArrayList<ArrayList<String>> tmpProductos) throws SQLException{
        String nombre, tipo, cantidad, precio;
    
        // Consultar tabla
        if (database.obtener("SELECT * FROM productos")) {
    
            int numeroIncremento = 1; // Variable usada para poner índices en los productos
    
            // Mientras haya productos en la base de datos
            while (database.resultSet.next()) {
                // Solo se imprimirán los productos que estén activos    
                if (database.resultSet.getString("estatus").equals("activo")) {
                    nombre = database.resultSet.getString("producto");
                    tipo = database.resultSet.getString("tipo");
                    cantidad = database.resultSet.getString("cantidad");
                    precio = database.resultSet.getString("precio");

                    ArrayList<String> tmp = new ArrayList<>(); // Arraylist para guardar toda la información del producto actual
                    tmp.add(String.valueOf(numeroIncremento));
                    tmp.add(nombre);
                    tmp.add(tipo);
                    tmp.add(cantidad);
                    tmp.add(precio);
    
                    // Agregamos toda la información sobre el producto a la lista de productos
                    tmpProductos.add(tmp);
                    
                    numeroIncremento++;
                }
            }
        }
    }

    //*Metodo que muestra los productos que se hayan agregado al carrito de compras
    public boolean mostrarCarrito(ArrayList<ArrayList<String>> productos , ArrayList<String> nombres, ArrayList<Integer> cantidad){

        float total = 0; //Variable que almacenara el total de la compra

        if (!carritoVacio(nombres)) {
            int numeroIncremento=1;
            //Ciclo parar recorrer los productos del carrito
            for (int i = 0; i < nombres.size(); i++) {
                //Ciclo para recorrer toda la lista de productos
                for (int j = 0; j < productos.size(); j++) {

                    //En caso de que se encuentre el producto del carrito en la lista de productos, se imprime toda la info
                    if (nombres.get(i).equals(productos.get(j).get(1))) {
                        System.out.println("\nProducto seleccionado #" + numeroIncremento);
                        System.out.println("Nombre: " + productos.get(j).get(1));
                        System.out.println("Tipo: " + productos.get(j).get(2));
                        System.out.println("Cantidad: " + cantidad.get(i));
                        System.out.println("Precio: $" + productos.get(j).get(4));
                        System.out.println("Subtotal: $" + (Float.parseFloat(productos.get(j).get(4))*cantidad.get(i)));
                        total += Float.parseFloat(productos.get(j).get(4))*cantidad.get(i);
        
                        numeroIncremento++;
                    }

                }
            } 

            if (numeroIncremento!=1) {
                System.out.println("\nTotal a pagar: $" + total);
                System.out.println("");

                return true;
            }

            return true;  
        }
        //Solo se regresa un false en caso de que el carrito este vacio
        return false;
    }

    //*Metodo para agregar un producto al carrito
    public boolean asignarProducto(ArrayList<ArrayList<String>> productos, ArrayList<String> nombre, ArrayList<Integer> cantidad_productos, String nombreProducto, int cantidad){
        //Se recorren todos los productos
        for (int i = 0; i < productos.size(); i++) {
            //En caso de encontrar el producto que se quiere agregar y que la cantidad que se pide no supere el limite, se agrega el producto al carrito
            if (nombreProducto.equals(productos.get(i).get(1)) && cantidadCorrecta(productos, cantidad,nombreProducto)) {
                ArrayList<String> tmp = new ArrayList<String>();
                tmp.add(productos.get(i).get(1));
                nombre.addAll(tmp);
                cantidad_productos.add(cantidad);
                return true;
            }
        }
        //Solo se retorna un false en caso de que el producto no se encontrara
        return false;
    }

    //*Metodo para modificar la cantidad de un producto que se quiere comprar
    public void modificarCantidad(ArrayList<ArrayList<String>> productos , ArrayList<String> nombres, ArrayList<Integer> cantidad,String nombre, int nuevaCantidad){
        if (!carritoVacio(nombres)) {
            int indice = existenciaCarrito(nombres, nombre);
            //Si el indice no es -2 significa que el producto se encontro y si la cantidad es aceptable, se cambia la cantidad del producto
            if (indice!=2 && cantidadCorrecta(productos, nuevaCantidad,nombre)) {
                cantidad.set(indice, nuevaCantidad);
            }
            else{
                System.out.println("\nProducto no encotrado o cantidad fuera del limite.");
            }
        }

    }

    //*Metodo para eliminar un producto del carrito
    public void eliminarElemento(ArrayList<ArrayList<String>> productos , ArrayList<String> nombres, ArrayList<Integer> cantidad,String nombre){
        if(!carritoVacio(nombres))
        {
            int indice = existenciaCarrito(nombres, nombre);
            //Si el indice no es -2 significa que el producto se encontro, entonces se elimina el producto
            if (indice!=-2) {
                nombres.remove(indice);
                cantidad.remove(indice);
            }else{
                System.out.println("\nProducto no encontrado.");
            }
        }
    }

    //*Metodo para pagar todo lo se tiene en el carrito
    public boolean pagarCarrito(DataBase database,ArrayList<ArrayList<String>> productos , ArrayList<String> nombres, ArrayList<Integer> cantidad) throws SQLException{
        if (!carritoVacio(nombres)) {
            int nuevaCantidad; //Variable que contiene la nueva cantidad de productos disponibles luego de pagar
    
            for (int i = 0; i < nombres.size(); i++) {
                //Se buscan los productos del carrito en la base de datos
                database.obtener("SELECT * FROM productos WHERE producto = '"  + nombres.get(i) + "'");
                database.resultSet.next();

                //La nueva cantidad del producto son los productos totales disponibles menos la cantidad de productos que se desea comprar
                nuevaCantidad = Integer.parseInt(database.resultSet.getString("cantidad"))-cantidad.get(i);

                //Se actualiza la cantidad de productos disponible
                database.poner("UPDATE productos SET cantidad = '" + nuevaCantidad + "' WHERE producto = '"+ nombres.get(i) +"' ");
                
                //En caso de que por los productos comprados ya no haya mas para comprar, el estatus del producto se cambia a inactivo
                if (nuevaCantidad==0) {
                    database.poner("UPDATE productos SET estatus = '" + "inactivo" + "' WHERE producto = '"+ nombres.get(i) +"' ");
                }
                    
            }   
            //Se borran todos los productos y cantidad del carrito
            nombres.clear();
            cantidad.clear();
            return true;
        }
        return false;
    }

    @Override
    //*Metodo sobreescrito para imprimir la tabla, lo unico que cambia es que no imprime productos inactivos
    public boolean imprimirTabla(String nombreTabla, int columnaInicio) throws SQLException {
        // Consultar tabla
        database.obtener("SELECT * FROM " + nombreTabla);

        boolean elementoActivo = false; // Bandera para verificar si hay elementos activos

        // Imprimir elementos
        while (database.resultSet.next()) {

            if (database.resultSet.getString("estatus").equals("inactivo")) continue;
            System.out.println("");

            for (int i = columnaInicio; i <= database.resultSetMetaData.getColumnCount(); i++) {
                if (database.resultSet.getString("estatus").equals("activo")) {
                    elementoActivo = true;

                    String columnValue = database.resultSet.getString(i);
                    
                    if (database.resultSetMetaData.getColumnTypeName(i).equals("FLOAT")) {
                        columnValue = "$" + columnValue;
                    } 
    
                    System.out.println(database.resultSetMetaData.getColumnName(i) + ": " + columnValue);
                }

            }
        }

        System.out.println("");

        if (!elementoActivo) return false;

        return true;
        
    }

    // ** Metodos abstractos **
    @Override
    public void menu() {
        //Variables de opciones para el primer y segundo menu
        String opcion, opcion2;
        // Variable de estado
        boolean isRunning = true;

        int cantidad = 0; //Variable usada para leer cantidades de productos
        String nombre = ""; //Variable usada para leer nombre de productos

        ArrayList<ArrayList<String>> productos = new ArrayList<ArrayList<String>>(); //ArrayList que guarda todos los productos de la tienda
        ArrayList<String> nombre_productos = new ArrayList<String>(); //ArrayList que almacena el nombre de los productos del carrito
        ArrayList<Integer> cantidad_productos = new ArrayList<Integer>(); //ArrayList que almacena la cantidad de los productos del carrito

        //Se inicializa el Database
        DataBase database = new DataBase();

        //Se extraen todos los productos de la base de datos y se agregan al arrayList
        try {
            extraerProductos(database, productos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (isRunning) {
            Funcion.clear();
            System.out.print(
                    "*** CIMATECH ***\n" +
                            "Cliente: " + getUsername() + "\n" +
                            "1. Ver y comprar productos\n" +
                            "2. Ver carrito\n" +
                            "3. Cerrar sesión\n" +
                            "Opción: ");
            opcion = Valida.readString();

            switch (opcion) {
                case "1":
                    try {
                        //Si imprimir tabla regresa un false significa que no hay productos
                        if (!imprimirTabla("productos", 3)) {
                            System.out.println("\nNo hay productos");
                        } else {
                            System.out.println("**COMPRAR PRODUCTO");
                            nombre = Leer.nombreProducto("Nombre del producto que deseas añadir: ");
                            cantidad = Integer.parseInt(Leer.cantidad("Cantidad que deseas del producto: "));
                            //Si al asignar un producto se retorna un false, significa que no se encontro el producto o se supero el limite de cantidad
                            if (!asignarProducto(productos, nombre_productos, cantidad_productos, nombre, cantidad)) {
                                System.out.print("\nProducto no encontrado o cantidad fuera del limite.");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Funcion.pause();
                    break;

                case "2":
                    do {
                        Funcion.clear();
                        System.out.print(
                                "*** CIMATECH ***\n" +
                                        "Carrito del cliente: " + getUsername() + "\n" +
                                        "1. Ver productos del carrito\n" +
                                        "2. Eliminar productos del carrito\n" +
                                        "3. Modificar cantidad de los productos del carrito\n" +
                                        "4. Pagar carrito\n" +
                                        "0. Regresar al menu principal\n" +
                                        "Opción: ");
                        opcion2 = Valida.readString();
                        switch (opcion2) {
                            case "0":
                                break;
                            case "1":
                                mostrarCarrito(productos, nombre_productos, cantidad_productos);
                                Funcion.pause();
                                break;
                            case "2":
                                //Esta accion solo se hace si hay productos en el carrito
                                if (mostrarCarrito(productos, nombre_productos, cantidad_productos)) {
                                    System.out.println("\n*ELIMINAR PRODUCTOS DEL CARRITO*");
                                    nombre = Leer.nombreProducto("Nombre del producto que quieres eliminar: ");
                                    eliminarElemento(productos, nombre_productos, cantidad_productos, nombre);
                                }
                                Funcion.pause();
                                break;
                            case "3":
                                //Esta accion solo se hace si hay productos en el carrito
                                if (mostrarCarrito(productos, nombre_productos, cantidad_productos)) {
                                    System.out.println("\n**MODIFICAR CANTIDAD DE UN PRODUCTO DEL CARRITO");
                                    nombre = Leer.nombreProducto("Nombre del producto: ");
                                    cantidad = Integer.parseInt(Leer.cantidad("Nueva cantidad: "));
                                    modificarCantidad(productos, nombre_productos, cantidad_productos, nombre, cantidad);
                                }
                                Funcion.pause();
                                break;
                            case "4":
                                try {
                                    if (pagarCarrito(database, productos, nombre_productos, cantidad_productos)) {
                                        System.out.println("\nCarrito pagado con exito.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                Funcion.pause();
                                break;

                            default:
                                break;
                        }
                    } while (!opcion2.equals("0"));

                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }

    }
}
