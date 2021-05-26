package Usuarios;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import pia_poo.Excepciones;

public class Jefe extends Usuario{
    public Jefe(String userName, String password, boolean activo) {
        super(userName, password, activo);
    }
    public Jefe(String userName, String password){
        super(userName, password); 
    }
    @Override
    public String nivel(){
        return "Jefe"; 
    }
    @Override
    public void crearUsuario(){
        int aux; 
        String us, usAux, pwAux;
        int clase; 
        boolean existe = false, estado;
        Excepciones excep = new Excepciones(); 
        Console consola = System.console(); 
        System.out.println("Crear Nuevo Usuario"); 
        System.out.println("Seleccione el tipo de usuario que desea crear: "); 
        System.out.println("0--Jefe\n1--Gerente\n2--Empleado\n3--Ninguno"); 
        aux = excep.leerInt(0, 3); 
        if(aux !=3){
            System.out.print("Ingrese el nombre de usuario: "); 
            us = excep.leerString();
            while(us.length()<5){
                System.out.print("Nombre de usuario muy corto, ingrese otro nombre de usuario (mínimo 5 caracteres): "); 
                us = excep.leerString(); 
            }
            try{
                File archivo = new File("usuarios.txt"); 
                Scanner leerArchivo = new Scanner(archivo);
                FileWriter escritor = new FileWriter(archivo, true); 
                while(leerArchivo.hasNext()&&existe==false){
                    if(us.equals(leerArchivo.next())){
                        existe=true; 
                    }
                    else{
                        leerArchivo.next(); 
                        leerArchivo.nextBoolean(); 
                        leerArchivo.nextInt(); 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    char[] pw = consola.readPassword(); 
                    while((new String(pw)).contains(us)){
                        System.out.print("El nombre de usuario y la contraseña no pueden ser iguales, ingrese una contraseña distinta: "); 
                        pw = consola.readPassword(); 
                    }
                    while(pw.length<5){
                        System.out.print("Contraseña muy corta, ingrese una contraseña distinta (mínimo 5 caracteres)"); 
                        pw = consola.readPassword(); 
                    }
                    escritor.write(us+" "+(new String(pw))+" "+true+" "+aux+" \n");
                    System.out.println("Usuario generado con éxito"); 
                }
                else{
                    System.out.println("El usuario ya existe"); 
                }
                escritor.close(); 
            }
            catch(IOException e){
                System.out.println("Ocurrió un error al crear el nuevo usuario"); 
                e.printStackTrace();
            }
        }
    }
    @Override
    public void eliminarUsuario(){
        String linea; 
        String eliminar; 
        boolean encontrado = false;
        Excepciones excep = new Excepciones(); 
        char sn; 
        System.out.println("Eliminar Usuarios"); 
        try{
            File f = new File("usuarios.txt"); 
            Scanner buffer = new Scanner(f); 
            File aux = new File("auxiliar.txt"); 
            aux.createNewFile(); 
            FileWriter escritor = new FileWriter(aux); 
            Scanner copiar = new Scanner(aux);
            System.out.print("¿Qué usuario desea eliminar? "); 
            eliminar = excep.leerString(); 
            if(eliminar.equals(getUserName())){
                System.out.println("No se puede eliminar al usuario activo"); 
            }
            while(buffer.hasNext()){
                linea = buffer.nextLine(); 
                if(linea.contains(eliminar) && !linea.contains(getUserName())){
                    System.out.println("¿Desea cambiar el estado del usuario?"); 
                    System.out.println("S--Si\nN--No");
                    sn = excep.leerChar('S', 'N'); 
                    if(sn=='S'){
                        System.out.println("Usuario eliminado");
                    }
                    else{
                        System.out.println("No se eliminó al usuario");
                        escritor.write(linea+"\n");
                    }
                    encontrado = true; 
                }
                else{
                    escritor.write(linea+"\n");
                }
            }
            escritor.close();
            FileWriter pegar = new FileWriter(f); 
            while(copiar.hasNext()){
                linea = copiar.nextLine(); 
                pegar.write(linea+"\n"); 
            }
            copiar.close();
            pegar.close();
            aux.delete(); 
            if(!encontrado){
                System.out.println("No se encontró al usuario \""+eliminar+"\""); 
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void cambiarEstado(){
        String us;
        String usAux, pwAux; 
        boolean activo, encontrado = false; 
        int categoria; 
        char sn; 
        Excepciones excep = new Excepciones(); 
        try{
            File f = new File("usuarios.txt"); 
            Scanner buffer = new Scanner(f); 
            File aux = new File("auxiliar.txt"); 
            aux.createNewFile(); 
            FileWriter escritor = new FileWriter(aux); 
            Scanner copiar = new Scanner(aux); 
            System.out.print("Ingrese el nombre del usuario que desea modificar: ");  
            us = excep.leerString(); 
            if(us.equals(getUserName())){
                System.out.println("No se puede eliminar al usuario activo"); 
            }
            else{
                while(buffer.hasNext()){
                    usAux = buffer.next(); 
                    pwAux = buffer.next(); 
                    activo = buffer.nextBoolean(); 
                    categoria = buffer.nextInt();
                    if(!usAux.equals(us)){
                        escritor.write(usAux+" "+pwAux+" "+activo+" "+categoria+" \n");  
                    }
                    else{
                        System.out.print("Usuario: "+usAux); 
                        System.out.print("Estado: "); 
                        if(activo){
                            System.out.println("Activo"); 
                        }
                        else{
                            System.out.println("Inactivo"); 
                        }
                        System.out.println("¿Desea cambiar el estado del usuario?"); 
                        System.out.println("S--Si\nN--No"); 
                        sn = excep.leerChar('S', 'N'); 
                        if(sn=='S'){
                            activo = !activo; 
                            System.out.println("Usuario modificado con éxito"); 
                        }
                        escritor.write(usAux+" "+pwAux+" "+activo+" "+categoria+" \n");
                        encontrado = true; 
                    }
                }
                escritor.close();
                FileWriter pegar = new FileWriter(f); 
                String linea; 
                while(copiar.hasNext()){
                    linea = copiar.nextLine(); 
                    pegar.write(linea+"\n"); 
                }
                if(!encontrado){
                    System.out.println("No se encontró al usuario \""+us+"\"");
                }
                copiar.close();
                pegar.close();
                aux.delete(); 
            }
        }
        catch(IOException e){
            e.printStackTrace();
        } 
    }
}
