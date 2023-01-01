package Statement.Flow;

import Lexer.Parser.Parser;
import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class WhileStatement extends Statement {
    ArrayList<Token> cond;
    ArrayList<Statement> instr = new ArrayList<Statement>();
    public WhileStatement(ArrayList<Token> stmt){
        // if keyword
        stmt.remove(0);

        // Condition
        this.cond = TokenStreamer.getCond(stmt);

        // Create a streamer
        TokenStreamer stream = new TokenStreamer(trim(stmt));

        // Create sub statement abd parse the body of control flow
        Parser.createStatement(stream, instr);

        this.eval();

    }
    public void eval(){
        String ast = new Tree(cond,table).parse();
        while(ast.equals("true")){
            for(Statement s: instr)
                s.eval();
            eval();
        }
    }

    public ArrayList<Token> trim(ArrayList<Token> stmt){
        // Remove condition token from stmt list
        // +2 for `(` before and `)` after the condition
        for(int i=0; i<cond.size() +2 ;i++){
            stmt.remove(0);
        }

        // remove curly brace at beigining and end
        stmt.remove(0);
        stmt.remove(stmt.size()-1);
        return stmt;
    }

}
