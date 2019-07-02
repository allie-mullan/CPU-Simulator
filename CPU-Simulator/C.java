import java.io.*;
import java.util.*;

/*The c register. It holds a value that has just come from the ALU.*/
public class C extends Registers{
    //instance variables
    private String  contents;
    private GUI     gui;
    public C(GUI gui){
        this.gui = gui;
    }
    
    public void addContents(String x){
        
        contents = x;
        
        gui.updatePrintOuts("          add " + contents + " to " + getName());
        gui.updateC(contents);
        
    }

    public String getContents(){
        return contents;
    }
    
    public String getName(){
        return "CC";
    }
}