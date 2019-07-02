import java.io.*;
import java.util.*;

/*The immediate register. A type of register that holds a string. It is used for immediate values in the assembly code.*/
public class ImmediateRegister extends Registers{
    
    //instance variables
    private String contents;
    private GUI gui;
     
    public ImmediateRegister(GUI gui){
        this.gui = gui;
    }

    public String getName(){
        return "IM";
    }
   
    public void addContents(String x){
        
       contents = x;
       
       gui.updatePrintOuts("          add " + contents + " to " + getName());
       gui.updateIM(contents);
       
    }

    public String getContents(){
        return contents;
    }
    
}