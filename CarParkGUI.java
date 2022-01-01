import java.util.Scanner;
import java.util.regex.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * COS70006
 * Project 2 - GUI Parking Spot System 
 * @author Sam Sam Lei (103196737)
 * @version jdk 14.0.1 
 */


 /** CarPark is responsible for maintaining a list of available parking slots. The user (Carpark manager) should be able 
 * to find a slot, add a slot, delete a slot, park a car in a slot, remove a car by registration number and provide a list
 * of all slots included in the car park.
 */ 
public class CarParkGUI extends JFrame
{   
    public Scanner user_input = new Scanner(System.in);                 // create a Scanner object 
    public ArrayList<ParkingSlots> staffSlots = new ArrayList<ParkingSlots>(); 
    public ArrayList<ParkingSlots> visitorSlots = new ArrayList<ParkingSlots>(); 
    public ArrayList<Cars> parkedCars = new ArrayList<Cars>(); 
    public String emptySlot = "emptySlot";

    protected JFrame frame = new JFrame("SAM SAM Car Park");
    protected DefaultListModel<String> staffList = new DefaultListModel<>();  
    protected DefaultListModel<String> visitorList = new DefaultListModel<>();   

     /**
     * Main method for starting the player from a command line.
     */
    public static void main(String[] args)
    {
        CarParkGUI gui = new CarParkGUI();
    }

     /**
     * Create a CarPark and display its GUI on screen.
     */
    public CarParkGUI()
    {
        super();
       
        makeFrame();

        createCarPark();
    }

   
     /** a method to add a slot to a given list of parking slots, using the given identifier as the first letter in the Slot ID
      */
      private void add_a_slot(ArrayList<ParkingSlots> theSlots, char identifier)
      {
        int currSize = theSlots.size();
        int currLastSlotNo = 0;            //represents the 3-digit number (without the '0' padding in front) in the slot ID of the last slot currently on the list
        int newLastSlotNo = 0;             //represents the 3-digit number (without the '0' padding in front) in the slot ID of the new slot to be added to the list
        ParkingSlots lastSlot = new ParkingSlots("", false, emptySlot);

        if (currSize == 0) {                 // when no parking slots have been created yet on the list
            newLastSlotNo = currSize + 1;    // first slot added will have a 3 digit number of "001" after padding

        /** the 'else' clause below is needed to get the correct 3-digit number when a slot is added to a list with pre-existing slots
         * this is to make sure the 3-digit number allocated to every slot added to the list is unique 
         * the method will look at the 3-digit number in the slot ID of the current last slot, convert it to integer and add 1 to it   
         */
        } else {
            if (currSize > 0) {
                lastSlot = theSlots.get(currSize - 1);     // get the slot which is the last slot on the list
                currLastSlotNo = UserInput.slotNo(lastSlot.getID());       
                newLastSlotNo = currLastSlotNo + 1; 	                   
            }
        }
        
        // creates a slot with an unique slot ID in the required format and adds it to the list
        if (newLastSlotNo < 10) {
              theSlots.add(new ParkingSlots(identifier + "00" + Integer.toString(newLastSlotNo), false, emptySlot)); 
          } else {
              theSlots.add(new ParkingSlots(identifier + "0" + Integer.toString(newLastSlotNo), false, emptySlot));                
          }
      }

        
    /** a method to establish a carpark with slots for staff and visitors
     * this is the first method to be called by the Start class
     * the user will be asked to enter the numbers of staff slots and visitor slots respectively to generate slots
     * all slot ID will start with a uppercase letter followed by 3 digits (eg. S001 for staff & V001 for visitor)
     */
    public void createCarPark()
    {  
        /*asks user for the number of staff slots to create, from 1 to 15 slots*/
        staffSlots = new ArrayList<>();
        int s_count = numOfSlotToCreate("staff");
        for(int i=0; i<s_count; i++) 
        { 
            add_a_slot(staffSlots, 'S');
        }

        /*asks user for the number of visitor slots to create, from 1 to 15 slots*/
        visitorSlots = new ArrayList<>();
        int v_count = numOfSlotToCreate("visitor");
        for(int i=0; i<v_count; i++) 
        { 
            add_a_slot(visitorSlots, 'V');
        }

        String msg = "The " + String.valueOf(s_count) + " slots for staff are created successfully\n" +
                     "The " + String.valueOf(v_count) + " slots for visitors are created successfully";

        // JOptionPane.showMessageDialog(this,msg,"Confirmation",JOptionPane.PLAIN_MESSAGE);

        /*http://www.java2s.com/Tutorial/Java/0240__Swing/AddingComponentstotheButtonAreaUsingJOptionPanewithaJButtoncontainingatextlabelandanicon.htm
        */
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        // Icon icon = new ImageIcon("yourFile.gif");
        JButton jButton = new JButton("List all slots created");
        jButton.addActionListener(e -> opt1_listAllSlots());
        optionPane.setOptions(new Object[] { jButton });
        JDialog dialog = optionPane.createDialog(frame, "Carpark created");
        // dialog.setFont(dialogFont);
        // dialog.setSize(600,500);             //Change the size of the dialog box
        dialog.setVisible(true);       
        
    }


