package tableau.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Arrays;

import org.junit.Test;

import tableau.parser.ASTNode;
import tableau.parser.ParseException;
import tableau.parser.Parser;
import tableau.parser.TokenType;

public class ParserTest {
   
   protected ASTNode parse(String formula) throws ParseException {
      return new Parser(formula).parseFormula();
   }

   /**
    * 5 points
    */
   @Test
   public void basicTest() throws ParseException {
      final Collection<String> formulas = Arrays.asList("T", "P", "~P",
               "~~~~~~P", "(P | Q)", "(P & Q)", "~(P -> Q)", "(Q <-> Q)",
               "(Q -> (P -> Q))");

      for (String formula : formulas) {
         assertEquals(formula, parse(formula).toString());
      }
   }

   /**
    * 5 points
    */
   @Test
   public void precedenceTest() throws ParseException {
      assertEquals(TokenType.NOT, parse("~P").getType());
      assertEquals(TokenType.AND, parse("P & Q").getType());
      assertEquals(TokenType.IMPLIES, parse("~P -> P").getType());
      assertEquals(TokenType.OR, parse("(P & Q) | P").getType());
      assertEquals(TokenType.IMPLIES, parse("Q -> P -> Q").getType());
      assertEquals(TokenType.IFF, parse("~(P & Q) <-> ~P | ~Q").getType());
   }

   /**
    * 1 point
    */
   @Test(expected = ParseException.class)
   public void exceptionTest1() throws ParseException {
      parse("((sss -> ttt) -> uuu) qqq");
   }

   /**
    * 1 point
    */
   @Test(expected = ParseException.class)
   public void exceptionTest2() throws ParseException {
      parse("test-driven development is good for the soul");
   }
}
