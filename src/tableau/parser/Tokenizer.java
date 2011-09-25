package tableau.parser;

/*
 * DO NOT MODIFY THIS FILE
 */

import java.io.*;

/**
 * A tokenizer for the parser. Instantiate only one {@code Tokenizer} object
 * for your parser. When you make a new {@code Tokenizer}, pass it the name of
 * the file that you would like to parse. You can then use your tokenizer's
 * {@code hastNext()} and {@code next()} functions to get the next tokens from
 * the file.
 * 
 * Note that there is no {@code peek()} or {@code putBack()} function. This
 * means that you must be careful in your parsing. When you call {@code next()},
 * you will get the next token (call it T1) and advance the pointer to the
 * following token (call it T2). The next time you call {@code next()}, you will
 * get T2. Use function {@code getNextType()} to get the type of the next token
 * without advancing the pointer.
 */
public class Tokenizer {
   
   private Reader input;
   private int nextChar;
   private int columnNumber;

   /**
    * Creates a new Tokenizer.
    * 
    * @param input
    *           The input stream to tokenize.
    */
   public Tokenizer(Reader input) {
      this.input = input;
      columnNumber = 0;
      // scan past whitespace
      for (getNextChar(); isWhitespace(nextChar); getNextChar()) {}
   }

   /**
    * Gets the next character, sets it to nextChar, and returns it. Returns -1
    * if there are no more characters in the input stream.
    * 
    * @return The value of the next character in the input stream, or -1 if
    *         there are no more characters.
    */
   private int getNextChar() {
      if (nextChar == -1) return -1;
      try {
         nextChar = input.read();
      } catch (IOException e) {
         fatalError("Cannot read input: " + e.getMessage());
      }
      columnNumber++;
      return nextChar;
   }
   
   private boolean isWhitespace(int c) {
      return c == ' ' || c == '\t' || c == '\n';
   }

   /**
    * Reads an alphanumeric string. Scans past following whitespace.
    */
   private String readAlphanumeric() {
      StringBuilder sb = new StringBuilder();
      sb.append((char)nextChar);
      for (getNextChar(); Character.isLetterOrDigit(nextChar); getNextChar()) {
         sb.append((char)nextChar);
      }
      while (isWhitespace(nextChar)) getNextChar();
      return sb.toString();
   }
   
   /**
    * @return true if there are more non-whitespace characters in the input
    *         stream.
    */
   public boolean hasNext() {
      while (isWhitespace(nextChar)) getNextChar();
      return nextChar != -1;
   }

   /**
    * Scans past the next token, checking whether it matches the expected token.
    * Throws a {@code ParseException} if the token did not match.
    * 
    * @param s
    *           {@code String} to check against the next token
    * @throws ParseException
    *            if there are no more tokens in the input stream or the next
    *            token did not match the expected token.
    */
   public void eat(String s) throws ParseException {
      if (!hasNext() || !next().equals(s))
         throw new ParseException("Expected token " + s, getColumnNumber());
   }

   /**
    * Gets the next token in the input stream and advances the pointer to the
    * beginning of the following token. Do not call this function without first
    * calling {@code hasNext()} to make sure that more tokens exist.
    * 
    * @return The next token.
    * @throws ParseException
    *            if there are no more tokens in the input stream.
    */
   public String next() throws ParseException {
      if (!hasNext())
         throw new ParseException("Unexpected end of file", getColumnNumber());

      String returnString = null;

      switch (getNextType()) {
      case VARIABLE:
         return readAlphanumeric();
      case IMPLIES:
         returnString = "->";
         if (getNextChar() != '>')
            throw new ParseException("Illegal token, -> expected", getColumnNumber());
         break;
      case IFF:
         returnString = "<->";
         if (getNextChar() != '-' || getNextChar() != '>')
            throw new ParseException("Illegal token, <-> expected", getColumnNumber());
         break;
      case CONSTANT:
      case LPAREN:
      case RPAREN:
      case AND:
      case OR:
      case NOT:
         returnString = String.valueOf((char)nextChar);
         break;
      case EOF:
         throw new ParseException("Unexpected end of file", getColumnNumber());
      default:
         throw new ParseException("Unrecognized token " + (char)nextChar, getColumnNumber());
      }
      // scan past whitespace
      for (getNextChar(); isWhitespace(nextChar); getNextChar()) {}
      return returnString;
   }

   /**
    * Gets the type of the next token in the input stream. Does not advance the
    * pointer.
    * 
    * @return The type of the next token or {@link TokenType#EOF} if no more
    *         tokens exist.
    */
   public TokenType getNextType() {
      switch (nextChar) {
      case -1:
         return TokenType.EOF;
      case 'F':
      case 'T':
         return TokenType.CONSTANT;
      case '-':
         return TokenType.IMPLIES;
      case '<':
         return TokenType.IFF;
      case '&':
         return TokenType.AND;
      case '|':
         return TokenType.OR;
      case '~':
         return TokenType.NOT;
      case '(':
         return TokenType.LPAREN;
      case ')':
         return TokenType.RPAREN;
      }
      if (Character.isLetterOrDigit(nextChar)) return TokenType.VARIABLE;
      return TokenType.ILLEGAL;
   }

   /**
    * Closes the input file.
    */
   public void close() {
      try {
         input.close();
      } catch (IOException e) {
         fatalError("Cannot close input file. " + e.getMessage());
      }
   }

   /**
    * Gets the approximate number of characters read. Useful for debugging.
    * 
    * @return The approximate column number of the iterator.
    */
   public int getColumnNumber() {
      return columnNumber;
   }
   
   private void fatalError(String s) {
      System.err.println("Tokenizer Error: " + s);
      System.exit(1);
   }
}
