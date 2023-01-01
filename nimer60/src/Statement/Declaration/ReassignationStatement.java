package Statement.Declaration;

import Lexer.Token.Token;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class ReassignationStatement extends Statement {
    ArrayList<Token> stmt;
    public ReassignationStatement(ArrayList<Token> stmt){
        this.stmt = stmt;
        this.eval();
    }

    public void eval(){
        if(table.isMutable(stmt.get(0).getVal())){
            if(stmt.size()>3){
                ArrayList<Token> increment = new ArrayList<>();
                increment.add(this.stmt.get(2));
                increment.add(this.stmt.get(3));
                increment.add(this.stmt.get(4));
                String a = new Tree(increment,table).parse();

                ArrayList<Token> assign = new ArrayList<>();
                assign.add(stmt.get(0));
                assign.add(stmt.get(1));
                assign.add(new Token(a, Token.TokenType.IDENTIFIER));
                new Tree(assign,table).parse();
                table.update(assign.get(0).getVal(),a);
            }
            else
            {
                new Tree(stmt,table).parse();
                table.update(stmt.get(0).getVal(),stmt.get(2).getVal());
            }
        }else{
            throw new Error("Can't reassign "+ stmt.get(0)+". This is a constant!");
        }
    }
}
