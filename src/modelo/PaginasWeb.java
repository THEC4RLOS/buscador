package modelo;

import Secuencial.Resultado;
import java.util.ArrayList;

public class PaginasWeb {

    private String url;
    private ArrayList<Resultado> listaResultados;
    private int incidencias = 0;
    
    public PaginasWeb(String url, ArrayList<Resultado> listaResultados) {
        this.url = url;
        this.listaResultados = listaResultados;        
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Resultado> getListaResultados() {
        return listaResultados;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setListaResultados(ArrayList<Resultado> listaResultados) {
        this.listaResultados = listaResultados;
    }
    
    public void addItemListaResultados (Resultado item){
        this.listaResultados.add(item);
    }

    /**
     * @return the incidencias
     */
    public int getIncidencias() {
        return incidencias;
    }

    /**
     * @param incidencias the incidencias to set
     */
    public void setIncidencias(int incidencias) {
        this.incidencias = incidencias;
    }

}
