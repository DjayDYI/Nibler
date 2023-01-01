package Statement.Declaration;

import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Statement.Statement;

import java.util.ArrayList;

public class DeclarationFunctionStatement extends Statement {

    Token name;
    ArrayList<Token> param;
    ArrayList<Token> context;
    public DeclarationFunctionStatement(ArrayList<Token> stmt){
        stmt.remove(0); //func
        this.name = stmt.get(0);
        this.param = TokenStreamer.extractparam(stmt, 0);
        this.context = stmt;
        this.eval();
    }

    public void eval(){
        table.addfunction(this.name.getVal(), this.param, context);
    }
}
