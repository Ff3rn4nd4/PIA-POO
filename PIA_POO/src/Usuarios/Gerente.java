package Usuarios;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import pia_poo.Excepciones;

public class Gerente extends Usuario{
    public Gerente(String userName, String password, boolean estado) {
        super(userName, password, estado);
    } 
    @Override
    public String nivel(){
        return "Gerente"; 
    }
    @Override
    public void crearUsuario(){
        char sn; 
        String us, usAux, pw, pwAux; 
        int categoria; 
        boolean activo, existe = false; 
        Scanner leer = new Scanner(System.in); 
        Excepciones excep = new Excepciones(); 
        System.out.println("Crear Nuevo Usuario"); 
        System.out.println("Desea crear un nuevo usuario nivel empleado?"); 
        System.out.println("S--Si\nN--No\n"); 
        sn =  excep.leerChar('S', 'N');
        if(sn == 'S'){
            System.out.print("Ingrese un nombre de usuario: "); 
            us = leer.next(); 
            while(us.length()<5){
                System.out.print("Nombre de usuario muy corto, ingrese otro nombre de usuario (mínimo 5 caracteres): "); 
                us = leer.next(); 
            }
            try{
                File archivo = new File("usuarios.txt"); 
                Scanner leerArchivo = new Scanner(archivo);
                FileWriter escritor = new FileWriter(archivo, true); 
                while(leerArchivo.hasNext()){
                    usAux = leerArchivo.next(); 
                    pwAux = leerArchivo.next();
                    activo = leerArchivo.nextBoolean(); 
                    categoria = leerArchivo.nextInt(); 
                    if(us.equals(usAux)){
                        existe = true; 
                        break; 
                    }
                }
                if(!existe){
                    System.out.print("Ingrese una contraseña: "); 
                    pw = leer.next(); 
                    while(!pw.contains(us)){
                        System.out.print("La contraseña no puede contener al nombre de usuario, ingrese una contraseña distinta: "); 
                    }
                    while(pw.length()<5){
                        System.out.print("Contraseña muy corta, ingrese una contraseña distinta (mínimo 5 caracteres)"); 
                        pw = leer.next(); 
                    }
                    escritor.write(us+" "+pw+" "+true+" "+2+" \n"); 
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
        Scanner sc = new Scanner(System.in);
        boolean encontrado = false; 
        System.out.println("Eliminar Usuarios"); 
        try{
            File f = new File("usuarios.txt"); 
            Scanner buffer = new Scanner(f); 
            File aux = new File("auxiliar.txt"); 
            aux.createNewFile(); 
            FileWriter escritor = new FileWriter(aux); 
            Scanner copiar = new Scanner(aux); 
            
            
            System.out.print("¿Qué usuario desea eliminar? "); 
            eliminar = sc.next(); 
            if(eliminar.equals(getUserName())){
                System.out.println("No se puede eliminar al usuario activo"); 
            }
            while(buffer.hasNext()){
                linea = buffer.nextLine(); 
                if(linea.contains(eliminar) &&!linea.contains(getUserName())){
                    encontrado = true; 
                    if(linea.contains(" 2 ")){
                        System.out.println("Usuario eliminado");
                    }
                    else{
                        System.out.println("No se logró eliminar al usuario "+eliminar+" porque tiene mayor nivel"); 
                        escritor.write(linea+"\n");
                    }
                }
                else{
                    escritor.write(linea+"\n");
                }
            }
            if(!encontrado){
                System.out.println("No se encontró al usuario a modificar"); 
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
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void cambiarEstado(){
        String us; 
        Scanner sc = new Scanner(System.in); 
        String usAux, pwAux; 
        boolean activo; 
        int categoria; 
        char sn; 
        boolean encontrado = false; 
        Excepciones excep = new Excepciones(); 
        try{
            File f = new File("usuarios.txt"); 
            Scanner buffer = new Scanner(f); 
            File aux = new File("auxiliar.txt"); 
            aux.createNewFile(); 
            FileWriter escritor = new FileWriter(aux); 
            Scanner copiar = new Scanner(aux); 
            System.out.print("Ingrese el nombre del usuario que desea modificar: ");  
            us = sc.next(); 
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
                        encontrado = true; 
                        if(categoria==2){
                            System.out.print("Usuario: "+usAux); 
                            System.out.print("Estado: "); 
                            if(activo){
                                System.out.println("Activo"); 
                            }
                            else{
                                System.out.println("Inactivo"); 
                            }
                            System.out.print("¿Desea cambiar el estado del usuario?"); 
                            System.out.println("S--Si\nN--No"); 
                            sn = excep.leerChar('S', 'N'); 
                            if(sn=='S'){
                                activo = !activo; 
                                System.out.println("Usuario modificado con éxito"); 
                            }
                            escritor.write(usAux+" "+pwAux+" "+activo+" "+categoria+" \n");
                        }
                        else{
                            System.out.println("El usuario "+us+" tiene mayor nivel y por tanto no puede modificar su estado"); 
                        }
                    }
                    if(!encontrado){
                        System.out.println("No se encontró al usuario "+us); 
                    }
                }
                escritor.close();
                FileWriter pegar = new FileWriter(f); 
                String linea; 
                while(copiar.hasNext()){
                    linea = copiar.nextLine(); 
                    pegar.write(linea+"\n"); 
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
