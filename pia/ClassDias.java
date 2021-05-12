package pia;
 
import java.util.Scanner;
 
public class ClassDias {
 
    public int[][] metLLenar(int c[][]) {
        Scanner kb = new Scanner(System.in);
        int fs, cs, fsr, csr;
        System.out.print("Día: ");
        fs = kb.nextInt();
        System.out.print("Mes: ");
        cs = kb.nextInt();
        fsr = fs - 1;
        csr = cs - 1;
        if (c[fsr][csr] == 1) {
            System.out.println("Ese día no esta disponible");
        } else {
            c[fsr][csr] = 1;
        }
        return c;
    }
 
    public int[][] metEli(int d[][]) {
        Scanner kb = new Scanner(System.in);
        int fs, cs, fsr, csr;
        System.out.print("Seleccione día a eliminar;\nDía: ");
        fs = kb.nextInt();
        System.out.print("Mes: ");
        cs = kb.nextInt();
        fsr = fs - 1;
        csr = cs - 1;
        d[fsr][csr] = 0;
        return d;
    }
    
 
    public void metImp(int a[][]) {
        
        System.out.print("\t\t\tCalendario\n");
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j <31; j++) {
                System.out.print(a[j][i]+" ");
            }
            System.out.print("\n");
        }
    }
}