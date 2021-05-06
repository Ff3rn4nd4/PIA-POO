#include <iostream>
#include <fstream>
#include <conio.h>
#include <string>

using namespace std;

struct Nodo{
    int clave;
    float puntuacion;
    string serie;
    Nodo *anterior;
    Nodo *siguiente;
};
class Series{
private:
    Nodo *primero;
    Nodo *ultimo;
    char *file;
    int claveBase;
    short incr;
public:
    Series(char *file){
        this->file = file;
        primero = NULL;
        ultimo = NULL;
        claveBase = 0;
        incr = 1;
        ifstream archivo;
        Nodo *aux = NULL;
        archivo.open(file, ios::binary);
        if(archivo.is_open()){
            aux = new Nodo;
            archivo >> aux->clave >> aux->puntuacion;
            archivo.get();
            getline(archivo, aux->serie);
            while(!archivo.eof()){
                insertarNodo(aux, 1);
                aux = new Nodo;
                archivo >> aux->clave >> aux->puntuacion;
                archivo.get();
                getline(archivo, aux->serie);
            }
            archivo.close();
        }
    }
    ~Series(){
        ofstream archivo;
        archivo.open(file, ios::binary);
        Nodo *aux, *aux2;
        if(archivo.is_open()){
            aux = primero;
            while(aux!=NULL){
                archivo << aux->clave << " " << aux->puntuacion << " ";
                archivo << aux->serie << endl;
                aux2 = aux;
                aux = aux->siguiente;
                delete aux2;
            }
            archivo.close();
        }
    }
    Nodo* getPrimero(){
        return primero;
    }
    Nodo* getUltimo(){
        return ultimo;
    }
    void insertarNodo(Nodo *nuevo, int fuente){
        //0--CIN
        //1--Archivo
        nuevo->anterior = NULL;
        nuevo->siguiente = NULL;
        if(fuente == 0){
            claveBase+=incr;
            nuevo->clave = claveBase;
        }
        else{
            claveBase = nuevo->clave;
        }
        if(primero == NULL){
            //nuevo->clave = claveBase;
            primero = nuevo;
            ultimo = nuevo;
        }
        else{
            ultimo->siguiente = nuevo;
            nuevo->siguiente = NULL;
            nuevo->anterior = ultimo;
            ultimo = nuevo;
        }
    }
    void eliminarNodo(Nodo *aux){
        if(aux != primero){
            aux->anterior->siguiente = aux->siguiente;
        }
        else{
            primero = aux->siguiente;
            if(primero != NULL){
                primero->anterior = NULL;
            }
        }
        if(aux != ultimo){
            aux->siguiente->anterior = aux->anterior;
        }
        else{
            ultimo = aux->anterior;
            if(ultimo != NULL){
                ultimo->siguiente = NULL;
            }
        }
        delete aux;
    }
    Nodo* buscarNodoID(int id){
        Nodo* aux = NULL;
        bool encontrado = false;
        aux = primero;
        while(aux != NULL){
            if(id == aux->clave){
                encontrado = true;
                break;
            }
            else{
                aux = aux->siguiente;
            }
        }
        if(encontrado){
            return aux;
        }
        else{
            return NULL;
        }
    }
    void capturarDatos(Nodo* nuevo){
        cout << "Nombre: ";
        cin.ignore(1, '\n');
        getline(cin, nuevo->serie);
        cout << "Puntuación: ";
        cin >> nuevo->puntuacion;
    }
    void imprimirLista(){
        if(primero != NULL){
            cout << "Clave\t\tSerie (Puntuación)" << endl;
            Nodo* aux = primero;
            while(aux!=NULL){
                cout << aux->clave << "\t\t" << aux->serie << " (" << aux->puntuacion << ")" << endl;
                aux = aux->siguiente;
            }
        }
        else{
            cout << "No hay series registradas en el sistema" << endl;
        }
    }
};

void mostrarDatos(Nodo* aux){
    cout << "Clave: " << aux->clave << endl;
    cout << "Nombre: " << aux->serie << endl;
    cout << "Puntuación: " << aux->puntuacion << endl;
}


int main(){
    Nodo *aux = 0, *nuevo = 0;
    int menu, clave, opc;
    string nombre;
    char archi[] = "series.txt";
    /************LLAMAR AL CONSTRUCTOR*******/
    Series lista(archi);
    /****************************************/
    do{
        system("cls");
        cout << "\t<<Series de Manga>>" << endl;
        cout << "1.- Agregar serie" << endl;
        cout << "2.- Buscar por ID" << endl;
        cout << "3.- Buscar por Nombre" << endl;
        cout << "4.- Mostrar lista de series" << endl;
        cout << "5.- Salir" << endl;
        cin >> menu;
        switch(menu){
        case 1:
            /*****Capturar Datos*****/
            system("cls");
            nuevo = new Nodo;
            lista.capturarDatos(nuevo);
            lista.insertarNodo(nuevo, 0);
            cout << "Entrada insertada con éxito" << endl;
            break;
        case 2:
            /******Busqueda de Nodo por Clave*****/
            system("cls");
            cout << "Ingrese la clave de la serie que desea encontrar: ";
            cin >> clave;
            aux = lista.buscarNodoID(clave);
            if(aux != NULL){
                do{
                    mostrarDatos(aux);
                    cout << "Desea: " << endl << "0) Regresar" << endl << "1) Modificar los datos" << endl << "2) Eliminar la entrada" << endl;
                    cin >> opc;
                    switch(opc)
                    {
                    case 0:
                        break;
                    case 1:
                        lista.capturarDatos(aux);
                        cout << "Entrada modificada con éxito" << endl;
                        break;
                    case 2:
                        lista.eliminarNodo(aux);
                        cout << "Entrada eliminada con éxito" << endl;
                        break;
                    default:
                        cout << "Opción no válida" << endl;
                        break;
                    }
                }while(opc!=0 && opc != 1 && opc != 2);
            }
            else{
                cout << "No se encontró una serie con la clave especificada" << endl;
            }
            break;
        case 3:
            /**********Busqueda de Nodo por Nombre*************/
              cout << "Opcion aún no disponible" << endl;
              break;
        case 4:
            system("cls");
            lista.imprimirLista();
            break;
        case 5:
            cout << "\a" << "Gracias por utilizar la aplicación" << endl;
            break;
        default:
            cout << "Opcion no válida" << endl;
            break;
        }
        cout << "Presione ENTER para continuar" << endl;
        _getch();
    }while(menu != 5);
    return 0;
}
