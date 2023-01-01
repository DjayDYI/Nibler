package SymbolTable;

import Lexer.Token.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable{
    Map<String, SymbolInfo> table = new HashMap<>();
    Map<String, FunctionInfo> tablefunction = new HashMap<>();
    SymbolTable(){}
    public void add(String name, String val){
        table.put(name,new SymbolInfo(val));
    }

    public void add(String name, ArrayList<String> val){
        table.put(name,new SymbolInfo(val));
    }

    public boolean contains(String name){
        return table.containsKey(name) || tablefunction.containsKey(name);
    }

    public boolean containsEntry(String name){
        return table.containsKey(name);
    }

    public String valueSymbol(String name){
        return table.get(name).getValue();
    }

    public String valueSymbol(String name, String index){
        return table.get(name).getValue(index);
    }


    public void setMutable(String name, boolean isMutable){
        table.get(name).setMutable(isMutable);
    }

    public boolean isMutable(String name){
        return table.get(name).isMutable();
    }

    public void update(String name, String val){
         table.get(name).setValue(val);
    }

    public void addfunction(String name, ArrayList<Token> param, ArrayList<Token> context){
        tablefunction.put(name,new FunctionInfo(param, context));
    }


    public String evalFunction(String name, ArrayList<Token> param) {
        return tablefunction.get(name).eval(param, this);
    }

    public boolean containFunction(String name) {
        return tablefunction.containsKey(name);
    }
}