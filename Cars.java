/**
 * COS70006
 * Project 2 - GUI Parking Spot System 
 * @author Sam Sam Lei (103196737)
 * @version jdk 14.0.1 
 */
 
 

/** create a Cars clas
 */
public class Cars
{
    private String rego;
    private String ownerType;     // staff or visitor
    private String slotID;
        

    // constructor    
    public Cars(String carRego, String carOwner, String slot)
    {
        rego = carRego;
        ownerType = carOwner;
        slotID = slot;
    }


    // getter method for car registration number
    public String getRego()
    {
        return rego;
    } 


    // getter method for owner type
    public String getOwnerType()
    {
        return ownerType;
    } 


     // getter method for Slot ID of the Slot where the car is parking
    public String getSlotID()
    {
        return slotID;
    } 

    
    // setter method for car registration number
    public void setRego(String newRego)
    {
        rego = newRego; 
    } 


    // setter method for owner type
    public void setOwnerType(String newOwner)
    {
        ownerType = newOwner; 
    } 


    // setter method for Slot ID of the Slot where the car is parking  
    public void setSlotID(String newSlotID)
    {
        slotID = newSlotID; 
    } 

    
    // toString method to return a string representation of the car
    public String toString()
    {
        String str = "The car with registration number of '" + getRego() + "' is parked at '" + getSlotID() + "' and owner of the car is a '" + getOwnerType() + "'.";
        return str;        
    }

} // end of class


           


    
