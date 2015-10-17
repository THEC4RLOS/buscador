
package Secuencial;

public class Resultado {
    private int coincidencias;
    private String url;
    private String textoCoincidencia;
    private String Titulo;
    
    public Resultado(int coincidencias, String url, String textoCoincidencia, String titulo) {
        this.coincidencias = coincidencias;
        this.url = url;
        this.textoCoincidencia = textoCoincidencia;
        this.Titulo = titulo;
    }
    
    public String descripcion(){
        String desc = "\n Titulo: "+this.Titulo+"\n URL: "+this.url+"\n Coincidencias: "+this.coincidencias+"\n texto: "+ this.textoCoincidencia;
        return desc;
    }

    public int getCoincidencias() {
        return coincidencias;
    }

    public String getUrl() {
        return url;
    }

    public String getTextoCoincidencia() {
        return textoCoincidencia;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setCoincidencias(int coincidencias) {
        this.coincidencias = coincidencias;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTextoCoincidencia(String textoCoincidencia) {
        this.textoCoincidencia = textoCoincidencia;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
    
    
}
