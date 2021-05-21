
package Usuarios;
import java.util.Scanner;


public class Usuario implements Permiso{
    private String userName, password;
    public Usuario(String userName, String password){
        this.userName = userName; 
        this.password = password;
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
    public String nivel(){
        return "Empleado"; 
    }
    @Override
    public void crearUsuario() {
        System.out.println(this.userName+" no tiene permiso de crear nuevos usuarios");
    }

    @Override
    public void eliminarUsuario() {
        System.out.println(this.userName+" no tiene permiso de eliminar usuarios"); 
    }
}
