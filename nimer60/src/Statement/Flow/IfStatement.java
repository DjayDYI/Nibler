package Statement.Flow;

import Lexer.Parser.Parser;
import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class IfStatement extends Statement {

    ArrayList<Token> cond;
    ArrayList<Statement> instr = new ArrayList<Statement>();
    TokenStreamer trueStream;
    TokenStreamer falseStream;

    public IfStatement(ArrayList<Token> stmt){

        // if keyword
        stmt.remove(0);

        // Condition
        this.cond = TokenStreamer.getCond(stmt);

        // Create a streamer
        TokenStreamer stream = new TokenStreamer(trim(stmt));

        if(hasFoundElse(stmt))
        {
            trueStream = new TokenStreamer(trueStatement(stream));
            falseStream = new TokenStreamer(falseStatement(stream));
        }
        else
        {
            trueStream = stream;
        }

        this.eval();
    }

    public void eval(){
        String ast = new Tree(cond,table).parse();
        if(ast.equals("true"))
            Parser.createStatement(trueStream, instr);
        else
            Parser.createStatement(falseStream, instr);
    }

    private boolean hasFoundElse(ArrayList<Token> stmt){
        for(Token tkn:stmt){
            if(tkn.getVal().equals("else")){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Token> trueStatement(TokenStreamer stream){
        ArrayList<Token> iftkn = stream.getUntilToken("else");
        iftkn.remove(iftkn.size()-1);
        return iftkn;
    }
    private ArrayList<Token> falseStatement(TokenStreamer stream){
        ArrayList<Token> elsetkn = stream.getUntilToken("}");
        elsetkn.remove(0);
        elsetkn.remove(0);
        return elsetkn;
    }

    public ArrayList<Token> trim(ArrayList<Token> stmt){
        // Remove condition token from stmt list
        // +2 for `(` before and `)` after the condition
        for(int i=0; i<cond.size() +2 ;i++){
            stmt.remove(0);
        }
        return stmt;
    }

}
