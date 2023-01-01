package Statement.Flow;

import Lexer.Parser.Parser;
import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Declaration.DeclarationVariableStatement;
import Statement.Declaration.ReassignationStatement;
import Statement.Statement;

import java.util.ArrayList;

public class ForStatement extends Statement {

    ArrayList<ArrayList<Token>> fullcond;
    ArrayList<Statement> instr = new ArrayList<Statement>();
    public ForStatement(ArrayList<Token> stmt){

        // for keyword
        stmt.remove(0);

        // Condition
        this.fullcond = TokenStreamer.getParam(stmt);

        // Declaration variable
        new DeclarationVariableStatement(this.fullcond.get(0));

        // Create a streamer
        TokenStreamer stream = new TokenStreamer(trim(stmt));

        // Create sub statement abd parse the body of control flow
        Parser.createStatement(stream, instr);

        this.eval();
    }

    public void eval(){
        String ast = new Tree(this.fullcond.get(1),table).parse();
        if(ast.equals("true")){
            for(Statement s: instr)
                s.eval();
            new ReassignationStatement(this.fullcond.get(2));
            eval();
        }
    }

    public ArrayList<Token> trim(ArrayList<Token> stmt){
        // Remove condition token from stmt list
        stmt.remove(0);
        for(int i=0; i<fullcond.get(0).size() ;i++)stmt.remove(0);
        stmt.remove(0);
        for(int i=0; i<fullcond.get(1).size() ;i++)stmt.remove(0);
        stmt.remove(0);
        for(int i=0; i<fullcond.get(2).size() ;i++)stmt.remove(0);
        stmt.remove(0);


        // remove curly brace at begining and end
        stmt.remove(0);
        stmt.remove(stmt.size()-1);
        return stmt;
    }
}
