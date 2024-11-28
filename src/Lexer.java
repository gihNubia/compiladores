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
                return Word.plus;
            case '-':
                return Word.minus;
            case '|':
                if (readncomparech('|')) return Word.or;
                else return new Token('|');
            case '*':
                return Word.multiply;
            case '/':
                return Word.divide;
            case '%':
                return Word.modulus;
            case '&':
                if (readncomparech('&')) return Word.and;
                return new Token('&');
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
                }
                return  new Float_c(valuef);
            }
            else{
                return new Int(value);
            }
        }

        //identificador
        if (Character.isLetter(ch)){
            StringBuilder sb = new StringBuilder();
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch));

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

            }

        }

    }

}
