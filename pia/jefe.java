package pia;

import java.util.Scanner;
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

public class jefe extends usuario{
    public jefe(String userName, String password) {
        super(userName, password);
    }
    public jefe(String userName, String password, boolean activo){
        super(userName, password, activo); 
    }
    @Override
    public String nivel(){
        return "Jefe"; 
    }
    @Override
    public void crearUsuario(){
        int aux; 
        String us, usAux, pw, pwAux; 
        int clase; 
        boolean existe = false, estado; 
        Scanner lectura = new Scanner(System.in); 
        System.out.println("Seleccione el tipo de usuario que desea crear: "); 
        System.out.println("0--Jefe\n1--Gerente\n2--Empleado\n3--Ninguno"); 
        do{
            aux = lectura.nextInt(); 
        }while(aux != 0 && aux !=1 && aux!=2 && aux != 3);
        if(aux !=3){
            System.out.print("Ingrese el nombre de usuario: "); 
            us = lectura.next();
            try{
                File archivo = new File("usuarios.txt"); 
                Scanner leerArchivo = new Scanner(archivo);
                FileWriter escritor = new FileWriter(archivo, true); 
                while(leerArchivo.hasNext()){
                    usAux = leerArchivo.next(); 
                    pwAux = leerArchivo.next(); 
                    estado = leerArchivo.nextBoolean(); 
                    clase = leerArchivo.nextInt(); 
                    if(us.equals(usAux)){
                        existe=true; 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    pw = lectura.next(); 
                    escritor.write(us+" "+pw+" "+true+" "+aux+"\n"); 
                    System.out.println("Usuario generado con éxito"); 
                }
                else{
                    System.out.println("El usuario ya existe"); 
                }
                escritor.close(); 
            }
            catch(IOException e){
                System.out.println("Ocurrió un error al crear el nuevo usuario"); 
                e.printStackTrace();
            }
        }
        
    }
}
