package tableau;

import java.util.Map;

import tableau.parser.ASTNode;

/**
 * A tableau theorem prover for propositional logic.
 */
public class Tableau {

   private ASTNode ast;
   
   /**
    * Constructs a tableau theorem prover for the given parsed formula.
    * 
    * @param ast the parsed formula to prove
    */
   public Tableau(ASTNode ast) {
      this.ast = ast;
   }

   /**
    * Proves that the formula represented by ASTNode ast is a tautology using an
    * implicit tableau tree. If the formula is a tautology, returns null.
    * If the formula is not a tautology, returns a truth assignment
    * that falsifies the formula.
    * 
    * @return - null if the formula is a tautology, otherwise a truth
    *           assignment that falsifies the formula
    */
   public Map<String, Boolean> prove() {
      Constraints c = new TabConstraints();
      c.add(new TabConstraint(ast, false));
      return satisfy(c);
   }
   
   public Map<String, Boolean> satisfy(Constraints cs) {
      Constraint c = cs.poll();
      if (c == null) return cs.getTruthAssignment();
      
      Constraints cloned;
      Map<String, Boolean> truthAssignment;
      ASTNode node = c.getFormula();
      ASTNode left = node.getLeft();
      ASTNode right = node.getRight();
      
      switch (node.getType()) {
      case NOT:
         // TODO: implement me
         break;
      case AND:
         if (c.getTruthValue()) {
            cs.add(new TabConstraint(left, true));
            cs.add(new TabConstraint(right, true));
         } else {
            cloned = cs.clone();
            cloned.add(new TabConstraint(left, false));
            truthAssignment = satisfy(cloned);
            if (truthAssignment != null) return truthAssignment;
            cs.add(new TabConstraint(right, false));
         }
         break;
      case OR:
         // TODO: implement me
         break;
      case IMPLIES:
         // TODO: implement me
         break;
      case IFF:
         // TODO: implement me
         break;
      case CONSTANT:
         // TODO: implement me
         break;
      case VARIABLE:
         // TODO: implement me
         break;

      }
      return satisfy(cs);
   }
}