    /** a method to pirint a list of carpark slots based on a given slot type
      */
    private ArrayList<String> get_a_slot_list(ArrayList<ParkingSlots> theSlots, String slotType)
    {   
        ArrayList<String> parkingList = new ArrayList<>();  
        String str = "";

        for (ParkingSlots slot: theSlots) 
        {
             str = slot.toString(slotType);
             parkingList.add(str);
        }  

        return parkingList;

    } 


    /** a method to print a full list of carpark slots in the carpark including both for staff and for visitors
      * this method will be triggered by user selection - option 1 "List all car slots" from the menu list
      * it will also be triggered to show a list of carpark slots when a user selects option 2 or 6  which is to park or delete a car
      */
    private void opt1_listAllSlots()
    {   
        ArrayList<String> sList = new ArrayList<>();  
        ArrayList<String> vList = new ArrayList<>();  
   
        sList = get_a_slot_list(staffSlots, "staff");
        vList = get_a_slot_list(visitorSlots, "visitor");
        
        int s = sList.size();
        int v = vList.size();
        
        staffList.clear();
        visitorList.clear();
        
        for (int i = 0; i < s; i++){
           staffList.addElement(sList.get(i)); 
        }
        
        for (int j = 0; j < v; j++){
           visitorList.addElement(vList.get(j)); 
        }
        
    }

    
    /** a method to park a car into a slot - given a list of slots and the index number to identiy the slot on the list
      * if the slot is not occupied, set the slot as occupied with the registration number of the car
      * advise user the slot is occupied
      */     
    private void park_a_car(ArrayList<ParkingSlots> theSlots, int index, String theRego)
    {         
        ParkingSlots slot = theSlots.get(index - 1);     //locate the slot on the list
        boolean status = slot.getIsOccup();  
        String slotID = slot.getID(); 
        String msg = "";           
        
        /*checks if the slot is occupied or not - only park the car when slot is not occupied*/
        if(status == false) {
            slot.setIsOccup(true);
            slot.setCarRego(theRego); 
            msg = "Car with registration number '" + theRego + "' is parked into slot '" + slotID + "' successfully.";              
        } else {
            msg = "The slot is occupied";           
        }
        JOptionPane.showMessageDialog(this,msg,"Message",JOptionPane.PLAIN_MESSAGE);  

    }

    
    /** this method will be triggeredd by user selection - option 2 "Park a car" from the menu list
      */
    private void opt2_parkCar()
    {   
        opt1_listAllSlots();                    // to show a full list of car slots

        int sSlotSize = staffSlots.size();
        int vSlotSize = visitorSlots.size();

        /*asks user for input of car registration number, the owner type and the slot ID to park the car*/
        String theRego = UserInput.input_rego();
        String theOwnerType = UserInput.input_ownerType(); 
        String slotID = UserInput.input_slotID(); 
        
        char slotType = UserInput.slotType_by_slotID(slotID);                               //gets the type of slot from the first letter of the slot id input by user 
        int slotNo = UserInput.slotNo(slotID);                                              //gets the slot number the 3 digits of the slot id input by user 
        boolean typeMatch = UserInput.ownerType_vs_slotType(theOwnerType, slotType);        //checks if the type of slot selected by the user match the owner type input by the user
        boolean slotNoMatch = UserInput.inputSlotNo_vs_SlotCount(slotNo, theOwnerType, sSlotSize, vSlotSize);    //check if the slot number is equal or lower than the number of slots for the given owner type
               
        //a loop to prompt the user to enter a slot ID until it passes both the slot type check and the slot number check 
        while (typeMatch == false || slotNoMatch == false)       
        {
            JOptionPane.showMessageDialog(this, "Please select a slot available for " + theOwnerType, "Invalid input", JOptionPane.ERROR_MESSAGE);
            slotID = UserInput.input_slotID(); 
            slotType = UserInput.slotType_by_slotID(slotID);
            slotNo = UserInput.slotNo(slotID);  
            typeMatch = UserInput.ownerType_vs_slotType(theOwnerType, slotType);
            slotNoMatch = UserInput.inputSlotNo_vs_SlotCount(slotNo, theOwnerType, sSlotSize, vSlotSize);  
        }

        Cars car = new Cars(theRego, theOwnerType, slotID);          //creates a new instance of the Cars object
        parkedCars.add(car);                                         //adds the car to the list of parked car
        
        //parks the car to the chosen slot with the correct owner type
        if(theOwnerType.equals("staff")) {
            park_a_car(staffSlots, slotNo, theRego);
        } else {
            park_a_car(visitorSlots, slotNo, theRego); 
        }
    }


