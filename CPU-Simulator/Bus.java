import java.io.*;
import java.util.*;

/*This is the bus that can take a String and move it from one register or object to another*/
public class Bus{
    /*Instance Variables*/

    private String   x, 
    contents;
    private double   busSize;

    /**
     * Constructor for objects of class Bus
     */
    public Bus(double busSize){
        this.busSize = busSize;
    }

    //Adds a string to the bus if it is not already occupied by another string
    public void addToBus(String x){
        contents = x;
    }

    //move the contents to the destination register, set the bus empty
    public void moveContentsTo(Registers destination){
        destination.addContents(contents);
    }

    //a getter method for the contents of the bus
    public String contents(){
        return contents;
    }

    //Moves the contents of the bus to the 'first' alu spot
    public void moveContentsToAluFirst(ALU alu){
        double a = Double.parseDouble(contents);
        alu.addFirst(a);
    }

    //Moves the contents of the bus to the 'second' alu spot
    public void moveContentsToAluSecond(ALU alu){
        double a = Double.parseDouble(contents);
        alu.addSecond(a);
    }
}
