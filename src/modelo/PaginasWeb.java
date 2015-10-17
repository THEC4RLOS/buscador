package modelo;

import Secuencial.Resultado;
import java.util.ArrayList;

public class PaginasWeb {

    private String url;
    private ArrayList<Resultado> listaResultados;

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

}
