import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {
    int line;
    char ch;
    BufferedReader file;
    Hashtable words;

    public Lexer(BufferedReader file){
        line = 1;
        this.file = file;
        this.words = new Hashtable<>();
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
        ch = (char) file.read();
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
        return new Token(0);
    }

}
