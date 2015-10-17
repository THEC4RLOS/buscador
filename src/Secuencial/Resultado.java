/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Secuencial;

/**
 *
 * @author manfred
 */
public class Resultado {
    int coincidencias;

    public Resultado(int coincidencias, String url, String textoCoincidencia) {
        this.coincidencias = coincidencias;
        this.url = url;
        this.textoCoincidencia = textoCoincidencia;
    }
    String url;
    String textoCoincidencia;
    
    public String descripcion(){
        String desc = "\n URL: "+this.url+"\n Coincidencias: "+this.coincidencias+"\n texto: "+ this.textoCoincidencia;
        return desc;
    }
}
