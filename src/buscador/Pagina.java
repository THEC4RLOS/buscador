package buscador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// Busca  una palabra en cada pagina
public class Pagina extends RecursiveTask<Integer> {
/*

    */
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
            //System.out.println("Splitting workLoad : " + this.workLoad);

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
            //System.out.println("Doing workLoad myself: " + this.workLoad);
            //vecesEncontrada = textoAux.indexOf(palabra);
            int mergedResult = 0;
            try {
                Document doc = Jsoup.connect(this.linkPagina.get(0)).get();
                //String title = doc.body().text();
                
                Texto tareaTexto = new Texto(0, doc.text(), doc.text(), this.palabra);
                int cores = Runtime.getRuntime().availableProcessors();
                ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
                mergedResult = forkJoinPool.invoke(tareaTexto);
                
            } catch (IOException e) {
            }
            return mergedResult;
        }
    }

    private List<Pagina> createSubtasks() {
        List<Pagina> subtasks = new ArrayList<>();

        //ArrayList<String> linkPaginaAux = this.paginasWeb.get(0);
        
        ArrayList<String> head = (ArrayList<String>)this.paginasWeb.subList(0, 1);
        ArrayList<String> tail = (ArrayList<String>)this.paginasWeb.subList(1, this.paginasWeb.size());

        
        //this.paginasWeb.remove(0);
        Pagina subtask1 = new Pagina(this.paginasWeb, head, this.palabra);
        Pagina subtask2 = new Pagina(this.paginasWeb, tail, this.palabra);
        //Paginas subtask2 = new Pagina(texto, textoAux.substring(mid+1, textoAux.length()), palabra);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

}
