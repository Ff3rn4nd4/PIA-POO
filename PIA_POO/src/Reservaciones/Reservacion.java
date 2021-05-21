package Reservaciones;
import java.util.Scanner;

public class Reservacion {
    private int day, month, year, clave, cliente, asistentes; 
    private float costo; 
    private Reservacion anterior; 
    private Reservacion siguiente; 
    public Reservacion(int dd, int mm, int yy, int clave, int cliente, int asistentes){
        this.day = dd; 
        this.month = mm; 
        this.year = yy; 
        this.clave = clave; 
        this.costo = 0; 
        this.cliente = cliente; 
        this.asistentes = asistentes; 
        this.anterior = null; 
        this.siguiente = null; 
    }
    public Reservacion(int dd, int mm, int yy, int clave, float costo, int cliente, int asistentes){
        this.day = dd; 
        this.month = mm; 
        this.year = yy; 
        this.clave = clave; 
        this.costo = costo; 
        this.cliente = cliente; 
        this.asistentes = asistentes; 
        this.anterior = null; 
        this.siguiente = null; 
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
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
    
    public int getAsistentes() {
        return asistentes;
    }
    public void setAsistentes(int asistentes) {
        this.asistentes = asistentes;
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
        return sn == 's' || sn =='S';
    }
    public void imprimir(){
        System.out.println("Fecha: "+getDay()+"/"+getMonth()+"/"+getYear()); 
        System.out.println("Costo: "+getCosto());
        System.out.println("Asistentes: "+getAsistentes()); 
        System.out.println("Clave de reservación: "+getClave());
    }
    public float calcularCosto(float cBase, float cVar, int p){
        return cBase+cVar*p; 
    }
}
