package Statement.Flow;

import Lexer.Parser.Parser;
import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class SwitchStatement extends Statement {
    ArrayList<Token> cond;
    ArrayList<Statement> instr = new ArrayList<Statement>();
    ArrayList<ArrayList<Token>> cases = new ArrayList<>();
    ArrayList<ArrayList<Token>> casecond = new ArrayList<ArrayList<Token>>();
    ArrayList<TokenStreamer> stmtStream = new ArrayList<>();



    public SwitchStatement(ArrayList<Token> stmt){

        // if keyword
        stmt.remove(0);

        // Condition
        this.cond = TokenStreamer.getCond(stmt);

        // Create a streamer
        TokenStreamer stream = new TokenStreamer(trim(stmt));

        if(!foundCaseDefault(stmt))
        {
            throw new Error("Switch case does not have defaut case");
        }

        cases = caseStatement(stream);

        // For each cases there is condition
        for(int i=0;i<cases.size();i++){
            ArrayList<Token> case_built = new ArrayList<>();
            case_built.add(cond.get(0));
            case_built.add(new Token("==", Token.TokenType.OPERATOR));
            case_built.add(cases.get(i).get(1));
            casecond.add(case_built);
        }

        // For each cases there is statement
        for(int i=0;i<cases.size();i++) {
            cases.get(i).remove(0);
            cases.get(i).remove(0);
            cases.get(i).remove(0);
            cases.get(i).add(new Token(";", Token.TokenType.NULL));
            TokenStreamer streamer = new TokenStreamer(cases.get(i));
            stmtStream.add(streamer);
        }

            this.eval();
    }

    public void eval(){

        for(int i =0; i<casecond.size();i++) {
            String ast = new Tree(casecond.get(i), table).parse();
            if(ast.equals("true")){
                Parser.createStatement(stmtStream.get(i), instr);
            }
        }
    }

    private boolean foundCaseDefault(ArrayList<Token> stmt){
        for(Token tkn:stmt){
            if(tkn.getVal().equals("default")){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Token> trim(ArrayList<Token> stmt){
        // Remove condition token from stmt list
        // +2 for `(` before and `)` after the condition
        for(int i=0; i<cond.size() +2 ;i++){
            stmt.remove(0);
        }
        return stmt;
    }

    private ArrayList<ArrayList<Token>> caseStatement(TokenStreamer stream){
        ArrayList<ArrayList<Token>> cases = new ArrayList<>();
        ArrayList<Token> caset = new ArrayList<>();
        stream.next();
        while(!stream.get().getVal().equals("default"))
        {
            if(stream.get().getVal().equals(";"))
            {
                ArrayList<Token> casecopy = new ArrayList<>(caset);
                cases.add(casecopy);
                caset.clear();
            }
            else
            {
                caset.add(stream.get());
            }
            stream.next();
        }
        while(!stream.get().getVal().equals("}"))
        {
            if(stream.get().getVal().equals(";"))
            {
                ArrayList<Token> casecopy = new ArrayList<>(caset);
                cases.add(casecopy);
                caset.clear();
            }
            else
            {
                caset.add(stream.get());
            }
            stream.next();
        }

        return cases;
    }
}
