package pia_poo;

import Clientes.CustomerData;
import Clientes.ListaClientes;
import Paquetes.ListaPaquetes;
import Reservaciones.ListaReservaciones;
import Reservaciones.Reservacion;
import Usuarios.Gerente;
import Usuarios.Jefe;
import Usuarios.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
         
        short menu, opc, sub, sub1;
        Scanner leer = new Scanner(System.in); 
        boolean encontrado = false; 
        boolean validar; 
        Usuario user = null;
        ListaClientes listaC = new ListaClientes(); 
        ListaReservaciones listaR = new ListaReservaciones(); 
        ListaPaquetes listaP = new ListaPaquetes(); 
        
        listaP.leerArchivo();
        listaR.leerArchivo();
        listaC.leerArchivo(); 
        try{
            File usuarios = new File("usuarios.txt");
            Scanner leerArchivo; 
            usuarios.createNewFile();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        do{
            System.out.println("    <<< BIENVENIDO AL PROGRAMA >>>");
            System.out.println("\n\nEste es el sistema de administración de un salón de eventos\n\n"); 
            do{
                System.out.println("0--Iniciar Sesión\n1--Cerrar el programa"); 
                opc = leer.nextShort(); 
                
            }while(!valido(0, 1, opc)); 
            if(opc==0){
                try{
                    File usuarios = new File("usuarios.txt"); 
                    Scanner leerArchivo = new Scanner(usuarios); 
                    if(!leerArchivo.hasNext()){
                        System.out.println("No hay usuarios registrados en el sistema"); 
                        System.out.println("Se creará un nuevo usuario nivel \"Jefe\""); 
                        System.out.print("Ingrese un nombre de usuario (no debe contener espacios): ");  
                        String userName = leer.next(); 
                        while(userName.length()<5){
                            System.out.print("Nombre de usuario muy corto, ingrese otro nombre de usuario (mínimo 5 caracteres): "); 
                            userName = leer.next(); 
                        }
                        System.out.print("Ingrese una contraseña: "); 
                        String password = leer.next();  
                        while(password.length()<5){
                            System.out.print("Contraseña muy corta, ingrese una contraseña distinta (mínimo 5 caracteres)"); 
                            password = leer.next(); 
                        }
                        user = new Jefe(userName, password); 
                        FileWriter escritor = new FileWriter(usuarios); 
                        escritor.write(user.getUserName()+" "+user.getPassword()+" "+0+"\n"); 
                        escritor.close();
                        System.out.println("Usuario creado con éxito"); 
                        encontrado = true; 
                    }
                    else{
                        System.out.print("Ingrese su nombre de usuario: "); 
                        String userName = leer.next(); 
                        
                        System.out.print("Ingrese su contraseña: "); 
                        String password = leer.next(); 
                        
                        while(leerArchivo.hasNext()){
                            String userAux = leerArchivo.next(); 
                            String pwAux = leerArchivo.next(); 
                            short clase = leerArchivo.nextShort();
                            if(userName.equals(userAux) && password.equals(pwAux)){
                                switch(clase){
                                    case 0: 
                                        user = new Jefe(userName, password); 
                                        break; 
                                    case 1: 
                                        user = new Gerente(userName, password); 
                                        break; 
                                    default: 
                                        user = new Usuario(userName, password); 
                                        break; 
                                }
                                encontrado = true; 
                                break; 
                            }
                        }
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                if(encontrado){
                    System.out.println("\nBienvenido "+user.getUserName()); 
                    do{
                        System.out.println("Seleccione una opción: "); 
                        System.out.println("1--Panel de Administración\n2--Reservaciones\n3--Cerrar Sesión"); 
                        menu = leer.nextShort(); 
                        switch(menu){
                            case 1: 
                                do{
                                    System.out.println("Panel de Administración"); 
                                    System.out.println("Nivel: "+user.nivel()); 
                                    System.out.println("Opciones: "); 
                                    System.out.println("1--Crear nuevo usuario\n2--Eliminar Usuario\n3--Crear Paquete\n4--Mostrar Paquetes\n5--Eliminar Paquete"
                                            + "\n6--Registrar Cliente\n7--Buscar Cliente por Matrícula\n8--Eliminar Cliente\n9--Mostrar Lista de Clientes\n10--Salir\n");  
                                    sub = leer.nextShort(); 
                                    switch(sub){
                                        case 1: 
                                            System.out.println("Crear nuevo usuario"); 
                                            user.crearUsuario();
                                            break; 
                                        case 2: 
                                            System.out.println("Eliminar usuario"); 
                                            user.eliminarUsuario();
                                            break; 
                                        case 3: 
                                            listaP.crear();
                                            break; 
                                        case 4: 
                                            listaP.mostrarLista();
                                            break; 
                                        case 5:
                                            listaP.eliminar();
                                            break; 
                                        case 6: 
                                            listaC.crear();
                                            break; 
                                        case 7: 
                                            CustomerData aux = listaC.buscar(); 
                                            if(aux!=null){
                                                aux.imprimir();
                                            }
                                            break; 
                                        case 8: 
                                            listaC.eliminar();
                                            break; 
                                        case 9: 
                                            listaC.mostrarLista();
                                            break; 
                                        case 10: 
                                            System.out.println("Saliendo del Panel de Administración..."); 
                                            break; 
                                        default: 
                                            System.out.println("Opción inválida"); 
                                            break; 
                                    }
                                }while(sub!=10); 
                                
                                break; 
                            case 2: 
                                do{
                                    System.out.println("\nPanel de Reservaciones"); 
                                    System.out.println("\n1--Reservar Fecha\n2--Ver fechas disponibles\n3--Eliminar Reservacion\n4--Mostrar Reservaciones\n5--Buscar Reservación por Clave"
                                            + "\n6--Salir"); 
                                    sub = leer.nextShort(); 
                                    switch(sub){
                                        case 1: 
                                            listaR.crear(listaP, listaC);
                                            break; 
                                        case 2:
                                            listaR.fechasDisponibles();
                                            break; 
                                        case 3: 
                                            listaR.eliminar();
                                            break; 
                                        case 4: 
                                            listaR.mostrarLista(listaC);
                                            break; 
                                        case 5: 
                                            System.out.println("Buscar Reservación por clave\n"); 
                                            Reservacion aux = listaR.buscar(); 
                                            CustomerData auxC = null; 
                                            if(aux!=null){
                                                System.out.println("\nDatos de la Reservación: "); 
                                                aux.imprimir();
                                                auxC=listaC.buscar(aux.getCliente()); 
                                                if(auxC!=null){
                                                    auxC.imprimir();
                                                }
                                            }
                                            break; 
                                        case 6: 
                                            System.out.println("Saliendo del Panel de Reservaciones..."); 
                                            break; 
                                        default: 
                                            System.out.println("Seleccione una opción válida"); 
                                            break; 
                                    }
                                }while(sub!=6); 
                                break; 
                            case 3: 
                                System.out.println("Sesión finalizada"); 
                                encontrado = false; 
                                user = null; 
                                break; 
                            default: 
                                System.out.println("Ingrese una opción válida"); 
                                break; 
                        }
                    }while(menu!=3); 
                }
                else{
                    System.out.println("\nUsuario o contraseña incorrectos o inexistentes"); 
                    
                }
            }
        }while(opc!=1);
        listaP.almacenar();
        listaR.almacenar();
        listaC.almacenar();
        System.out.println("Adios"); 
    }
    static boolean valido(int min, int max, int entrada){ 
        return !(entrada<min || entrada>max);
    }
}