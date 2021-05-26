package pia_poo;

import Clientes.CustomerData;
import Clientes.ListaClientes;
import Paquetes.ListaPaquetes;
import Reservaciones.ListaReservaciones;
import Reservaciones.Reservacion;
import Usuarios.Gerente;
import Usuarios.Jefe;
import Usuarios.Usuario;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//El programa utiliza System.console() para leer la contraseña del usuario y evitar que se vea
//Por tanto, el programa se necesita correr desde la consola

public class Main {
    public static void main(String[] args) {
         
        int menu, opc, sub, sub1;
        Scanner leer = new Scanner(System.in); 
        boolean encontrado = false; 
        boolean validar; 
        Usuario user = null;
        ListaClientes listaC = new ListaClientes(); 
        ListaReservaciones listaR = new ListaReservaciones(); 
        ListaPaquetes listaP = new ListaPaquetes(); 
        Excepciones excep = new Excepciones(); 
        Console console = System.console();
        
         
        if(console==null){
            System.out.println("No se puede acceder al programa desde un IDE. Acceda a él desde la consola"); 
        }
        else{
            try{
                File usuarios = new File("usuarios.txt");
                Scanner leerArchivo; 
                usuarios.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            listaP.leerArchivo();
            listaR.leerArchivo();
            listaC.leerArchivo();
            do{
                System.out.println("<<< BIENVENIDO AL PROGRAMA >>>");
                System.out.println("\nEste es el sistema de administración de un salón de eventos\n"); 
                System.out.println("0--Iniciar Sesión\n1--Cerrar el programa"); 
                opc = excep.leerInt(0, 1); 
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
                            char[] password = console.readPassword(); 
                            while(password.length<5){
                                System.out.print("Contraseña muy corta, ingrese una contraseña distinta (mínimo 5 caracteres): ");
                                password = console.readPassword(); 
                            }
                            while((new String(password)).contains(userName)){
                                System.out.print("La contraseña no debe contener al nombre de usuario, ingrese una contraseña distinta: "); 
                                password = console.readPassword(); 
                            }
                            user = new Jefe(userName, new String(password)); 
                            FileWriter escritor = new FileWriter(usuarios); 
                            escritor.write(user.getUserName()+" "+user.getPassword()+" "+true+" "+0+"\n"); 
                            escritor.close();
                            System.out.println("Usuario creado con éxito"); 
                            encontrado = true; 
                        }
                        else{

                            System.out.print("Ingrese su nombre de usuario: "); 
                            String userName = leer.next();
                            System.out.print("Ingrese su contraseña: "); 
                            //String password = leer.next();
                            char[] password = console.readPassword(); 
                            while(leerArchivo.hasNext()&&encontrado==false){
                                String usAux = leerArchivo.next(); 
                                String pwAux = leerArchivo.next(); 
                                boolean activo = leerArchivo.nextBoolean(); 
                                int categoria = leerArchivo.nextInt(); 
                                if(userName.equals(usAux) && (new String(password)).equals(pwAux)){
                                    switch(categoria){
                                        case 0: 
                                            user = new Jefe(userName, new String(password), activo); 
                                            break; 
                                        case 1: 
                                            user = new Gerente(userName, new String(password), activo); 
                                            break; 
                                        default: 
                                            user = new Usuario(userName, new String(password), activo); 
                                            break; 
                                    }
                                    encontrado = true; 
                                }
                            }
                        }
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    if(encontrado && user.isActivo()){
                        System.out.println("\nBienvenido "+user.getUserName()); 
                        do{
                            System.out.println("Seleccione una opción: "); 
                            System.out.println("1--Panel de Administración\n2--Reservaciones\n3--Cerrar Sesión"); 
                            menu = excep.leerInt(1, 4); 
                            switch(menu){
                                case 1: 
                                    do{
                                        System.out.println("Panel de Administración"); 
                                        System.out.println("Nivel: "+user.nivel()); 
                                        System.out.println("Opciones: "); 
                                        System.out.println("1--Menú de Usuarios\n2--Menú de Paquetes\n3--Menú de Clientes\n4--Salir\n");  
                                        sub = excep.leerInt(1, 4);  
                                        switch(sub){
                                            case 1: 
                                                if(user.nivel().equals("Empleado")){
                                                    System.out.println("No tiene permitido acceder a este apartado"); 
                                                }
                                                else{
                                                    do{
                                                        System.out.println("Menu de Usuarios"); 
                                                        System.out.println("1--Crear nuevo usuario\n2--Eliminar Usuario\n3---Activar/Desactivar Usuario\n4--Salir\n"); 
                                                        sub1 = excep.leerInt(1, 4);
                                                        switch(sub1){
                                                            case 1: 
                                                                user.crearUsuario();
                                                                break; 
                                                            case 2: 
                                                                user.eliminarUsuario();
                                                                break; 
                                                            case 3: 
                                                                user.cambiarEstado();
                                                                break;
                                                        }
                                                    }while(sub1!=4); 
                                                }
                                                break; 
                                            case 2: 
                                                do{
                                                    System.out.println("Menú de Paquetes"); 
                                                    System.out.println("1--Crear Paquete\n2--Eliminar Paquete\n3--Mostrar lista de Paquetes\n4--Salir\n"); 
                                                    sub1 = excep.leerInt(1, 4);
                                                    switch(sub1){
                                                        case 1: 
                                                            listaP.crear();
                                                            break; 
                                                        case 2: 
                                                            listaP.eliminar();
                                                            break; 
                                                        case 3: 
                                                            listaP.mostrarLista();
                                                            break; 
                                                    }
                                                }while(sub1!=4); 
                                                break; 
                                            case 3: 
                                                do{
                                                    System.out.println("Menu de Clientes"); 
                                                    System.out.println("1--Registrar Cliente\n2--Buscar Cliente por matrícuala\n3--Eliminar Cliente\n4--Mostrar lista de clientes\n5--Actualizar Datos de un Cliente\n6--Salir"); 
                                                    sub1=excep.leerInt(1, 5); 
                                                    switch(sub1){
                                                        case 1: 
                                                            listaC.crear();
                                                            break; 
                                                        case 2: 
                                                            CustomerData aux = listaC.buscar(); 
                                                            if(aux!=null){
                                                                aux.imprimir();
                                                            }
                                                            break; 
                                                        case 3: 
                                                            listaC.eliminar();
                                                            break; 
                                                        case 4: 
                                                            listaC.mostrarLista();
                                                            break; 
                                                        case 5: 
                                                            listaC.actualizarDatos();
                                                            break; 
                                                    }
                                                }while(sub1!=6); 
                                                break; 
                                            case 4: 
                                                System.out.println("Saliendo del Panel de Administración..."); 
                                                break;
                                        }
                                    }while(sub!=4); 

                                    break; 
                                case 2: 
                                    do{
                                        System.out.println("\nPanel de Reservaciones"); 
                                        System.out.println("\n1--Reservar Fecha\n2--Ver fechas disponibles\n3--Eliminar Reservacion\n4--Mostrar Reservaciones\n5--Buscar Reservación por Clave"
                                                + "\n6--Salir"); 
                                        sub = excep.leerInt(1, 6);  
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
                                        }
                                    }while(sub!=6); 
                                    break; 
                                case 3: 
                                    System.out.println("Sesión finalizada"); 
                                    encontrado = false; 
                                    user = null; 
                                    break;
                            }
                        }while(menu!=3); 
                    }
                    else if(encontrado && !user.isActivo()){
                        System.out.println("El usuario ingresado se encuentra inactivo\n"); 
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
    }
}