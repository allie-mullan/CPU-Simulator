import java.io.*;
import java.util.*;

/*The memory data register. It holds a value that corresponds with the data that has been gotten from main memory
 * to then be moved to the bus.*/
public class MemoryD extends Registers{
    
    //instance variables
    private String  contents;
    private GUI     gui;
    
    public MemoryD(GUI gui){
        this.gui = gui;
    }
    
    public void addContents(String x){
        
       contents = x;
       
       gui.updatePrintOuts("          add " + contents + " to " + getName());
       gui.updateMD(contents);
       
    }

    public String getContents(){
        return contents;
    }
    
    public String getName(){
        return "MD";
    }
}