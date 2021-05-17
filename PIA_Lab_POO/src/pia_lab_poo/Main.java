package pia_lab_poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int calendario[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; 
        short menu, opc, sub, sub1; 
        int yAux, claveMayor=0; 
        Scanner lectura = new Scanner(System.in); 
        boolean encontrado = false; 
        Usuario user = null; 
        Reservacion primerR = null, ultimaR = null, auxR=null; 
        Paquete primerP = null, ultimoP = null, auxP=null; 
        CustomerData primerC = null, ultimoC =null, auxC=null;
        LocalDate fechaActual = LocalDate.now(); 
        int d = fechaActual.getDayOfMonth(), y = fechaActual.getYear(), m = fechaActual.getMonthValue();
        int dd=d+180, mm=m, yy=y;
        while(dd>calendario[mm-1]){
            dd-=calendario[mm-1]; 
            mm+=1; 
            if(mm>12){
                yy+=1; 
            }
        }
        System.out.print("Hoy: "+d+"/"+m+"/"+y); 
        System.out.print("En 6 meses: "+dd+"/"+mm+"/"+yy); 
        try{
            File usuarios = new File("usuarios.txt"); 
            File reservaciones = new File("reservaciones.txt"); 
            File paquetes = new File("paquetes.txt"); 
            File clientes = new File("clientes.txt"); 
            usuarios.createNewFile(); 
            reservaciones.createNewFile(); 
            paquetes.createNewFile();
            clientes.createNewFile(); 
            Scanner leerArchivo = new Scanner(reservaciones);
            while(leerArchivo.hasNext()){
                int clave = leerArchivo.nextInt(); 
                if(clave>claveMayor){
                    claveMayor=clave+1; 
                }
                int dia = leerArchivo.nextInt(); 
                int mes = leerArchivo.nextInt();
                yAux = leerArchivo.nextInt(); 
                float costo = leerArchivo.nextFloat();
                int matricula = leerArchivo.nextInt(); 
                Reservacion nuevaR = new Reservacion(dia, mes, yAux, clave, costo, matricula);  
                if(primerR==null){
                    primerR=ultimaR = nuevaR; 
                }
                else{
                    ultimaR.setSiguiente(nuevaR);
                    nuevaR.setAnterior(ultimaR); 
                    ultimaR = nuevaR; 
                }
                
            }
            leerArchivo = new Scanner(paquetes);
            while(leerArchivo.hasNext()){
                float costoBase = leerArchivo.nextFloat(); 
                float costoVariable = leerArchivo.nextFloat(); 
                int num = leerArchivo.nextInt(); 
                Paquete nuevoP = new Paquete(costoBase, costoVariable, num); 
                if(primerP==null){
                    primerP = ultimoP = nuevoP; 
                }
                else{
                    ultimoP.setSiguiente(nuevoP);
                    nuevoP.setAnterior(ultimoP); 
                    ultimoP = nuevoP; 
                }
            }
            leerArchivo = new Scanner(clientes); 
            while(leerArchivo.hasNext()){
                String customerName = leerArchivo.next(); 
                String customerLastName = leerArchivo.next(); 
                String email = leerArchivo.next(); 
                String numcel = leerArchivo.next(); 
                int matricula = leerArchivo.nextInt(); 
                CustomerData nuevoC = new CustomerData(customerName, customerLastName, email, numcel, matricula); 
                if(primerC==null){
                    primerC=ultimoC=nuevoC; 
                }
                else{
                    ultimoC.setSiguiente(nuevoC); 
                    nuevoC.setAnterior(ultimoC); 
                    ultimoC = nuevoC; 
                }
                
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        do{
            System.out.println("    <<< BIENVENIDO AL PROGRAMA >>>");
            System.out.println("\n\nEste es el sistema de administración de un salón de eventos\n\n"); 
            do{
                System.out.println("0--Iniciar Sesión\n1--Cerrar el programa"); 
                opc = lectura.nextShort(); 
            }while(!valido(0, 1, opc)); 
            if(opc==0){
                try{
                    File usuarios = new File("usuarios.txt"); 
                    Scanner leerArchivo = new Scanner(usuarios); 
                    if(!leerArchivo.hasNext()){
                        System.out.println("No hay usuarios registrados en el sistema"); 
                        System.out.println("Se creará un nuevo usuario nivel \"Jefe\""); 
                        System.out.print("Ingrese un nombre de usuario (no debe contener espacios): ");  
                        String userName = lectura.next(); 
                        System.out.print("Ingrese una contraseña: "); 
                        String password = lectura.next();  
                        user = new Jefe(userName, password); 
                        FileWriter escritor = new FileWriter(usuarios); 
                        escritor.write(user.getUserName()+" "+user.getPassword()+" "+0+"\n"); 
                        escritor.close();
                        System.out.println("Usuario creado con éxito"); 
                        encontrado = true; 
                    }
                    else{
                        System.out.print("Ingrese su nombre de usuario: "); 
                        String userName = lectura.next(); 
                        while(userName.length()<5){
                            System.out.print("Nombre de usuario muy corto, ingrese otro nombre de usuario (mínimo 5 caracteres): "); 
                            userName = lectura.next(); 
                        }
                        System.out.print("Ingrese su contraseña: "); 
                        String password = lectura.next(); 
                        while(password.length()<5){
                            System.out.print("Contraseña muy corta, ingrese una contraseña distinta (mínimo 5 caracteres)"); 
                            password = lectura.next(); 
                        }
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
                        menu = lectura.nextShort(); 
                        switch(menu){
                            case 1: 
                                do{
                                    System.out.println("Panel de Administración"); 
                                    System.out.println("Nivel: "+user.nivel()); 
                                    System.out.println("Opciones: "); 
                                    System.out.println("1--Crear nuevo usuario\n2--Eliminar Usuario\n3--Mostrar Paquetes\n4--Crear Paquete\n5--Eliminar Paquete\n6--Salir"); 
                                    sub = lectura.nextShort(); 
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
                                            System.out.println("\nMostrar paquetes"); 
                                            auxP = primerP; 
                                            if(primerP==null){
                                                System.out.println("\nNo hay paquetes en el sistema\n"); 
                                            }
                                            while(auxP != null){
                                                auxP.imprimir();
                                                auxP = auxP.getSiguiente(); 
                                            }
                                            break; 
                                        case 4: 
                                            System.out.println("Crear paquete"); 
                                            if(primerP==null){
                                                primerP = ultimoP = new Paquete(1); 
                                            }
                                            else{
                                                ultimoP.setSiguiente(new Paquete(ultimoP.getNum()+1)); 
                                                ultimoP.getSiguiente().setAnterior(ultimoP);
                                                ultimoP=ultimoP.getSiguiente(); 
                                            }
                                            break; 
                                        case 5: 
                                            System.out.println("Eliminar paquete"); 
                                            boolean pEncontrado= false; 
                                            System.out.print("Ingrese el paquete que desea eliminar: ");
                                            sub1 = lectura.nextShort(); 
                                            auxP = primerP; 
                                            while(auxP != null){
                                                if(sub1 == auxP.getNum()){
                                                    pEncontrado = true; 
                                                    if(auxP!=primerP){
                                                        auxP.getAnterior().setSiguiente(auxP.getSiguiente()); 
                                                    }
                                                    else{
                                                        primerP = auxP.getSiguiente(); 
                                                    }
                                                    if(auxP!=ultimoP){
                                                        auxP.getSiguiente().setAnterior(auxP.getAnterior()); 
                                                    }
                                                    else{
                                                        ultimoP = auxP.getAnterior(); 
                                                    }
                                                    auxP = null; 
                                                }
                                                else{
                                                    auxP = auxP.getSiguiente(); 
                                                }
                                            }
                                            if(pEncontrado){
                                                System.out.println("El paquete se eliminó con éxito"); 
                                            }
                                            else{
                                                System.out.println("No se encontró el paquete indicado"); 
                                            }
                                            break; 
                                        case 6: 
                                            System.out.println("Saliendo del Panel de Administración..."); 
                                            break; 
                                        default: 
                                            System.out.println("Opción inválida"); 
                                            break; 
                                    }
                                }while(sub!=6); 
                                
                                break; 
                            case 2: 
                                do{
                                    System.out.println("\nPanel de Reservaciones"); 
                                    System.out.println("\n1--Reservar Fecha\n2--Ver fechas disponibles\n3--Eliminar Reservacion\n4--Mostrar Reservaciones\n5--Salir");
                                    sub = lectura.nextShort(); 
                                    switch(sub){
                                        case 1: 
                                            System.out.print("Ingrese una fecha\n");
                                            System.out.print("Ingrese el año: "); 
                                            yAux = lectura.nextInt(); 
                                            System.out.print("Mes (número): "); 
                                            int mes = lectura.nextInt(); 
                                            System.out.print("Ingrese un día: "); 
                                            int dia = lectura.nextInt(); 
                                            System.out.print("Seleccione un paquete: "); 
                                            sub1 = lectura.nextShort();
                                            auxP = primerP; 
                                            boolean existeP=false; 
                                            while(auxP!=null){
                                                if(auxP.getNum()==sub1){
                                                    existeP=true; 
                                                    break; 
                                                }
                                                else{
                                                    auxP = auxP.getSiguiente(); 
                                                }
                                            }
                                            if(!valido(1, 12, mes)){
                                                System.out.println("Mes no valido"); 
                                            }
                                            else if(!valido(1, calendario[mes-1], dia)){
                                                System.out.println("Día no válido"); 
                                            }
                                            else if((yAux<y || (yAux==y && mes<m) || (yAux==y &&mes ==m &&dia<d))
                                                    ||(yAux>yy ||(yAux==yy &&mes>mm) ||(yAux==yy &&mes==mm && dia>dd)))
                                            {
                                                System.out.println("Fecha inválida para reservación, solo se puede reservar con hasta 6 meses de anticipo");  
                                            }
                                            else if(!existeP){
                                                System.out.println("El paquete es inexistente"); 
                                            }
                                            
                                            else{
                                                int p;
                                                char sn; 
                                                int matricula; 
                                                boolean clienteEncontrado=false; 
                                                do{
                                                    System.out.println("¿Cuantas personas asistirán a su evento?"); 
                                                    p = lectura.nextInt(); 
                                                }while(!valido(1, 100, p)); 
                                                System.out.println("Ingrese los datos del cliente"); 
                                                do{
                                                    do{
                                                        System.out.println("¿Desea registrar los datos de un nuevo cliente?"); 
                                                        System.out.println("S--Sí\nN--No"); 
                                                        sn = lectura.next().charAt(0); 
                                                    }while(sn!='s'&&sn!='S'&&sn!='N'&&sn!='n');
                                                    if(sn=='s' || sn =='S'){
                                                        System.out.print("Ingrese el primer nombre del cliente: "); 
                                                        String customerName = lectura.next(); 
                                                        System.out.print("Ingrese el primer apellido del cliente: "); 
                                                        String customerLastName = lectura.next(); 
                                                        String email; 
                                                        do{
                                                            System.out.print("Ingrese un e-mail válido: "); 
                                                            email = lectura.next(); 
                                                        }while(!email.contains("@") || !email.contains(".com"));  
                                                        System.out.print("Ingrese su numero telefonico (10 dígitos): "); 
                                                        String numcel = lectura.next();
                                                        while(numcel.length()!=10 || !numcel.matches("[0-9]+")){
                                                            System.out.print("Ingrese un número telefónico válido: "); 
                                                            numcel = lectura.next(); 
                                                        }
                                                        CustomerData nuevoC; 
                                                        if(primerC==null){
                                                            nuevoC = new CustomerData(customerName, customerLastName, email, numcel, 0);
                                                            primerC = ultimoC = nuevoC;
                                                        }
                                                        else{
                                                            nuevoC = new CustomerData(customerName, customerLastName, email, numcel, ultimoC.getMatricula()+1); 
                                                            ultimoC.setSiguiente(nuevoC); 
                                                            nuevoC.setAnterior(ultimoC);  
                                                            ultimoC = nuevoC; 
                                                        }
                                                        matricula = ultimoC.getMatricula(); 
                                                        System.out.println("Su clave de cliente es: "+matricula);
                                                        clienteEncontrado = true; 
                                                    }
                                                    else{
                                                        System.out.print("Ingrese la matricula del cliente: "); 
                                                        matricula = lectura.nextInt();
                                                        auxC = primerC; 
                                                        while(auxC!=null){
                                                            if(auxC.getMatricula()==matricula){
                                                                clienteEncontrado = true; 
                                                                break; 
                                                            }
                                                            else{
                                                                auxC = auxC.getSiguiente(); 
                                                            }
                                                        }
                                                    }
                                                }while(!clienteEncontrado); 
                                                 
                                                
                                                Reservacion nuevaR; 
                                                boolean reservado=false; 
                                                if(primerR == null){
                                                    nuevaR = new Reservacion(dia, mes, yAux, claveMayor, auxP.getCostoBase()+p*auxP.getCostoVariable(), matricula); 
                                                    claveMayor++; 
                                                }
                                                else{
                                                    nuevaR = new Reservacion(dia, mes, yAux, claveMayor, auxP.getCostoBase()+p*auxP.getCostoVariable(), matricula);
                                                    claveMayor++; 
                                                } 
                                                if(nuevaR.costoEstimado()){
                                                    auxR = primerR; 
                                                    while(auxR!=null){
                                                        if(auxR.getMes()==nuevaR.getMes() && auxR.getDia()==nuevaR.getDia()&&auxR.getYear()==nuevaR.getYear()){
                                                            reservado = true; 
                                                            auxR=null; 
                                                        }
                                                        else{
                                                            auxR=auxR.getSiguiente(); 
                                                        }
                                                    }
                                                    
                                                    if(!reservado){
                                                        if(primerR==null){
                                                            primerR = ultimaR = nuevaR; 
                                                        }
                                                        else if(mes>ultimaR.getMes() ||(mes==ultimaR.getMes() && dia>ultimaR.getDia())){
                                                            ultimaR.setSiguiente(nuevaR);
                                                            nuevaR.setAnterior(ultimaR); 
                                                            ultimaR = nuevaR; 
                                                        }
                                                        else if(mes < primerR.getMes() || (mes == primerR.getMes() && dia < primerR.getDia())){
                                                            nuevaR.setSiguiente(primerR); 
                                                            primerR.setAnterior(nuevaR);
                                                            primerR = nuevaR; 
                                                        }
                                                        else{
                                                            auxR = primerR; 
                                                            while(auxR!=null){
                                                                if(mes < auxR.getMes() || (mes == auxR.getMes() && dia < auxR.getDia())){
                                                                    auxR.getAnterior().setSiguiente(nuevaR);
                                                                    nuevaR.setAnterior(auxR.getAnterior()); 
                                                                    nuevaR.setSiguiente(auxR); 
                                                                    auxR.setAnterior(nuevaR);
                                                                    auxR = null; 
                                                                }
                                                                else{
                                                                    auxR = auxR.getSiguiente(); 
                                                                }
                                                            }
                                                        }
                                                        System.out.println("Fecha reservada con éxito"); 
                                                        System.out.println("La clave de su reservación es: "+nuevaR.getClave()); 
                                                    }
                                                    else{
                                                        System.out.println("La fecha ya está reservada"); 
                                                    }
                                                }
                                            }
                                            break; 
                                        case 2: 
                                            int dAux=d, mAux=m, lim=0; 
                                            yAux = y;
                                            auxR= primerR; 
                                            for(int cont = 0; cont<181; cont++){
                                                if(auxR!=null){
                                                    if(dAux!=auxR.getDia() || mAux != auxR.getMes()){
                                                        if(dAux<10){
                                                            System.out.print("0"); 
                                                        }
                                                        System.out.print(dAux+"/"); 
                                                        if(mAux<10){
                                                            System.out.print("0"); 
                                                        }
                                                        System.out.print(mAux+"/"+yAux+" "); 
                                                        
                                                    }
                                                    else{
                                                        auxR = auxR.getSiguiente();
                                                    }
                                                }
                                                else{
                                                    if(dAux<10){
                                                        System.out.print("0"); 
                                                    }
                                                    System.out.print(dAux+"/"); 
                                                    if(mAux<10){
                                                        System.out.print("0"); 
                                                    }
                                                    System.out.print(mAux+"/"+yAux+" "); 
                                                }
                                                lim++; 
                                                if(lim>10){
                                                    System.out.print("\n");
                                                    lim = 0; 
                                                }
                                                dAux++; 
                                                if(dAux>calendario[mAux-1]){
                                                    dAux-=calendario[mAux-1]; 
                                                    mAux++; 
                                                    if(mAux>12){
                                                        yAux++; 
                                                    }
                                                }
                                            }
                                            break; 
                                        case 3: 
                                            char sn; 
                                            System.out.print("Ingrese la clave de la reservación a eliminar: "); 
                                            int clave = lectura.nextInt(); 
                                            auxR = primerR; 
                                            boolean eliminada=false, encontrada=false; 
                                            while(auxR!=null){
                                                if(auxR.getClave()==clave){
                                                    encontrada=true; 
                                                    System.out.print("¿Seguro que desea eliminar su reservación?\n"); 
                                                    do{
                                                        System.out.println("S--Sí\nN--No"); 
                                                        sn = lectura.next().charAt(0); 
                                                    }while(sn!='s'&&sn!='S'&&sn!='n'&&sn!='N');
                                                    if(sn!='s'||sn!='S'){
                                                        if(auxR==primerR){
                                                            primerR=auxR.getSiguiente(); 
                                                        }
                                                        else{
                                                            auxR.getAnterior().setSiguiente(auxR.getSiguiente());
                                                        }
                                                        if(auxR==ultimaR){
                                                            ultimaR=auxR.getAnterior(); 
                                                        }
                                                        else{
                                                            auxR.getSiguiente().setAnterior(auxR.getAnterior()); 
                                                        }
                                                        auxR=null; 
                                                        eliminada = true; 
                                                        System.out.println("Reservación eliminada con éxito"); 
                                                    }
                                                }
                                                else{
                                                    auxR = auxR.getSiguiente(); 
                                                }
                                                if(eliminada){
                                                    System.out.println("Se eliminó la reservación con éxito"); 
                                                }
                                                else if(encontrada){
                                                    System.out.println("La reservación no ha sido eliminada"); 
                                                }
                                                else{
                                                    System.out.println("No se encontró la reservación buscada, verifique que la clave ingresada sea la correcta"); 
                                                }
                                            }
                                            break; 
                                        case 4: 
                                            System.out.println("Mostrar Reservaciones"); 
                                            auxR = primerR; 
                                            if(primerR==null){
                                                System.out.println("No hay reservaciones almacenadas");
                                            }
                                            while(auxR!=null){
                                                auxR.imprimir();
                                                System.out.print("\n"); 
                                                auxR = auxR.getSiguiente(); 
                                            }
                                            break; 
                                        case 5: 
                                            System.out.println("Saliendo del Panel de Reservaciones..."); 
                                            break; 
                                        default: 
                                            System.out.println("Seleccione una opción válida"); 
                                            break; 
                                    }
                                }while(sub!=5); 
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
        auxR = primerR; 
        auxP = primerP; 
        auxC = primerC; 
        try{
            File reservaciones = new File("reservaciones.txt"); 
            File paquetes = new File("paquetes.txt"); 
            File clientes = new File("clientes.txt"); 
            FileWriter borrador = new FileWriter(reservaciones); 
            borrador.write(""); 
            borrador.close();
            borrador = new FileWriter(paquetes); 
            borrador.write(""); 
            borrador.close();
            borrador = new FileWriter(clientes); 
            borrador.write(""); 
            borrador.close(); 
        }
        catch(IOException e){
            e.printStackTrace();
        }
        while(auxR!=null){
            auxR.escribir();
            auxR = auxR.getSiguiente(); 
        }
        
        while(auxP!=null){
            auxP.escribir();
            auxP = auxP.getSiguiente(); 
        }
        while(auxC!=null){
            auxC.escribir();
            auxC = auxC.getSiguiente(); 
        }
        System.out.println("Adios"); 
    }
    static boolean valido(int min, int max, int entrada){ 
        return !(entrada<min || entrada>max);
    }
}
