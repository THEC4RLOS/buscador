
package Secuencial;

public class Resultado {
    int coincidencias;
    String url;
    String textoCoincidencia;
    String Titulo;
    
    public Resultado(int coincidencias, String url, String textoCoincidencia, String titulo) {
        this.coincidencias = coincidencias;
        this.url = url;
        this.textoCoincidencia = textoCoincidencia;
        this.Titulo = titulo;
    }
    
    public String descripcion(){
        String desc = "\n URL: "+this.url+"\n Coincidencias: "+this.coincidencias+"\n texto: "+ this.textoCoincidencia;
        return desc;
    }
}
