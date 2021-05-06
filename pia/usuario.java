package pia;

public class usuario implements permiso{
    private String userName; 
    private String password;
    private boolean activo; 
    public usuario(String userName, String password){
        this.userName = userName; 
        this.password = password; 
        activo = true; 
    }
    public usuario(String userName, String password, boolean activo){
        this.userName = userName; 
        this.password = password; 
        this.activo = activo; 
    }
    public String nivel(){
        return "Usuario"; 
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public void crearUsuario() {
        System.out.println("No tiene permiso de crear nuevos usuarios"); 
    }

  
}
