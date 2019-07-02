import java.io.*;
import java.util.*;

/*The general register class. Refers to the registers that are used in assembly code*/
public class GeneralRegister extends Registers{
    
    /*Instance Variables*/
    private int     id;
    private String  contents;
    private GUI     gui;
    
    public GeneralRegister(int id, GUI gui){
        this.id  = id;
        this.gui = gui;
    }

    public String getName(){
        return "Register " + id;
    }
   
    public void addContents(String x){
        
       contents = x;
       
       gui.updatePrintOuts("          Add " + contents + " to " + getName());
       gui.updateRegister(id-1, contents);
       
    }

    public String getContents(){
        return contents;
      }
    
}

