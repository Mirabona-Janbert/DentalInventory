/*
 * This class provides the exception thrown if fields are empty.
 */
package dentalinventory;

/**
 *
 * @author Sourav
 */
public class EmptyException extends Exception {

    public EmptyException() {

    }

    @Override
    public String toString() {
        return "The entry was empty.";
    }
    
}
