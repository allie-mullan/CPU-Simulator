import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The fast GUI.
 */
public class GUI extends Frame implements ActionListener{
    
    // instance variables - replace the example below with your own
    private Label     register_label, 
                      printOutLabel;  // Declare a Label component 
                      
    private TextField register_value, 
                      register_value1, 
                      pcLabel, 
                      registerPanel,
                      cLabel,
                      maLabel,
                      mdLabel, 
                      irPanel, 
                      imLabel;  // Declare a TextField component 
                      
    private Button    time_step,       // Declare a Button component
                      exit_button;     // Declare a Button component

    private TextArea  printLabel;
    
    private int     time                = 0;  
    private double  numberOfRegisters;
    private boolean keepGoing           = false;
    
    ArrayList<Panel> genReg          = new ArrayList<Panel>();
    ArrayList<TextField> genRegLabel = new ArrayList<TextField>();

    /**
     * Constructor for objects of class CS203_GUI
     */
    public GUI(double numberOfRegisters){
        
        keepGoing = false;
        this.numberOfRegisters = numberOfRegisters;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        // "super" Frame (a Container) sets its layout to FlowLayout, which arranges
        // the components from left-to-right, and flow to next row from top-to-bottom.

        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());

        time_step = new Button("Step");          // construct the Button component
        p1.add(time_step);                       // "super" Frame adds Button
        time_step.addActionListener(this);

        exit_button = new Button("Exit");        // construct the Button component
        p1.add(exit_button);                     // "super" Frame adds Button
        exit_button.addActionListener(this);

        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout());

        register_label = new Label("PC: ");     // construct the Label component
        p2.add(register_label);                 // "super" Frame adds Label

        pcLabel = new TextField("0", 10); // construct the TextField component
        pcLabel.setEditable(false);       // set to read-only
        p2.add(pcLabel);                  // "super" Frame adds TextField

        setTitle("CS203 Simple GUI");  // "super" Frame sets its title
        setSize(250, 100);             // "super" Frame sets its initial window size

        // add subpanels to the primary frame
        add(p1);
        add(p2);

        //add a panel for every register
        for(int u = 0; u<numberOfRegisters; u++){ 
            Panel p3 = new Panel();
            p3.setLayout(new FlowLayout());

            register_label = new Label("Register: " + (u+1));     // construct the Label component
            p3.add(register_label);                 // "super" Frame adds Label

            registerPanel = new TextField("0", 10); // construct the TextField component
            registerPanel.setEditable(false);       // set to read-only
            p3.add(registerPanel); 

            add(p3);

            genReg.add(p3);
            genRegLabel.add(registerPanel);
        }

        //add a panel for every other register
        //C
        Panel p4 = new Panel();
        p4.setLayout(new FlowLayout());

        register_label = new Label("C");     // construct the Label component
        p4.add(register_label);                 // "super" Frame adds Label

        cLabel = new TextField("0", 10); // construct the TextField component
        cLabel.setEditable(false);       // set to read-only
        p4.add(cLabel);  
        add(p4);

        //MA
        Panel p6 = new Panel();
        p6.setLayout(new FlowLayout());

        register_label = new Label("MA");     // construct the Label component
        p6.add(register_label);                 // "super" Frame adds Label

        maLabel = new TextField("0", 10); // construct the TextField component
        maLabel.setEditable(false);       // set to read-only
        p6.add(maLabel);  
        add(p6);

        //MD
        Panel p7 = new Panel();
        p7.setLayout(new FlowLayout());

        register_label = new Label("MD");     // construct the Label component
        p7.add(register_label);                 // "super" Frame adds Label

        mdLabel = new TextField("0", 10); // construct the TextField component
        mdLabel.setEditable(false);       // set to read-only
        p7.add(mdLabel);  
        add(p7);

        //Instruction register
        Panel p8 = new Panel();
        p8.setLayout(new FlowLayout());

        register_label = new Label("Instruction Reg");     // construct the Label component
        p8.add(register_label);                 // "super" Frame adds Label

        irPanel = new TextField("0", 10); // construct the TextField component
        irPanel.setEditable(false);       // set to read-only
        p8.add(irPanel);  
        add(p8);

        //Immediate register
        Panel p5 = new Panel();
        p5.setLayout(new FlowLayout());

        register_label = new Label("Immediate Reg");     // construct the Label component
        p5.add(register_label);                 // "super" Frame adds Label

        imLabel = new TextField("0", 10); // construct the TextField component
        imLabel.setEditable(false);       // set to read-only
        p5.add(imLabel);  
        add(p5);

        setVisible(true);  
        
        //print out text area    
        printLabel = new TextArea("", 25,50);
        printLabel.setEditable(false);
       
        add(printLabel);
        
    }

    // ActionEvent handler - Called back upon button-click.
    @Override
    public void actionPerformed(ActionEvent evt) {

        if (evt.getActionCommand().equals("Step")) {
            
            keepGoing = true;
           
        } else if (evt.getActionCommand().equals("Exit")) {

            System.exit(0);  // Terminate the program

        } else {

            System.out.println("Unknown action command " + evt.getActionCommand());
        }
    }

    public void updateRegister(int k, String contents){
        
        TextField ta = genRegLabel.get(k);
        
        ta.setText(contents);

    }

    public void updatePC(String contents){
        pcLabel.setText(contents);
    }

    public void updateC(String contents){
        cLabel.setText(contents);
    }

    public void updateMA(String contents){
        maLabel.setText(contents);
    }

    public void updateMD(String contents){
        mdLabel.setText(contents);
    }

    public void updateIM(String contents){
        imLabel.setText(contents);
    }

    public void updateIR(String contents){
        irPanel.setText(contents);
    }

    public void setKeepGoingFalse(){
        keepGoing = false;
    }

    public boolean getKeepGoing(){
        return keepGoing;  
    }
    
    public void updatePrintOuts(String s){
        printLabel.setText(printLabel.getText() + "\n" + s);
    }
}
