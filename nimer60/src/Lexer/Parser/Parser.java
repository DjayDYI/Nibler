package Lexer.Parser;

import Lexer.Token.Token;
import Statement.*;
import Statement.Declaration.*;
import Statement.Flow.ForStatement;
import Statement.Flow.IfStatement;
import Statement.Flow.SwitchStatement;
import Statement.Flow.WhileStatement;
import SymbolTable.Visitor;

import java.util.ArrayList;

public class Parser implements Visitor {

    ArrayList<Statement> flow = new ArrayList<Statement>();

    public Parser(TokenStreamer stream){
        createStatement(stream,flow);
    }

    public static void createStatement(TokenStreamer stream, ArrayList<Statement> flow){
            while(stream.hasNext()) {
                ArrayList<Token> stmt = stream.getUntil(";");
                if (stmt.get(0).getVal().equals("var")) flow.add(new DeclarationVariableStatement(stmt));
                else if (stmt.get(0).getVal().equals("const")) flow.add(new DeclarationConstantStatement(stmt));
                else if (stmt.get(0).getVal().equals("func")) flow.add(new DeclarationFunctionStatement(stmt));
                else if (stmt.get(0).getVal().equals("if")) flow.add(new IfStatement(stmt));
                else if (stmt.get(0).getVal().equals("while")) flow.add(new WhileStatement(stmt));
                else if (stmt.get(0).getVal().equals("for")) flow.add(new ForStatement(stmt));
                else if (stmt.get(0).getVal().equals("switch")) flow.add(new SwitchStatement(stmt));
                else if (stmt.get(0).getVal().equals("print")) flow.add(new PrintStatement(stmt));
                else if (stmt.get(0).getType().equals(Token.TokenType.ARRAY)) flow.add(new ExpressionStatement(stmt));
                else if (stmt.get(1).getVal().equals("=")) flow.add(new ReassignationStatement(stmt));
                else new ExpressionStatement(stmt);

            }
    }

    public String getSymbolTable(String name){
        return table.valueSymbol(name);
    }

}
