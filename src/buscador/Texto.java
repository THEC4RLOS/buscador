package buscador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
    
// Busqueda realizada en un texto de las apaciones de una palabra
public class Texto extends RecursiveTask<Integer> {

    private int vecesEncontrada = 0;
    private String texto = "";
    private String textoAux = "";
    private String palabra = "";

    
    public Texto(int vecesEncontrada, String texto, String textoAux, String palabra) {
        this.vecesEncontrada = vecesEncontrada;
        this.texto = texto;
        this.textoAux = textoAux;        
        this.palabra = palabra;
    }

    @Override
    protected Integer compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if(this.textoAux.length() > this.texto.length()/50) {
            //System.out.println("Splitting workLoad : " + this.workLoad);

            List<Texto> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for(Texto subtask : subtasks){
                subtask.fork();
            }

            int result = 0;
            for(Texto subtask : subtasks) {
                result += subtask.join();
            }
            return result;

        } else {
            //System.out.println("Doing workLoad myself: " + this.workLoad);
            //vecesEncontrada =textoAux.indexOf(palabra);
            int contador=0;
            while (textoAux.contains(palabra)) {
                textoAux = textoAux.substring(textoAux.indexOf(palabra)+palabra.length(),textoAux.length());
                contador++; 
            }
            return contador;
        }
    }

    private List<Texto> createSubtasks() {
        List<Texto> subtasks = new ArrayList<>();
        int mid = textoAux.length()/2;
        Texto subtask1 = new Texto(vecesEncontrada, texto, 
                textoAux.substring(0, mid), palabra);
        
        Texto subtask2 = new Texto(vecesEncontrada, texto, 
                textoAux.substring(mid+1, textoAux.length()), palabra);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }
    
}