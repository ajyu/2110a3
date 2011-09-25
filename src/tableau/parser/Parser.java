package tableau.parser;

import java.io.StringReader;

/**
 * Formula ::= Equivalence<br>
 * Equivalence ::= Equivalence "<->" Implication | Implication<br>
 * Implication ::= Disjunction "->" Implication | Disjunction<br>
 * Disjunction ::= Disjunction "|" Conjunction | Conjunction<br>
 * Conjunction ::= Conjunction "&" Negation | Negation<br>
 * Negation ::= "~" Negation | Grouping<br>
 * Grouping ::= "(" Formula ")" | Constant | Variable<br>
 * Constant ::= "T" | "F"<br>
 * Variable ::= any alphanumeric string besides "T" or "F"
 */
public class Parser {
   private Tokenizer tok;

   public Parser(Tokenizer tok) {
      this.tok = tok;
   }

   public Parser(String formula) {
      this(new Tokenizer(new StringReader(formula)));
   }

   /**
    * Parse a formula.
    * 
    * Formula ::= Equivalence
    * 
    * @return An AST representing a formula
    * @throws ParseException
    */
   public ASTNode parseFormula() throws ParseException {

      ASTNode t = parseEquivalence();

      // check for spurious leftover tokens
      if (tok.hasNext()) {
         throw new ParseException("Unexpected token " + tok.next(), tok.getColumnNumber());
      }
      return t;
   }

   /**
    * Parse an Equivalence.
    * 
    * Equivalence ::= Equivalence "<->" Implication | Implication
    * 
    * @return An AST representing an Equivalence
    * @throws ParseException if syntactically ill-formed or not an equivalence
    */
   public ASTNode parseEquivalence() throws ParseException {
      // parse the first implication
      ASTNode equivalence = parseImplication();

      // parse any other implications, maintaining left-to-right order
      while (tok.getNextType() == TokenType.IFF) {
         tok.eat("<->");
         equivalence = new ASTNode(TokenType.IFF, "<->", equivalence, parseImplication());
      }
      return equivalence;
   }

   /**
    * Parse an implication.
    * 
    * Implication ::= Disjunction "->" Implication | Disjunction
    * 
    * @return An AST representing an Implication
    * @throws ParseException -
    *             General parsing exception
    */
   public ASTNode parseImplication() throws ParseException {
      // TODO: implement me
      return null;
   }

   /**
    * Parse a Disjunction.
    * 
    * Disjunction ::= Disjunction "|" Conjunction | Conjunction
    * 
    * @return AST representing a Disjunction
    * @throws ParseException -
    *             General parsing exception
    */
   public ASTNode parseDisjunction() throws ParseException {
      // TODO: implement me
      return null;
   }

   /**
    * Parse a Conjunction.
    * 
    * Conjunction ::= Conjunction "&" Negation | Negation
    * 
    * @return AST representing a Conjunction
    * @throws ParseException -
    *             General parsing exception
    */
   public ASTNode parseConjunction() throws ParseException {
      // TODO: implement me
      return null;
   }

   /**
    * Parse a Negation.
    * 
    * Negation ::= "~" Negation | Grouping
    * 
    * @return AST representing a Negation
    * @throws ParseException -
    *             General parsing exception
    */
   public ASTNode parseNegation() throws ParseException {
      // TODO: implement me
      return null;
   }
   
   /**
    * Parse a Grouping.
    * 
    * Grouping ::= "(" Formula ")" | Constant | Variable
    * 
    * @return AST representing a Grouping
    * @throws ParseException
    */
   public ASTNode parseGrouping() throws ParseException {
      // check for ( )
      if (tok.getNextType() == TokenType.LPAREN) {
         tok.eat("(");
         ASTNode equivalence = parseEquivalence();
         tok.eat(")");
         return equivalence;
      }
      return parseAtomic();
   }

   /**
    * Parse a Variable (any alphanumeric besides "T" or "F") or
    * Constant ("T" or "F").
    * 
    * @return AST representing a Variable or Constant
    * @throws ParseException
    */
   public ASTNode parseAtomic() throws ParseException {
      if (tok.getNextType() == TokenType.VARIABLE || tok.getNextType() == TokenType.CONSTANT) {
         return new ASTNode(tok.getNextType(), tok.next());
      }
      throw new ParseException("Expected a variable or primitive", tok.getColumnNumber());
   }
}
