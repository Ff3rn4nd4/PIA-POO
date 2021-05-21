package Clientes;

public class CustomerData {
    
    private String customerName; 
    private String customerLastName; 
    private String email;
    private String numcel;
    private int matricula;
    private CustomerData anterior; 
    private CustomerData siguiente; 

    public CustomerData(String customerName, String lastName, String email, String numcel, int matricula){
        this.customerName = customerName; 
        this.customerLastName =lastName; 
        this.email = email; 
        this.numcel = numcel;  
        this.matricula= matricula; 
        this.anterior=null; 
        this.siguiente = null; 
    }
                
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumcel() {
        return numcel;
    }

    public void setNumcel(String numcel) {
        this.numcel = numcel;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public CustomerData getAnterior() {
        return anterior;
    }
    public void setAnterior(CustomerData anterior) {
        this.anterior = anterior;
    }
    public CustomerData getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(CustomerData siguiente) {
        this.siguiente = siguiente;
    }
    public void imprimir(){
        System.out.println("Nombre: "+getCustomerName()+" "+getCustomerLastName()); 
        System.out.println("E-mail: "+getEmail()); 
        System.out.println("Número telefónico: "+getNumcel()); 
        System.out.println("Matrícula: "+getMatricula()+"\n");   
    }
    
}