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
            String title = doc.text();
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

    private String capitalize(String palabra) {
        return Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
    }

    /*public int buscarCoincidencia(String palabra, String[] palabras) {
     int coincidencias = 0;
     String palabraCap = capitalize(palabra);

     for (int i = 0; i < palabras.length; i++) {
     if (palabras[i].equals(palabra) || palabras[i].equals(palabra + ",")
     || palabras[i].equals(palabra + ".") || palabras[i].equals(palabra + ":")
     || palabras[i].equals(palabra + ";") || palabraCap.equals(palabras[i])
     || palabras[i].equals("("+palabra+")") || palabras[i].equals("("+palabra+").")
     || palabras[i].equals("("+palabra+"),")|| palabras[i].equals(palabra+")")
     || palabras[i].equals("("+palabra)
     || palabraCap.equals(palabras[i]+",") || palabraCap.equals(palabras[i]+".")
     || palabraCap.equals(palabras[i]+":") || palabraCap.equals(palabras[i]+";")
     || palabraCap.equals("("+palabras[i]+")") || palabraCap.equals("("+palabras[i]+").")
     || palabraCap.equals("("+palabras[i]+"),")|| palabraCap.equals(palabras[i]+")")
     || palabraCap.equals("("+palabras[i])) {
     System.out.println(palabras[i]);
     coincidencias++;
     }
     }
     return coincidencias;
     }*/
    /**
     * Función para buscar las coincidencias de una palabra en el contenido de
     * la página
     *
     * @param palabra la palabra a buscar en el contenido
     * @param contenido el texto de la página en el que se buscan coincidencias
     * @return la cantidad de coincidencias encontradas
     */
    public int buscarCoincidencia(String palabra, String contenido) {
        int coincidencias = 0;
        palabra = palabra.toLowerCase();
        String texto = null;
        String contenidoAux =  contenido.toLowerCase();
//        System.out.println(contenido);
        while (contenido.contains(palabra)) {
            //System.out.println(contenido.substring(contenido.indexOf(palabra),contenido.indexOf(palabra)+palabra.length()));
            if (texto == null) {
                if (contenido.indexOf(palabra) - 40 >= 0 && contenido.indexOf(palabra) + palabra.length() + 40 < contenido.length()) {

                    texto = "..." + contenido.substring(contenido.indexOf(palabra) - 40, contenido.indexOf(palabra) + palabra.length() + 40) + "...";
                    
                } else if (contenido.indexOf(palabra) - 40 >= 0 && contenido.indexOf(palabra) + palabra.length() + 40 >= contenido.length()) {
                    
                    texto = "..." + contenido.substring(contenido.indexOf(palabra) - 40, contenido.indexOf(palabra) + palabra.length());
                } else if (contenido.indexOf(palabra) - 40 <= 0 && contenido.indexOf(palabra) + palabra.length() + 40 <= contenido.length()) {
//                    System.out.println("Aqui");
                    texto = contenido.substring(contenido.indexOf(palabra), contenido.indexOf(palabra) + palabra.length() + 40) + "...";
                } else if (contenido.indexOf(palabra) - 40 >= 0 && contenido.indexOf(palabra) + palabra.length() + 40 <= contenido.length()) {
                    texto = "..." + contenido.substring(contenido.indexOf(palabra) - 1, contenido.indexOf(palabra) - 1);
                }
            }
            contenido = contenido.substring(contenido.indexOf(palabra) + palabra.length(), contenido.length());
            coincidencias++;
        }
        System.out.println(texto);
        return coincidencias;
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

    public static void main(String[] args) {
        BuscadorSecuencial busqueda = new BuscadorSecuencial();
        String[] urls = busqueda.leerURLs("//home//manfred//NetBeansProjects//TaskforceProjects//Buscador//trunk//src//Secuencial//urls.txt");
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                String contenido = busqueda.leerPagina(urls[i]);
                int coincidencias = busqueda.buscarCoincidencia("CIF", contenido);
                System.out.println("Coincidencias en " + urls[i] + " : " + coincidencias);
            }
        }
    }
}
