package Paquetes;

public class Paquete {
    private float costoBase, costoVariable; 
    private int num; 
    private Paquete anterior, siguiente; 
    public Paquete(int num, float cBase, float cVariable){
        this.num = num; 
        this.costoBase = cBase; 
        this.costoVariable = cVariable; 
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
        System.out.println("Costo Base: "+getCostoBase()); 
        System.out.println("Costo Variable: "+getCostoVariable()+"\n");
    }
}