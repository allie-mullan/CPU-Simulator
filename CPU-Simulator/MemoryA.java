import java.io.*;
import java.util.*;

/*The memory address register. It is used to hold a String that corresponds to an address in memory.*/
public class MemoryA extends Registers{
    
    //instance variables
    private String contents;
    private GUI    gui;
    
    public MemoryA(GUI gui){
        this.gui = gui;
    }
    
    public void addContents(String x){
        
       contents = x;
       
       gui.updatePrintOuts("          add " + contents + " to " + getName());
       gui.updateMA(contents);
       
    }

    public String getContents(){
        return contents;
    }
    
    //gets the line in memory at the address that is in it's contents
    public String getLineAtThisMemoryAddress(String address, MainMemory mainMem){
        return mainMem.getLineAtThisAddress(address);        
    }
    
    public String getName(){
        return "MA";
    }
}