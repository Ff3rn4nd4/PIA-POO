
package Usuarios;



public class Usuario implements Permiso{
    private String userName, password;
    private boolean activo; 
    public Usuario(String userName, String password){
        this.userName = userName; 
        this.password = password;
        this.activo = true; 
    }
    public Usuario(String userName, String password, boolean activo){
        this.userName = userName; 
        this.password = password; 
        this.activo = activo; 
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
    public String nivel(){
        return "Empleado"; 
    }
    @Override
    public void crearUsuario() {
        System.out.println("No tiene permiso de crear nuevos usuarios");
    }
    @Override
    public void eliminarUsuario() {
        System.out.println("No tiene permiso de eliminar usuarios"); 
    }
    @Override
    public void cambiarEstado(){
        System.out.println("No tiene permiso de modificar el estado de otros usuarios"); 
    }
}
