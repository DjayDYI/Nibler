package SymbolTable;

import Lexer.Parser.Parser;
import Lexer.Token.Token;
import Lexer.Parser.TokenStreamer;

import java.util.ArrayList;

import static Lexer.Tree.Tree.isNumeric;

public class FunctionInfo {

    private ArrayList<Token> param;
    private ArrayList<Token> context;
    FunctionInfo(ArrayList<Token> param, ArrayList<Token> context){
        this.param = param;
        this.context = context;
    }

    public String eval(ArrayList<Token> param, SymbolTable table) {
        context.remove(0);
        TokenStreamer stream = new TokenStreamer(context);

        // Extract param value from table
        ArrayList<String> para = new ArrayList<>();
        for(Token t:param) {
            if (table.contains(t.getVal())){
                para.add(table.valueSymbol(t.getVal()));
            }
            else
            {
                para.add(t.getVal());
            }
        }


        // param
        ArrayList<Token> funcparam = new ArrayList<>();
        for(int i = 0; i<param.size();i++){
            funcparam.add(new Token("var", Token.TokenType.IDENTIFIER));
            funcparam.add(new Token(this.param.get(i).getVal(), Token.TokenType.IDENTIFIER));
            funcparam.add(new Token("=", Token.TokenType.ASSIGN));
            funcparam.add(new Token(para.get(i), Token.TokenType.IDENTIFIER));
            funcparam.add(new Token(";", Token.TokenType.NULL));
        }


        // context
        if(hasFoundReturn()){
            ArrayList<Token> ctx = stream.getUntilToken("return");
            for(Token t: ctx){
                funcparam.add(t);
            }
        }else{
            while(!stream.get().getVal().equals("}")){
                funcparam.add(stream.get());
                stream.next();
            }
        }

        // return
        if(hasFoundReturn()) {
            ArrayList<Token> ret = stream.getUntilToken(";");
            ret.remove(0);
            ret.add(0, new Token("var", Token.TokenType.KEYWORD));
            ret.add(1, new Token("retval", Token.TokenType.IDENTIFIER));
            ret.add(2, new Token("=", Token.TokenType.ASSIGN));
            ret.add(new Token(";", Token.TokenType.NULL));
            funcparam.addAll(ret);

            TokenStreamer func = new TokenStreamer(funcparam);
            Parser p = new Parser(func);
            String funcval = p.getSymbolTable("retval");
            while(!isNumeric(funcval))
                funcval = p.getSymbolTable(funcval);
            return funcval;
        }else{
            return "void";
        }
    }
    
    boolean hasFoundReturn(){
        for(Token t:context){
            if (t.getVal().equals("return"))
                return true;
        }
        return false;
    }

}
