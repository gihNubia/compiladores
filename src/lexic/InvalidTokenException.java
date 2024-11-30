package lexic;

public class InvalidTokenException extends RuntimeException {
    Token token;
    public InvalidTokenException(String type, int line, String context) {
        super(type + " na linha " + line + ": " + context);
    }
}

