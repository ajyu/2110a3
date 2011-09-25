package tableau;

/*
 * DO NOT MODIFY THIS FILE
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import tableau.parser.ASTNode;
import tableau.parser.ParseException;
import tableau.parser.Parser;

public class Driver {

   public static void main(String args[]) {

      if (args.length == 0) {
         System.out.println("Please supply a filename.");
         return;
      }
      Scanner input = null;
      try {
         input = new Scanner(new File(args[0]));
      } catch (FileNotFoundException fnf) {
         System.out.format("File %s not found\n", args[0]);
         return;
      }

      while (input.hasNextLine()) {
         String formula = input.nextLine().trim();
         if (formula.startsWith("#")) continue;
         if (formula.equals("")) continue;
         Parser parser = new Parser(formula);
         System.out.format("Parsing %s ...\n", formula);

         ASTNode ast;
         try {
            ast = parser.parseFormula();
         } catch (ParseException e) {
            error(String.format("Parse error: %s near column %d\n",
                     e.getMessage(), e.getLocation()));
            continue;
         }

         System.out.print("Result: ");
         System.out.println(ast.toString());

         Map<String, Boolean> truthAssignment = new Tableau(ast).prove();

         if (truthAssignment == null) {
            System.out.println("The formula is a tautology!");
         } else {
            System.out.println("The formula is not a tautology. The following truth assignment falsifies it:");
            System.out.println(truthAssignment);
         }
         System.out.println();
      }
   }

   private static void error(String message) {
      System.out.flush();
      System.err.println(message);
      System.err.flush();
   }
}
