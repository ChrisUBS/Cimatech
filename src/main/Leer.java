package main;

public class Leer {

    // ** Metodos para imprimir mensaje y leer los datos **

    public static String status(String mensaje) {
        String status = "";
        while (status.isEmpty() || (!status.equals("activo") && !status.equals("inactivo"))) {
            System.out.print(mensaje);
            status = Valida.readString();
        }

        return status;
    }

    public static String rol(String mensaje) {
        String rol = "";
        while (rol.isEmpty() || (!rol.equals("admin") && !rol.equals("buyer") && !rol.equals("seller"))) {
            System.out.print(mensaje);
            rol = Valida.readString().toLowerCase();
        }

        return rol;
    }

    public static String nombre(String mensaje) {
        String nombre = "";
        while (nombre.isEmpty() || !Valida.isOnlyString(nombre)) {
            System.out.print(mensaje);
            nombre = Valida.readString();
        }

        return nombre;  
    }

    public static String apellido(String mensaje) {
        String apellido = "";
        while (apellido.isEmpty() || !Valida.isOnlyString(apellido)) {
            System.out.print(mensaje);
            apellido = Valida.readString();
        }

        return apellido;
    }

    public static String username(String mensaje) {
        String usuario = "";
        while (usuario.isEmpty() || usuario.contains(" ")) {
            System.out.print(mensaje);
            usuario = Valida.readString().toLowerCase();
        }

        return usuario;
    }

    public static String password(String mensaje) {
        String contraseña = "";
        while (contraseña.isEmpty() || contraseña.contains(" ")) {
            System.out.print(mensaje);
            contraseña = Valida.readString();
        }

        return contraseña;
    }

    public static String tipo(String mensaje) {
        String tipo = "";
        while (tipo.isEmpty() || ((!tipo.equals("periferico") && !tipo.equals("componente") && !tipo.equals("computadora")))) {
            System.out.print(mensaje);
            tipo = Valida.readString();
        }

        return tipo;
    }

    public static String nombreProducto(String mensaje) {
        String nombre = "";
        while (nombre.isEmpty()) {
            System.out.print(mensaje);
            nombre = Valida.readString();
        }

        return nombre;
    }

    public static String cantidad(String mensaje) {
        String cantidad = "";
        while (cantidad.isEmpty() || !Valida.isAIntNumber(cantidad) || Valida.isNegativeNumber(cantidad)) {
            System.out.print(mensaje);
            cantidad = Valida.readString();
        }

        return cantidad;
    }

    public static String precio(String mensaje) {
        String precio = "";
        while (precio.isEmpty() || !Valida.isAFloatNumber(precio) || Valida.isNegativeNumber(precio)) {
            System.out.print(mensaje);
            precio = Valida.readString();
        }

        return precio;
    }

}
