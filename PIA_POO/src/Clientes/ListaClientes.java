
package Clientes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import pia_poo.Excepciones;

public class ListaClientes {
    private CustomerData primero, ultimo; 
    public ListaClientes(){
        this.primero = null; 
        this.ultimo = null; 
    }
    public CustomerData getPrimero() {
        return primero;
    }
    public void setPrimero(CustomerData primero) {
        this.primero = primero;
    }
    public CustomerData getUltimo() {
        return ultimo;
    }
    public void setUltimo(CustomerData ultimo) {
        this.ultimo = ultimo;
    }
    public CustomerData buscar(){
        CustomerData aux = getPrimero(); 
        int matricula; 
        Excepciones excep = new Excepciones(); 
        System.out.print("Ingrese la matrícula del cliente que desea buscar: "); 
        matricula = excep.leerInt(); 
        while(aux!=null){
            if(aux.getMatricula()==matricula){
                return aux; 
            }
            else{
                aux = aux.getSiguiente(); 
            }
        }
        System.out.println("No se encontró al cliente con matrícula "+matricula); 
        return null; 
    }
    public CustomerData buscar(int matricula){
        CustomerData aux = getPrimero();
        while(aux!=null){
            if(aux.getMatricula()==matricula){
                return aux; 
            }
            else{
                aux = aux.getSiguiente(); 
            }
        }
        return null; 
    }
    public void crear(){
        System.out.println("Crear Cliente");
        Scanner leer = new Scanner(System.in); 
        System.out.print("Ingrese el primer nombre del cliente: "); 
        String customerName = leer.next();
        System.out.print("Ingrese el primer apellido del cliente: "); 
        String customerLastName = leer.next();
        String email;
        do{
            System.out.print("Ingrese un e-mail válido: "); 
            email = leer.next();
        }while(!email.contains("@") || !email.contains(".com"));
        System.out.print("Ingrese su numero telefonico (10 dígitos): "); 
        String numcel = leer.next();
        while(numcel.length()!=10 || !numcel.matches("[0-9]+")){
            System.out.print("Ingrese un número telefónico válido: "); 
            numcel = leer.next();
        }
        CustomerData nuevo; 
        if(getPrimero()==null){
            nuevo = new CustomerData(customerName, customerLastName, email, numcel, 0); 
            setPrimero(nuevo); 
            setUltimo(nuevo); 
        }
        else{
            nuevo = new CustomerData(customerName, customerLastName, email, numcel, getUltimo().getMatricula()+1); 
            getUltimo().setSiguiente(nuevo);
            nuevo.setAnterior(getUltimo()); 
            setUltimo(nuevo); 
        }
        System.out.println("Cliente registrado con éxito");
        System.out.println("Su matrícula es: "+nuevo.getMatricula()+"\n"); 
    }
    public void eliminar(){
        System.out.println("Eliminar Cliente"); 
        CustomerData aux = buscar();
        if(aux!=null){
            if(aux==getPrimero()){
                setPrimero(aux.getSiguiente()); 
            }
            else{
                aux.getAnterior().setSiguiente(aux.getSiguiente()); 
            }
            if(aux==getUltimo()){
                setUltimo(aux.getAnterior()); 
            }
            else{
                aux.getSiguiente().setAnterior(aux.getAnterior()); 
            }
            System.out.println("Cliente eliminado con éxito"); 
        }
    }
    public void mostrarLista(){
        System.out.println("Lista de Clientes"); 
        CustomerData aux = getPrimero(); 
        if(aux!=null){
            while(aux!=null){
                aux.imprimir();
                aux = aux.getSiguiente(); 
            }
        }
        else{
            System.out.println("No hay clientes registrados en el sistema"); 
        }
    }
    public void leerArchivo(){
        try{
            File archivo = new File("clientes.txt"); 
            archivo.createNewFile(); 
            Scanner leerArchivo = new Scanner(archivo);
            CustomerData nuevo = null; 
            while(leerArchivo.hasNext()){
                nuevo = new CustomerData(leerArchivo.next(), leerArchivo.next(), leerArchivo.next(), leerArchivo.next(), leerArchivo.nextInt());   
                if(getPrimero()==null){
                    setPrimero(nuevo); 
                    setUltimo(nuevo); 
                }
                else{
                    getUltimo().setSiguiente(nuevo);
                    nuevo.setAnterior(getUltimo()); 
                    setUltimo(nuevo); 
                }
            }
        }
        catch(IOException e){
        }
    }
    public void almacenar(){
        
        try{
            CustomerData aux = getPrimero(); 
            File archivo = new File("clientes.txt"); 
            FileWriter escritor = new FileWriter(archivo); 
            while(aux!=null){
                escritor.write(aux.getCustomerName()+" "+aux.getCustomerLastName()+" "+aux.getEmail()+" "+aux.getNumcel()+" "+aux.getMatricula()+" ");  
                aux = aux.getSiguiente(); 
            }
            escritor.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void actualizarDatos(){
        String email, numcel;
        CustomerData aux = buscar(); 
        Excepciones excep = new Excepciones(); 
        if(aux!=null){
            aux.imprimir();
            do{
                System.out.print("Ingrese un nuevo e-mail válido: "); 
                email = excep.leerString(); 
            }while(!email.contains("@") || !email.contains(".com"));
            do{
                System.out.print("Ingrese un nuevo número telefónico válido: "); 
                numcel = excep.leerString(); 
            }while(numcel.length()!=10 || !numcel.matches("[0-9]+")); 
            aux.setEmail(email);
            aux.setNumcel(numcel);
            System.out.println("Datos actualizados con éxito\n"); 
        }
    }
}