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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author manfred
 */
public class BuscadorSecuencial {

    Resultado[] resultados;
    long tiempo;

    /**
     * Función para leer el contenido de una página
     *
     * @param pagina dirección de la página a leer
     * @return Retorna un string con el contenido de la página
     */
    public String leerPagina(String pagina) {
        try {
            Document doc = Jsoup.connect(pagina).get();
            String title = doc.text();
            return title;
        } catch (IOException e) {
            System.out.println("Error leyendo el contenido de la página");
            return null;
        }
    }

    private String capitalize(String palabra) {
        return Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
    }

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

        while (contenidoAux.contains(palabra)) {
            //System.out.println(contenido.substring(contenido.indexOf(palabra),contenido.indexOf(palabra)+palabra.length()));
            if (texto == null) {
                if (contenidoAux.indexOf(palabra) - 40 >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 < contenidoAux.length()) {

                    texto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - 40, contenidoAux.indexOf(palabra) + palabra.length() + 40) + "...";

                } else if (contenidoAux.indexOf(palabra) - 40 >= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 >= contenidoAux.length()) {

                    texto = "..." + contenido.substring(contenidoAux.indexOf(palabra) - 40, contenidoAux.indexOf(palabra) + palabra.length());

                } else if (contenidoAux.indexOf(palabra) - 40 <= 0 && contenidoAux.indexOf(palabra) + palabra.length() + 40 <= contenidoAux.length()) {
//                    System.out.println("Aqui");
                    texto = contenido.substring(contenidoAux.indexOf(palabra), contenidoAux.indexOf(palabra) + palabra.length() + 40) + "...";

                }
            }
            contenido = contenido.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            contenidoAux = contenidoAux.substring(contenidoAux.indexOf(palabra) + palabra.length(), contenidoAux.length());
            coincidencias++;
        }
        Document doc = Jsoup.connect(url).get();
        String titulo = doc.title();
        Resultado resultado = new Resultado(coincidencias, url, texto, titulo,"");
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

    public Resultado[] buscar(String palabra) throws IOException {
        String[] urls = leerURLs("//home//manfred//NetBeansProjects//TaskforceProjects//Buscador//trunk//src//Secuencial//urls.txt");
        this.resultados = new Resultado[urls.length];
        if (urls != null) {
            for (int i = 0; i < resultados.length; i++) {
                String contenido = getContenidoHTML(urls[i]);
                Resultado resultado = buscarCoincidencia(palabra, contenido, urls[i]);

                //si es el primer                                
                if (i == 0) {
                    this.resultados[i] = resultado;
                } else {
                    //aqui vamos a insertar el resultado llamando a la funcion ordenada
                    int posicion = this.encontrarIndice(resultados, i, resultado);
                    this.resultados = this.insertarOrdenado(resultados, i, posicion, resultado);
                }
            }
        }
        return this.resultados;
    }

    /**
     * Funcion auxiliar para insertar ordenado devuelve el indice en el que se
     * debe insertar el nuevo resultado
     *
     * @param arrayResultados arreglo a ordenar
     * @param largoActual largo actual del arreglo, para efectos del proyecto,
     * el largo actal equivale al valor de "i" de la funcion donde es llamado
     * @param resultado el nodo a insertar
     * @return el indice en el que se debe insertar ese dato
     */
    public int encontrarIndice(Resultado[] arrayResultados, int largoActual, Resultado resultado) {
        int i = 0;
        while (i < largoActual && arrayResultados[i].getCoincidencias() < resultado.getCoincidencias()) {
            i++;
        }
        return i;
    }

    /**
     *
     * @param arrayResultados
     * @param tPosiciones
     * @param indice
     * @param resultado
     * @return
     */
    public Resultado[] insertarOrdenado(Resultado[] arrayResultados, int tPosiciones, int indice, Resultado resultado) {
        tPosiciones--;
        if (tPosiciones == arrayResultados.length) {
            System.out.println("Error, el arreglo ya está lleno");
            return arrayResultados;
        } else {

            for (int j = tPosiciones; j >= indice; j--) {
                arrayResultados[j + 1] = arrayResultados[j];
            }
            arrayResultados[indice] = resultado;
            return arrayResultados;
        }
    }

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

    public static void main(String[] args) throws IOException {
        BuscadorSecuencial buscador = new BuscadorSecuencial();

        long startTime = System.currentTimeMillis();
        buscador.buscar("vida");
        long estimatedTime = System.currentTimeMillis() - startTime;
        buscador.tiempo = estimatedTime / 1000;
        System.out.println("largo: " + buscador.resultados.length);
        for (int i = 0; i < buscador.resultados.length; i++) {
            if (buscador.resultados[i].getCoincidencias() > 0) {
                System.out.println(buscador.resultados[i].descripcion());
                System.out.println("----------------------------------------------------------------");
            }
        }
        System.out.println("tiempo de ejecución:" + buscador.tiempo + "segundos");
    }
}
