package Statement.Declaration;

import Lexer.Token.Token;

import java.util.ArrayList;

public class DeclarationConstantStatement extends Declaration {
    ArrayList<Token> stmt;
    public DeclarationConstantStatement(ArrayList<Token> stmt){
        super(stmt, false);
    }
}
