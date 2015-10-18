/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secuencial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author manfred
 */
public class BuscadorSecuencial {

    ArrayList<Resultado> resultados = new ArrayList();//resultados de las busquedas
    long tiempo;// tiempo total de la busqueda

    /**
     * Función para buscar las coincidencias de una palabra en el contenido de
     * la página
     *
     * @param palabra la palabra a buscar en el contenido
     * @param contenido el texto de la página en el que se buscan coincidencias
     * @param url
     * @return la cantidad de coincidencias encontradas
     */
    public Resultado buscarCoincidencia(String palabra, String contenido, String url) throws IOException {
        int coincidencias = 0;
        String texto = null;
        palabra = palabra.toLowerCase();
        String contenidoAux = contenido.toLowerCase();

        long startTime = System.currentTimeMillis();

        while (contenidoAux.contains(palabra)) {
            //System.out.println(contenido.substring(contenido.indexOf(palabra),contenido.indexOf(palabra)+palabra.length()));
            if (texto == null) {
                if (contenidoAux.indexOf(palabra) - 40 >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 < contenidoAux.length()) {

                    texto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - 40, contenidoAux.indexOf(palabra) + palabra.length() + 40) + "...";

                } else if (contenidoAux.indexOf(palabra) - 40 >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 >= contenidoAux.length()) {

                    texto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - 40, contenidoAux.indexOf(palabra) + palabra.length());

                } else if (contenidoAux.indexOf(palabra) - 40 <= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 <= contenidoAux.length()) {
                    texto = contenido.substring(contenidoAux.indexOf(palabra), contenidoAux.indexOf(palabra) + palabra.length() + 40) + "...";

                }
            }
            contenido = contenido.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            contenidoAux = contenidoAux.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            coincidencias++;
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        long tiempoR = estimatedTime;
        Document doc = Jsoup.connect(url).get();
        String titulo = doc.title();
        Resultado resultado = new Resultado(coincidencias, url, texto, titulo, palabra, tiempoR);
        return resultado;
    }

    /**
     * Función para leer los url de las páginas del archivo y almacenarlos en un
     * arreglo, para mantenerlos en memoria dinámica
     *
     * @param rutaArchivo la ruta del archivo a leer
     * @return un arreglo con los urls leídos
     */
    public String[] leerURLs(String rutaArchivo) {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(rutaArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            int largo = 0;//cantidad de urls
            while (br.readLine() != null) {
                largo++;
            }
            // Lectura del fichero
            String linea;
            String[] urls = new String[largo];

            fr = new FileReader(archivo);//resetear valores del fileReader
            br = new BufferedReader(fr);//resetear valores del bufferReader

            int i = 0;//indice para el arreglo        
            while (i < largo) {
                linea = br.readLine();
                urls[i] = linea;
                i++;
            }
            return urls; // arreglo con las URLS
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public void buscar(String palabra) throws IOException {
        String[] urls = leerURLs("//home//manfred//NetBeansProjects//TaskforceProjects//Buscador//trunk//src//Secuencial//urls.txt");
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                String contenido = getContenidoHTML(urls[i]);
                Resultado resultado = buscarCoincidencia(palabra, contenido, urls[i]);
                this.resultados.add(resultado);
                ordenar();
            }
        }
    }

    /**
     * Funcion para obtener el contenido de un determinado url
     *
     * @param pagina url de la pagina a leer
     * @return un string, con el contenido del url
     * @throws IOException en caso de haber algun error en la lectura de la
     * pagina
     */
    private String getContenidoHTML(String pagina) throws IOException {
        try {
            // the HTML to convert
            URL url = new URL(pagina);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String finalContents = "";
            while ((inputLine = reader.readLine()) != null) {
                finalContents += "\n" + inputLine.replace("<br", "\n<br");
            }
            return finalContents;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * *
     * Funcion para ejecutar la busqueda y determinar el tiempo total de la
     * busqueda
     *
     * @param terminoBusqueda termino o terminos a buscar
     */
    public void searchManager(String terminoBusqueda) throws IOException {
        //limpiar el texto de espacios y dividirlo por palabras
        String terminoBusquedaAux = "";
        for (int x = 0; x < terminoBusqueda.length(); x++) {
            if (terminoBusqueda.charAt(x) != ' ') {
                terminoBusquedaAux += terminoBusqueda.charAt(x);
            }
        }
        terminoBusqueda = terminoBusquedaAux.replace("|", " ");
        String[] terminos = terminoBusqueda.split("\\s");//dividir el texto en palabras
        //
        String palabra;
        long startTime = System.currentTimeMillis();// empieza el tiempo de busqueda de los terminos
        for (int i = 0; i < terminos.length; i++) {
            palabra = terminos[i];
            this.buscar(palabra);
        }
        long estimatedTime = System.currentTimeMillis() - startTime;// finaliza el tiempo de busqueda de los terminos
        this.tiempo = estimatedTime / 1000; // convertir de milisegundos a segundos y asignarlo al tiempo de la busqueda

        for (int i = 0; i < this.resultados.size(); i++) {
            if (this.resultados.get(i).getCoincidencias() > 0) {
                System.out.println(this.resultados.get(i).descripcion());
                System.out.println("----------------------------------------------------------------");
            }
        }
        System.out.println("Tiempo total: " + this.tiempo);
    }

    /**
     * calcula el tiempo de la coincidencia de cada palabra del termino de la
     * busqueda es decir, el tiempo que dura la busqueda con una determinada
     * palabra
     *
     * @param terminoBusqueda el termino de busqueda
     */
    public void calcularTiempoPalabra(String terminoBusqueda) {
        //limpiar el texto de espacios y dividirlo por palabras
        String terminoBusquedaAux = "";
        ArrayList<EstadisticaPalabra> tiemposPalabras = new ArrayList<>();
        for (int x = 0; x < terminoBusqueda.length(); x++) {
            if (terminoBusqueda.charAt(x) != ' ') {
                terminoBusquedaAux += terminoBusqueda.charAt(x);
            }
        }
        terminoBusqueda = terminoBusquedaAux.replace("|", " ");
        String[] terminos = terminoBusqueda.split("\\s");//dividir el texto en palabras
        //
        for (int i = 0; i < terminos.length; i++) {
            EstadisticaPalabra palabra = new EstadisticaPalabra();
            palabra.setPalabra(terminos[i]);
            for (int j = 0; j < this.resultados.size(); j++) {
                if (this.resultados.get(j).getCoincidencias() > 0
                        && this.resultados.get(j).getPalabra().equals(terminos[i])) {
                    palabra.setTiempo(palabra.getTiempo() + this.resultados.get(j).getTiempo());
                }
            }
            tiemposPalabras.add(palabra);
        }
        for (int i = 0; i < tiemposPalabras.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("Palabra: " + tiemposPalabras.get(i).getPalabra()
                    + "\nTiempo: " + tiemposPalabras.get(i).getTiempo() + " milisegungos");
        }
    }

    public void ordenar() {
        Collections.sort(this.resultados, new Comparator<Resultado>() {
            @Override
            public int compare(Resultado p1, Resultado p2) {
                return new Integer(p1.getCoincidencias()).compareTo(new Integer(p2.getCoincidencias()));
            }
        });
    }

    public static void main(String[] args) throws IOException {
        BuscadorSecuencial buscador = new BuscadorSecuencial();
        buscador.searchManager("vida");
        buscador.calcularTiempoPalabra("vida");
    }

}
