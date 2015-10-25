
package modelo;

import java.util.ArrayList;

public class CPU {
    
    public Integer intLink;
    public Integer porcentaje;
    public String strLink;
    public ArrayList<Integer> porcentajeCpus;
    
    public CPU(){
        this.intLink = 0;
        this.strLink = "";
        this.porcentajeCpus = new ArrayList<>();
        this.porcentaje = 0;
    }
    
    
    
}
