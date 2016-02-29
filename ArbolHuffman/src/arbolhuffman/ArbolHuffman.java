
package arbolhuffman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ArbolHuffman {

  static  Arbol arbol;
  static ArrayList<Nodo> lista;
  //Diana&Carlos
  private Stack<Integer> tablaDeco;
  private String code;
  private ArrayList<tablaDeco> tableD;
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  static ArrayList<Nodo> temp  = new ArrayList();
    public static void main(String[] args) throws IOException {
        
        ArbolHuffman arbolH = new ArbolHuffman();
        arbol = new Arbol();
        String nombre_archivo, texto;
        int texto_tam;
        char[] caracteres;
        Scanner sc = new Scanner(System.in);
        
        
        System.out.println("introduzca el archivo a comprimir");
        nombre_archivo=sc.nextLine();
        texto = arbolH.muestraContenido(nombre_archivo);
        texto_tam = texto.length();
        System.out.println("el tamano de la cadena es: "+texto_tam);
        caracteres = texto.toCharArray();
        arbolH.tablaFrecuencia(caracteres);
        System.out.println("TABLA DECO V0.1");
        arbolH.tablaDecodificacion();
        System.out.println("Tree code");
        System.out.println("Escriba el mensaje a incluir:");
        String mensaje = sc.nextLine();
        arbolH.codeTree();
        arbolH.message(mensaje);
        /*Descriptado*/
        String decodificar = arbolH.muestraContenido("Code.huffman");
        arbolH.crearArbol(decodificar);
    }
    ////////////////////////////////////////////////////////
        public void crearArbol(String mensaje){
        String[] convecion = new String[mensaje.length()]; 
        ArrayList<Character> octetos = new ArrayList(); 
        char[] caracteres = mensaje.toCharArray();
        for (int i = 0; i < mensaje.length(); i++) {
            convecion = mensaje.split("11000001");
        }
        System.out.println("Arbol: "+convecion[0]);
        System.out.println("\n");
        System.out.println("Mensaje: "+convecion[1]);
        String arbol = convecion[0];
        for (int i = 0; i < arbol.length(); i++) {
            arbol.charAt(i);
        }
    }
    ////////////////////////////////////////////////////////
    //Se encarga de leer el archivo seleccionado para mostrar el mensaje que se va a comprimir
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
    
    /*Se encarga de imprimir la tabla de frecuencia de los carateres del mensaje
      y los caracteres con sus frecuencias los almacena en un nodo que se agrega
      a un ArrayList que guarda nodos
    */
    public void tablaFrecuencia(char[] letras){
        
        lista = new ArrayList();
        int contador=0;
        char auxiliar;
        
        for(int i=0; i<letras.length;i++){
            if(letras[i] != 0){
                auxiliar = letras[i];
                for(int m=0;m<letras.length;m++){
                    if(auxiliar == letras[m]){
                        contador++;
                        letras[m] = '\0';
                    }
                }
                
                Nodo n = new Nodo(auxiliar,contador);
                lista.add(n);
                temp.add(n);
                contador=0;
            }
        }
        
        lista = ordenaLista(lista);
        for(int n=0;n<lista.size();n++){
            
         
            System.out.println("caracter: "+ lista.get(n).data()+ " frecuencia: "+ lista.get(n).frecuency());
        }
        registroRemplazo(temp);
        createTree();
    }
    public void registroRemplazo(ArrayList<Nodo> lista){
        ArrayList<Nodo> temp  = new ArrayList();
        temp = lista;
        int num1 = 0;
        int num2 = 0;
        int suma = 0;
        int tam = temp.size()*2;
        for (int i = 0; i < temp.size(); i++) {
            System.out.print(","+temp.get(i).frecuency());
        }
        System.out.println("\n");
        try {
             for (int i = 0; i < tam; i=i+2) {
            num1 = temp.get(i).frecuency();
            num2 = temp.get(i+1).frecuency();
            suma = num1 + num2;
            Nodo nuevo = new Nodo('\0',suma);
            temp.add(nuevo);
            ordenaLista(temp);
            String frecu="";
            
            for (int j = i+2; j < tam; j++) {
                try {
                    frecu = frecu + ","+  temp.get(j).frecuency();
                } catch (Exception e) {}
            }
            System.out.println("Registro: "+frecu);
        }   
            } catch (Exception e) {}
        
    }
    
    /*Se utiliza este método para ordenar el ArrayList cuando sea necesario*/
    
    public ArrayList<Nodo> ordenaLista(ArrayList<Nodo> lista){
        
         int i, j, aux;
         Nodo a;
         for(i=0;i<lista.size()-1;i++){
              for(j=0;j<lista.size()-i-1;j++){
                   if(lista.get(j+1).frecuency()<lista.get(j).frecuency()){
                      a=lista.get(j+1);
                      lista.set(j+1,lista.get(j));
                      
                      lista.set(j,a);
                     
                   }
               }
         	}
         return lista;
    }
    
    /*
        Método encargado de generar el arbol a partir de los nodos del ArrayList
        
    */

    public void createTree(){
        int tam = lista.size()*2;
        for(int x = 0;x<tam;x+=2){
            int izquierdo=0, derecho = 0;
            izquierdo = lista.get(x).frecuency();
            try{
            derecho = lista.get(x+1).frecuency();
            }catch(Exception e){
                
            }
            int padre = izquierdo+derecho;
            Nodo rama = new Nodo('\0',padre);
            lista.add(rama);
            ordenaLista(lista);
        }
        System.out.println("");
        /*for(int n=0;n<lista.size();n++){
            
         
            System.out.println("caracter: "+ lista.get(n).data()+ " frecuencia: "+ lista.get(n).frecuency());
        }*/
        
        System.out.println("FORMACION DEL ARBOL");
        for(int i=lista.size()-1;i>=0; i-=2){
            
            arbol.insertar(lista.get(i),lista.get(i-1));
        }
        
        arbol.preOrder();
        
        JFrame arbolB = new JFrame("Arbol grafico");
		 		arbolB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 		arbolB.add(dibujaArbol());
		 		arbolB.setSize(600,800);
		 		arbolB.setVisible(true);
    }

    /*Método encargado de dibujar el árbol*/
    public JPanel dibujaArbol(){
		return new Grafico(arbol);
	}
    
    //Diana&Carlos
    public void tablaDecodificacion(){
        tablaDeco = new Stack<>();
        tableD = new ArrayList<>();
        Nodo root = arbol.getRaiz();
        preOrdertd(root);
    }

    private void preOrdertd(Nodo reco){
        if(reco!=null){
            if(reco != arbol.getRaiz()){
                tablaDeco.push(reco.peso);
            }
            if(arbol.esHoja(reco)){
                tablaDeco tb = new tablaDeco(tablaDeco.toString(),reco.caracter);
                tableD.add(tb);
                System.out.println(reco.caracter + "::" + tablaDeco.toString());
            }
            preOrdertd(reco.izq);

            preOrdertd(reco.der);
            try {
                tablaDeco.pop();
            }catch (EmptyStackException ex){
                System.err.println("The stack is empty");
            }
        }
    }

    private void codeTree(){
        code = "";
        preOrderct(arbol.getRaiz());
        //int w = (int) 'Ⱶ';
        //code+= Integer.toBinaryString(w);
        code+="11000001";
    }
    
    private void preOrderct(Nodo reco){
        if(reco!=null){
            
            if(arbol.esHoja(reco)){
                code+= 1;
                code+=0;
                
                int a = (int) reco.caracter;
                code+=Integer.toBinaryString(a);
                if(reco.caracter == ' '){
                    code+=0;
                }
            }else
                code+=0; 
            preOrderct(reco.izq);

            preOrderct(reco.der);
            
        }
    }

    public void message(String message){
        boolean founded = false;
        char[] allCM = message.toCharArray();
        for (int i = 0; i < allCM.length; i++) {
            for (int j = 0; j < tableD.size(); j++) {
                if (tableD.get(j).caracter == allCM[i]){
                    founded = true;
                    String[] cd = tableD.get(j).code.split("(\\[)|(\\])|(,)");
                    for (String q: cd) {
                        code+=q.trim();
                    }
                    break;
                }else
                    founded = false;
            }
            if (!founded){
                System.err.println("No se puede generar el mensaje, insuficientes caracteres para formular el mensaje");
                //message(message);
                System.out.println("Escriba el mensaje: ");
                Scanner sc = new Scanner(System.in);
                String mensajito = sc.nextLine();
                message(mensajito);
                break;
            }
        }
        if (founded){
            generateData();
        }
    }

    public void generateData(){
        FileOutputStream fr = null;
        try {
            fr = new FileOutputStream("Code.huffman");
            fr.write(code.getBytes());
            fr.flush();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

/*Clase que se encarga de almacenar el arbol con los nodos*/
class Arbol{
    Nodo raiz;
    
    public Arbol(){
        raiz = null;
    }
    
    public void insertar(Nodo nodo1, Nodo nodo2){
       
        if(raiz == null){
            raiz = nodo1;
           
        }else{
           int val1 = nodo1.frecuency();
           int val2 = nodo2.frecuency();
           int resultado = val1+val2;
           buscar(resultado,nodo1,nodo2);
        }
        
    }
    
    public void buscar (int aux,Nodo nodoIzq, Nodo nodoDer)
      {
          buscar (raiz,aux,nodoIzq,nodoDer);
          bandera = true;
          
      }
    
    private void buscar (Nodo root, int valor,Nodo nodoIzq, Nodo nodoDer)
      {
          Nodo reco = root;
        /*
          Método para recorrer el árbol en el sentido inOrder(izquierda, raíz, derecha)
        */
          if(bandera){
          if (reco != null)
          {    if(reco.frecuency()==valor && reco.data()=='\0'&& reco.der == null && reco.izq == null)
                  {
                      //Diana&Carlos B|
                      nodoIzq.setPeso(1);
                      nodoDer.setPeso(0);
                      //>>>>>>>>>>>>>>>>>>>
                      reco.der = nodoIzq;
                      reco.izq = nodoDer;
                      bandera = false;
                  }
                if(reco != null){
                    buscar (reco.izq,valor,nodoIzq,nodoDer);
                    buscar (reco.der,valor,nodoIzq,nodoDer);
                }
          }
          }else
              return;

      }
    
    boolean bandera = true;
    
    private void preOrder (Nodo reco)
    {
        if (reco != null)
        {
            System.out.println("Frecuencia: "+reco.frecuency() + "Dato: "+reco.data());
            preOrder (reco.izq);
            preOrder (reco.der);
        }
    }

    public void preOrder ()
    {
        preOrder (raiz);
        System.out.println();
    }
      
    public boolean esHoja(Nodo nodo){
          if(nodo.izq == null && nodo.der==null)
              return true;
          return false;
      }

    Nodo getRaiz() {
        return raiz;
    }

}

/*Clase que genera los nodos que conforman el árbol*/

class Nodo{
    
    char caracter;
    int frecuencia;
    int peso;
    Nodo der,izq;
    
    public Nodo(char caracter,int frecuencia){
        this.caracter = caracter;
        this.frecuencia = frecuencia;
    }
    
    public char data(){
        return caracter;
    }
    
    public int frecuency(){
        return frecuencia;
    }
    
    public void setFrecuency(int frecuencia){
        this.frecuencia = frecuencia;
    }
    
    public void setPeso(int peso){
        this.peso = peso;
    }
    
}

//Diana&Carlos
class tablaDeco{

    char caracter;
    String code;

    tablaDeco(String code,char caracter){
        this.caracter = caracter;
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public char getCaracter() {
        return caracter;
    }

    public String getCode() {
        return code;
    }
}