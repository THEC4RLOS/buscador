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
        if (this.linkPagina.size() > 1) {

            List<Pagina> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Pagina subtask : subtasks) {
                subtask.fork();
            }

            Resultado result;
            ArrayList<Resultado> aregloResultadoTarea;
            for (Pagina subtask : subtasks) {
                aregloResultadoTarea = subtask.join();
                result = aregloResultadoTarea.get(0);
                this.resultado.addAll(aregloResultadoTarea);
            }
            imprimir(resultado);
            return this.resultado;

        } else {
            ArrayList<Resultado> arregloResultado = new ArrayList<>();
            Resultado resultadoTareaTexto;

            try {
                Document doc = Jsoup.connect(this.linkPagina.get(0)).get();
                int cores = Runtime.getRuntime().availableProcessors();

                String titulo = doc.title();

                Texto tareaTexto = new Texto(0, doc.body().text(), doc.body().text(), this.palabra);
                ForkJoinPool forkJoinPool = new ForkJoinPool(cores);

                resultadoTareaTexto = forkJoinPool.invoke(tareaTexto);
                //System.out.println("Titulo: "+titulo);
                resultadoTareaTexto.setTitulo(titulo);
                resultadoTareaTexto.setUrl(this.linkPagina.get(0));
                //arregloResultado.add(resultadoTareaTexto);
                arregloResultado.add(resultadoTareaTexto);
            } catch (IOException e) {
            }
            return arregloResultado;
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

    public void imprimir(ArrayList<Resultado> resultadoAux) {
        for (Resultado qwerty : resultadoAux) {
            System.out.println("<IIIIIIIIII    Pagina    IIIIIIIII");
            System.out.println(qwerty.getCoincidencias());
            System.out.println(qwerty.getPalabra());
            System.out.println(qwerty.getTextoCoincidencia());
            System.out.println(qwerty.getTitulo());
            System.out.println(qwerty.getUrl());
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII>");
        }
    }

}
