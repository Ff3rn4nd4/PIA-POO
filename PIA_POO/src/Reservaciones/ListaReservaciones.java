
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
import pia_poo.Excepciones;

public class ListaReservaciones {
    private Reservacion primero, ultimo; 
    private int calendario[]; 
    private int claveMayor;
    private int hoyD, hoyM, hoyY;
    private LocalDate hoy = LocalDate.now();
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
                nuevo = new Reservacion(leerArchivo.nextInt(), leerArchivo.nextInt(), leerArchivo.nextInt(), leerArchivo.nextInt(), leerArchivo.nextFloat(),
                        leerArchivo.nextInt(), leerArchivo.nextInt()); 
                if(nuevo.getClave()==getClaveMayor()){
                    setClaveMayor(nuevo.getClave()+1); 
                }
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
        char sn; 
        Excepciones excep = new Excepciones(); 
        System.out.println("Ingrese una fecha para su reservaci??n"); 
        System.out.print("A??o: ");
        y = excep.leerInt(getHoyY(), getHoyY()+1); 
        System.out.print("Mes (d??gito): "); 
        m = excep.leerInt(1, 12); 
        System.out.print("D??a: "); 
        d = excep.leerInt(1, getCalendario()[m-1]); 
        if(fechaValida(d, m, y)){
            Paquete auxP = listaP.buscar(); 
            if(auxP!=null){
                int p; 
                System.out.println("??Cu??ntas personas asistir??n a su evento?"); 
                p = excep.leerInt(1, 100); 
                CustomerData auxC = listaC.buscar(); 
                if(auxC!=null){
                    Reservacion nueva = null; 
                    if(getPrimero()==null){
                        nueva = new Reservacion(d, m, y, 0, auxP.getCostoBase()+p*auxP.getCostoVariable(), auxC.getMatricula(), p);  
                        System.out.println("Su total es: $"+nueva.getCosto()); 
                        System.out.println("??Acepta su total?\nS--S??\nN--No");
                        sn = excep.leerChar('S', 'N'); 
                        if(sn=='S'){
                            setPrimero(nueva); 
                            setUltimo(nueva);
                            System.out.println("Reservaci??n almacenada con ??xito\nSu clave de reservaci??n es: "+nueva.getClave());
                        }
                        else{
                            nueva = null;
                            System.out.println("Reservaci??n cancelada"); 
                        }
                    }
                    else if(!reservada(d, m, y)){
                        nueva = new Reservacion(d, m, y, getClaveMayor(), auxP.getCostoBase()+p*auxP.getCostoVariable(), auxC.getMatricula(), p); 
                        System.out.println("Su total es: $"+nueva.getCosto()); 
                        System.out.println("??Acepta su total?\nS--S??\nN--No");
                        sn = excep.leerChar('S', 'N');
                        if(sn=='S'){
                            setClaveMayor(getClaveMayor()+1);
                            if(nueva.getYear()<getPrimero().getYear() || 
                                    (nueva.getYear()==getPrimero().getYear() && nueva.getMonth()<getPrimero().getMonth()) ||
                                    (nueva.getYear()==getPrimero().getYear() && nueva.getMonth()==getPrimero().getMonth() && nueva.getDay()<getPrimero().getDay())){
                                nueva.setSiguiente(getPrimero()); 
                                getPrimero().setAnterior(nueva);
                                setPrimero(nueva);
                            }
                            else if(nueva.getYear()>getUltimo().getYear() ||
                                    (nueva.getYear()==getUltimo().getYear() && nueva.getMonth()>getUltimo().getMonth()) ||
                                    (nueva.getYear()== getUltimo().getYear() && nueva.getMonth()==getUltimo().getMonth() && nueva.getDay()>getUltimo().getDay())){
                                nueva.setAnterior(getUltimo()); 
                                getUltimo().setSiguiente(nueva);
                                setUltimo(nueva);
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
                            System.out.println("Reservaci??n almacenada con ??xito\nSu clave de reservaci??n es: "+nueva.getClave());
                        }
                        else{
                            nueva = null;
                            System.out.println("Reservaci??n cancelada"); 
                        }
                    }
                    else{
                        System.out.println("La fecha ya est?? reservada"); 
                    }
                     
                }
            }
        }
    }
    public boolean fechaValida(int d, int m, int y){
        int futuroD = getHoyD()+180, futuroM = getHoyM(), futuroY=getHoyY(); 
        
        
        while(futuroD>getCalendario()[futuroM-1]){
            futuroD -= getCalendario()[futuroM-1]; 
            futuroM++; 
            if(futuroM>12){
                futuroM = 1; 
                futuroY++; 
            }
        }
        if(y<getHoyY() || (y== getHoyY() && m<getHoyM()) || (y == getHoyY() && m == getHoyM() && d < getHoyD())){
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
            if(cont==10){
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
        System.out.println("Eliminar Reservaci??n"); 
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
            System.out.println("Reservaci??n eliminada con ??xito"); 
        }
    }
    public Reservacion buscar(){
        Reservacion auxR = getPrimero(); 
        System.out.print("Ingrese la clave de la reservaci??n que desea buscar: ");
        Excepciones excep = new Excepciones(); 
        int clave = excep.leerInt(); 
        while(auxR!=null){
            if(auxR.getClave()==clave){
                return auxR; 
            }
            else{
                auxR = auxR.getSiguiente(); 
            }
        }
        System.out.println("La reservaci??n #"+clave+" no fue encontrada"); 
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
                    System.out.println("Matr??cula: "+auxC.getMatricula()+"\n"); 
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
