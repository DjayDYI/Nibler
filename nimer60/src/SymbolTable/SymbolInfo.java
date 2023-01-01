package SymbolTable;

import java.util.ArrayList;

public class SymbolInfo {

    private ArrayList<String> value = new ArrayList<>();
    private boolean isMutable;
    public SymbolInfo(String value){
        this.value.add(value);
        boolean isMutable = true;
    }

    public SymbolInfo(ArrayList<String> value){
        this.value = value;
        boolean isMutable = true;
    }

    public void setMutable(boolean isMutable){
        this.isMutable = isMutable;
    }

    public void setValue(String value) {
        this.value.remove(0);
        this.value.add(value);
    }

    public String getValue() {
        return value.get(0);
    }

    public String getValue(String index) {
        return value.get(Integer.parseInt(index));
    }

    public boolean isMutable() {
        return isMutable;
    }
}
