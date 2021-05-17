package pia_lab_poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gerente extends Usuario{
    public Gerente(String userName, String password) {
        super(userName, password);
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
        boolean existe = false; 
        
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
                    clase = leerArchivo.nextInt(); 
                    if(us.equals(usAux)){
                        existe = true; 
                        break; 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    pw = lectura.next(); 
                    escritor.write(us+" "+pw+" "+" "+2+"\n"); 
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
                if(linea.contains(eliminar) &&!linea.contains(getUserName())){
                    if(linea.contains(" 2 ")){
                        System.out.println("Usuario eliminado");
                    }
                    else{
                        System.out.println("No se logró eliminar al usuario"); 
                        escritor.write(linea+"\n");
                    }
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
