package pia;

import java.util.Scanner;
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

public class PIA {
    public static void main(String[] args) {
        int opc, menu, sub; 
        Scanner lectura = new Scanner(System.in); 
        String userName, password, userAux, pwAux; 
        int clase;  
        boolean activo = false, encontrado = false; 
        usuario user = null; 
        try{
            File archivo = new File("usuarios.txt"); 
            archivo.createNewFile(); 
        }
        catch(IOException e){
            System.out.println("No se puede acceder a los usuarios, intente más tarde"); 
            e.printStackTrace();
        }
        do{
            do{
                System.out.println("0--Iniciar sesión\n1--Cerrar el programa");  
                opc = lectura.nextInt(); 
            }while(opc!=0 && opc !=1); 
            /**/
            if(opc == 0){
                //Acceso
                try{
                    File archivo = new File("usuarios.txt"); 
                    Scanner leerArchivo = new Scanner(archivo);
                    if(!leerArchivo.hasNext()){
                        System.out.println("No hay usuarios registrados en el sistema"); 
                        System.out.println("Se creará un usuario nivel \"Jefe\""); 
                        System.out.print("Ingrese un nombre de usuario: ");
                        userName = lectura.next(); 
                        System.out.print("Ingrese una contraseña: "); 
                        password = lectura.next(); 
                        user = new jefe(userName, password); 
                        FileWriter escritor = new FileWriter(archivo); 
                        escritor.write(user.getUserName()+" "+user.getPassword()+" "+user.isActivo()+" "+0+"\n"); 
                        escritor.close(); 
                        System.out.println("Usuario creado con éxito"); 
                        encontrado = true; 
                    }
                    else{
                        System.out.print("Ingrese su nombre de usuario: "); 
                        userName = lectura.next(); 
                        System.out.print("Ingrese su contraseña: "); 
                        password = lectura.next(); 
                        while(leerArchivo.hasNext()){
                            userAux = leerArchivo.next(); 
                            pwAux = leerArchivo.next(); 
                            activo = leerArchivo.nextBoolean(); 
                            clase = leerArchivo.nextInt(); 
                            if(userName.equals(userAux) && password.equals(pwAux)){
                                switch(clase){
                                    case 0: 
                                        user = new jefe(userName, password, activo); 
                                        break; 
                                    case 1: 
                                        user = new gerente(userName, password, activo); 
                                        break; 
                                    default: 
                                        user = new empleado(userName, password, activo); 
                                        break; 
                                }
                                encontrado = true; 
                                break; 
                            }
                        }
                    }
                }
                catch(IOException e){
                    System.out.println("No se pudo crear el usuario \"Jefe\""); 
                    e.printStackTrace();
                }
                if(encontrado){
                    if(user.isActivo()){
                        System.out.println("Bienvenido "+user.getUserName()); 
                        do{
                            System.out.println(user.getUserName()+", seleccione una opción: "); 
                            System.out.println("1--Acceder al Panel de Administración"); 
                            System.out.println("2--Reservaciones"); 
                            System.out.println("3--Cerrar Sesión"); 
                            menu = lectura.nextInt(); 
                            switch(menu){
                                case 1: 
                                    System.out.println("Panel de Administración"); 
                                    System.out.println("Nivel: "+user.nivel());  
                                    System.out.println("Opciones: "); 
                                    System.out.println("1--Crear nuevo usuario"); 
                                    System.out.println("2--Cambiar usuario y contraseña");
                                    sub = lectura.nextInt(); 
                                    switch(sub){
                                        case 1: 
                                            System.out.println("Crear nuevo usuario"); 
                                            user.crearUsuario();
                                            break; 
                                        case 2: 
                                            System.out.println("Cambiar nombre de Usuario y contraseña"); 
                                            break; 
                                        default: 
                                            System.out.println("Seleccione una opción válida"); 
                                    }
                                    break; 
                                case 2: 
                                    System.out.println("Crear Reservación"); 
                                    break; 
                                case 3: 
                                    System.out.println("Sesión finalizada"); 
                                    encontrado = false; 
                                    user = null; 
                                    break; 
                                default: 
                                    System.out.println("Seleccione una opción válida"); 
                                    break; 
                            }
                        }while(menu!=3); 
                    }
                    else{
                        System.out.println("\n\nEl usuario no está activo.");
                        System.out.println("Para activarlo, acceda desde una cuenta válida\n"); 
                    }
                }
                else{
                    System.out.println("Usuario o contraseña incorrectos o inexistentes"); 
                }
            }
        }while(opc!=1); 
        System.out.println("Adios"); 
    }
}