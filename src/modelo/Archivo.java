package modelo;

import java.io.*;
import java.util.ArrayList;

public class Archivo {

    public ArrayList<String> leer() {
        File archivo;
        FileReader fr = null;
        BufferedReader br;
        ArrayList<String> urlWebPages = new ArrayList<>(); 
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine())./home/carlos/Escritorio/
            //archivo = new File("C:\\urlWebPages.txt");
            archivo = new File("/home/carlos/Escritorio/urlWebPages.txt");
            
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
            //fichero = new FileWriter("c://urlWebPages.txt");
            fichero = new FileWriter("/home/carlos/Escritorio/urlWebPages.txt");
            
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
