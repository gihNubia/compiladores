package lexic;

public class StringException extends InvalidTokenException {
    public StringException(String message, int line, String token_content) {
        super(message + " na linha " + line + ": " + token_content);
    }
}
