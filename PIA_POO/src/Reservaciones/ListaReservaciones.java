
package Reservaciones;

import Clientes.CustomerData;
import Clientes.ListaClientes;
import Paquetes.ListaPaquetes;
import Paquetes.Paquete;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ListaReservaciones {
    private Reservacion primero, ultimo; 
    private int calendario[]; 
    private int claveMayor;
    private int hoyD, hoyM, hoyY;
    private LocalDate hoy = LocalDate.now();
    @SuppressWarnings("empty-statement")
    public ListaReservaciones(){
        this.calendario = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        this.primero = null; 
        this.ultimo = null;
        this.claveMayor = 1;  
        this.hoyD = hoy.getDayOfMonth(); 
        this.hoyM = hoy.getMonthValue(); 
        this.hoyY = hoy.getYear(); 
    }
    public Reservacion getPrimero() {
        return primero;
    }
    public void setPrimero(Reservacion primero) {
        this.primero = primero;
    }
    public Reservacion getUltimo() {
        return ultimo;
    }
    public void setUltimo(Reservacion ultimo) {
        this.ultimo = ultimo;
    }
    public int getClaveMayor(){
        return claveMayor; 
    }
    public void setClaveMayor(int claveMayor){
        this.claveMayor = claveMayor; 
    }
    public void leerArchivo(){
        try{
            File archivo = new File("reservaciones.txt"); 
            archivo.createNewFile(); 
            Scanner leerArchivo = new Scanner(archivo); 
            Reservacion nuevo = null; 
            while(leerArchivo.hasNext()){
                int day = leerArchivo.nextInt(); 
                int month = leerArchivo.nextInt(); 
                int year = leerArchivo.nextInt(); 
                int clave = leerArchivo.nextInt(); 
                if(clave>getClaveMayor()){
                    setClaveMayor(clave+1); 
                }
                float costo = leerArchivo.nextFloat(); 
                int matricula = leerArchivo.nextInt(); 
                int personas = leerArchivo.nextInt();
                nuevo = new Reservacion(day, month, year, clave, costo, matricula, personas); 
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
            e.printStackTrace();
        }
    }
    public void crear(ListaPaquetes listaP, ListaClientes listaC){
        
        int d, m, y; 
        Scanner leer = new Scanner(System.in); 
        System.out.println("Ingrese una fecha para su reservación"); 
        System.out.print("Año: ");
        y = leer.nextInt(); 
        System.out.print("Mes (dígito): "); 
        m = leer.nextInt(); 
        System.out.print("Día: "); 
        d = leer.nextInt();
        if(fechaValida(d, m, y)){
            Paquete auxP = listaP.buscar(); 
            if(auxP!=null){
                int p; 
                do{
                    System.out.println("¿Cuántas personas asistirán a su evento?"); 
                    p = leer.nextInt(); 
                }while(p<1 || p>100); 
                CustomerData auxC = listaC.buscar(); 
                if(auxC!=null){
                    Reservacion nueva = null; 
                    if(getPrimero()==null){
                        nueva = new Reservacion(d, m, y, 0, auxP.getCostoBase()+p*auxP.getCostoVariable(), auxC.getMatricula(), p);  
                        setPrimero(nueva); 
                        setUltimo(nueva);
                        System.out.println("Reservación almacenada con éxito\nSu clave de reservación es: "+nueva.getClave());
                    }
                    else if(!reservada(d, m, y)){
                        nueva = new Reservacion(d, m, y, getClaveMayor(), auxP.getCostoBase()+p*auxP.getCostoVariable(), auxC.getMatricula(), p); 
                        setClaveMayor(getClaveMayor()+1);
                        if(nueva.getYear()<getPrimero().getYear() || 
                                (nueva.getYear()==getPrimero().getYear() && nueva.getMonth()<getPrimero().getMonth()) ||
                                (nueva.getYear()==getPrimero().getYear() && nueva.getMonth()==getPrimero().getMonth() && nueva.getDay()<getPrimero().getDay())){
                            nueva.setSiguiente(getPrimero()); 
                            getPrimero().setAnterior(nueva);
                            setPrimero(nueva); 
                            System.out.println("Reservación almacenada con éxito\nSu clave de reservación es: "+nueva.getClave());
                        }
                        else if(nueva.getYear()>getPrimero().getYear() ||
                                (nueva.getYear()==getPrimero().getYear() && nueva.getMonth()>getPrimero().getMonth()) ||
                                (nueva.getYear()== getPrimero().getYear() && nueva.getMonth()==getPrimero().getMonth() && nueva.getDay()>getPrimero().getDay())){
                            nueva.setAnterior(getUltimo()); 
                            getUltimo().setSiguiente(nueva);
                            setUltimo(nueva); 
                            System.out.println("Reservación almacenada con éxito\nSu clave de reservación es: "+nueva.getClave());
                        }
                        else{
                            Reservacion aux = getPrimero(); 
                            while(aux!=null){
                                if(nueva.getYear()<aux.getYear() || (nueva.getYear()==aux.getYear() && nueva.getMonth()<aux.getMonth()) 
                                        || (nueva.getYear()==aux.getYear() && nueva.getMonth()==aux.getMonth() && nueva.getDay() < aux.getDay())){
                                    aux.getAnterior().setSiguiente(nueva);
                                    nueva.setAnterior(aux.getAnterior()); 
                                    nueva.setSiguiente(aux); 
                                    aux.setAnterior(nueva);
                                    break; 
                                }
                                else{
                                    aux = aux.getSiguiente(); 
                                }
                            }
                        }
                        System.out.println("Reservación almacenada con éxito\nSu clave de reservación es: "+nueva.getClave());
                    }
                    else{
                        System.out.println("La fecha ya está reservada"); 
                    }
                     
                }
            }
        }
    }
    public boolean fechaValida(int d, int m, int y){
        int futuroD = getHoyD()+180, futuroM = getHoyM(), futuroY=getHoyY(); 
        Scanner leer = new Scanner(System.in); 
        
        
        while(futuroD>getCalendario()[futuroM-1]){
            futuroD -= getCalendario()[futuroM-1]; 
            futuroM++; 
            if(futuroM>12){
                futuroM = 1; 
                futuroY++; 
            }
        }
        if(m<1 || m>12 || d<1 || d>getCalendario()[m-1]){
            System.out.println("Día o Mes incorrectos"); 
            return false; 
        }
        else if(y<getHoyY() || (y== getHoyY() && m<getHoyM()) || (y == getHoyY() && m == getHoyM() && d < getHoyD())){
            System.out.println("La fecha ingresada es anterior a la fecha actual, no se puede registrar");
            return false; 
        }
        else if(y>futuroY || (y==futuroY && m>futuroM) || (y==futuroY && m==futuroM && d>futuroD)){
            System.out.println("La fecha ingresada es mayor a seis meses en el futuro, no se puede registrar"); 
            return false; 
        }
        else{
            return true; 
        }
    }
    public boolean reservada(int d, int m, int y){
        Reservacion aux = getPrimero(); 
        while(aux!=null){
            if(aux.getDay()==d && aux.getMonth()==m && aux.getYear()==y){
                return true; 
            }
            else{
                aux = aux.getSiguiente(); 
            }
        }
        return false; 
    }
    public void fechasDisponibles(){
        int dAux = getHoyD(), mAux = getHoyM(), yAux = getHoyY(), cont=0; 
        Reservacion aux = getPrimero(); 
        for(int i=0; i<=180; i++){
            if(aux!=null){
                if(aux.getDay()!=dAux || aux.getMonth()!=mAux || aux.getYear()!=yAux){
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
                    aux = aux.getSiguiente(); 
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
            cont++; 
            if(cont>10){
                System.out.print("\n"); 
                cont = 0; 
            }
            dAux++;
            if(dAux>getCalendario()[mAux-1]){
                dAux=1; 
                mAux++; 
                if(mAux>12){
                    mAux=1; 
                    yAux++; 
                }
            }
        }
    }
    public int[] getCalendario() {
        return calendario;
    }
    public void setCalendario(int[] calendario) {
        this.calendario = calendario;
    }
    public int getHoyD() {
        return hoyD;
    }
    public void setHoyD(int hoyD) {
        this.hoyD = hoyD;
    }
    public int getHoyM() {
        return hoyM;
    }
    public void setHoyM(int hoyM) {
        this.hoyM = hoyM;
    }
    public int getHoyY() {
        return hoyY;
    }
    public void setHoyY(int hoyY) {
        this.hoyY = hoyY;
    }
    public LocalDate getHoy() {
        return hoy;
    }
    public void setHoy(LocalDate hoy) {
        this.hoy = hoy;
    }
    public void eliminar(){
        System.out.println("Eliminar Reservación"); 
        Reservacion aux = buscar(); 
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
            System.out.println("Reservación eliminada con éxito"); 
        }
    }
    public Reservacion buscar(){
        Reservacion auxR = getPrimero(); 
        System.out.print("Ingrese la clave de la reservación que desea buscar: "); 
        Scanner leer = new Scanner(System.in); 
        int clave = leer.nextInt(); 
        while(auxR!=null){
            if(auxR.getClave()==clave){
                return auxR; 
            }
            else{
                auxR = auxR.getSiguiente(); 
            }
        }
        System.out.println("La reservación #"+clave+" no fue encontrada"); 
        return null;
    }
    public void mostrarLista(ListaClientes listaC){
        System.out.println("Lista de Reservaciones");  
        Reservacion auxR = getPrimero(); 
        CustomerData auxC = null; 
        if(auxR!=null){
            while(auxR!=null){
                auxR.imprimir();
                auxC=listaC.buscar(auxR.getCliente()); 
                if(auxC!=null){
                    System.out.println("Cliente: "+auxC.getCustomerName()+" "+auxC.getCustomerLastName()); 
                    System.out.println("Matrícula: "+auxC.getMatricula()+"\n"); 
                }
                auxR = auxR.getSiguiente();
            }
        }
        else{
            System.out.println("No hay reservaciones registradas en el sistema"); 
        }
    }
    public void almacenar(){
        try{
            Reservacion aux = getPrimero(); 
            File archivo = new File("reservaciones.txt"); 
            FileWriter escritor = new FileWriter(archivo); 
            while(aux!=null){
                escritor.write(aux.getDay()+" "+aux.getMonth()+" "+aux.getYear()+" "+aux.getClave()+" "+
                        aux.getCosto()+" "+aux.getCliente()+" "+aux.getAsistentes()+" ");  
                aux = aux.getSiguiente(); 
            }
            escritor.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
