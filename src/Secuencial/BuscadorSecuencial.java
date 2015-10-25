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
import modelo.Archivo;
import modelo.PaginasWeb;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author manfred
 */
public class BuscadorSecuencial {

    public Archivo archivo = new Archivo();/////////////////////Borrar cuando se pegue todo            
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ArrayList<String> urlWebPages;
    public ArrayList<Resultado> resultados = new ArrayList();//resultados de las busquedas    
    

    /**
     * Función para buscar las coincidencias de una palabra en el contenido de
     * la página
     *
     * @param palabra la palabra a buscar en el contenido
     * @param contenido el texto de la página en el que se buscan coincidencias
     * @param url
     * @return la cantidad de coincidencias encontradas
     */
    public Resultado buscarCoincidencia(String palabra, String contenido, String url) throws IOException, SigarException {
        int coincidencias = 0;
        String texto = null;
        palabra = palabra.toLowerCase();
        String contenidoAux = contenido.toLowerCase();

        long startTime = System.currentTimeMillis();
        Document doc = Jsoup.connect(url).get();

        String titulo = doc.title();
        Resultado resultado = new Resultado(coincidencias, url, texto, titulo, palabra, 0l);
        
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
            resultado.infoCPUs.guardarUsoProcesadores();
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        long tiempoR = estimatedTime;
        //System.out.println("correcto");
        

        {   //Resultado resultado = new Resultado(coincidencias, url, this.extracto, titulo, this.palabra,0l);
            resultado.setCoincidencias(coincidencias);
            resultado.setTextoCoincidencia(texto);
            resultado.setPalabra(palabra);
            resultado.setTiempo(tiempoR);
        }
        
        return resultado;
    }

    public void buscar(String palabra) throws IOException, SigarException {
        //borrar
        //this.archivo.direccionArchivo = "//home//manfred//NetBeansProjects//TaskforceProjects//Buscador//trunk//src//Secuencial//urls.txt";
        this.archivo.direccionArchivo = "/home/carlos/Escritorio/urlWebPages.txt";
        //fin de borrar
        this.urlWebPages = archivo.leer();
        
        if (urlWebPages != null) {
            for (String urlWebPage : urlWebPages) {
                Document doc = Jsoup.connect(urlWebPage).get();
                String contenido = doc.body().text();
                //  System.out.println(urls[i]);
                Resultado resultado = buscarCoincidencia(palabra, contenido, urlWebPage);
                this.resultados.add(resultado);
                //ordenar();
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
    public ArrayList<Resultado> searchManager(String terminoBusqueda) throws IOException, SigarException {
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
        
        for (int i = 0; i < terminos.length; i++) {
            palabra = terminos[i];
            System.out.println(palabra);
            this.buscar(palabra);
        }
        

        /////////////////////////BORAR////////////////////////////////////
        for (int i = 0; i < this.resultados.size(); i++) {
            if (this.resultados.get(i).getCoincidencias() > 0) {
                System.out.println(this.resultados.get(i).descripcion());
                System.out.println("----------------------------------------------------------------");
            }
        }
        
        /////////////////////////BORAR////////////////////////////////////
        return this.resultados;
    }

    /**
     * calcula el tiempo de la coincidencia de cada palabra del termino de la
     * busqueda es decir, el tiempo que dura la busqueda con una determinada
     * palabra
     *
     * @param terminoBusqueda el termino de busqueda
     */
    public ArrayList<EstadisticaPalabra> calcularTiempoPalabra(String terminoBusqueda, ArrayList<Resultado> arrayResultados) {
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
            for (int j = 0; j < arrayResultados.size(); j++) {
                if (arrayResultados.get(j).getCoincidencias() > 0
                        && arrayResultados.get(j).getPalabra().equals(terminos[i])) {
                    palabra.setTiempo(palabra.getTiempo() + arrayResultados.get(j).getTiempo());
                }
            }
            tiemposPalabras.add(palabra);
        }
        for (int i = 0; i < tiemposPalabras.size(); i++) {
            System.out.println("-------------------------");
            System.out.println("Palabra: " + tiemposPalabras.get(i).getPalabra()
                    + "\nTiempo: " + tiemposPalabras.get(i).getTiempo() + " milisegungos");
        }
        return tiemposPalabras;
    }

    public void ordenar() {
        Collections.sort(this.resultados, new Comparator<Resultado>() {
            @Override
            public int compare(Resultado p1, Resultado p2) {
                return new Integer(p1.getCoincidencias()).compareTo(new Integer(p2.getCoincidencias()));
            }
        });
    }

}
