/**
 * COS70006
 * Project 2 - GUI Parking Spot System 
 * @author Sam Sam Lei (103196737)
 * @version jdk 14.0.1 
 */
 

/** create a ParkingSlots clas
 */

public class ParkingSlots
{
    private String id;                   // unique ID of the slot
    private boolean isOccup;             // true when the slot is occupied, false when not occupied
    private String carRego;              // registration number of the car parked at the slot when it is occupied
    

    // constructor    
    public ParkingSlots(String slotID, boolean status, String rego)
    {
        id = slotID;
        isOccup = status;              
        carRego = rego;
    }

    
    // getter method for the parking slot ID
    public String getID()
    {
        return id;
    } 


    // getter method for the status of the parking slot
    public boolean getIsOccup()
    {
        return isOccup;
    } 

    
    // getter method for the status of the registration of the car parked at the slot
    public String getCarRego()
    {
        return carRego;
    } 

    
    // setter method for the parking slot ID
    public void setID(String newID)
    {
        id = newID; 
    } 


    // setter method for the status of the parking slot
    public void setIsOccup(boolean newIsOccup)
    {
        isOccup = newIsOccup; 
    } 


    // setter method for the status of the registration of the car parked at the slot
    public void setCarRego(String newCarRego)
    {
        carRego = newCarRego; 
    } 


    // toPrint method to print the information of the parking slot
    public String toString(String slotType)
    {
        String status_str;
        String str;
        if (getIsOccup() == true) {
            status_str = ", and is occupied by " + getCarRego();
        } else {
            status_str = ", and is not occupied";
        }
        
        str = "Slot ID: " + getID() +", is for " + slotType + status_str;
        return str;
    }

} // end of class

  
 

           


    
