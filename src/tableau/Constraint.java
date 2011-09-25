package tableau;

/*
 * DO NOT MODIFY THIS FILE
 */

import tableau.parser.ASTNode;

/**
 * A representation of a (formula, truth value) pair. When trying to satisfy a
 * formula, the formula is broken down into several constraints. We must try to
 * satisfy all the constraints by finding a truth assignment that makes each
 * formula evaluate to its truth value.
 */
public interface Constraint {

   /**
    * Returns the {@code ASTNode} representing the formula that was supplied
    * when the object was created.
    * 
    * @return an {@code ASTNode} representing the formula supplied when the
    *         object was created.
    */
   ASTNode getFormula();

   /**
    * Returns the truth value that was supplied when the object was created.
    * 
    * @return the truth value that was supplied when the object was created.
    */
   boolean getTruthValue();

}
