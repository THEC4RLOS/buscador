package Paralelo;

import Secuencial.Resultado;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import modelo.PaginasWeb;

// Busca  una palabra en cada pagina
public class Palabra extends RecursiveTask<ArrayList<PaginasWeb>> {

    private ArrayList<String> paginasWeb = null;
    private String palabra = "";
    private ArrayList<PaginasWeb> sitiosWeb = null;

    public Palabra(ArrayList<String> paginasWeb, String palabra) {
        this.paginasWeb = paginasWeb;
        this.palabra = palabra;
        this.sitiosWeb = new ArrayList<>();
        initArrayPaginasWeb();
    }

    @Override
    protected ArrayList<PaginasWeb> compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.palabra.contains("|")) {
            List<Palabra> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Palabra subtask : subtasks) {
                subtask.fork();
            }

            //int result = 0;
            ArrayList<PaginasWeb> result;
            for (Palabra subtask : subtasks) {
                result = subtask.join();
            }
            //System.out.println("muchos Palabra Numero "+sitiosWeb.size());
            return this.sitiosWeb;

        } else {
            ArrayList<Resultado> mergedResult;
            
            //int mergedResult;
            ArrayList<String> paginasWebAux = new ArrayList<>(this.paginasWeb);
            Pagina tareaPagina = new Pagina(paginasWebAux, paginasWebAux, this.palabra);
            int cores = Runtime.getRuntime().availableProcessors();
            ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
            mergedResult = forkJoinPool.invoke(tareaPagina);
            addResultado(mergedResult);
            //System.out.println("uno Palabra Numero "+sitiosWeb.size());
            return this.sitiosWeb;
        }
    }

    private List<Palabra> createSubtasks() {
        List<Palabra> subtasks = new ArrayList<>();
        
        int fin = this.palabra.indexOf("|");
        
        String palabraAtomo = palabra.substring(0, fin).trim();
        String palabraCadena = palabra.substring(fin+1, palabra.length()).trim();
        Palabra subtask1 = new Palabra(this.paginasWeb, palabraAtomo);
        Palabra subtask2 = new Palabra(this.paginasWeb, palabraCadena);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

    /**
     * Agrega un objeto de tipo resultado al objeto Paginzas Web correcto 
     * @param resultado
     */
    public void addResultado(ArrayList<Resultado> resultado){
        for (Resultado resultadoAux: resultado){
            addResultadoAux(resultadoAux);
            //System.out.println("addResultado");
        }
    }
    
    public void addResultadoAux (Resultado resultado){
        for (PaginasWeb paginaWeb: this.sitiosWeb){
            if (paginaWeb.getUrl().equals(resultado.getUrl())){
                paginaWeb.addItemListaResultados(resultado);
                //System.out.println("addResultadoAux");                
            }
        }
    }
    
    public final void initArrayPaginasWeb(){
        for(String url: paginasWeb){
            PaginasWeb nPaginasWeb = new PaginasWeb(url, new ArrayList<Resultado>());
            sitiosWeb.add(nPaginasWeb);
            //System.out.println("initArrayPaginasWeb");
        }
    }

}