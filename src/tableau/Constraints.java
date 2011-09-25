package tableau;

/*
 * DO NOT MODIFY THIS FILE
 */

import java.util.Map;
import java.util.Queue;

/**
 * A collection of {@code Constraint} objects along with a
 * truth assignment, which is a partial map from variables to boolean
 * values.
 */
public interface Constraints extends Queue<Constraint> {

   /**
    * Returns the partial truth assignment associated with this
    * {@code Constraints} object.
    * 
    * @return the partial truth assignment associated with this
    *         {@code Constraints} object
    */
   Map<String, Boolean> getTruthAssignment();

   /**
    * Creates a deep copy of this {@code Constraints} object. "Deep copy" means
    * that any Object maintained as part of the state of this object must be
    * physically copied, making == false.
    * 
    * @return a deep copy of this {@code Constraints} object
    */
   Constraints clone();

}
