import java.io.*;
import java.util.*;

/*The program counter register. It holds a value that corresponds with the position of the program that is being
   executed.*/
public class ProgramCounter extends Registers{
    
    //instance variables
    private String contents, 
                   programCounter;
    private GUI    gui;
    
    public ProgramCounter(GUI gui){
        this.gui       = gui;
        programCounter = "0";
    }
    
    public String getProgramCounter(){
       return programCounter;
    }
        
    public void addContents(String x){
        
       programCounter = x;
       
       gui.updatePrintOuts("          add " + programCounter + " to " + getName());
       gui.updatePC(programCounter);
       
    }

    public String getContents(){
        return programCounter;
    }
    
    public String getName(){
        return "PC";
    }
}