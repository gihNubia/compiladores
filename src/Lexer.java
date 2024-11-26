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
