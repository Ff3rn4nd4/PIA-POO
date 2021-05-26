package pia_poo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Excepciones {
    public Scanner leer; 
    public Excepciones(){
        leer = new Scanner(System.in); 
    }
    public int leerInt(){
        int opc = 0; 
        boolean error; 
        do{
            try{
                opc = leer.nextInt(); 
                error = false; 
            }
            catch(InputMismatchException err){
                leer.next(); 
                System.out.print("Entrada inválida, por favor ingrese un valor adecuado: "); 
                error = true; 
            }
        }while(error); 
        return opc; 
    }
    public int leerInt(int min, int max){
        int opc = 0; 
        boolean error; 
        do{
            try{
                opc = leer.nextInt(); 
                while(opc<min || opc>max){
                    System.out.print("Ingrese un valor entre "+min+" y "+max+": "); 
                    opc = leer.nextInt(); 
                }
                error = false;
            }
            catch(InputMismatchException err){
                leer.next();
                System.out.print("Entrada inválida, por favor ingrese un valor entre "+min+" y "+max+": ");
                error = true; 
            }
        }while(error); 
        return opc;  
    }
    public char leerChar(char c1, char c2){
        char c = 0; 
        boolean error; 
        do{
            try{
                c = leer.next().charAt(0);
                c = Character.toUpperCase(c); 
                while(c!=c1 && c!= c2){
                    System.out.print("Entrada inválida, por favor ingrese '"+c1+"' o '"+c2+"': ");
                    c = leer.next().charAt(0);
                    c = Character.toUpperCase(c);
                }
                error = false; 
            }
            catch(InputMismatchException err){
                leer.next(); 
                System.out.print("Entrada inválida, por favor ingrese '"+c1+"' o '"+c2+"': "); 
                error = true; 
            }
        }while(error); 
        return c;
    }
    public float leerFloat(){
        float f = 0; 
        boolean error; 
        do{
            try{
                f = leer.nextFloat(); 
                while(f<0){
                    System.out.print("Ingrese un valor mayor o igual a 0: "); 
                    f = leer.nextFloat(); 
                }
                error = false; 
            }
            catch(InputMismatchException err){
                leer.next();
                System.out.print("Entrada inválida, ingrese un valor mayor o igual a 0: ");
                error = true; 
            }
        }while(error); 
        return f; 
    }
}
