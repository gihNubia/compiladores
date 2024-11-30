import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {
    int line;
    char ch;
    BufferedReader file;
    Hashtable<String, Word> words = new Hashtable<String, Word>();
    boolean eof = false;
    public Lexer(BufferedReader file){
        line = 1;
        this.file = file;
        try{
            readch();
        }
        catch (IOException exception){
            System.out.print("Arquivo Vazio");
        }

        reserve(new Word ("start", Tag.START));
        reserve(new Word ("exit", Tag.EXIT));
        reserve(new Word ("int", Tag.INT));
        reserve(new Word ("float", Tag.FLOAT));
        reserve(new Word ("string", Tag.STRING));
        reserve(new Word ("if", Tag.IF));
        reserve(new Word ("then", Tag.THEN));
        reserve(new Word ("else", Tag.ELSE));
        reserve(new Word ("end", Tag.END));
        reserve(new Word ("do", Tag.DO));
        reserve(new Word ("while", Tag.WHILE));
        reserve(new Word ("scan", Tag.SCAN));
        reserve(new Word ("print", Tag.PRINT));

    }
    private void readch() throws IOException {
        int aux = file.read();
        if (aux == -1)
            eof = true;
        ch = (char) aux;
    }

    private boolean readncomparech(char c) throws IOException{
        readch();

        if (ch != c){
            return false;
        }
        ch = ' ';
        return true;
    }

    private void reserve(Word w){
        words.put(w.getLexeme(), w);
    }

    public Token scan() throws IOException {

            while(true){
                  if(eof){
                    throw new EndOfFileException("Fim do Arquivo");
                }
                if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b'){
                    readch();
                }
                else if(ch == '\n'){
                    readch();
                    line++;
                }
                else break;

            }
            //token simples

            switch (ch){
                case '=':
                    if (readncomparech('=')) return Word.equal;
                    return Word.at;
                case '>':
                    if (readncomparech('=')) return Word.ge;
                    return Word.gt;
                case '<':
                    if (readncomparech('=')) return Word.le;
                    return Word.lt;
                case '!':
                    if (readncomparech('=')) return Word.ne;
                    return Word.nt;
                case '+':
                    ch= ' ';
                    return Word.plus;
                case '-':
                    ch= ' ';
                    return Word.minus;
                case '|':
                    if (readncomparech('|')) return Word.or;
                    return new InvalidToken("|");
                case '*':
                    ch= ' ';
                    return Word.multiply;
                case '/':
                    readch();
                    //comentario de uma linha
                    if (ch == '/'){
                        do {
                            readch();
                            if (eof){
                                return null;
                            }
                        }while (ch != '\n');
                        readch();
                        return null;
                    }
                    else if (ch == '*'){
                        readch();
                        char before;
                        while (true) {
                            before = ch;
                            readch();
                            if (before == '*' && ch =='/'){
                                readch();
                                return null;
                            }
                        }
                    }
                    else{
                        return Word.divide;
                    }

                case '%':
                    ch= ' ';
                    return Word.modulus;
                case '&':
                    if (readncomparech('&')) return Word.and;
                    return new InvalidToken("&");
                case ',':
                    ch= ' ';
                    return Word.cl;
                case ';':
                    ch= ' ';
                    return Word.sc;
                case '(':
                    ch = ' ';
                    return Word.op;
                case ')':
                    ch = ' ';
                    return Word.cp;
            }
            //numeros
            if (Character.isDigit(ch)){
                int value=0;

                do{
                    value = 10*value + Character.digit(ch,10);
                    readch();
                }while(Character.isDigit(ch));

                if(ch == '.'){
                    readch();
                    double valuef = (double) value;
                    double fraction =  0.1;
                    while(Character.isDigit(ch)){
                        valuef = valuef + fraction * Character.digit(ch,10);
                        fraction = fraction/10;
                        readch();
                    }
                    return new Float_c(valuef);
                }
                else{
                    return new Int(value);
                }
            }

            //identificador
            if (Character.isLetter(ch) || ch == '_'){
                StringBuilder sb = new StringBuilder();
                do{
                    sb.append(ch);
                    readch();
                }while(Character.isLetterOrDigit(ch) || ch == '_');

                String s = sb.toString();
                Word w = (Word)words.get(s);
                if (w != null) return w; //palavra já existe
                w = new Word (s, Tag.ID);
                words.put(s, w);
                return w;
            }

            //literal
            if(ch == '{'){
                StringBuilder sb = new StringBuilder();
                do{
                    sb.append(ch);
                    readch();
                }while(ch != '}' && !eof && ch != '\n');

                if(ch =='}'){
                    sb.append(ch);
                    ch = ' ';
                    return  new Literal(sb.toString());
                }
                else{
                    throw new StringException("String mal formada", line, sb.toString());
                }

            }
        if (ch == ' ' || ch == '\r') {
            return null;
        }

        Token t = new InvalidToken(String.valueOf(ch));
        ch = ' ';
        return t;

    }

}
