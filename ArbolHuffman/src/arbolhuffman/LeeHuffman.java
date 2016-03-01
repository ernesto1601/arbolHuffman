/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolhuffman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LeeHuffman{

	static ArrayList<NodoArbol> arbolNodos = new ArrayList();
	static NodoArbol raizA;
	static LeeHuffman lh;
        static String[] convecion = new String[2];
        static String archivo;
        static String arbol="";
        static String ms=""; 
        static ArrayList<String> caracteresBin = new ArrayList();// guarda los codigos de los caracteres


        
        public void iniciar() throws IOException{
            String nombre = "";
            Scanner entrada = new Scanner (System.in);
            System.out.println("Escriba el nombre del archivo (Extesion .huffman)");
            nombre = entrada.nextLine();
            archivo = muestraContenido(nombre);
            separacion(archivo);
            
            creaNodos(arbol);//comenzara a recontruir el arbol a partir de el codigo del arbol
            recontruyeArbol();
            
            /*
                CODIGO QUE FALTA:
                    - RECONSTRUIR EL MENSAJE A PARTIR DE LA CADENA ms HE IMPRIMIRLA
                    -CHECAR LO DE LA CONVERSION DE BINARIO A CARACTER
            
            */
            
            /*String mensaje = convecion[0];
		lh = new LeeHuffman();
		lh.creaNodos(mensaje);

		for (int x=0;x<arbolNodos.size() ; x++) {
			System.out.println("padre: "+(x+1));
			lh.preOrder(arbolNodos.get(x));
		}

		lh.recontruyeArbol();*/
            
        }

	static int movimiento = 1;

	public void recontruyeArbol(){
		raizA = arbolNodos.get(0);//Se asigna la estructura base al arbol
		union = raizA;
		for (int x=1; x<arbolNodos.size(); x++) {
			auxUnion = union;
			recoInsercion(raizA);
			union.der=arbolNodos.get(x);
		}

		JFrame arbolB = new JFrame("Arbol grafico");
		 		arbolB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 		arbolB.add(dibujaArbol());
		 		arbolB.setSize(600,800);
		 		arbolB.setVisible(true);
	}

	public JPanel dibujaArbol(){
		return new Grafico2(lh);
	}

	static NodoArbol union;
	static NodoArbol auxUnion;

        /*Metodo que inserta los nodos al arbol*/
	public void recoInsercion(NodoArbol reco){

		if(reco != null){

			if(reco.der==null && reco.izq != null){
				union = reco;
			}

			recoInsercion(reco.izq);
			recoInsercion(reco.der);
		}

	}

        /*Metodo que crea los nodos para la recontruccion del arbol*/
	public void creaNodos(String mensaje){

		String codigos[] = mensaje.split("");//spliteo el mensaje
		NodoArbol aux;
		NodoArbol guardar;
		NodoArbol conexion;

		for (int i=0; i<codigos.length; i++) {//comienzo a recorrer el array
			
			if(codigos[i].equals("0")){//si el caracter actual del recorrido es 0 creo un nodo rama
				NodoArbol nodo = new NodoArbol("1");
				aux = nodo;
				guardar = nodo;
				conexion = nodo;
				i++;
				while(codigos[i].equals("0")){
					NodoArbol nodo2 = new NodoArbol("1");
					aux.izq = nodo2;
					conexion = aux;
					aux = nodo2;
					i++;
				}
				i--;
				NodoArbol hijo1 = new NodoArbol("1");
				aux.izq = hijo1;
				arbolNodos.add(guardar);
				i+=9;// se salta la parte del dato del nodo;
			}else{
				NodoArbol nodo = new NodoArbol("1");
				arbolNodos.add(nodo);
				i+=8;
			}
		}

	}

	/*public void preOrder(NodoArbol reco){
		if(reco != null){
			System.out.println(reco.caracter);
			preOrder(reco.izq);
			preOrder(reco.der);
		}
	}*/
        /*Metodo para leer el archivo de texto .huffman*/
       public String muestraContenido(String archivo) throws FileNotFoundException, IOException{
        
            String cadena,texto="";
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine()) != null){
                System.out.println(cadena);
                texto +=cadena;
            }

            b.close();
            return texto;
    }
       
       /*Metodo para separa codigo del arbol y mensaje*/
       
       public void separacion (String archivo){
           String[] caracteres = archivo.split("");
           String comprobacion = "", comprobacion2="";
           
           int aux = 0;
           int j=0;
           for (int i = 0; i < archivo.length(); i++) {
              
               if (caracteres[i].equals("1")) {
                   arbol+=caracteres[i];
                   i++;
                   int y = i+8;
                   for ( int x=i; i < y; x++) {
                       
                        comprobacion+= caracteres[x];
                        i++;
                        aux = i;
                   }
                   caracteresBin.add(comprobacion);
                   for (int k = i; k < i+8; k++) {
                       comprobacion2+=caracteres[k];
                   }
                   
                   if (comprobacion2.equals("11000001")) {
                       aux = i;
                       for (int k = i-8; k < i; k++) {
                            arbol+=caracteres[k];
                        }
                       i += archivo.length();
                   }else{
                       arbol+=comprobacion;
                       comprobacion="";
                       comprobacion2="";
                   }
                  i--;
               }else{
                   arbol+=caracteres[i];
               }
           }
           for (int i = aux+8; i < archivo.length(); i++) {
               ms+=caracteres[i];
           }
           System.out.println("Arbol: "+arbol);
           System.out.println("Mensaje: "+ms);
       }
}


/*Clase que guarda los nodos del arbol a recontruir*/
class NodoArbol{

	NodoArbol izq, der;
	String caracter;

	public NodoArbol(String c){
		izq = null;
		der=null;
		caracter = c;
	}

	
	
}