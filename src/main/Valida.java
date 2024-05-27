package main;

// Librerias
import java.util.Scanner;

public class Valida {

    // ** Metodos generales de validacion **

    static Scanner read = new Scanner(System.in);
    public static String readString() {
        String word;
        word = read.nextLine();
        return word;
    }

    public static boolean isOnlyString(String dato) {
        try {
            // No contempla los espacios
            if (dato.matches("[a-zA-Z]+")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception error) {
            return false;
        }
    }
    
    public static boolean isAIntNumber(String dato) {
        try {
            Integer.parseInt(dato);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }
    
    public static boolean isADoubleNumber(String dato) {
        try {
            Double.parseDouble(dato);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    public static boolean isAFloatNumber(String dato) {
        try {
            Float.parseFloat(dato);
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    public static boolean isNegativeNumber(String dato) {
        try {
            if (Double.parseDouble(dato) <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException error) {
            return false;
        }
    }
    
    public static boolean isInRange(String dato, int min, int max) {
        try {
            if (Integer.parseInt(dato) >= min && Integer.parseInt(dato) <= max) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException error) {
            return false;
        }
    }
    
}
