/*
 * This class creates the exception for an entry being out of bounds.
 */
package dentalinventory;

public class RangeException extends Exception {
        
    public RangeException(){
        
    }

    @Override
    public String toString() {
        return "The item was out of the current quantity bounds.";
    }
    
    
}
