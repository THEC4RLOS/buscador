/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

/**
 *
 * @author carlos
 */
public class Buscador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // en.wikipedia.org  www.cnn.com   www.example.com
        StringBuffer s;
        try {
            Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
            String title = doc.body().text();
            System.out.print(title);
        } catch (IOException e) {
        }

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

*/
