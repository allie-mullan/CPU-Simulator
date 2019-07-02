import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is the CPU Simulator. It is the class that holds all of the separate pieces of the cpu and uses them to carry out the functions of the CPU. 
 * It creates the pieces of the CPU, innitializing instances of the CPU objects. 
 * 
 * @author Allie Mullan 
 * @version 11/11/2016
 */
public class CPUSimulator {
    ////////*instance variables*//////////
    String configFileName, rtnFileName;

    ///////*components of the cpu*////////
    //readers
    ConfigFileReader    configFileReader;
    RTNReader           rtnReader;
    GUI gui;

    //objects
    MainMemory      memory;
    ALU             alu;
    Bus             bus;

    //registers
    MemoryA ma;
    MemoryD md;
    C       c;
    InstructionRegister instructionR;
    ProgramCounter      pc;
    ImmediateRegister   immediateRegister;

    //register arrays
    Registers[] allProgramRegisters = new Registers[500];
    GeneralRegister[] registers     = new GeneralRegister[500]; 

    //constructor
    public CPUSimulator(){
    }

    //main method
    public static void main(String[] args){

        CPUSimulator cpu = new CPUSimulator();
        
        cpu.create(args);
        cpu.start();

    }

    /*Create all of the objects, including Registers, in the CPU as specified in the config file and rtn file. Call for the GUI to be created.*/
    public void create(String args[]){

        //read input files
        configFileReader = new ConfigFileReader(args[0]);
        rtnReader        = new RTNReader(args[1]);

        configFileReader.readConfigFile();
        
        memory = new MainMemory(configFileReader.wordSize());
        
        rtnReader.readRTNFile();
        memory.readProgram();

        //generate the cpu objects
        generateGUI();
        bus   = new Bus(configFileReader.busSize());
        alu   = new ALU(configFileReader.getALUFunctArray(), gui);
        
        //generate the registers 
        pc                = new ProgramCounter(gui);
        immediateRegister = new ImmediateRegister(gui);
        instructionR      = new InstructionRegister(immediateRegister, gui);
        ma                = new MemoryA(gui);
        md                = new MemoryD(gui);
        c                 = new C(gui);
        
        //add the registers to an array of all the Registers type in the CPU
        allProgramRegisters[0] = pc;
        allProgramRegisters[1] = immediateRegister;       
        allProgramRegisters[2] = instructionR;       
        allProgramRegisters[3] = ma;
        allProgramRegisters[4] = md;        
        allProgramRegisters[5] = c;

        //Create an array of GeneralRegisters type. Add as many GeneralRegisters as is specified in the config file
        for(int i = 0; i<configFileReader.noOfRegisters(); i++){
            
            registers[i]             = new GeneralRegister(i+1, gui); 
            allProgramRegisters[6+i] = registers[i];
            
        }

    }

