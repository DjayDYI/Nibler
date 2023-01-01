package Statement.Declaration;

import Lexer.Token.Token;

import java.util.ArrayList;

public class DeclarationVariableStatement extends Declaration {
    ArrayList<Token> stmt;
    public DeclarationVariableStatement(ArrayList<Token> stmt){
        super(stmt, true);
    }

}
