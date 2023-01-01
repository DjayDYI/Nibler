package Lexer.Token;

public class Token{

    public enum TokenType{
        OPERATOR("Operator"),
        PARENTHESIS("Parenthesis"),
        ASSIGN("Assignation"),
        IDENTIFIER("Identifier"),
        NULL("null"),
        CONTROL("Control"),
        NUMERIC("numeric"),
        STRING("string"),
        BOOL("boolean"),
        VOID("void"),
        ARRAY("array"),
        SEPARATOR("separator"),
        KEYWORD("keyword");

        private String type;
        TokenType(String type){
            this.type = type;
        }

        public String getType(){
            return type;
        }
    }

    String val = null;
    TokenType type = null;
    public Token(String val, TokenType type){
        this.val = val;
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public String getVal() {
        return val;
    }

}
