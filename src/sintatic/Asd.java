package sintatic;

import lexical.Token;

import java.util.List;

public class Asd {
    private final List<Token> tokens;
    private int currentIndex;
    public Asd(List<Token> tokens) throws Exception {
        this.tokens = tokens;
        this.currentIndex = 0;
        program();
    }
    Token tok = getToken();

    private Token getToken(){
        if (currentIndex <= tokens.size()){
            return tokens.get(currentIndex);
        }
        return null;
    }
    private void advance() {
        tok = getToken();
    }

    private void eat(String t) throws Exception {

        if (tok.getTagString().equals(t)) advance();
        else {
            throw new Exception("Erro de sintaxe");
        }
    }

    private void program() throws Exception {
        // program ::= start   [decl-list]   stmt-list  exit
        eat("START");
        //opcional
        decl_list();
        stmt_list();
        eat("EXIT");
    }
    private void decl_list() throws Exception {
        //opcional
        //decl-list ::= decl {decl}
        switch (tok.getTagString()){
            case "INT": decl();
                    break;
            case "FLOAT": decl();
                    break;
            case "STRING": decl();
                    break;
            default: break; //opcional
        }
        //fazer o while
        while (tok.getTagString().equals("INT") || tok.getTagString().equals("FLOAT")
                || tok.getTagString().equals("STRING")){
            switch (tok.getTagString()){
                case "INT": decl();
                    break;
                case "FLOAT": decl();
                    break;
                case "STRING": decl();
                    break;
                default: break;
            }
        }

    }
    private void stmt_list() throws Exception {
        // TO-DO
        // stmt-list  ::= stmt {stmt}
        switch (tok.getTagString()){
            case "ID": stmt();
                break;
            case "IF": stmt();
                break;
            case "WHILE": stmt();
                break;
            case "SCAN": stmt();
                break;
            case "PRINT": stmt();
                break;
            default: throw new Exception("stmt error: Expect ID, IF, WHILE, SCAN or PRINT");
        }
        //fazer o while
        while (tok.getTagString().equals("ID") || tok.getTagString().equals("IF")
                || tok.getTagString().equals("WHILE")|| tok.getTagString().equals("SCAN")
                || tok.getTagString().equals("PRINT")){
            switch (tok.getTagString()){
                case "ID": stmt();
                    break;
                case "IF": stmt();
                    break;
                case "WHILE": stmt();
                    break;
                case "SCAN": stmt();
                    break;
                case "PRINT": stmt();
                    break;
                default: break;
            }
        }
    }

    private void stmt() throws Exception {
        //stmt ::= assign-stmt ";"   |   if-stmt   |  while-stmt | read-stmt ";"   |  write-stmt ";"
        switch (tok.getTagString()){
            case "ID":  assign_stmt();
                        eat("SC"); // ADC SC EXCEPTION
                        break;
            case "IF":  if_stmt();
                        break;
            case "WHILE": while_stmt();
                        break;
            case "SCAN": read_stmt();
                        eat("SC"); // ADC SC EXCEPTION
                        break;
            case "PRINT":  write_stmt();
                        eat("SC"); // ADC SC EXCEPTION
                        break;
            default: throw new Exception("stmt error: Expect ID, IF, WHILE, SCAN or PRINT");
        }
    }

    private void decl() throws Exception {
        //decl ::= type ident-list ";"
        type();
        ident_list();
        eat("SC"); //adc erro
    }

    private void type() throws Exception {
        // type ::= int | float  | string
        switch (tok.getTagString()){
            case "INT": eat("INT");
                break;
            case "FLOAT": eat("FLOAT");
                break;
            case "STRING": eat("STRING");
            default: throw new Exception("Tipo inválido"); //tipo invalido
        }
    }

    private void ident_list() throws Exception {
        //ident-list ::= identifier {"," identifier}
        eat("ID"); //adc exception
        //parte opicional
        while (tok.getTagString().equals("CL")){
            eat("CL"); //adc exception
            eat("ID"); //adc exception
        }

    }

    private void assign_stmt() throws Exception {
        //assign-stmt ::= identifier "=" simple_expr
        eat("ID"); //ADC ID EXCEPRION
        eat("AT"); //ADC AT EXCEPRION
        simple_expr();
    }
    private void if_stmt(){
        //if-stmt ::= if condition then stmt-list end
        // TO-DO
    }
    private void while_stmt(){
        //while-stmt ::= do stmt-list stmt-sufix
        // TO-DO
    }

    private void read_stmt(){
        //read-stmt ::= scan "(" identifier ")"
        //TO-DO
    }

    private void write_stmt(){
        //write-stmt ::= print "(" writable ")"
        //TO-DO
    }

    private void simple_expr(){
        //simple-expr ::= term | simple-expr addop term
        //TO-DO
    }

}
