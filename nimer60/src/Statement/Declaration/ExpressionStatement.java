package Statement.Declaration;

import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class ExpressionStatement extends Statement {
    ArrayList<Statement> instr = new ArrayList<Statement>();
    String res = "null";
    public ExpressionStatement(ArrayList<Token> stmt){
        ArrayList<Token> expr = new ArrayList<>();

        int i=0;
        while(i<stmt.size()){
            if(stmt.get(i).getType().equals(Token.TokenType.IDENTIFIER)){

                String term = stmt.get(i).getVal();
                if(table.containFunction(term)){
                    String res = table.evalFunction(term, TokenStreamer.extractparam(stmt,0));
                    expr.add(new Token(res, Token.TokenType.NUMERIC));
                }

                if(table.containsEntry(term)){
                    String res = table.valueSymbol(term);
                    expr.add(new Token(res, Token.TokenType.NUMERIC));
                }

            }
            if(stmt.get(i).getType().equals(Token.TokenType.NUMERIC)){
                expr.add(stmt.get(i));
            }
            if(stmt.get(i).getType().equals(Token.TokenType.OPERATOR)){
                expr.add(stmt.get(i));
            }
            if(stmt.get(i).getType().equals(Token.TokenType.BOOL)){
                expr.add(stmt.get(i));
            }
            if(stmt.get(i).getType().equals(Token.TokenType.STRING)){
                expr.add(stmt.get(i));
            }
            if(stmt.get(i).getType().equals(Token.TokenType.ARRAY)){
                String name = stmt.get(i).getVal().substring(0, stmt.get(i).getVal().indexOf('['));
                String index = stmt.get(i).getVal().substring(stmt.get(i).getVal().indexOf('[')+1,stmt.get(i).getVal().indexOf(']'));
                expr.add(new Token(table.valueSymbol(name, index), Token.TokenType.NUMERIC));
            }

            i++;
        }

        res = new Tree(expr,table).parse();

    }

    public String result(){
        return res;
    }
}
