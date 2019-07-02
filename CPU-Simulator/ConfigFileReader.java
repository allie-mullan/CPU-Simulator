import java.io.*;
import java.util.*;

/**
 * Reads and parses the config file. Saves the pieces and contains getters to get them. 
 * 
 * @Allie Mullan 
 * @10/25/2016
 */

public class ConfigFileReader{

    //instance vars
    private double wordSize, 
                   busSize, 
                   noOfRegisters;
    private String nameOfConfigFile, 
                   line, 
                   word, 
                   aluFunctionality;
    private ArrayList<Double> aluFunctArray = new ArrayList<Double>();

    //the constructor. Takes in a String that is the name of the file the program will parse
    public ConfigFileReader(String nameOfConfigFile){
        this.nameOfConfigFile = nameOfConfigFile;
    }

    //Scans the config file and saves the various parts of it
    public void readConfigFile(){  
        try{

            Scanner scLine = new Scanner(new File(nameOfConfigFile));

            while(scLine.hasNextLine()){

                line           = scLine.nextLine();
                Scanner scWord = new Scanner(line);
                word           = scWord.next();

                if(word.equals("wordSize")){ //saves the word size
                    wordSize = Double.parseDouble(scWord.next());
                }else if(word.equals("busSize")){ //saves the size of the bus
                    busSize = Double.parseDouble(scWord.next());
                }else if(word.equals("aluFunctionality")){ //saves the String of 1s and 0s that correspond with which ALU operation is turned on and off

                    aluFunctionality = scWord.next();

                    for(int i = 0; i < aluFunctionality.length(); i++){ 
                        String q = "" + aluFunctionality.charAt(i);
                        aluFunctArray.add(Double.parseDouble(q));
                    }

                }else if(word.equals("noOfRegisters")){ //saves the number of registers
                    noOfRegisters = Double.parseDouble(scWord.next());
                }
            }
        }catch(IOException e){
            System.out.println("Exception: " + e);
        }  
    }

    //prints the ALU functionality arrat
    public void printALUFunctArray(){
        for(int i = 0 ; i<10; i++){
            System.out.println(aluFunctArray.get(i));
        }
    }

    //ALL THE GETTERS 
    public double busSize(){
        return busSize;
    }
    
    public double wordSize(){
        return wordSize;
    }

    public ArrayList<Double> getALUFunctArray(){
        return aluFunctArray;
    }
    
     public double noOfRegisters(){
        return noOfRegisters;
    }

}

