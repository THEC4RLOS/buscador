
package Secuencial;

public class Resultado {
    private int coincidencias;
    private String url;
    private String textoCoincidencia;
    private String Titulo;
    private String palabra;
    private long tiempo;

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
    
    public Resultado(int coincidencias, String url, String textoCoincidencia, String titulo, String palabra,long tiempo) {
        this.coincidencias = coincidencias;
        this.url = url;
        this.textoCoincidencia = textoCoincidencia;
        this.Titulo = titulo;
        this.palabra = palabra;
        this.tiempo = tiempo;
    }
    
    public String descripcion(){
        String desc = "\n Titulo: "+this.Titulo+"\n URL: "+this.url+"\n Coincidencias: "+this.coincidencias+"\n texto: "+ this.textoCoincidencia+
                "Término: "+this.palabra+"\n Tiempo: "+this.tiempo+" milisegundos";
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

    public String getPalabra() {
        return palabra;
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

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
    
}
