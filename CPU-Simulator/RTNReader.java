import java.io.*;
import java.util.*;
/**
 * This class reads and parses a file that is passed in.
 * 
 * @Allie Mullan 
 * @10/25/2016
 */
public class RTNReader{
    
    //instance vars
    private double noOfRegisters;
    private String nameOfRTNFile, 
                   opcode, 
                   destination, 
                   source;
    private HashMap<String, ArrayList<String>> rtnMap = new HashMap<String, ArrayList<String>>();
            
    //the constructor. takes a String that will be the name of the rtn file
    public RTNReader(String nameOfRTNFile){
        this.nameOfRTNFile = nameOfRTNFile;
    }

    //reads the rtn file and saves the different components to different variables
    public void readRTNFile(){   
        try{

            Scanner scOpcode    = new Scanner(new File(nameOfRTNFile)).useDelimiter("opcode ");
            String opcodeBlock  = "";
            
            while(scOpcode.hasNext()){
                                       
                //determine the opcode
                opcodeBlock     = scOpcode.next(); //the opcode and each line of rtn that goes with it
                Scanner scan    = new Scanner(opcodeBlock);
                
                opcode          = scan.next(); //save the name of the opcode
                
                ArrayList<String> sdArray = new ArrayList<String>(); 
                
                //get the RTN instructions piece by piece and add them to the array
                while(scan.hasNext()){
                    
                    String string = scan.next(); //get the next RTN instruction, separated by a space
                    sdArray.add(string); //add the string to the array 
                    
                }
               
                rtnMap.put(opcode, sdArray); //add the opcode and the array of RTN instructions to the RTN map
                
            }
        }catch(IOException e){
            System.out.println("Exception: " + e);
        }  
    }
    
    //a getter for the array of RTN code the corresponds with the given opcode
    public ArrayList<String> getInstructions(String opcode){
        return rtnMap.get(opcode);
    }
}
