package tableau.parser;

/*
 * DO NOT MODIFY THIS FILE
 */

/**
 * Node for building an abstract syntax tree (AST) for propositional logic
 */
public class ASTNode {
   private ASTNode left, right;
   private TokenType type;
   private String name;

   /**
    * Creates a new ASTNode of type {@link TokenType#VARIABLE} or
    * {@link TokenType#CONSTANT} with no children.
    * 
    * @param type
    *           Type of the {@code ASTNode} (See the {@link TokenType} for the
    *           options)
    * @param name
    *           A String representation of this {@code ASTNode}
    */
   public ASTNode(TokenType type, String name) {
      this.type = type;
      this.name = name;
   }

   /**
    * Creates a new {@code ASTNode} with children. Use this constructor for
    * making an {@code ASTNode} for any of the the following {@link TokenType}s:
    * OR, NOT, AND, IMPLIES, IFF.
    * 
    * @param type
    *           The type of this {@code ASTNode}
    * @param left
    *           The left child of this {@code ASTNode}
    * @param right
    *           The right child of this {@code ASTNode}
    */
   public ASTNode(TokenType type, String name, ASTNode left, ASTNode right) {
      this(type, name);
      this.left = left;
      this.right = right;
   }

   /**
    * Gets the name of this {@code ASTNode}.
    * 
    * @return The name of this {@code ASTNode}
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the {@code TokenType} represented by this {@code ASTNode}. Legal
    * types for {@code ASTNode}s are: VARIABLE, PRIMITIVE, OR, NOT, AND,
    * IMPLIES, IFF.
    * 
    * @return The type of this {@code ASTNode}
    */
   public TokenType getType() {
      return type;
   }

   /**
    * Gets the left child of this {@code ASTNode}.
    * 
    * @return The left child of this {@code ASTNode}
    */
   public ASTNode getLeft() {
      return left;
   }

   /**
    * Gets the right child of this {@code ASTNode}.
    * 
    * @return The right child of this {@code ASTNode}
    */
   public ASTNode getRight() {
      return right;
   }

   /**
    * Returns a {@code String} infix representation of the abstract syntax tree.
    */
   public String toString() {
      StringBuilder sb = new StringBuilder();
      toString(sb);
      return sb.toString();
   }
   
   /**
    * Recursive helper for {@link #toString()}.
    * 
    * @param sb
    *           a {@code StringBuilder} to append the characters to
    */
   private void toString(StringBuilder sb) {
      switch (getType()) {
      case CONSTANT:
      case VARIABLE:
         sb.append(getName());
         break;
      case NOT:
         sb.append("~");
         getRight().toString(sb);
         break;
      default: // binary op
         sb.append("(");
         getLeft().toString(sb);
         sb.append(" ");
         sb.append(getName());
         sb.append(" ");
         getRight().toString(sb);
         sb.append(")");
      }
   }

   /**
    * Returns a {@code String} representation of an abstract syntax tree on its
    * side.
    */
   public String toTree() {
      StringBuilder sb = new StringBuilder();
      toTree(sb, 0);
      return sb.toString();
   }

   /**
    * Recursive helper for {@link #toTree()}. Returns a {@code String}
    * representation of an AST starting at the given indent level
    * 
    * @param sb
    *           a {@code StringBuilder} to append the characters to
    * @param level
    *           The level of indentation
    */
   private void toTree(StringBuilder sb, int level) {
      final String spaces = "                         ";
      if (level > spaces.length()) {
         sb.append(spaces);
         sb.append("...");
         return;
      }

      if (getRight() != null) {
         getRight().toTree(sb, level + 1);
      }
      sb.append(spaces.substring(0, level));
      sb.append(getName());
      sb.append('\n');
      if (getLeft() != null) {
         getLeft().toTree(sb, level + 1);
      }
   }
}
