package pia_lab_poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


public class PIA_Lab_POO {
    public static void main(String[] args) {
        int calendario[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; 
        short menu, opc, sub, sub1, sub2; 
        int clase, matricula = 0, op=0, dia, mes, clave, num, yAux; 
        float costo, costoBase, costoVariable; 
        Scanner lectura = new Scanner(System.in); 
        String userName, password, userAux, pwAux; 
        String customerName, customerLastName, email, numcel; 
        boolean activo = false, encontrado = false; 
        Usuario user = null; 
        Reservacion primerR = null, ultimaR = null, auxR = null; 
        Paquete primerP = null, ultimoP = null, auxP =null; 
        CustomerData cliente = null; 
        CustomerData primerC = null, ultimoC =null, auxC = null; 
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
            if(leerArchivo.hasNext()){
                clave = leerArchivo.nextInt(); 
                dia = leerArchivo.nextInt(); 
                mes = leerArchivo.nextInt(); 
                yAux = leerArchivo.nextInt(); 
                costo = leerArchivo.nextFloat(); 
                matricula = leerArchivo.nextInt(); 
                primerR = ultimaR = new Reservacion(dia, mes, yAux, clave, costo, matricula); 
            }
            while(leerArchivo.hasNext()){
                clave = leerArchivo.nextInt(); 
                dia = leerArchivo.nextInt(); 
                mes = leerArchivo.nextInt();
                yAux = leerArchivo.nextInt(); 
                costo = leerArchivo.nextFloat();
                matricula = leerArchivo.nextInt(); 
                Reservacion nuevaR = new Reservacion(dia, mes, yAux, clave, costo, matricula);  
                ultimaR.setSiguiente(nuevaR);
                nuevaR.setAnterior(ultimaR); 
                ultimaR = nuevaR; 
            }
            leerArchivo = new Scanner(paquetes);
            if(leerArchivo.hasNext()){ 
                costoBase = leerArchivo.nextFloat(); 
                costoVariable = leerArchivo.nextFloat(); 
                num = leerArchivo.nextInt();
                primerP = ultimoP = new Paquete(costoBase, costoVariable, num); 
            }
            while(leerArchivo.hasNext()){
                costoBase = leerArchivo.nextFloat(); 
                costoVariable = leerArchivo.nextFloat(); 
                num = leerArchivo.nextInt(); 
                Paquete nuevoP = new Paquete(costoBase, costoVariable, num); 
                ultimoP.setSiguiente(nuevoP);
                nuevoP.setAnterior(ultimoP); 
                ultimoP = nuevoP; 
            }
            leerArchivo = new Scanner(clientes); 
            if(leerArchivo.hasNext()){
                customerName = leerArchivo.next(); 
                customerLastName = leerArchivo.next(); 
                email = leerArchivo.next(); 
                numcel = leerArchivo.next(); 
                matricula = leerArchivo.nextInt(); 
                primerC = ultimoC = new CustomerData(customerName, customerLastName, email, numcel, matricula); 
            }
            while(leerArchivo.hasNext()){
                customerName = leerArchivo.next(); 
                customerLastName = leerArchivo.next(); 
                email = leerArchivo.next(); 
                numcel = leerArchivo.next(); 
                matricula = leerArchivo.nextInt(); 
                CustomerData nuevoC = new CustomerData(customerName, customerLastName, email, numcel, matricula); 
                ultimoC.setSiguiente(nuevoC); 
                nuevoC.setAnterior(ultimoC); 
                ultimoC = nuevoC; 
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
        do{
            System.out.println("    <<< BIENVENIDO AL PROGRAMA >>>");
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
                        userName = lectura.next(); 
                        System.out.print("Ingrese una contraseña: "); 
                        password = lectura.next();  
                        user = new Jefe(userName, password); 
                        FileWriter escritor = new FileWriter(usuarios); 
                        escritor.write(user.getUserName()+" "+user.getPassword()+" "+0+"\n"); 
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
                            clase = leerArchivo.nextShort();
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
                                            System.out.println("Mostrar paquetes");
                                            auxP = primerP; 
                                            if(primerP==null){
                                                System.out.println("No hay paquetes en el sistema"); 
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
                                            }
                                            break; 
                                        case 5: 
                                            System.out.println("Eliminar paquete"); 
                                            System.out.print("Ingrese el paquete que desea eliminar: ");
                                            sub1 = lectura.nextShort(); 
                                            auxP = primerP; 
                                            while(auxP != null){
                                                if(sub1 == auxP.getNum()){
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
                                            System.out.print("Mes (número): "); 
                                            mes = lectura.nextInt(); 
                                            System.out.print("Ingrese un día: "); 
                                            dia = lectura.nextInt(); 
                                            System.out.print("Ingrese el año: "); 
                                            yAux = lectura.nextInt(); 
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
                                            else if(((mes<m || (mes==m && dia<d)) && (mes<mm || (mes ==mm && dia < dd))) 
                                                    ||((mes>m || (mes == m && dia>d)) && (mes>mm ||(mes ==mm && dia>dd)))
                                                    ||((mes<m || (mes == m && dia<d)) && (mes>mm || (mes ==mm &&dia > dd)))
                                                    ||yAux<y||yAux>(yy+1))
                                                    {
                                                System.out.println("Fecha inválida para reservación, solo se puede reservar con hasta 6 meses de anticipo");  
                                            }
                                            else if(!existeP){
                                                System.out.println("El paquete es inexistente"); 
                                            }
                                            
                                            else{
                                                int p;
                                                char sn; 
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
                                                        customerName = lectura.next(); 
                                                        System.out.print("Ingrese el primer apellido del cliente: "); 
                                                        customerLastName = lectura.next(); 
                                                        do{
                                                            System.out.print("Ingrese su e-mail (debe contener @): "); 
                                                            email = lectura.next(); 
                                                        }while(!email.contains("@")); 
                                                        System.out.print("Ingrese su numero telefonico (10 dígitos): "); 
                                                        numcel = lectura.next(); 
                                                        CustomerData nuevoC = new CustomerData(customerName, customerLastName, email, numcel, 0); 
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
                                                    nuevaR = new Reservacion(dia, mes, yAux, 0, auxP.getCostoBase()+p*auxP.getCostoVariable(), matricula); 
                                                }
                                                else{
                                                    nuevaR = new Reservacion(dia, mes, yAux, ultimaR.getClave()+1, auxP.getCostoBase()+p*auxP.getCostoVariable(), matricula);
                                                } 
                                                if(nuevaR.costoEstimado()){
                                                    auxR = primerR; 
                                                    while(auxR!=null){
                                                        if(auxR.getMes()==nuevaR.getMes() && auxR.getDia()==nuevaR.getDia()){
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
                                            for(int cont = 0; cont<180; cont++){
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
                                            clave = lectura.nextInt(); 
                                            auxR = primerR; 
                                            while(auxR!=null){
                                                if(auxR.getClave()==clave){
                                                    System.out.print("¿Seguro que desea eliminar su reservación? "); 
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
                                                        System.out.println("Reservación eliminada con éxito"); 
                                                    }
                                                }
                                                else{
                                                    auxR = auxR.getSiguiente(); 
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
