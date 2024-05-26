package main;

public class Leer {

    // ** Metodos para imprimir mensaje y leer los datos **

    public static String nombre(){
        String nombre = "";
        while (!Valida.isOnlyString(nombre) || nombre.isEmpty()) {
            System.out.print("Nombre: ");
            nombre = Valida.readString();
        }
        return nombre;  
    }

    public static String apellido(){
        String apellido = "";
        while (apellido.isEmpty()) {
            System.out.print("Apellido: ");
            apellido = Valida.readString();
        }
        return apellido;
    }

    public static String username(){
        String usuario = "";
        while (usuario.isEmpty()) {
            System.out.print("Usuario: ");
            usuario = Valida.readString();
        }
        return usuario;
    }

    public static String password(){
        String contraseña = "";
        while (contraseña.isEmpty()) {
            System.out.print("Contraseña: ");
            contraseña = Valida.readString();
        }

        return contraseña;
    }

    public static String rol(){
        String rol = "";
        while (rol.isEmpty() || (!rol.equals("admin") || !rol.equals("buyer") || rol.equals("seller"))) {
            System.out.print("Rol: ");
            rol = Valida.readString();
        }

        return rol;
    }

    public static int cantidad(){
        String cantidad = "";
        while (cantidad.isEmpty() || !Valida.isAIntNumber(cantidad))  {
            System.out.print("Cantidad: ");
            cantidad = Valida.readString();
        }

        return Integer.parseInt(cantidad);
    }

    public static float precio(){
        String precio = "";
        while (precio.isEmpty() || !Valida.isAFloatNumber(precio))  {
            System.out.print("Precio: $");
            precio = Valida.readString();
        }

        return Float.parseFloat(precio);
    }

    public static String tipo(){
        String tipo = "";
        
        while (tipo.isEmpty() || ((!tipo.equals("periferico") && !tipo.equals("componente") && !tipo.equals("computadora")))) {
            System.out.print("Tipo: ");
            tipo = Valida.readString();
        }

        return tipo;
    }

    public static int status(){
        String status = "";
        boolean formatoCorrecto=false;
        
        while (!formatoCorrecto) {
            System.out.print("Tipo: ");
            status = Valida.readString();
            if (status.isEmpty() || Valida.isAIntNumber(status)) {
                if (Valida.isInRange(status,0,1)) formatoCorrecto = true;
            }
        }

        return Integer.parseInt(status);
    }

    // ** Metodos para leer los datos **

    public static String nuevoNombre() {
        String nombre = "";
        while (nombre.isEmpty()) {
            System.out.print("Nuevo nombre: ");
            nombre = Valida.readString();
        }
        return nombre;  
    }

    public static String nuevoTipo(){
        String tipo = "";
        
        while (tipo.isEmpty() || ((!tipo.equals("periferico") && !tipo.equals("componente") && !tipo.equals("computadora")))) {
            System.out.print("Nuevo tipo: ");
            tipo = Valida.readString();
        }

        return tipo;
    }

    public static int nuevaCantidad(){
        String cantidad = "";
        while (cantidad.isEmpty() || !Valida.isAIntNumber(cantidad))  {
            System.out.print("Nueva cantidad: ");
            cantidad = Valida.readString();
        }

        return Integer.parseInt(cantidad);
    }

    public static float nuevoPrecio(){
        String precio = "";
        while (precio.isEmpty() || !Valida.isAFloatNumber(precio))  {
            System.out.print("Nuevo precio: $");
            precio = Valida.readString();
        }

        return Float.parseFloat(precio);
    }

    public static int cantidadProducto(int max){
        String cantidad = "";
        while (cantidad.isEmpty() || !Valida.isAIntNumber(cantidad) || (!Valida.isInRange(cantidad, 0, max)))  {
            System.out.print("Cantidad del producto que desea: ");
            cantidad = Valida.readString();
        }

        return Integer.parseInt(cantidad);
    }
}
