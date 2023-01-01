package Lexer.Tree;

import Lexer.Token.Token;
import SymbolTable.SymbolTable;

import java.util.List;

public class Tree {

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isAlphabetic(String str) {
        for(int i = 0; i<str.length(); i++){
            if (!Character.isAlphabetic(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isIdentifier(String str){
        return (str.equals("true")||str.equals("false")||isNumeric(str)||isAlphabetic(str));
    }
        Node root;
        SymbolTable table;
        class Node{
            Node Left;
            Node right;
            String key;

            Node(String key){
                this.key = key;
            }
        }

        public Tree(List<Token> val, SymbolTable table){
            this.table = table;
            for(Token s : val) {

                if(isIdentifier(s.getVal())){
                    if (root == null) {
                        root = new Node(s.getVal());
                    }
                    else if (root.Left == null) {
                        root.Left = new Node(s.getVal());
                    }
                    else if (root.right == null) {
                        root.right = new Node(s.getVal());
                    }
                }else{
                    if (root == null) {
                        root = new Node(s.getVal());
                    } else {
                        Node n = new Node(s.getVal());
                        n.Left = root;
                        root = n;
                    }
                }
            }
        }

        public String evalTree(Node root){
            // Empty tree
            if (root == null)
                return "0";

            // Leaf node i.e, an integer
            if (root.Left == null && root.right == null)
                // return toInt(root.key);
                return root.key;

            // Evaluate left subtree
            String leftEval = evalTree(root.Left);
            if(!isNumeric(leftEval) && !root.key.equals("="))
            {
                if(table.contains(leftEval)){
                    leftEval = table.valueSymbol(leftEval);
                }else{
                    leftEval = root.Left.key;
                }
            }

            // Evaluate right subtree
            String rightEval = evalTree(root.right);
            if (!isNumeric(rightEval)&& !root.key.equals("="))
            {
                if(table.contains(rightEval)){
                    rightEval = table.valueSymbol(rightEval);
                }else{
                    rightEval = root.right.key;
                }
            }

            // Check which operator to apply
            if (root.key.equals("+"))
                return String.valueOf(Integer.parseInt(leftEval) + Integer.parseInt(rightEval));

            if (root.key.equals("+f"))
                return String.valueOf(Float.parseFloat(leftEval) + Float.parseFloat(rightEval));


            if (root.key.equals("-"))
                return String.valueOf(Integer.parseInt(leftEval) - Integer.parseInt(rightEval));

            if (root.key.equals("-f"))
                return String.valueOf(Float.parseFloat(leftEval) - Float.parseFloat(rightEval));


            if (root.key.equals("*"))
                return String.valueOf(Integer.parseInt(leftEval) * Integer.parseInt(rightEval));

            if (root.key.equals("*f"))
                return String.valueOf(Float.parseFloat(leftEval) * Float.parseFloat(rightEval));


            if (root.key.equals("/"))
                return String.valueOf(Integer.parseInt(leftEval) / Integer.parseInt(rightEval));

            if (root.key.equals("/f"))
                return String.valueOf(Float.parseFloat(leftEval) / Float.parseFloat(rightEval));


            if (root.key.equals("%"))
                return String.valueOf(Integer.parseInt(leftEval) % Integer.parseInt(rightEval));

            if (root.key.equals("<"))
                if (Float.parseFloat(leftEval) < Float.parseFloat(rightEval)) return "true"; else return "false";

            if (root.key.equals("<="))
                if (Float.parseFloat(leftEval) <= Float.parseFloat(rightEval)) return "true"; else return "false";

            if (root.key.equals(">"))
                if (Float.parseFloat(leftEval) > Float.parseFloat(rightEval)) return "true"; else return "false";

            if (root.key.equals(">="))
                if (Float.parseFloat(leftEval) >= Float.parseFloat(rightEval)) return "true"; else return "false";

            if (root.key.equals("=="))
                if (leftEval.equals(rightEval)) return "true";else return "false";

            if (root.key.equals("!="))
                if (!leftEval.equals(rightEval)) return "true";else return "false";

            if (root.key.equals("=")) {
                if(table.contains(leftEval)){
                    table.update(String.valueOf(leftEval), String.valueOf(rightEval));
                }else {
                    table.add(String.valueOf(leftEval), String.valueOf(rightEval));
                }
                return String.valueOf(leftEval);
            }

            // pas implementer
            return "pas implementer";
        }

        public String parse(){
            return evalTree(root);
        }

    }


