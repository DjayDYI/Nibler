package Lexer.Parser;

import Lexer.Token.Lexer;

public class Main {

    public static void main(String[] args) {
        Lexer lex = new Lexer("class a { var a1; var a2; func m1(){ return a2; };};");
        TokenStreamer streamer = new TokenStreamer(lex);
        Parser parser = new Parser(streamer);
    }

}