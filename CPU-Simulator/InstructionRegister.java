import java.io.*;
import java.util.*;

/*The instruction register. After each fetch, a new line of machine code is added to the instruction
   register to tell the CPU what to do. The instruction is also parsed inside the instruction register
   and the different pieces of the machine code instruction can be accessed.*/
public class InstructionRegister extends Registers{
    //instance variables
    private String contents, 
                   opcode,
                   partA,
                   partB, 
                   partAName, 
                   partBName;
                   
    private boolean           full;
    private ImmediateRegister immediateRegister;
    private GUI               gui;

    public InstructionRegister(ImmediateRegister immediateRegister, GUI gui){
        this.immediateRegister = immediateRegister;
        this.gui               = gui;
    }

    public void addContents(String x){
        
        contents = x;
        
        gui.updatePrintOuts("          add " + contents + " to " + getName());
        gui.updateIR(contents);
        
    }
    
    public void parseInstruction(){
        
        Scanner scn = new Scanner(contents).useDelimiter("");

        String opcodeNumber       = "";
        String instructionPartANo = "";
        String instructionPartBNo = "";

        for(int i = 0; i<4; i++){
            opcodeNumber = opcodeNumber + scn.next();
        }

        for(int i = 0; i<6; i++){
            instructionPartANo = instructionPartANo + scn.next();
        }

        for(int i = 0; i<6; i++){
            instructionPartBNo = instructionPartBNo + scn.next();
        }

        //call getOpcode
        parseOpcodeNumber(opcodeNumber);

        //call get parta
        partAName = parsePartA(instructionPartANo);
        
        //call get partb
        partBName = parsePartB(instructionPartBNo);

    }

    public void parseOpcodeNumber(String opcodeNumber){
        if(opcodeNumber.equals( "0000")){
            opcode = "Fetch";
        }else if(opcodeNumber.equals("0001")){
            opcode = "Add";
        }else if(opcodeNumber.equals("0010")){
            opcode = "irmov";
        }else if(opcodeNumber.equals("0011")){ 
            opcode = "rrmov";
        }else if(opcodeNumber.equals("0100")){ 
            opcode = "sub";
        }else if(opcodeNumber.equals("0101")){
            opcode = "sal";
        }else if(opcodeNumber.equals("0110")){
            opcode = "sar";
        }else if(opcodeNumber.equals("0111")){
            opcode = "mul";
        }else if(opcodeNumber.equals("1000")){ 
            opcode = "and";
        }else if(opcodeNumber.equals("1001")){
            opcode = "or";
        }else if(opcodeNumber.equals("1010")){
            opcode = "xor";
        }
    }

    //uses if else statements to compare the first half of the instruction part of the machine code string. Parses them as strings that can be compared to the registers
    public String parsePartA(String instructionPartANo){
        
        partA = "";
        
        if(instructionPartANo==null){
            return partA;
        }else{
            if(instructionPartANo.charAt(0)=='0'){
                
                //if the first bit is a 0, then it is a number in the immediate register 
                int a = Integer.parseInt(instructionPartANo, 2);
              
                //place the number in the immediate register
                immediateRegister.addContents(a+"");

                //return string immediate register
                partA = "Immediate Register";
                
            }else{
                if(instructionPartANo.equals( "100000")){
                    partA = "Register 1"; 
                }else if(instructionPartANo.equals("110000")){
                    partA = "Register 2";
                }else if(instructionPartANo.equals( "111000")){
                    partA = "Register 3"; 
                }else if(instructionPartANo.equals("111100")){
                    partA = "Register 4";
                }else if(instructionPartANo.equals( "111110")){
                    partA = "Register 5"; 
                }else if(instructionPartANo.equals("111111")){
                    partA = "Register 6";
                }
            }
            
            return partA;
        }
    }

    //uses if else statements to compare the second half of the instruction part of the machine code string. Parses them as strings that can be compared to the registers
    public String parsePartB(String instructionPartBNo){
        
        partB = "";
        
        if(instructionPartBNo==null){
            return partB;
        }else{
            if(instructionPartBNo.charAt(0)=='0'){
                
                //if the first bit is a 0, then it is a number in the immediate register 
                int a = Integer.parseInt(instructionPartBNo, 2);
             

                //place the number in the immediate register
                immediateRegister.addContents(a+"");

                //return string immediate register
                partB = "Immediate Register";
                
            }else{
                if(instructionPartBNo.equals( "100000")){
                    partB = "Register 1"; 
                }else if(instructionPartBNo.equals("110000")){
                    partB = "Register 2";
                }else if(instructionPartBNo.equals( "111000")){
                    partB = "Register 3"; 
                }else if(instructionPartBNo.equals("111100")){
                    partB = "Register 4";
                }else if(instructionPartBNo.equals( "111110")){
                    partB = "Register 5"; 
                }else if(instructionPartBNo.equals("111111")){
                    partB = "Register 6";
                }
            }
        }
        
        return partB;
    }

    //Getter methods for the different pieces of the machine code instruction line
    public String getPartAName(){
        return partAName;
    }

    public String getPartBName(){
        return partBName;
    }

    public String getOpcode(){
        return opcode;
    }

    public String getContents(){
        return contents;
    }

    public String getName(){
        return "IR";
    }

}