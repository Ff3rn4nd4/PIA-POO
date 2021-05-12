package pia;

public class customerdata {
    
    private String customerName; 
    private String email;
    private String numcel;
    private int matricula;

    public customerdata(String customerName, String email,String numcel){
        this.customerName = customerName; 
        this.email = email; 
        this.numcel = numcel;  
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
    
}
