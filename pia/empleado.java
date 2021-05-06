package pia;
public class empleado extends usuario{
    public empleado(String userName, String password) {
        super(userName, password);
    }
    public empleado(String userName, String password, boolean activo){
        super(userName, password, activo); 
    }
    @Override
    public String nivel(){
        return "Empleado"; 
    }
}
