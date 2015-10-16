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

    private ArrayList<String> paginasWeb = null;
    private String linkPagina = "";
    private String palabra = "";

    public Pagina(ArrayList<String> paginasWeb, String linkPagina, String palabra) {
        this.paginasWeb = paginasWeb;
        this.linkPagina = linkPagina;
        this.palabra = palabra;
    }

    @Override
    protected Integer compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (paginasWeb.size() > 0) {
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
            //vecesEncontrada =textoAux.indexOf(palabra);
            int mergedResult = 0;
            try {
                Document doc = Jsoup.connect(this.linkPagina).get();
                //String title = doc.body().text();
                Texto tareaTexto = new Texto(0, doc.body().text(), doc.body().text(), this.palabra);
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
        String linkPaginaAux = this.paginasWeb.get(0);
        this.paginasWeb.remove(0);
        Pagina subtask1 = new Pagina(this.paginasWeb, linkPaginaAux, this.palabra);
        //Paginas subtask2 = new Pagina(texto, textoAux.substring(mid+1, textoAux.length()), palabra);

        subtasks.add(subtask1);
        //subtasks.add(subtask2);

        return subtasks;
    }

    public static void main(String[] args) throws Exception {
        
        ArrayList<String> pw = new ArrayList<>();
        pw.add("http://elpais.com/elpais/portada_america.html");// 1
        pw.add("http://edition.cnn.com/");// 1
        pw.add("http://www.foxnews.com/"); //5
        pw.add("http://www.20minutos.es/");// 1
        pw.add("http://www.wsj.com/"); // 7
        String p = "Obama";
        Pagina myRecursiveTask = new Pagina(pw,"",p);
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        int mergedResult = forkJoinPool.invoke(myRecursiveTask);

        System.out.println("mergedResult = " + mergedResult);
    }
}