    /** this method is to find a car by registration number
      * it looks up the list of the parked car and see if any of the car on the list has a registration number that matches
      * it returns the index number of the car with the matching rego number.  If no car is found, it returns -1.
      */
    private int find_a_car_index(String rego)
    {
        int indexFound = -1;
        int count = parkedCars.size();
        if (count == 0) {
            indexFound = -1;
        } else {
            int index = 0;
            Cars theCar = parkedCars.get(index);
            String theRego = theCar.getRego();
            // loops through the list of parked cars until a car with matching rego number is found
            for (index = 0; index < count; index++)
            {
                theCar = parkedCars.get(index);
                theRego = theCar.getRego();
                if (rego.equals(theRego)){
                    indexFound = index;
                    break;
                }
            }
        }

        //returns the index number of the car with the matching rego number.  If no car is found, it returns -1.
        return indexFound;      
    }


    /** this method will be triggeredd by user selection - option 3 "Find a car" from the menu list
     *  the user will be asked to enter a registration number
     */
    private void Opt3_findCar()
    {
        opt1_listAllSlots();                    // to show a full list of car slots
        
        String theRego = UserInput.input_rego();
        String owner = "";
        String slotID = "";
        String msg = "";

        /*calls the find_a_car_index() method and pass on the rego number. An index number will be returned if a car is found, or -1 if not found.*/
        int index = find_a_car_index(theRego);             

        /*index will be -1 when no car is found. Advise the user no car with the given registration number is found.*/
        if(index < 0) {
            msg = "Car with registration number '" + theRego + "' is not in this Car Park"; 
            JOptionPane.showMessageDialog(this,msg,"Car not found",JOptionPane.PLAIN_MESSAGE);   
        /*index will be >= 0 when a car is found. Advise the user of the slot ID and the Owner type of the slot in which the car is parked*/          
        } else {
            Cars theCar = parkedCars.get(index);
            slotID = theCar.getSlotID();
            owner = theCar.getOwnerType();
            msg = theCar.toString(); 
            JOptionPane.showMessageDialog(this,msg,"Car Found",JOptionPane.PLAIN_MESSAGE);  
        }      
    }


     /** this method is to remove a car from a parking slot by registration number
      *  it looks up a list of parking slots
      *  for each parking slot, first check if it is occupied, if not, move to the next parking slot
      *  if it is occupied, check if the rego number of the car occupying the slot matches the rego given by the user. if not, move to the next parking slot
      *  until a matching rego number is found, returns the slot object
      */
    private ParkingSlots remove_a_car_from_slot(ArrayList<ParkingSlots> theSlots, String rego)
    {         
        int i = theSlots.size();
        ParkingSlots slot = theSlots.get(0);
        boolean status = slot.getIsOccup();
        String carRego = slot.getCarRego();
    
        for(int j=0; j<i; j++) {
            slot = theSlots.get(j);
            status = slot.getIsOccup();
            carRego = slot.getCarRego();
            if(status == true){
                if (carRego.equals(rego)) {
                    slot.setCarRego(emptySlot);
                    slot.setIsOccup(false);
                    break;
                } 
            }
        }
        return slot;                // returns the slot object
    }


