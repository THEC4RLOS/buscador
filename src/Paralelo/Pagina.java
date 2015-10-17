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
public class Pagina extends RecursiveTask<Integer> {

    private ArrayList<String> paginasWeb = null;
    private ArrayList<String> linkPagina = null;
    private String palabra = "";

    public Pagina(ArrayList<String> paginasWeb, ArrayList<String> linkPagina, String palabra) {
        this.paginasWeb = paginasWeb;
        this.linkPagina = linkPagina;
        this.palabra = palabra;
    }

    @Override
    protected Integer compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.linkPagina.size() != 1) {

            List<Pagina> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Pagina subtask : subtasks) {
                subtask.fork();
            }

            int result = 0;
            for (Pagina subtask : subtasks) {
                result += subtask.join();
            }
            return result;

        } else {
            Resultado mergedResult = null;
            try {
                Document doc = Jsoup.connect(this.linkPagina.get(0)).get();
                String titulo = doc.title();
                
                Texto tareaTexto = new Texto(0, doc.text(), doc.text(), this.palabra);
                int cores = Runtime.getRuntime().availableProcessors();
                ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
                mergedResult = forkJoinPool.invoke(tareaTexto);
                mergedResult.setTitulo(titulo);
                mergedResult.setUrl(this.linkPagina.get(0));
                System.out.println(titulo + " "+ this.palabra + " " +mergedResult);
                
            } catch (IOException e) {
            }
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