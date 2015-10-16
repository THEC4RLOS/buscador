/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secuencial;

import java.io.IOException;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author manfred
 */
public class BuscadorSecuencial {

    /**
     * Función para leer el contenido de una página
     *
     * @param pagina dirección de la página a leer
     * @return Retorna un string con el contenido de la página
     */
    public String leerPagina(String pagina) {
        try {
            Document doc = Jsoup.connect(pagina).get();
            String title = doc.body().text();
            return title;
        } catch (IOException e) {
            System.out.println("Error leyendo el contenido de la página");
            return null;
        }
    }

    /**
     * Función para crear un arreglo de palabras a partir del contenido de un
     * string
     *
     * @param contenido contenido de la página (texto a separar en palabras)
     * @return devuelve un arreglo de string (palabras) del contenido de la
     * página
     */
    public String[] split(String contenido) {
        //StringTokenizer tokenPalabras = new StringTokenizer(contenido);
        //String [] palabras = new String[tokenPalabras.countTokens()];
        String[] palabras = contenido.split("\\s");

        return palabras;
    }

    public int buscarCoincidencia(String palabra, String[] palabras) {
        int coincidencias = 0;
        for (int i = 0; i < palabras.length; i++) {
            if(palabras[i]==palabra){
                coincidencias ++;
            }
        }
        
        return coincidencias;
    }

    public static void main(String[] args) {
        BuscadorSecuencial busqueda = new BuscadorSecuencial();

        String contenido = busqueda.leerPagina("https://es.wikipedia.org/wiki/Vida");
        System.out.println("/////////////////////////////////////");
        //System.out.println(contenido);
        System.out.println("largo de contenido: " + contenido.length());
        System.out.println("/////////////////////////////////////");
        busqueda.split(contenido);
    }
}
