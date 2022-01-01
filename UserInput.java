import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

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


/**
 * UserInput is a class to group together all methods in relation to the handling of user input data.
 */ 

public class UserInput {

    private static Scanner in = new Scanner(System.in);


     /** getString(String prompt) prompts the user for an input, 
     * reads it as a String object and returns it.
     * @author Shamara Gibson
     */
    public static String getString(String prompt)
    {
        System.out.print(prompt + " ");
        return in.nextLine();
    }


     /** getInteger(String prompt) prompts the user for an input 
     * and parses the input to an Integer object.
     *  @author Shamara Gibson
     */
    



    /** a method to get the slot ID from user which will meet the required pattern (eg. S001 for staff and V001 for visitor)
     * it will keep prompting the user for input until the required pattern is met
     */
    public static String input_slotID()
    {   
        boolean match_pattern = false;
        String regx = "(S|V){1}[0-9]{3}";       //an upper letter followed with 3 digits (eg. S001 for staff and V001 for visitor)
        Pattern p = Pattern.compile(regx);      //create a Pattern object
        
        String slotID;

        do {
                slotID = JOptionPane.showInputDialog("Please select a slot: ");     
                Matcher m = p.matcher(slotID);

                if (m.matches()) {
                    match_pattern = true;
                } else {
                    //System.out.println("Please enter a slot ID starts with a capital letter 'S' for staff or 'V' for visitor, followed by a 3-digit number, eg. 'S001', 'V003'.");
                    JOptionPane.showMessageDialog(null, "Please enter a slot ID starts with a capital letter 'S' for staff or 'V' for visitor, followed by a 3-digit number, eg. 'S001', 'V003'.","Invalid Input",JOptionPane.ERROR_MESSAGE);   
                }   
            } while (match_pattern == false);

        return slotID;
    } 


    /** a method to get the registration number from user input which meets the required pattern (eg. 'T12345')
     *  it will keep prompting the user for input until the required pattern is met
     */
    public static String input_rego()
    {   
        String theRego = "";        
        boolean match_pattern = false;
        String regx = "[A-Z]{1}[0-9]{5}";      //a Capital Letter followed by a 5-digit number (eg. 'T12345')
        Pattern p = Pattern.compile(regx);  //create a Pattern object

        do {
                theRego = JOptionPane.showInputDialog("Please enter a registration number (eg. 'T12345'): ");
        
                Matcher m = p.matcher(theRego);

                if (m.matches()) {
                    match_pattern = true;
                } else {
                   JOptionPane.showMessageDialog(null, "A registration number should start with a Capital Letter, followed by a 5-digit number.","Invalid Input",JOptionPane.ERROR_MESSAGE);   
                }   
            } while (match_pattern == false);

        return theRego;
    }


    /** a method to get the owner identifier from the slot ID input by user
     */
    public static char slotType_by_slotID (String slotID)
    {
        char type = slotID.charAt(0);
        return type;
    }


    /** a method to get the slot number in integer from the slot ID input by user
     */
    public static int slotNo(String slotID)
    {
        String str = slotID.substring(1);
        char c1 = str.charAt(0);
        char c2 = str.charAt(1);
        if (c1 == '0') {
            if (c2 == '0') {
               str = str.substring(2);
            } else {
                str = str.substring(1);
            }
        }   
           
        int slotNo = Integer.valueOf(str);
        return slotNo;
    }
    
    
    /** a method to get the owner type from user input which meets the required pattern (1 for Staff, 2 for Visitor)
     * it will keep prompting the user for input until the required pattern is met
     * it returns a string of either "staff" or "visitor" depending on user input
     */
    public static String input_ownerType()
    {   
        boolean match_pattern = false;
        String regx = "[12]{1}";                //1 for Staff, 2 for Visitor
        Pattern p = Pattern.compile(regx);      //create a Pattern object 
        
        String str = "";
        String ownerType = "";
    
        do {
                str = JOptionPane.showInputDialog("Please enter the type of car park to add (1 for Staff, 2 for Visitor): ");    
                Matcher m = p.matcher(str);

                if (m.matches()) {
                    match_pattern = true;
                } else {
                    JOptionPane.showMessageDialog(null,"Please enter 1 for Staff, 2 for Visitor: ","Invalid Input",JOptionPane.ERROR_MESSAGE);   
                }   
            } while (match_pattern == false);

        switch (str) 
        {
            case "1":  ownerType = "staff";
                break;
            case "2":  ownerType = "visitor";
                break;
        }
        
        return ownerType;
    }
    

    /** a method to test if the owner type input by user matches the slot type indicated by the first letter of the slotID ('S' for staff & 'V' for visitor)
     * it returns true if there is a match, false if no match
     */    
    public static boolean ownerType_vs_slotType(String ownerType, char slotType)
    {
        boolean typeMatch = false;
        if(ownerType == "staff" && slotType == 'S') {
            typeMatch = true;
        } else {
            if (ownerType == "visitor" && slotType == 'V') {
                typeMatch = true;
            } else {
                typeMatch = false;
            }
        }
        return typeMatch;
    }


    /** a method to test if the slot number input by user is lower or equal the existing number of slots applicable to the owner type of the user
     *  it returns true if the the input number is lower or equal, false if higher
     */ 
    public static boolean inputSlotNo_vs_SlotCount(int slotNo, String theOwnerType, int sSlotCount, int vSlotCount)
    {
        boolean slotNoMatch = false;
        if (theOwnerType.equals("staff")) {
            if(slotNo <= sSlotCount) {
            slotNoMatch = true; 
            } else {
                slotNoMatch = false;
            }
        } 
        
        if (theOwnerType.equals("visitor")) {
            if(slotNo <= vSlotCount) {
            slotNoMatch = true;
            } else {
                slotNoMatch = false;
            } 
        }
        
        return slotNoMatch;
    }
        
    
} // end of class
