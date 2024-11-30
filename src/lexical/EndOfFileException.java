package lexical;

public class EndOfFileException extends InvalidTokenException {
    public EndOfFileException(int line, String context) {

        super("Fim de arquivo inesperado", line, context);
    }
}
