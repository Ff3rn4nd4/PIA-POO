
package Paquetes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import pia_poo.Excepciones;

public class ListaPaquetes {
    private Paquete primero, ultimo; 
    public ListaPaquetes(){
        this.primero = null; 
        this.ultimo = null; 
    }
    public Paquete getPrimero() {
        return primero;
    }
    public void setPrimero(Paquete primero) {
        this.primero = primero;
    }
    public Paquete getUltimo() {
        return ultimo;
    }
    public void setUltimo(Paquete ultimo) {
        this.ultimo = ultimo;
    }
    public void leerArchivo(){
        try{
            File archivo = new File("paquetes.txt"); 
            archivo.createNewFile(); 
            Scanner leerArchivo = new Scanner(archivo);
            Paquete nuevo = null; 
            while(leerArchivo.hasNext()){
                nuevo = new Paquete(leerArchivo.nextInt(), leerArchivo.nextFloat(), leerArchivo.nextFloat());  
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
    public void crear(){
        float cBase, cVar;
        Paquete nuevo = null; 
        Excepciones excep = new Excepciones(); 
        System.out.println("CREAR PAQUETES"); 
        System.out.println("Un paquete consiste en el costo base y en el costo variable (por persona)"); 
        System.out.print("Ingrese el costo base del paquete a crear: "); 
        cBase = excep.leerFloat(); 
        System.out.print("Ingrese el costo variable del paquete a crear: "); 
        cVar = excep.leerFloat(); 
        if(getPrimero()==null){
            nuevo = new Paquete(1, cBase, cVar); 
            setPrimero(nuevo); 
            setUltimo(nuevo); 
        }
        else{
            nuevo = new Paquete(getUltimo().getNum()+1, cBase, cVar); 
            getUltimo().setSiguiente(nuevo);
            nuevo.setAnterior(getUltimo()); 
            setUltimo(nuevo); 
        }
    }
    public void mostrarLista(){
        System.out.println("Lista de Paquetes"); 
        Paquete aux = getPrimero(); 
        if(aux!=null){
            while(aux!=null){
                aux.imprimir();
                aux = aux.getSiguiente(); 
            }
        }
        else{
            System.out.println("No hay paquetes registrados en el sistema"); 
        }
    }
    public void eliminar(){
        System.out.println("Eliminar Paquete"); 
        Paquete aux = buscar();
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
            System.out.println("Paquete eliminado con ??xito"); 
        }
    }
    public Paquete buscar(){
        Paquete aux = getPrimero(); 
        System.out.print("Ingrese el # del paquete que desea buscar: ");
        Excepciones excep = new Excepciones(); 
        int num = excep.leerInt(); 
        while(aux!=null){
            if(aux.getNum()==num){
                return aux; 
            }
            else{
                aux = aux.getSiguiente(); 
            }
        }
        System.out.println("El paquete #"+num+" no fue encontrado"); 
        return null; 
    }
    public void almacenar(){
        try{
            Paquete aux = getPrimero(); 
            File archivo = new File("paquetes.txt"); 
            FileWriter escritor = new FileWriter(archivo); 
            while(aux!=null){
                escritor.write(aux.getNum()+" "+aux.getCostoBase()+" "+aux.getCostoVariable()+" "); 
                aux = aux.getSiguiente(); 
            }
            escritor.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void actualizarDatos(){
        Paquete aux = buscar(); 
        aux.imprimir();
        float cBase, cVar; 
        Excepciones excep = new Excepciones(); 
        System.out.print("Ingrese el nuevo costo base del paquete: "); 
        cBase = excep.leerFloat(); 
        System.out.print("Ingrese el nuevo costo variable del paquete: "); 
        cVar = excep.leerFloat(); 
        aux.setCostoBase(cBase);
        aux.setCostoVariable(cVar);
        System.out.println("Datos del paquete #"+aux.getNum()+" actualizados con ??xito"); 
    }
}
