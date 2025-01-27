package lexical;

public class TokenWithLine {
    private final Token token;
    private final int line;

    public TokenWithLine(Token token, int line) {
        this.token = token;
        this.line = line;
    }

    public Token getToken() {
        return token;
    }

    public int getLine() {
        return line;
    }

}
