package pia_lab_poo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Paquete {
    private float costoBase, costoVariable; 
    private int num; 
    private Paquete anterior, siguiente; 
    public Paquete(int num){
        Scanner leer = new Scanner(System.in); 
        do{
            System.out.print("Ingrese el costo base: "); 
            this.costoBase = leer.nextFloat();
        }while(this.costoBase<0);
        do{
            System.out.print("Ingrese el costo por persona: "); 
            this.costoVariable = leer.nextFloat(); 
        }while(this.costoVariable <0); 
        this.num = num; 
        this.anterior = null; 
        this.siguiente = null; 
    }
    public Paquete(float cBase, float cVariable, int num){
        this.costoBase = cBase; 
        this.costoVariable = cVariable; 
        this.num = num; 
        anterior = siguiente = null; 
    }
    public float getCostoBase() {
        return costoBase;
    }
    public void setCostoBase(float costoBase) {
        this.costoBase = costoBase;
    }
    public float getCostoVariable() {
        return costoVariable;
    }
    public void setCostoVariable(float costoVariable) {
        this.costoVariable = costoVariable;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void escribir(){
        try{
            FileWriter escritor = new FileWriter("paquetes.txt", true);
            escritor.write(getCostoBase()+" "+getCostoVariable()+" "+getNum()+" ");  
            escritor.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public Paquete getAnterior() {
        return anterior;
    }
    public void setAnterior(Paquete anterior) {
        this.anterior = anterior;
    }
    public Paquete getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Paquete siguiente) {
        this.siguiente = siguiente;
    }
    public void imprimir(){
        System.out.println("Paquete #"+getNum()); 
        System.out.println("Costo Base: "+getCostoBase()+" Costo por persona: "+getCostoVariable()+"\n"); 
    }
}