     /** this method will be triggeredd by user selection - option 4 "Remove a car" from the menu list
      *  the user will be asked to enter a registration number
      *  calls the find_a_car_index() method if the car with the give registration number is in the parked car list
      *  if it is, get the car in the list, its owner type and the ID of slot where it is parked
      *  calls the remove_a_car() method to remove the car from the list of parking slot with the correct owner type
      */
    private void opt4_removeCar()
    {
        opt1_listAllSlots();                    // to refresh the carpark lists
        
        String theRego = UserInput.input_rego();
        String owner = "";                         
        String slotID = "";  
        String msg = "";                      

        /*calls the find_a_car_index() method and pass on the rego number. An index number will be returned if a car is found, or -1 if not found.*/
        int index = find_a_car_index(theRego);      
        
        /*index will be -1 when no car is found. Advise the user no car with the given registration number is found.*/
        if(index < 0) {
           msg = "Car with registration number '" + theRego + "' is not in this Car Park";
           JOptionPane.showMessageDialog(this,msg,"Car not Found",JOptionPane.PLAIN_MESSAGE);   

        /*index will be >= 0 when a car is found.*/          
        } else {
            Cars theCar = parkedCars.get(index);        //gets the car from the parked car list based on the index found
            owner = theCar.getOwnerType();                //gets the owner type
            if(owner == "staff") {                     
                ParkingSlots slot = remove_a_car_from_slot(staffSlots, theRego);      //removes the car from the slots for staff and return the slot
                slotID = slot.getID();                                                // gets the slot ID of the slot where the car is removed.
            } else {
                ParkingSlots slot = remove_a_car_from_slot(visitorSlots, theRego);    //removes the car from the slots for visitor and return the slot
                slotID = slot.getID();                                                // gets the slot ID of the slot where the car is removed.
            }
            parkedCars.remove(index);                                                  //removes the car from the list of parked cars

            /*advises the user the car has been removed from the parking slot and the slot ID*/
            msg = "Car with registration number '" + theRego + "' is removed from slot '" + slotID + "' successfully."; 
            JOptionPane.showMessageDialog(this,msg,"Car Found",JOptionPane.PLAIN_MESSAGE);     
        }        
    }   


    /** this method will be triggeredd by user selection - option 5 "Add a car slot" from the menu list
     *  the user will be asked to specify which type of car park slot to add
     */
    private void opt5_addSlot()
    {   
        opt1_listAllSlots();                    // to refresh the carpark lists
        
        String ownerType = UserInput.input_ownerType();
        if (ownerType.equals("staff")) {
            add_a_slot(staffSlots, 'S');
            JOptionPane.showMessageDialog(this,"A carpark slot for staff is created successfully.","Confirmation",JOptionPane.PLAIN_MESSAGE);   
        } else {
            add_a_slot(visitorSlots, 'V');
            JOptionPane.showMessageDialog(this,"A carpark slot for visitor is created successfully.","Confirmation",JOptionPane.PLAIN_MESSAGE);   
        }
    }


   
    /** this method is to delete a slot from the carpark by a given slot ID
     *  it will loop through each slots in the carpark to see if any slot has a slot ID matches the given slot ID 
     *  once a match is find, then check if the slot with the matching ID is occipied or not
     *  only an unoccupied slot will be deleted
     *  a message will print out to advise if a slot is successfully deleted or not
     */
    private void delete_a_slot(ArrayList<ParkingSlots> theSlots, String slotID)
    {
        boolean slotFound = false;
        int index = theSlots.size() - 1;        // set the index number to be the number of slots less 1 in the given slot list
        String msg = "";
        
        for(int j=index; j>=0; j--) {                // start the loop from the last element in the given slot list
            ParkingSlots slot = theSlots.get(j);    //get the slot from the slot list
            String thisSlotID = slot.getID();       // get the slot ID
            if (thisSlotID.equals(slotID)) {          // if the slot ID matches the give ID
                boolean status = slot.getIsOccup();    // get the status of the slot
                slotFound = true;
                if(status == false) {                    // if is it not occupied
                   theSlots.remove(j);                 // remove the slot from the slot list
                   msg = slotID + " is deleted successfully.";
                   JOptionPane.showMessageDialog(this,msg,"Message",JOptionPane.PLAIN_MESSAGE);  
                   break; 
                } else {
                   msg = slotID + " can not be deleted as it is occupied.";
                   JOptionPane.showMessageDialog(this,msg,"Message",JOptionPane.PLAIN_MESSAGE);  
                   break;
                }
            }
        }
        if (slotFound == false) {             
            msg = slotID + " does not exist.";
            JOptionPane.showMessageDialog(this,msg,"Message",JOptionPane.PLAIN_MESSAGE);  
        }
    }


    /** this method will be triggeredd by user selection - option 6 "Delete a car slot" from the menu list
     *  the user will be asked to enter a slot ID for deletion
     */
    private void opt6_deleteSlot()
    {   
        opt1_listAllSlots();                    // to refresh a full list of car slots before asking user for input

        String slotID = UserInput.input_slotID();
        char slotType = UserInput.slotType_by_slotID(slotID);              //gets the type of slot type identifier from the Slot ID
      
         //based on the identifier, passes on the right list of parking slots and the slot ID to the delete_a_slot() method
        if (slotType == 'S') {                                  
            delete_a_slot(staffSlots, slotID);              
        } else { 
          delete_a_slot(visitorSlots, slotID);
        }
    }


