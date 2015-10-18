
package buscador;

import Paralelo.Palabra;
import Secuencial.Resultado;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import modelo.PaginasWeb;

public class Buscador {
    
    public ArrayList<PaginasWeb> paginasWeb;

    public Buscador(ArrayList<PaginasWeb> paginasWeb) {
        this.paginasWeb = paginasWeb;
    }
    
    public static void main(String[] args) {
        
        ArrayList<String> pw = new ArrayList<>();
        //pw.add("http://elpais.com/elpais/portada_america.html");//2
        //pw.add("http://edition.cnn.com/");// 0 5
        //pw.add("http://www.20minutos.es/");// 0 0
        /*String enlaceAAceder = "http://www.example.com";
        Desktop enlace=Desktop.getDesktop();
        try {
                enlace.browse(new URI(enlaceAAceder));
                System.out.println("abrio");
        } catch (IOException | URISyntaxException e) {
            e.getMessage();
            System.out.println(e.getMessage());
        }*/
        pw.add("http://www.wsj.com/");
        String p = "Wall";
        Palabra myRecursiveTask = new Palabra(pw,p);
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        /*ArrayList<PaginasWeb> mergedResult = forkJoinPool.invoke(myRecursiveTask);
        
        for (PaginasWeb paw: mergedResult){
            System.out.println(paw.getUrl());
            for (Resultado pawq: paw.getListaResultados()){
                System.out.println(pawq.getPalabra() + " " +pawq.getCoincidencias() 
                        + " " + pawq.getTextoCoincidencia() + " " 
                        + pawq.getUrl() + " " + pawq.getTitulo());
            }
        }*/
        //System.out.println("mergedResult = " + mergedResult);
    }
}
/*
 try {
 // the HTML to convert
 URL url = new URL("http://www.javadb.com/write-to-file-using-bufferedwriter");
 URLConnection conn = url.openConnection();
 BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
 String inputLine;
 String finalContents = "";
 while ((inputLine = reader.readLine()) != null) {
 finalContents += "\n" + inputLine.replace("<br", "\n<br");
 }
 System.out.println(finalContents);
 }catch (IOException e) {
 }


ArrayList<Integer> arrlist = new ArrayList<Integer>(5);

   // use add() method to add elements in the deque
   arrlist.add(20);
   arrlist.add(15);
   arrlist.add(30);
   arrlist.add(45);

   System.out.println("Size of list: " + arrlist.size());
	
   // let us print all the elements available in list
   for (Integer number : arrlist) {
   System.out.println("Number = " + number);
   }  
	
   // Removes element at 3rd position
   arrlist.remove(0);
   arrlist.remove(0);
   arrlist.remove(0);
   arrlist.remove(0);
   

   System.out.println("Now, Size of list: " + arrlist.size());
	
   // let us print all the elements available in list
   for (Integer number : arrlist) {
   System.out.println("Number = " + number);
   }


// Main Texto.java
public static void main(String[] args) throws Exception {
        String texto = "s";
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



    // Main Pagina.java
    public static void main(String[] args) throws Exception {
        
        ArrayList<String> pw = new ArrayList<>();
        pw.add("http://elpais.com/elpais/portada_america.html");// 1
        pw.add("http://edition.cnn.com/");// 1
        pw.add("http://www.foxnews.com/"); //5
        pw.add("http://www.20minutos.es/");// 1
        pw.add("http://www.wsj.com/"); // 7
        String p = "Obama";
        Pagina myRecursiveTask = new Pagina(pw,"",p);
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        int mergedResult = forkJoinPool.invoke(myRecursiveTask);

        System.out.println("mergedResult = " + mergedResult);
    }



main Buscador
// en.wikipedia.org  www.cnn.com   www.example.com
        StringBuffer s;
        try {
            Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
            String title = doc.body().text();
            System.out.print(title);
        } catch (IOException e) {
        }



String link = "http://www.wsj.com/";
        Document doc = Jsoup.connect(link).get();
        String texto = doc.text();
        //System.out.println(texto);
        /*
         URL url = new URL(link);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        String finalContents = "";
        while ((inputLine = reader.readLine()) != null) {
        finalContents += "\n" + inputLine.replace("<br", "\n<br");
        }
        texto = finalContents;///
        
 
        String textoAux = texto;
        String palabra = "Obama";
        Texto myRecursiveTask = new Texto(0,texto,texto,palabra);
        int contador=0;
        while (textoAux.contains(palabra)) {
            textoAux = textoAux.substring(textoAux.indexOf(palabra)+palabra.length(),textoAux.length());
            contador++; 
        }
        System.out.println(contador);
        
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        int mergedResult = forkJoinPool.invoke(myRecursiveTask);

        System.out.println("mergedResult = " + mergedResult);

*/
