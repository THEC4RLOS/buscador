
package Secuencial;

public class Resultado {
    int coincidencias;
    String url;
    String textoCoincidencia;
    
    public Resultado(int coincidencias, String url, String textoCoincidencia) {
        this.coincidencias = coincidencias;
        this.url = url;
        this.textoCoincidencia = textoCoincidencia;
    }
    
    public String descripcion(){
        String desc = "\n URL: "+this.url+"\n Coincidencias: "+this.coincidencias+"\n texto: "+ this.textoCoincidencia;
        return desc;
    }
}