     /** this method will exit the program
     */
    private void opt7_exit()
    {   
        JOptionPane.showMessageDialog(this,"You have exited the application","Message",JOptionPane.PLAIN_MESSAGE);  
        System.exit(0);  
    }

    
    private void makeFrame()
    {
        frame.setVisible(true); 

        JPanel selections = new JPanel();
        selections.setBounds(50, 80, 220, 400); 
        {
            selections.setLayout(new GridLayout(7, 1));

            Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);            //Font object for buttons
   
            JButton button = new JButton("1. Update slot lists");
            button.addActionListener(e -> opt1_listAllSlots());
            button.setFont(buttonFont);                                         //set font style for buttons
            button.setHorizontalAlignment(SwingConstants.LEFT);                 //set button label to be left aligned
            selections.add(button);
             
            button = new JButton("2. Park a car");
            button.addActionListener(e -> opt2_parkCar());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
     
            button = new JButton("3. Find a car");
            button.addActionListener(e -> Opt3_findCar());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
             
            button = new JButton("4. Remove a car");
            button.addActionListener(e -> opt4_removeCar());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
 
            button = new JButton("5. Add a car slot");
            button.addActionListener(e -> opt5_addSlot());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
 
            button = new JButton("6. Delete a car slot");
            button.addActionListener(e -> opt6_deleteSlot());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
 
            button = new JButton("7. Exit");
            button.addActionListener(e -> opt7_exit());
            button.setFont(buttonFont);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            selections.add(button);
        }

        // add carpark incon below the selection menu;
        JLabel carparkIcon = new JLabel(new ImageIcon("carpark.jpg"));
        carparkIcon.setBounds(50, 500, 220, 200);
        // carparkIcon.setFont(labelFont);
        carparkIcon.setHorizontalAlignment(SwingConstants.CENTER);

        

        // Create the scrolled list for track listing.
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);            //Font object for List labels
        Font listFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);            //Font object for Lists

        JLabel label1 = new JLabel("STAFF");
        label1.setBounds(300, 30, 600, 50);
        label1.setFont(labelFont);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        // JPanel staffPane = new JPanel(new GridLayout(10, 1));
        JPanel staffPane = new JPanel(new BorderLayout());
        staffPane.setBounds(300, 80, 600, 800);      
        {
            JList<String> staffJList = new JList<>(staffList);  
            staffJList.setFont(listFont); 
            staffJList.setForeground(new Color(0,0,255));
            staffJList.setBackground(new Color(255,255,204));                //very light yellow
                //   staffJList.setBounds(50,130, 400,800);     
            staffPane.add(staffJList);
        }

        JLabel label2 = new JLabel("VISITOR");
        label2.setBounds(920, 30, 600, 50);
        label2.setFont(labelFont);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel visitorPane = new JPanel(new BorderLayout());
        visitorPane.setBounds(920, 80, 600, 800);
        {
            JList<String> visitorJList = new JList<>(visitorList);  
            visitorJList.setFont(listFont);  
            visitorJList.setForeground(new Color(0,0,255));
            visitorJList.setBackground(new Color(255,255,204));
            // visitorJList.setBounds(600,130, 400,800);  
            visitorPane.add(visitorJList);
        }

       
        
        frame.add(carparkIcon);
        // frame.add(icon);
        frame.add(label1);
        frame.add(label2);
        frame.add(selections);
        frame.add(staffPane);
        frame.add(visitorPane);
        frame.setSize(1600,1200);  
        frame.setLayout(null);  
        // the following makes sure that our application exits when the user closes its window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

    }

    
    private int numOfSlotToCreate(String ownerType) 
    {
        int i = 1;
        //Object [] possibileValues = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}; 
        Object [] possibileValues = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; 
        String msg = "Parking slot for " + ownerType + " - Please select a number from 0 to 15: ";        
        Object selectedValue = JOptionPane.showInputDialog(this, msg,"Number of slots to create", JOptionPane.PLAIN_MESSAGE, null, possibileValues, possibileValues[1]);
        if(selectedValue instanceof String) {
            i = Integer.valueOf((String)selectedValue); 
        }
        if(selectedValue instanceof Integer) {
            i = ((Integer)selectedValue).intValue(); 
        }  

       return i;
    }
    


} //end of class
  
  

