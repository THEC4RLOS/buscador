package Paralelo;

import Secuencial.Resultado;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// Busca  una palabra en cada pagina
public class Pagina extends RecursiveTask<ArrayList<Resultado>> {

    private ArrayList<String> paginasWeb = null;
    private ArrayList<String> linkPagina = null;
    private String palabra = "";
    private ArrayList<Resultado> resultado = null;

    public Pagina(ArrayList<String> paginasWeb, ArrayList<String> linkPagina, String palabra) {
        this.paginasWeb = paginasWeb;
        this.linkPagina = linkPagina;
        this.palabra = palabra;
        this.resultado = new ArrayList<>();
    }

    @Override
    protected ArrayList<Resultado> compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.linkPagina.size() != 1) {

            List<Pagina> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Pagina subtask : subtasks) {
                subtask.fork();
            }

            //int result = 0;
            Resultado result;
            for (Pagina subtask : subtasks) {
                result = subtask.join().get(0);
                this.resultado.add(result);
            }
            //System.out.println("muchos Pagina Numero "+resultado.size());
            return this.resultado;

        } else {
            ArrayList<Resultado> mergedResult = new ArrayList<>();
            Resultado result;
            
            try {
                Document doc = Jsoup.connect(this.linkPagina.get(0)).get();
                String titulo = doc.title();
                
                Texto tareaTexto = new Texto(0, doc.body().text(), doc.body().text(), this.palabra);
                int cores = Runtime.getRuntime().availableProcessors();
                ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
                result = forkJoinPool.invoke(tareaTexto);
                result.setTitulo(titulo);
                result.setUrl(this.linkPagina.get(0));
                //System.out.println(titulo + " "+ this.palabra + " " +mergedResult);
                mergedResult.add(result);
                
            } catch (IOException e) {
            }
            //System.out.println("uno Pagina Numero "+mergedResult.size());
            return mergedResult;
        }
    }

    private List<Pagina> createSubtasks() {
        List<Pagina> subtasks = new ArrayList<>();
        
        ArrayList<String> linkPaginaAux = new ArrayList<>();
        linkPaginaAux.add(this.paginasWeb.get(0));
        this.paginasWeb.remove(0);
        Pagina subtask1 = new Pagina(this.paginasWeb, linkPaginaAux, this.palabra);
        Pagina subtask2 = new Pagina(this.paginasWeb, this.paginasWeb, this.palabra);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

}