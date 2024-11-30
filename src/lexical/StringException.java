package lexical;

public class StringException extends InvalidTokenException {
    public StringException(int line, String context) {
        super("Erro de String", line, context);
    }
}
