import java.util.*;
import java.io.*;

/* The main memory class is not a register. Instead it is an object that uses a hashmap to store information at an address.
   The key of the hashmap is the address in memory while the value is what is stored at that particular address.*/
   
public class MainMemory{
    /*Instance Variables*/
    
    private double programSize,
                   noOfBits, 
                   currentDouble,
                   lineNumber;
                   
    private HashMap<Double, String> programInstructions = new HashMap<Double, String>();               
    private Stack<Double>           stack;

    //the constructor for main memory. It currently takes the number of bits, which is unnecessary and I should fix
    public MainMemory(double noOfBits){
        noOfBits      = ((noOfBits-4)/2);
        lineNumber    = 0;
        this.noOfBits = noOfBits;
    }

    //scans the memory code program to 
    public void readProgram(){
        try{

            Scanner scLine = new Scanner(new File("machinecode1.txt")); //machine code that is carried out is hard coded in

            while(scLine.hasNextLine()){

                String line = scLine.nextLine(); 
               
                programInstructions.put(lineNumber, line); //saves each line of the machine code in the 'lineNumber' address in memory
                
                lineNumber ++; //line number corresponds to address in memory

            }
        }catch(IOException e){
            System.out.println("Exception: " + e);
        }  

        programSize = lineNumber; //saves the size of the program

    }
    
    //had I implemented a stack these would be used 
    public void pushToStack(double x){
        stack.push(x);
    }
    
    public double  popfromStack(){
        return stack.pop();
    }

    //getter methods
    public double getProgramSize(){
        return programSize;
    }
    
    public double getImmediateInt(){
        return currentDouble;
    }
    //given an address, returns the contents of main memory stored at this address
    public String getLineAtThisAddress(String address){
        try{    
            
            double a = Double.parseDouble(address);
            
            return programInstructions.get(a);
            
        }catch(NumberFormatException e){
            System.out.println(e);            
        }
        return "";
    }
}
