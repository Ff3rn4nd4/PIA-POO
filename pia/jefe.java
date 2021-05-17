package pia;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    
    @Override
    public void eliminarUsuario(){
        String linea; 
        String eliminar; 
        Scanner sc = new Scanner(System.in); 
        try{
            File f = new File("usuarios.txt"); 
            Scanner buffer = new Scanner(f); 
            File aux = new File("auxiliar.txt"); 
            aux.createNewFile(); 
            FileWriter escritor = new FileWriter(aux); 
            Scanner copiar = new Scanner(aux); 
            
            
            System.out.print("¿Qué usuario desea eliminar? "); 
            eliminar = sc.next(); 
            while(buffer.hasNext()){
                linea = buffer.nextLine(); 
                if(linea.contains(eliminar)){
                   System.out.println("Usuario eliminado");  
                }
                else{
                    escritor.write(linea+"\n");
                }
            }
            escritor.close();
            FileWriter pegar = new FileWriter(f); 
            while(copiar.hasNext()){
                linea = copiar.nextLine(); 
                pegar.write(linea+"\n"); 
            }
            copiar.close();
            pegar.close();
            System.out.print(aux.delete()); 
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