    /*Begin executing the program of machine code that is stored in MainMemory.*/
    public void start(){
        
        //loop through the memory program code. Alternately, this should probably be done with the gui, not with a for loop.
        for(int i = 0; i < memory.getProgramSize(); i++){
            gui.updatePrintOuts("\nOPCODE: fetch");
                       
            //waitForClick();

            fetchInstruction(); //the fetch instruction as described in the RTN file is carried out before each line of memory code.  
            
            //waitForClick();
            
            instructionR.parseInstruction(); //the instruction register parses the line of machine code that it currently holds and saves the three pieces of the code as 3 separate strings of binary

            //get the three significant pieces of the current line of machine code from the instruction register
            String currentOpcode = instructionR.getOpcode();
            String ptA           = instructionR.getPartAName();
            String ptB           = instructionR.getPartBName();
            
            gui.updatePrintOuts("\nOPCODE: " + currentOpcode);
            
            //get the array of RTN instructions, saved as strings, for the current opcode
            ArrayList<String> rtnInstructions = rtnReader.getInstructions(currentOpcode);
            
            //loop through each RTN location (source, destination) in the array of RTN instructions for this current opcode
            for(int j = 0; j < rtnInstructions.size()-1; j=j+2){
                              
                //get the source and destination string from the array of RTN instructions
                String sourceString      = rtnInstructions.get(j);
                String destinationString = rtnInstructions.get(j+1);
                
                gui.updatePrintOuts("*RTN Instruction: " + rtnInstructions.get(j) + " " + rtnInstructions.get(j+1));
                
                //parse the source string and carry out the corresponding instructions
                if(sourceString.equals("R{rA}")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //Compare the CPU registers created at the start of the program with the name of the register specified in the machine code
                            //add the contents of the register to the bus
                            bus.addToBus(registers[k].getContents()); 
                            
                            gui.updateRegister(k, registers[k].getContents());
                            
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("R{rB}")){
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //Compare the CPU registers created at the start of the program with the name of the register specified in the machine code
                            //add the contents of the register to the bus
                            bus.addToBus(registers[k].getContents());     
                            
                            gui.updateRegister(k, registers[k].getContents());
                       
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("PC")){ //the program counter
                    
                    bus.addToBus(pc.getContents());
                    
                    //waitForClick();
                }else if(sourceString.equals("CC")){ //the C component
                    
                    bus.addToBus(c.getContents());
                    
                    //waitForClick();
                }else if(sourceString.equals("PC+1")){ //specific to the fetch instruction
                   
                    bus.addToBus(pc.getContents()); //add the contents of the program counter to the bus
                    bus.moveContentsToAluFirst(alu); //move what's in the bus to the first place in the ALU
                    
                    alu.addSecond(1); //add 1 to the second place in the ALU
                    
                    //bus.addToBus(alu.execute("+")); //execute the ALU's addition function. It will add together what is in the 'first' place and the 'second' place
                    c.addContents(alu.execute("+"));
                   
                   // waitForClick();
                }else if(sourceString.equals("MD")){ //memory data register
                    
                    bus.addToBus(md.getContents());
                    
                    //waitForClick();
                }else if(sourceString.equals("MA")){ //memory address register
                   
                    bus.addToBus(ma.getContents());
                    
                   // waitForClick();
                }else if(sourceString.equals("MM[MA]")){ //if the RTN instructs the cpu to get something from an address in memory
                   
                    bus.addToBus(memory.getLineAtThisAddress(ma.getContents())); //add the string in memory at the address in the memory address register to the bus
                    
                   // waitForClick();
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("Add")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("+"));
                         
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("Add")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                          
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("+"));
                            
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("sub")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("-"));
                          
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("sub")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("-"));
                            
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("sal")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("sal"));
                           
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("sal")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("sal"));
                           
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("sar")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("sar"));
                      
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("sar")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("sar"));
                           
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("mul")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("*"));
                      
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("mul")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("*"));
                         
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("and")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("&"));
                        
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("and")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("&"));
                      
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rA}") && currentOpcode.equals("xor")){ //if the RTN instructs the cpu to carry out addition of something in a register to something already in the ALU
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptA)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus   
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                            
                            c.addContents(alu.execute("^"));
                        
                            //waitForClick();
                        }else{
                        }
                    }
                }else if(sourceString.equals("AA+R{rB}") && currentOpcode.equals("xor")){ 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){ //find out what register you need out of all the registers in the simulation according to the machine code line
                            
                            bus.addToBus(registers[k].getContents()); //add the contents of the register to the bus    
                            bus.moveContentsToAluSecond(alu); //put it in the ALU
                           
                            c.addContents(alu.execute("^"));
                         
                           // waitForClick();
                        }else{
                        }
                    }
                }else if (sourceString.equals("IM")){ //if it's something in the immediate register 
                    
                    bus.addToBus(immediateRegister.getContents());
                 
                    ///waitForClick();
                }
               
                //parse the destination string and place what's in the bus 
                if(destinationString.equals("R{rA}")){ //move to a register 
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){
                            
                            bus.moveContentsTo(registers[k]);
                            
                            // waitForClick();
                        }else{
                        }
                    }
                }else if(destinationString.equals("R{rB}")){ //move to a register
                    for(int k = 0; k < configFileReader.noOfRegisters(); k++){
                        if(registers[k].getName().equals(ptB)){
                           
                            bus.moveContentsTo(registers[k]);  
                           
                           // waitForClick();
                        }else{
                        }
                    }
                }else if(destinationString.equals("PC")){ //move to the program counter
                    bus.moveContentsTo(pc); 
                    
                   // waitForClick();
                }else if(destinationString.equals("CC")){ //move to the c component
                    //only comes here from the ALU 
                    //bus.moveContentsTo(c);
                    
                }else if(destinationString.equals("MD")){//move to the memory data register
                    
                    bus.moveContentsTo(md);
                    
                    //waitForClick();
                }else if(destinationString.equals("MA")){ //move to the memory address register
                    
                    bus.moveContentsTo(ma); 
                    
                   // waitForClick();
                }else if(destinationString.equals("IR")){ //move to the instruction register
                    
                    bus.moveContentsTo(instructionR); 
                
                    //waitForClick();
                }else if(destinationString.equals("AA")){ //move to the ALU
                    
                    bus.moveContentsToAluFirst(alu);
                                       
                    //waitForClick();
                }
            }
        }
    }

    //Get the 
    public Registers getRegister(String registerName){
        
        Scanner scan = new Scanner(registerName).useDelimiter("");
        String a = scan.next() + scan.next();
        
        if(scan.hasNext()){
            
            String b = scan.next() + scan.next();
            
            //if its a register you use the second part 
            if(b.equals("rA") || b.equals("rB")){
                registerName = b;
               
                for(int i = 0; i<configFileReader.noOfRegisters(); i++){
                    if(registers[i].getName().equals(instructionR.getPartBName())){
                        return registers[i]; 
                    }
                }
            }
        }else{
        }
        
        registerName = a;

        for(int i = 0; i<(configFileReader.noOfRegisters()+5); i++){

            if(allProgramRegisters[i].getName().equals(registerName)){
                
                return allProgramRegisters[i]; 
            }
        }

        return null;

    }

    /*Start executing the Fetch instruction*/
    public void fetchInstruction(){

        ArrayList<String> rtnInstructions = rtnReader.getInstructions("Fetch");

        Registers rSrc = null;
        Registers rDst = null;

        for(int i = 0; i < rtnInstructions.size()-1; i=i+2){
            
            boolean useAlu       = doIUseTheAlu(rtnInstructions.get(i), rtnInstructions.get(i+1));
            boolean useMainMem   = doIUseMainMem(rtnInstructions.get(i), rtnInstructions.get(i+1));

            rSrc = getRegister(rtnInstructions.get(i));
            rDst = getRegister(rtnInstructions.get(i+1));
            
            gui.updatePrintOuts("*RTN Instruction: " + rtnInstructions.get(i) + " " + rtnInstructions.get(i+1));

            executeRTNLine(rSrc,rDst,useAlu,useMainMem, rtnInstructions.get(i));
        }
    }

    public boolean doIUseTheAlu(String instructionA, String instructionB){
    
        String x = "" + instructionB.charAt(0);

        Scanner scan = new Scanner(instructionA+instructionB).useDelimiter("\\+");
        String a = scan.next();

        if(scan.hasNext()){
            return true;

        }else if(!scan.hasNext()){
            return false;
        }else{
            return false;
        }

    }

    public boolean doIUseMainMem(String instructionA, String instructionB){

        Scanner scan = new Scanner(instructionA+instructionB).useDelimiter("\\[");
        scan.next();
        return scan.hasNext();

    }

    public void executeRTNLine(Registers source, Registers destination, boolean useAlu, boolean useMainMem, String fullSourceString){
        if(useAlu){
            
            //first src register: get register, put in bus, put in first alu spot
            bus.addToBus(source.getContents());
            bus.moveContentsToAluFirst(alu);
            
                     
            //add the second part of the source to the alu
            char b = fullSourceString.charAt(3);
            alu.addSecond(Double.parseDouble(b+""));
        
            //get the alu operation
            char operation = fullSourceString.charAt(2);
            
            //carry out the alu operation and add it to the c register
            c.addContents(alu.execute("+"));
            //bus.setBusEmpty();

        }else if(useMainMem){

            String q = ma.getContents();
            String z = memory.getLineAtThisAddress(q);
            
            bus.addToBus(z);
            bus.moveContentsTo(destination);
       
        }else{
            
            bus.addToBus(source.getContents());
            bus.moveContentsTo(destination);

        }
    }  

    public Registers getRegisterB(String fullSourceString){
        //what we want to do here is find out what the second register is
        char a = fullSourceString.charAt(2);
        if(a == '['){

            char b = fullSourceString.charAt(3);
            char c = fullSourceString.charAt(4);

            for(int i = 0; i<(configFileReader.noOfRegisters()); i++){
                if(registers[i].getName().equals(b + c + "")){
                    
                    return registers[i]; 
                    
                }
            }

        }else if(fullSourceString.charAt(3) == 'R'){

            char b = fullSourceString.charAt(5);
            char c = fullSourceString.charAt(6);

            for(int i = 0; i<(configFileReader.noOfRegisters()); i++){
                
                if(registers[i].getName().equals(b + c + "")){
                   
                    return registers[i]; 
                    
                }
            }
        }

        return null;

    }

    //method for the gui
    public void generateGUI(){
        gui = new GUI(configFileReader.noOfRegisters());
    }

    public void waitForClick(){
        
        while(!gui.getKeepGoing()){
            
        }
        
        gui.setKeepGoingFalse();
        
    }
}
