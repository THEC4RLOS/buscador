package Paralelo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Busca  una palabra en cada pagina
public class Palabra extends RecursiveTask<Integer> {

    private ArrayList<String> paginasWeb = null;
    private String palabra = "";

    public Palabra(ArrayList<String> paginasWeb, String palabra) {
        this.paginasWeb = paginasWeb;
        this.palabra = palabra;
    }

    @Override
    protected Integer compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.palabra.contains("|")) {
            System.out.println("Palabra con |: >" + this.palabra + "<");
            List<Palabra> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Palabra subtask : subtasks) {
                subtask.fork();
            }

            int result = 0;
            for (Palabra subtask : subtasks) {
                result += subtask.join();
            }
            return result;

        } else {
            System.out.println("Palabra sin |: >" + this.palabra + "<");
            System.out.println("paginas");
            for(String link: this.paginasWeb){
                System.out.println(link);
            }
            int mergedResult;
            ArrayList<String> paginasWebAux = new ArrayList<>(this.paginasWeb);
            Pagina tareaPagina = new Pagina(paginasWebAux, paginasWebAux, this.palabra);
            int cores = Runtime.getRuntime().availableProcessors();
            ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
            mergedResult = forkJoinPool.invoke(tareaPagina);
            return mergedResult;
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

}