package Paralelo;

import Secuencial.Resultado;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

// Busqueda realizada en un texto de las apaciones de una palabra
public class Texto extends RecursiveTask<Resultado> {

    private int vecesEncontrada = 0;
    private String texto = "";
    private String textoAux = "";
    private String palabra = "";
    private String extracto = null;

    public Texto(int vecesEncontrada, String texto, String textoAux, String palabra) {
        this.vecesEncontrada = vecesEncontrada;
        this.texto = texto;
        this.textoAux = textoAux;
        this.palabra = palabra;
    }

    @Override
    protected Resultado compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if (this.textoAux.length() > this.texto.length() / 50) {

            List<Texto> subtasks = new ArrayList<>();
            subtasks.addAll(createSubtasks());

            for (Texto subtask : subtasks) {
                subtask.fork();
            }
            Resultado result = new Resultado(0, "", this.extracto, "", this.palabra,0l);
            Resultado resultAux;
            //int result = 0;
            for (Texto subtask : subtasks) {
                //result += subtask.join();
                resultAux = subtask.join();
                result.setCoincidencias(result.getCoincidencias() + resultAux.getCoincidencias());
                result.setTiempo(result.getTiempo()+resultAux.getTiempo());
                if (result.getTextoCoincidencia() == null || result.getTextoCoincidencia().equals("")) {
                    result.setTextoCoincidencia(resultAux.getTextoCoincidencia());
                }
            }
            //imprimir(result);
            return result;

        } else {
            /*
             int contador=0;
             while (textoAux.contains(palabra)) {
             textoAux = textoAux.substring(textoAux.indexOf(palabra)+palabra.length(),textoAux.length());
             contador++; 
             }
             return contador;*/
            return buscarCoincidencia(palabra, textoAux);
        }
    }

    private List<Texto> createSubtasks() {
        List<Texto> subtasks = new ArrayList<>();
        int mid = textoAux.length() / 2;
        Texto subtask1 = new Texto(vecesEncontrada, texto,
                textoAux.substring(0, mid), palabra);

        Texto subtask2 = new Texto(vecesEncontrada, texto,
                textoAux.substring(mid + 1, textoAux.length()), palabra);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

    /**
     * Función para buscar las coincidencias de una palabra en el contenido de
     * la página
     *
     * @param palabra la palabra a buscar en el contenido
     * @param contenido el texto de la página en el que se buscan coincidencias
     * @return la cantidad de coincidencias encontradas
     */
    public Resultado buscarCoincidencia(String palabra, String contenido) {
        int coincidencias = 0;
        //String extracto = null;
        //System.out.println(contenido);
        int limite = 20;
        palabra = palabra.toLowerCase();
        String contenidoAux = contenido.toLowerCase();
        long startTime = System.currentTimeMillis();
        
        while (contenidoAux.contains(palabra)) {
            //System.out.println(contenido.substring(contenido.indexOf(palabra),contenido.indexOf(palabra)+palabra.length()));
            if (this.extracto == null || this.extracto.equals("")) {
                if (contenidoAux.indexOf(palabra) - limite >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + limite < contenidoAux.length()) {

                    this.extracto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - limite, contenidoAux.indexOf(palabra) + palabra.length() + limite) + "...";

                } else if (contenidoAux.indexOf(palabra) - limite >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + limite >= contenidoAux.length()) {

                    this.extracto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - limite, contenidoAux.indexOf(palabra) + palabra.length());

                } else if (contenidoAux.indexOf(palabra) - limite <= 0 && contenidoAux.indexOf(palabra) + palabra.length() + limite <= contenidoAux.length()) {
//                    System.out.println("Aqui");
                    this.extracto = contenido.substring(contenidoAux.indexOf(palabra), contenidoAux.indexOf(palabra) + palabra.length() + limite) + "...";

                }
            }
            contenido = contenido.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            contenidoAux = contenidoAux.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            coincidencias++;
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        long tiempoR = estimatedTime;
        String url = "", titulo = "";

        Resultado resultado = new Resultado(coincidencias, url, this.extracto, titulo, this.palabra,tiempoR);

        return resultado;
    }
    
    public void imprimir(Resultado qwerty){
        if (qwerty.getTextoCoincidencia() != null && !"".equals(qwerty.getTextoCoincidencia())){
            System.out.println("<IIIIIIIIII    Texto    IIIIIIIII");
            System.out.println(qwerty.getCoincidencias());
            System.out.println(qwerty.getPalabra());
            System.out.println(qwerty.getTextoCoincidencia());
            System.out.println(qwerty.getTitulo());
            System.out.println(qwerty.getUrl());
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII>");}
        
    }

}
