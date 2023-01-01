package Statement;

import Lexer.Token.Token;
import Statement.Declaration.ExpressionStatement;

import java.util.ArrayList;

public class PrintStatement extends Statement{

    ArrayList<Token> stmt;
    public PrintStatement(ArrayList<Token> stmt){
        stmt.remove(0);
        this.stmt = stmt;
        this.eval();
    }

    public void eval(){
        System.out.println(new ExpressionStatement(stmt).result());
    }
}
