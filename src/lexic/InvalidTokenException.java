package lexic;

public class InvalidTokenException extends RuntimeException {
    Token token;
    public InvalidTokenException(String message, int line, Token token) {
        super(message + " na linha " + line + ": " + token.toString());
        this.token = token;
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public String tokenValue(){
        return  token.toString();
    }

    public Token getToken(){
        return token;
    }
}

