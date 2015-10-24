package modelo;

import java.io.*;
import java.util.ArrayList;
////home//manfred//NetBeansProjects//TaskforceProjects//Buscador//trunk//src//Secuencial//urls.txt
public class Archivo {///home/carlos/Escritorio/urlWebPages.txt
    public String direccionArchivo = "/home/carlos/Escritorio/urlWebPages.txt";
    public ArrayList<String> leer() {
        File archivo;
        FileReader fr = null;
        BufferedReader br;
        ArrayList<String> urlWebPages = new ArrayList<>(); 
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine())./home/carlos/Escritorio/
            archivo = new File(direccionArchivo);
            
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                urlWebPages.add(linea);
            }
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
        return urlWebPages;
    }

    public void escribir(ArrayList<String> urlWebPages) {
        FileWriter fichero = null;
        PrintWriter pw;
        try {
            fichero = new FileWriter(direccionArchivo);
            
            pw = new PrintWriter(fichero);

            for (int i = 0; i < 10; i++) {
                pw.println(urlWebPages.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
           // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
