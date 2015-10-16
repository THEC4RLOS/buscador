package buscador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
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
    
    public static void main(String[] args) throws Exception {
        String texto = "I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+                
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers "+
"I will here give a brief sketch of the progress of opinion on the \n" +
"Origin of Species. Until recently the great majority of naturalists \n" +
"believed that species were immutable productions, and had been \n" +
"separately created. This view has been ably maintained by many \n" +
"authors. Some few naturalists, on the other hand, have believed \n" +
"that species undergo modification, and that the existing forms of \n" +
"life are the descendants by true generation of pre existing forms. \n" +
"Passing over allusions to the subject in the classical writers ";
        /* http://elpais.com/elpais/portada_america.html
        http://edition.cnn.com/
        http://www.foxnews.com/
        http://www.20minutos.es/
        wall street journal
        http://www.wsj.com/
        */
        Texto myRecursiveTask = new Texto(0,texto,texto,"undergo");
        String textoAux = texto;
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        int mergedResult = forkJoinPool.invoke(myRecursiveTask);

        System.out.println("mergedResult = " + mergedResult);
        int contador=0;
        String palabra="undergo";
        while (texto.contains(palabra)) {
            texto = texto.substring(texto.indexOf(palabra)+palabra.length(),texto.length());
            contador++; 
        }
        System.out.println(contador);
    }
}