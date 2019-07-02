import java.io.*;
import java.util.*;

/*A parent class for all of the objects in the CPU that are registers.*/

public class Registers{
    
    //instance variables
    private String  contents, 
                    name;
    private boolean full;
    
    public Registers(){ 
    }
    
    //All the registers are containers able to contain some kind of string
    public void addContents(String x){
    }

    //getter method for the contents of the register
    public String getContents(){
        return contents;
    }
    
    //checks to see if the register is full or empty
    public boolean getFull(){
        return full;
    }
    
    //returns the name of the register
    public String getName(){
        return name;
    }
}
