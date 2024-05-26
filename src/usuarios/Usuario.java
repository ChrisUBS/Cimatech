package usuarios;

public abstract class Usuario {
    
    // Atributos
    private String username;
    private String password;

    // Constructor
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // *** Getters y Setters ***

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ** Metodos abstractos **

    public abstract void menu();

}
