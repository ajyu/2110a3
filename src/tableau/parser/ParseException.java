package tableau.parser;

/*
 * DO NOT MODIFY THIS FILE
 */

/**
 * An Exception thrown by the parser
 * 
 * @param msg
 *           An error message
 * @param location
 *           Approximate column number where the error occurred
 */
public class ParseException extends Exception {
   private int location;

   /**
    * Constructs a {@code ParseException}.
    * 
    * @param msg
    *           A message
    * @param location
    *           Approximate column number where the error occurred
    */
   public ParseException(String msg, int location) {
      super(msg);
      this.location = location;
   }

   /**
    * Returns an approximate column number where the error occurred.
    * 
    * @return an approximate column number where the error occurred
    */
   public int getLocation() {
      return location;
   }
}
