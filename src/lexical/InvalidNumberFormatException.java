package lexical;

public class InvalidNumberFormatException extends InvalidTokenException {
    public InvalidNumberFormatException(int line, String context) {

        super("Formato incorreto de numero", line, context);
    }
}
