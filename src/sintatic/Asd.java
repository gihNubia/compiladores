package sintatic;

import lexical.Token;
import lexical.TokenWithLine;

import java.util.List;

public class Asd {
    private final List<Token> tokens;

    private final List<Integer> lines;
    private int currentIndex;
    Token tok;
    public Asd(List<TokenWithLine> tokensWithLine) throws Exception {
        this.tokens = tokensWithLine.stream().map(TokenWithLine::getToken).toList();

        this.lines = tokensWithLine.stream().map(TokenWithLine::getLine).toList();

        this.currentIndex = 0;
        tok = getToken();
        program();
    }
    private Token getToken(){
        if (currentIndex <= tokens.size()){
            currentIndex++;
            return tokens.get(currentIndex-1);
        }
        return null;
    }
    private void advance() {
        tok = getToken();
    }

    private void eat(String t) throws Exception {

        if (tok.getTagString().equals(t)) advance();
        else {
            throw new Exception("Erro de sintaxe no token " + tok.getTagString() + " " + tok.getValueString()
            + " na linha " + lines.get(currentIndex));
        }
    }

    private void program() throws Exception {
        // program ::= start   [decl-list]   stmt-list  exit
        eat("START");
        //opcional
        decl_list();
        stmt_list();
        try{
            eat("EXIT");
        }
        catch (IndexOutOfBoundsException e){

        }

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
        // stmt-list  ::= stmt {stmt}
        switch (tok.getTagString()){
            case "ID": stmt();
                break;
            case "IF": stmt();
                break;
            case "DO": stmt();
                break;
            case "SCAN": stmt();
                break;
            case "PRINT": stmt();
                break;
            default: throw new Exception("stmt error: Expect ID, IF, WHILE, SCAN or PRINT" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
            + " na linha " + lines.get(currentIndex));
        }
        //fazer o while
        while (tok.getTagString().equals("ID") || tok.getTagString().equals("IF")
                || tok.getTagString().equals("DO")|| tok.getTagString().equals("SCAN")
                || tok.getTagString().equals("PRINT")){
            switch (tok.getTagString()){
                case "ID":
                case "IF":
                case "DO":
                case "SCAN":
                case "PRINT":
                    stmt();
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
            case "DO": while_stmt();
                        break;
            case "SCAN": read_stmt();
                        eat("SC"); // ADC SC EXCEPTION
                        break;
            case "PRINT":  write_stmt();
                        eat("SC"); // ADC SC EXCEPTION
                        break;
            default: throw new Exception("stmt error: Expect ID, IF, WHILE, SCAN or PRINT" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex));
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
                break;
            default: throw new Exception("Tipo inválido, expect INT, FLOAT or STRING" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //tipo invalido
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
    private void if_stmt() throws Exception {
        //if-stmt ::= if condition then stmt-list end
        eat("IF"); //adc ERRO
        condition();
        eat("THEN");
        stmt_list();
        if(tok.getTagString().equals("END")){
            eat("END"); //adc erro
        }
        else{
            eat("ELSE"); //adc erro
            stmt_list();
            eat("END"); //adc erro
        }
    }
    private void while_stmt() throws Exception {
        //while-stmt ::= do stmt-list stmt-sufix
        eat("DO"); //adc erro
        stmt_list();
        stmt_sufix();
    }
    private void read_stmt() throws Exception {
        //read-stmt ::= scan "(" identifier ")"
        eat("SCAN"); //adc erro
        eat("OP"); //adc erro
        eat("ID"); //adc erro
        eat("CP"); //adc erro
    }

    private void write_stmt() throws Exception {
        //write-stmt ::= print "(" writable ")"
        eat("PRINT"); //adc erro
        eat("OP"); //adc erro
        writable(); //adc erro
        eat("CP"); //adc erro
    }

    private void simple_expr() throws Exception {
        //simple-expr ::= term | simple-expr addop term
        //simple-expr ::= term simple-expr-tail
        switch (tok.getTagString()){
            case "ID":
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
            case "NT":
            case "MINUS":
            case "OP":
                term();
                simple_expr_tail();
                break;
            default:
                throw new Exception("invalid operation - " + tok.getValueString()
                        + " na linha " + lines.get(currentIndex)); //adc erro


        }
    }

    private void simple_expr_tail() throws Exception {
        //simple-expr-tail ::= addop term simple-expr-tail | lambda
        switch (tok.getTagString()){
            case "PLUS":
            case "MINUS":
            case "OR":
            case "MULTIPLY":
            case "DIVIDE":
            case "MODULUS":
            case "AND":
            case "NOT":
            case "ID":
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
            case "OP":
                addop();
                term();
                if(tok.getTagString().equals("PLUS")||tok.getTagString().equals("MINUS")
                ||tok.getTagString().equals("OR")){
                    simple_expr_tail();
                }
                break;
            default:;
        }
    }

    private void condition() throws Exception {
        //condition ::= expression
        expression();
    }

    private void stmt_sufix() throws Exception {
        //stmt-sufix ::= while condition end
        eat("WHILE"); //adc erro
        condition();
        eat("END"); //adc erro
    }
    private void writable() throws Exception {
        //writable ::= simple-expr | literal
        if(tok.getTagString().equals("LITERAL")){
            eat("LITERAL"); //adc erro
        }
        else{
            simple_expr(); //adc erro
        }
    }
    public void term() throws Exception {
       //term ::= factor-a | term mulop factor-a
       //term ::= factor-a term-tail
        // term-tail ::= mulop factor-a term-tail | lambda
        switch (tok.getTagString()){
            case "ID":
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
            case "NT":
            case "MINUS":
            case "OP":
                factor_a();
                term_tail();
                break;
            default:    throw new Exception("invalid operation, expect PLUS, MINUS or OR" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }

    private void term_tail()  throws Exception{
        // term-tail ::= mulop factor-a term-tail | lambda
        switch (tok.getTagString()){
            case "ID":
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
            case "NT":
            case "MINUS":
            case "OP":
            case "MULTIPLY":
            case "DIVIDE":
            case "MODULUS":
            case "AND":
                mulop();
                //add sub or semu
                if(tok.getTagString().equals("NT")||tok.getTagString().equals("MINUS")
                || tok.getTagString().equals("ID") || tok.getTagString().equals("FLOAT_C")
                || tok.getTagString().equals("INT") || tok.getTagString().equals("LITERAL")
                || tok.getTagString().equals("OP")){
                    factor_a();
                }
                term_tail();
                break;
            default: ;
        }
    }
    private void addop() throws Exception {
        //addop ::= "+" | "-" | "||"
        switch (tok.getTagString()){
            case "PLUS":
                eat("PLUS"); //adc erro
                break;
            case "MINUS":
                eat("MINUS"); //adc erro
                break;
            case "OR":
                eat("OR"); //adc erro
                break;
            default: ;
        }
    }
    public void mulop() throws Exception {
        //mulop ::=  "*" | "/" | “%” | "&&"
        switch (tok.getTagString()){
            case "MULTIPLY":
                eat("MULTIPLY"); //adc erro
                break;
            case "DIVIDE":
                eat("DIVIDE"); //adc erro
                break;
            case "MODULUS":
                eat("MODULUS"); //adc erro
                break;
            case "AND":
                eat("AND"); //adc erro
                break;
            default: throw new Exception("invalid operation, expect MULTIPLY, DIVIDE, MODULUS or AND" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }
    public void factor_a() throws Exception {
        //fator-a ::= factor | "!" factor |  "-" factor
        switch (tok.getTagString()){
            case "ID":
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
            case "OP":
                factor();
                break;
            case "NT":
                eat("NT");
                factor();
                break;
            case "MINUS":
                eat("MINUS");
                break;
            default: throw new Exception("Tipo inválido, expect ID, FLOAT_C, INT, LITERAL, OP, NT or MINUS" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }

    public void expression() throws Exception {
        //expression ::= simple-expr | simple-expr relop simple-expr
        simple_expr();

        //opcional
        switch (tok.getTagString()){
            case "EQ":
            case "GT":
            case "GE":
            case "LT":
            case "LE":
            case "NE":
                relop();
                simple_expr();
                break;
            default: ;
        }
    }
    public void factor() throws Exception {
        //factor ::= identifier | constant | "(" expression ")"
        switch (tok.getTagString()){
            case "ID":
                eat("ID"); //adc erro
                break;
            case "FLOAT_C":
            case "INT":
            case "LITERAL":
                constant();
                break;
            case "OP":
                eat("OP");
                expression();
                eat("CP");
                break;
            default: throw new Exception("Tipo inválido, expect ID, FLOAT_C, INT, LITERAL or OP" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }
    private void constant() throws Exception {
        //constant ::= integer_const | float_const | literal
        switch (tok.getTagString()){
            case "FLOAT_C":
                eat("FLOAT_C");
                break;
            case "INT":
                eat("INT");
                break;
            case "LITERAL":
                eat("LITERAL");
                break;
            default: throw new Exception("Tipo inválido, expect FLOAT_C, INT or LITERAL" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }
    private void relop() throws Exception {
        //relop ::= "==" | ">" | ">=" | "<" | "<=" | "!="
        switch (tok.getTagString()){
            case "EQ":
                eat("EQ");
                break;
            case "GT":
                eat("GT");
                break;
            case "GE":
                eat("GE");
                break;
            case "LT":
                eat("LT");
                break;
            case "LE":
                eat("LE");
                break;
            case "NE":
                eat("NE");
                break;
            default: throw new Exception("Tipo inválido, expect EQ, GT, GE, LT, LE or NE" +
                    "\nErro de sintaxe no token" + tok.getTagString() + " " + tok.getValueString()
                    + " na linha " + lines.get(currentIndex)); //adc erro
        }
    }

}
