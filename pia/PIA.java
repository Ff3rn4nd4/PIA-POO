package pia;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PIA {

    public static void main(String[] args) {
        int opc, menu, sub, sub1, sub2;
        int clase, matricula, op = 0;

        Scanner lectura = new Scanner(System.in);
        Scanner kb = new Scanner(System.in);
        ClassDias b = new ClassDias();
        int dia[][] = new int[31][12];
        String userName, password, userAux, pwAux;
        String customerName, email, numcel;

        boolean activo = false, encontrado = false;
        usuario user = null;
        customerdata customer = null;

        //ciclo para elección de fecha
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 31; j++) {
                dia[j][i] = 0;
            }
        }

        //primer archivo USUARIOS
        try {
            File archivo = new File("usuarios.txt");
            archivo.createNewFile();
        } catch (IOException e) {
            System.out.println("No se puede acceder a los usuarios, intente más tarde");
            e.printStackTrace();
        }
        do {
            do {
                System.out.println("0--Iniciar sesión\n1--Cerrar el programa");
                opc = lectura.nextInt();
            } while (opc != 0 && opc != 1);

            // segundo archivo RESERVACIONES
            try {
                File seg_archivo = new File("reservacionies.txt");
                seg_archivo.createNewFile();
            } catch (IOException f) {
                System.out.println("No se puede acceder a las reservaciones, intente más tarde");
                f.printStackTrace();
            }
            /**/
            if (opc == 0) {
                //Acceso
                try {
                    File archivo = new File("usuarios.txt");
                    Scanner leerArchivo = new Scanner(archivo);
                    if (!leerArchivo.hasNext()) {
                        System.out.println("No hay usuarios registrados en el sistema");
                        System.out.println("Se creará un usuario nivel \"Jefe\"");
                        System.out.print("Ingrese un nombre de usuario: ");
                        userName = lectura.next();
                        System.out.print("Ingrese una contraseña: ");
                        password = lectura.next();
                        user = new jefe(userName, password);
                        FileWriter escritor = new FileWriter(archivo);
                        escritor.write(user.getUserName() + " " + user.getPassword() + " " + user.isActivo() + " " + 0 + "\n");
                        escritor.close();
                        System.out.println("Usuario creado con éxito");
                        encontrado = true;
                    } else {
                        System.out.print("Ingrese su nombre de usuario: ");
                        userName = lectura.next();
                        System.out.print("Ingrese su contraseña: ");
                        password = lectura.next();
                        while (leerArchivo.hasNext()) {
                            userAux = leerArchivo.next();
                            pwAux = leerArchivo.next();
                            activo = leerArchivo.nextBoolean();
                            clase = leerArchivo.nextInt();
                            if (userName.equals(userAux) && password.equals(pwAux)) {
                                switch (clase) {
                                    case 0:
                                        user = new jefe(userName, password, activo);
                                        break;
                                    case 1:
                                        user = new gerente(userName, password, activo);
                                        break;
                                    default:
                                        user = new empleado(userName, password, activo);
                                        break;
                                }
                                encontrado = true;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo crear el usuario \"Jefe\"");
                    e.printStackTrace();
                }
                if (encontrado) {
                    if (user.isActivo()) {
                        System.out.println("\nBienvenido " + user.getUserName());
                        do {
                            System.out.println(user.getUserName() + ", seleccione una opción: ");
                            System.out.println("1--Acceder al Panel de Administración");
                            System.out.println("2--Reservaciones");
                            System.out.println("3--Cerrar Sesión");
                            menu = lectura.nextInt();
                            switch (menu) {
                                case 1:
                                    System.out.println("Panel de Administración");
                                    System.out.println("Nivel: " + user.nivel());
                                    System.out.println("Opciones: ");
                                    System.out.println("1--Crear nuevo usuario");
                                    System.out.println("2--Cambiar usuario y contraseña");
                                    System.out.println("3--Desactivar usuario");
                                    System.out.println("4--Crear nuevo paquete");
                                    System.out.println("5--Mostrar Paquetes");
                                    sub = lectura.nextInt();
                                    switch (sub) {
                                        case 1:
                                            System.out.println("Crear nuevo usuario");
                                            user.crearUsuario();
                                            break;
                                        case 2:
                                            System.out.println("Cambiar nombre de Usuario y contraseña");
                                            break;
                                        case 3:
                                            System.out.println("Desactivar Usuario");
                                            break;
                                        default:
                                            System.out.println("Seleccione una opción válida");
                                    }
                                    break;
                                case 2:
                                    do {
                                        System.out.println("\nReservaciones");
                                        System.out.println("Opciones: ");
                                        System.out.println("1--Crear reservación");
                                        System.out.println("2--Edición de Reservación");
                                        System.out.println("3--salir");
                                        sub1 = lectura.nextInt();

                                        switch (sub1) {
                                            case 1:
                                                System.out.println("\tIngrese datos del cliente\n");

                                                System.out.println("Asignamiento de matricula: ");
                                                matricula = lectura.nextInt();
                                                System.out.print("Ingrese nombre del cliente: ");
                                                customerName = lectura.next();
                                                System.out.print("Ingrese un email: ");
                                                email = lectura.next();
                                                System.out.print("Ingrese un número telefonico: ");
                                                numcel = lectura.next();
                                                customer = new customerdata(customerName, email, numcel);
                                                do {
                                                    System.out.println("\n\tEspecificaciones del evento\n");
                                                    System.out.println("Opciones: ");
                                                    System.out.println("1-- Elegir fecha");
                                                    System.out.println("2-- Eliminar fecha");
                                                    System.out.println("3--Mostrar fechas disponibles");
                                                    System.out.println("4--salir");
                                                    op = kb.nextInt();

                                                    switch (op) {
                                                        case 1:
                                                            dia = b.metLLenar(dia);
                                                            break;
                                                        case 2:
                                                            dia = b.metEli(dia);
                                                            break;
                                                        case 3:
                                                            b.metImp(dia);
                                                            break;
                                                        case 4:
                                                            break;
                                                        default:
                                                            System.out.println("Seleccione una opción válida");
                                                    }
                                                } while (op != 4);

                                                break;

                                            case 2:
                                                do{ 
                                                    
                                                
                                                System.out.println("Opciones: ");
                                                System.out.println("1--Buscar ");
                                                System.out.println("2--Modificar ");
                                                System.out.println("3--Eliminarla");
                                                System.out.println("4--salir");
                                                sub2 = lectura.nextInt();

                                                switch (sub2) {
                                                    case 1:
                                                        System.out.println("Buscar ");
                                                        break;
                                                    case 2:
                                                        System.out.println("Modificar ");
                                                        break;
                                                    case 3:
                                                        System.out.println("Eliminarla");
                                                        break;
                                                    case 4:
                                                        break;
                                                    default:
                                                        System.out.println("Seleccione una opción válida");

                                                }
                                                }while(sub2!=4);
                                        }

                                    } while (sub1 != 3);

                                    break;
                                case 3:
                                    System.out.println("Sesión finalizada");
                                    encontrado = false;
                                    user = null;
                                    break;
                                default:
                                    System.out.println("Seleccione una opción válida");
                                    break;
                            }

                        } while (menu != 3);
                    } else {
                        System.out.println("\n\nEl usuario no está activo.");
                        System.out.println("Para activarlo, acceda desde una cuenta válida\n");
                    }
                } else {
                    System.out.println("Usuario o contraseña incorrectos o inexistentes");
                }
            }

        } while (opc
                != 1);
        System.out.println(
                "Adios");
    }

}
