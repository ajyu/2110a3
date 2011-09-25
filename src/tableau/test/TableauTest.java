package tableau.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import tableau.Tableau;
import tableau.parser.ParseException;
import tableau.parser.Parser;

public class TableauTest {
   
   private Map<String, Boolean> getTa(String s) throws ParseException {
      Tableau tableau = new Tableau(new Parser(s).parseFormula());
      return tableau.prove();
   }

   @Test
   public void basicTest() throws ParseException {
      assertNull(getTa("T"));
      assertNull(getTa("a | b -> b | a"));
      assertNull(getTa("a & b -> a"));
      assertNull(getTa("a -> b | a"));
      assertNull(getTa("((p -> q) & (p & q -> r)) -> (p -> r)"));
      assertNull(getTa("((p -> q) & (~p -> r)) <-> ((p & q) | (~p & r))"));
      assertNull(getTa("((pa -> qb) & (pa & qb -> rc)) -> (pa -> rc)"));
      assertNull(getTa("(a -> b) & (~a -> c) <-> (a & b) | (~a & c)"));
      assertNull(getTa("(a -> b) <-> (~b -> ~a)"));
   }
   
   @Test
   public void implicationTest() throws ParseException {
      assertNotNull(getTa("((((s -> t) -> u) -> (s -> t)) -> s) -> u"));
      assertNotNull(getTa("(p -> q) -> p"));
   }
   
   @Test
   public void negativeTest1() throws ParseException {
      Map<String, Boolean> ta = getTa("F");
      assertTrue(ta.size() == 0);
   }
   
   @Test
   public void negativeTest2() throws ParseException {
      Map<String, Boolean> ta = getTa("P");
      assertNotNull(ta);
      assertTrue(ta.size() > 0);
   }
   
   @Test
   public void negativeTest3() throws ParseException {
      Map<String, Boolean> ta = getTa("aa -> bb");
      assertNotNull(ta);
      assertEquals(2,ta.size());
      assertTrue(ta.get("aa"));
      assertFalse(ta.get("bb"));
   }
   
   @Test
   public void deMorganTest() throws ParseException {
      assertNull(getTa("~(a | b) <-> ~a & ~b"));
      assertNull(getTa("~(a & b) <-> ~a | ~b"));
   }

   @Test
   public void skTest() throws ParseException {
      assertNull(getTa("(s -> t -> u) -> (s -> t) -> s -> u"));
      assertNull(getTa("p -> q -> p"));
   }

   @Test
   public void peirceTest() throws ParseException {
      assertNull(getTa("((x -> y) -> x) -> x"));
   }
}
