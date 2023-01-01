package Statement.Declaration;

import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;
import Lexer.Tree.Tree;
import Statement.Statement;

import java.util.ArrayList;

public class Declaration extends Statement {
    ArrayList<Token> stmt;
    boolean isMutable;

    boolean isArray;
    public Declaration(ArrayList<Token> stmt, boolean isMutable){
        stmt.remove(0); // var keyword
        this.isMutable = isMutable;
        this.stmt = stmt;
        this.isArray = stmt.get(0).getType() == Token.TokenType.ARRAY;
        this.eval();
    }

    public void eval(){

        if(!isArray){
            if(table.containFunction(stmt.get(2).getVal())){
                String res = table.evalFunction(stmt.get(2).getVal(), TokenStreamer.extractparam(stmt,2));
                stmt.add(new Token(res, Token.TokenType.NUMERIC));
            }

            if(stmt.size()>3){
                Token left = stmt.get(0);
                Token op= stmt.get(1);

                stmt.remove(0);
                stmt.remove(0);
                String a = new ExpressionStatement(stmt).result();
                Token b = new Token(a, Token.TokenType.NUMERIC);

                ArrayList<Token> assign = new ArrayList<>();
                assign.add(left);
                assign.add(op);
                assign.add(b);
                String c = new Tree(assign,table).parse();
                table.setMutable(c, true);

            }else{
                String a = new ExpressionStatement(stmt).result();
                table.add(stmt.get(0).getVal(), a);
                table.setMutable(stmt.get(0).getVal(), isMutable);
            }
        }else{
            String name = stmt.get(0).getVal().substring(0,stmt.get(0).getVal().indexOf("["));
            stmt.remove(0);
            stmt.remove(0);

            ArrayList<String> values = new ArrayList<>();
            for(Token t:stmt){
                if(!t.getType().equals(Token.TokenType.SEPARATOR)&&
                        !t.getType().equals(Token.TokenType.PARENTHESIS))
                    values.add(t.getVal());
            }
            table.add(name, values);
        }
    }
}
