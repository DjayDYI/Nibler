package Lexer.Parser;

import Lexer.Token.Lexer;
import Lexer.Token.Token;

import java.util.ArrayList;

public class TokenStreamer {

    ArrayList<Token> list = new ArrayList<>();
    int i = 0;


    TokenStreamer(Lexer lex){
        list = lex.tkn;
    }
    public TokenStreamer(ArrayList<Token> strings){
        list =  strings;
    }

    TokenStreamer(Token[] strings){
        for(Token s: strings)
            list.add(s);
    }

    public Token get(){ return list.get(i); }
    public Token getLast(){
        return list.get(i-1);
    }
    public Token getNext(){
        return list.get(i+1);
    }
    public Token next(){ return list.get(++i); }
    public Token last(){
        return list.get(--i);
    }
    public boolean hasNext(){
        return i<list.size()-1;
    }

    public ArrayList<Token> getUntil(String str){
        ArrayList<Token> ret = new ArrayList<>();
        int level = 0;
        do {
            ret.add(this.get());
            this.next();
            if(this.get().getVal().equals("{")) level++;
            if(this.get().getVal().equals("}")) level--;

        }while(!this.get().getVal().equals(str) || level != 0);
        if(hasNext())
            this.next();
        return ret;
    }

    public ArrayList<Token> getUntilToken(String str){
        ArrayList<Token> ret = new ArrayList<>();
        while(!this.get().getVal().equals(str)){
                ret.add(this.get());

            if(this.hasNext())
                this.next();
        }
        return ret;
    }

    public static ArrayList<Token> getCond(ArrayList<Token> stream){
        TokenStreamer s = new TokenStreamer(stream);
        ArrayList<Token> full = s.getUntil(")");
        full.remove(0);
        return full;
    }
    public static ArrayList<ArrayList<Token>> getParam(ArrayList<Token> stream){
        ArrayList<Token> cond = getCond(stream);
        cond.add(new Token(",", Token.TokenType.NULL));
        ArrayList<Token> par = new ArrayList<>();
        ArrayList<ArrayList<Token>> pars = new ArrayList<>();
        int j = 0;
        while(j<cond.size()) {
            if (!cond.get(j).getVal().equals(",")) {
                par.add(cond.get(j));
            } else {
                ArrayList<Token> p = new ArrayList<>(par);
                pars.add(p);
                par.clear();
            }
            j++;
        }
        return pars;
    }

    public static ArrayList<Token> extractparam(ArrayList<Token> stmt, int start){
        int  i =start;
        ArrayList<Token> param = new ArrayList<>();
        while(!stmt.get(i).getVal().equals("("))
            stmt.remove(i);
        stmt.remove(i);
        while(!stmt.get(i).getVal().equals(")")) {
            param.add(stmt.get(i));
            stmt.remove(i);
        }
        stmt.remove(i);
        return param;
    }

}
