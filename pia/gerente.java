package pia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class gerente extends empleado{
    public gerente(String userName, String password) {
        super(userName, password);
    }
    public gerente(String userName, String password, boolean activo){
        super(userName, password, activo); 
    }
    @Override
    public String nivel(){
        return "Gerente";
    }
    @Override 
    public void crearUsuario(){
        char sn; 
        String us, usAux, pw, pwAux; 
        int clase; 
        boolean existe = false, estado; 
        
        Scanner lectura = new Scanner(System.in); 
        do{
            System.out.println("Desea crear un nuevo usuario nivel empleado?"); 
            System.out.println("S--Si\nN--No\n"); 
            sn = lectura.next().charAt(0); 
        }
        while(sn!='s' && sn != 'S' && sn!= 'n' && sn != 'N'); 
        if(sn == 's' || sn == 'S'){
            System.out.print("Ingrese un nombre de usuario: "); 
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
                        existe = true; 
                        break; 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    pw = lectura.next(); 
                    escritor.write(us+" "+pw+" "+true+" "+2+"\n"); 
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
