package pia_lab_poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Reservacion {
    private int dia, mes, year, clave, cliente; 
    private float costo; 
    private Reservacion anterior; 
    private Reservacion siguiente; 
    public Reservacion(int dd, int mm, int yy, int clave, float costo, int cliente){
        this.dia = dd; 
        this.mes = mm; 
        this.year = yy; 
        this.clave = clave; 
        this.costo = costo; 
        this.cliente = cliente; 
        this.anterior = null; 
        this.siguiente = null; 
    }
    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public Reservacion getAnterior() {
        return anterior;
    }
    public void setAnterior(Reservacion anterior) {
        this.anterior = anterior;
    }
    public Reservacion getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Reservacion siguiente) {
        this.siguiente = siguiente;
    }
    public int getClave() {
        return clave;
    }
    public void setClave(int clave) {
        this.clave = clave;
    }
    public float getCosto() {
        return costo;
    }
    public void setCosto(float costo) {
        this.costo = costo;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getCliente(){
        return cliente; 
    }
    public void setCliente(int cliente){
        this.cliente = cliente; 
    }
    public boolean costoEstimado(){
        char sn; 
        boolean aceptado; 
        System.out.println("Su costo estimado es "+getCosto());
        System.out.println("¿Esta de acuerdo con él?\nS--Sí\nN--No"); 
        Scanner leer = new Scanner(System.in); 
        sn = leer.next().charAt(0); 
        while(sn!='s' && sn != 'S' && sn != 'N' && sn != 'n'){
            System.out.println("Ingrese S o N"); 
            sn = leer.next().charAt(0); 
        }
        if(sn == 's' || sn =='S'){
            return  true; 
        }
        else{
            return false; 
        }
    }
    public void escribir(){
        try{
            FileWriter escritor = new FileWriter("reservaciones.txt", true); 
            escritor.write(getClave()+" "+getDia()+" "+getMes()+" "+getYear()+" "+getCosto()+" "+getCliente()+" "); 
            escritor.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void imprimir(){
        System.out.println("Fecha: "+getDia()+"/"+getMes()+"/"+getYear()); 
        System.out.println("Total: "+getCosto()); 
        System.out.println("Clave: "+getClave()); 
        System.out.println("Clave del cliente: "+getCliente());
    }
}
