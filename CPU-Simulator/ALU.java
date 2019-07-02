import java.io.*;
import java.util.*;

/**
 * The ALU accepts two things and performs some kind of operation on them. The ALU has a few different
 * operations that it is capable of carrying out, however, the constructor takes a code which determines
 * which of these operations are available for use in that instance of the ALU object.
 * 
 * @author Allie Mullan 
 * @version 11/11/2016
 */
public class ALU{

    /*Instance Variables*/
    private ArrayList<Double> aluFuncitonalityCode;
    private GUI               gui;
    private double            add, 
                              subtract, 
                              multiply, 
                              result, 
                              firstDouble, 
                              secondDouble, 
                              x, 
                              divide, 
                              xor, 
                              or,
                              and,
                              sal,
                              sar;
    
    /**
     * Constructor for objects of class ALU
     */
    public ALU(ArrayList<Double> aluFuncitonalityCode, GUI gui){
        
        // initialise instance variables
        this.aluFuncitonalityCode = aluFuncitonalityCode;
        this.gui                  = gui;

        add      = aluFuncitonalityCode.get(0);
        subtract = aluFuncitonalityCode.get(1);
        multiply = aluFuncitonalityCode.get(2);
        divide   = aluFuncitonalityCode.get(3);
        xor      = aluFuncitonalityCode.get(4);
        or       = aluFuncitonalityCode.get(5);
        and      = aluFuncitonalityCode.get(6);
        sal      = aluFuncitonalityCode.get(7);
        sar      = aluFuncitonalityCode.get(8);

    }

    public String execute(String operation){
        if(operation.charAt(0)== '+'){
            gui.updatePrintOuts("               execute:" + firstDouble + " + " + secondDouble);
            return add(firstDouble, secondDouble);
        }else if(operation.charAt(0)== '-'){
            gui.updatePrintOuts("               execute:" +firstDouble + " - " + secondDouble);
            return subtract(firstDouble, secondDouble);
        }else if(operation.charAt(0)== '*'){
            gui.updatePrintOuts("               execute:" +firstDouble + " * " + secondDouble);
            return multiply(firstDouble, secondDouble);
        }else if(operation.charAt(0)== '/'){
            gui.updatePrintOuts("               execute:" +firstDouble + " / " + secondDouble);
            return divide(firstDouble, secondDouble);
        }else if(operation.charAt(0)== '&'){
            gui.updatePrintOuts("               execute:" +firstDouble + " & " + secondDouble);
            return and((int)(firstDouble), (int)(secondDouble));
        }else if(operation.charAt(0)== '|'){
            gui.updatePrintOuts("               execute:" +firstDouble + " | " + secondDouble);
            return or((int)(firstDouble), (int)(secondDouble));
        }else if(operation.charAt(0)== '^'){
            gui.updatePrintOuts("               execute:" +firstDouble + " ^ " + secondDouble);
            return xor((int)(firstDouble), (int)(secondDouble));
        }else if(operation.charAt(2)== 'r'){
            gui.updatePrintOuts("               execute:" +firstDouble + " >> " + secondDouble);
            return sar((int)(firstDouble), (int)(secondDouble));
        }else if(operation.charAt(2)== 'l'){
            gui.updatePrintOuts("               execute:" +firstDouble + " << " + secondDouble);
            return sal((int)(firstDouble), (int)(secondDouble));
        }
        
        return "";
    }

    //Adds a double to the 'A' place in the ALU 
    public void addFirst(double x){
        
        firstDouble = x;
        
        gui.updatePrintOuts("     add " + firstDouble + " to ALU A");
    
    }

    //Adds a double to the 'B' place in the ALU 
    public void addSecond(double x){
    
        secondDouble = x;
        
        gui.updatePrintOuts("     add " + secondDouble + " to ALU B");
        
    }

    //add the first and second 
    public String add(double a, double b){
        if(add == 1){
            result = a+b;
            return result + "";
        }else{
            return "";
        }
    }

    //subtract the first and second
    public String subtract(double a, double b){
        if(subtract == 1){
            result = a-b;
            return result + "";
        }else{
            return "";
        }
    }

    //multiply the first and second
    public String multiply(double a, double b){
        if(multiply == 1){
            result = a*b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //divide
    public String divide(double a, double b){
        if(divide == 1){
            result = a/b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //sar
    public String sar(int a, int b){
        if(sar == 1){
            System.out.println(a>>b);
            result = a>>b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //sal
    public String sal(int a, int b){
        if(sal == 1){
            result = a<<b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //xor
    public String xor(int a, int b){
        if(xor == 1){
            result = a^b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //and
    public String and(int a, int b){
        if(and == 1){
            result = a&b;
            return result + "";
        }else{
            return "";
        }
    }
    
    //or
    public String or(int a, int b){
        if(or == 1){
            result = a|b;
            return result + "";
        }else{
            return "";
        }
    }

    //A getter for the alu functionality code
    public ArrayList<Double> getALUFunctionality(){
        return aluFuncitonalityCode;
    }

    //a getter for the result of the operation
    public double result(){
        return result;
    }    

    public String getFirst(){
        return firstDouble + "";
    }
    
    public String getSecond(){
        return secondDouble + "";
    }
}
