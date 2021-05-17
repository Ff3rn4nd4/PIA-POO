package pia_lab_poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Jefe extends Usuario{
    public Jefe(String userName, String password){
        super(userName, password); 
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
        }while(aux <0 || aux>3);
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
                    clase = leerArchivo.nextInt();
                    if(us.equals(usAux)){
                        existe=true; 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    pw = lectura.next(); 
                    escritor.write(us+" "+pw+" "+aux+" \n");
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
            if(eliminar.equals(getUserName())){
                System.out.println("No se puede eliminar al usuario activo"); 
            }
            while(buffer.hasNext()){
                linea = buffer.nextLine(); 
                if(linea.contains(eliminar) && !linea.contains(getUserName())){
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
            aux.delete(); 
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
