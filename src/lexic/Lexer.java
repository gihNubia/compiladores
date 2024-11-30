package lexic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

public class Lexer {
    int line;
    char ch;
    BufferedReader file;
    Hashtable<String, Word> words = new Hashtable<String, Word>();
    boolean eof = false;

    public Lexer(BufferedReader file) {
        line = 1;
        this.file = file;
        try {
            readch();
        } catch (RuntimeException e) {
        }

        reserve(new Word("start", Tag.START));
        reserve(new Word("exit", Tag.EXIT));
        reserve(new Word("int", Tag.INT));
        reserve(new Word("float", Tag.FLOAT));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("then", Tag.THEN));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("end", Tag.END));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("scan", Tag.SCAN));
        reserve(new Word("print", Tag.PRINT));

    }

    /**
     * Caso esteja no fim do arquivo, nao faz nada.
     * Le proximo caractere do arquivo e preenche variavel ch. Caso alcance fim do
     * arquivo, eof se torna true
     * 
     * @throws RuntimeException se houve erro de IO
     */
    private void readch() {
        if (eof)
            return;

        try {
            int aux = file.read();
            if (aux == -1)
                eof = true;
            ch = (char) aux;
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    /**
     * Le proximo caracter e preenche ch. Se for o esperado, limpa ch. Caso
     * contrario mantem ch com o caractere lido.
     * 
     * @param c proximo caractere esperado
     * @return true caso proximo caractere seja o esperado, falso caso contrario
     * @throws RuntimeException se houve erro de IO
     */
    private boolean readncomparech(char c) {
        readch();

        if (ch != c) {
            return false;
        }
        ch = ' ';
        return true;
    }

    private void reserve(Word w) {
        words.put(w.getLexeme(), w);
    }

    private boolean isDivide() {
        readch();

        return ch != '/' && ch != '*';
    }

    private void skipOneLineComment() {
        while (ch != '\n' && !eof)
            readch();
    }

    private void skipMultipleLinesCommentLastStar() {
        readch();
        if (ch == '\n') {
            line++;
        }
        if (ch != '/' && ch != '*' && !eof) {
            skipMultipleLinesComment();
            return;
        }
        if (eof) {
            throw new EndOfFileException(line, "Comentario de multiplas linhas nao encerrado");
        }
        if (ch == '*') {
            skipMultipleLinesCommentLastStar();
            return;
        }
        if (ch == '/') {
            readch();
            return;
        }

        throw new RuntimeException("Estado inalcancavel");
    }

    private void skipMultipleLinesComment() {
        readch();
        if (ch == '\n') {
            line++;
        }
        if (ch != '*' && !eof) {
            skipMultipleLinesComment();
            return;
        }
        if (eof) {
            throw new EndOfFileException(line, "Comentario de multiplas linhas nao encerrado");
        }
        if (ch == '*') {
            skipMultipleLinesCommentLastStar();
            return;
        }

        throw new RuntimeException("Estado inalcancavel");
    }

    private void skipComment() {
        if (ch == '/') {
            skipOneLineComment();
            return;
        }
        if (ch == '*') {
            skipMultipleLinesComment();
            return;
        }

        throw new RuntimeException("Estado inalcancavel");
    }

    private Optional<Token> matchSimpleToken() {
        switch (ch) {
            case '=':
                if (readncomparech('='))
                    return Optional.of(Word.equal);
                return Optional.of(Word.at);
            case '>':
                if (readncomparech('='))
                    return Optional.of(Word.ge);
                return Optional.of(Word.gt);
            case '<':
                if (readncomparech('='))
                    return Optional.of(Word.le);
                return Optional.of(Word.lt);
            case '!':
                if (readncomparech('='))
                    return Optional.of(Word.ne);
                return Optional.of(Word.nt);
            case '+':
                ch = ' ';
                return Optional.of(Word.plus);
            case '-':
                ch = ' ';
                return Optional.of(Word.minus);
            case '|':
                if (readncomparech('|'))
                    return Optional.of(Word.or);
                return Optional.of(new InvalidToken("|"));
            case '*':
                ch = ' ';
                return Optional.of(Word.multiply);
            case '%':
                ch = ' ';
                return Optional.of(Word.modulus);
            case '&':
                if (readncomparech('&'))
                    return Optional.of(Word.and);
                return Optional.of(new InvalidToken("&"));
            case ',':
                ch = ' ';
                return Optional.of(Word.cl);
            case ';':
                ch = ' ';
                return Optional.of(Word.sc);
            case '(':
                ch = ' ';
                return Optional.of(Word.op);
            case ')':
                ch = ' ';
                return Optional.of(Word.cp);
            default:
                return Optional.empty();
        }
    }

    private Optional<Token> matchNumber() {
        if (!Character.isDigit(ch)) {
            return Optional.empty();
        }

        int value = 0;

        do {
            value = 10 * value + Character.digit(ch, 10);
            readch();
        } while (Character.isDigit(ch));

        if (ch != '.') {
            return Optional.of(new Int(value));
        }

        readch();
        if (!Character.isDigit(ch)) {
            throw new InvalidNumberFormatException(line, "Deve haver pelo menos um digito apos um ponto");
        }

        double valuef = (double) value;
        double fraction = 0.1;
        do {
            valuef = valuef + fraction * Character.digit(ch, 10);
            fraction = fraction / 10;
            readch();
        } while (Character.isDigit(ch));

        return Optional.of(new Float_c(valuef));
    }

    private Optional<Token> matchIdentifier() {
        if (!Character.isLetter(ch) && ch != '_') {
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            readch();
        } while (Character.isLetterOrDigit(ch));

        String s = sb.toString();

        return Optional.of(s)
                .<Token>map(words::get)
                .or(() -> {
                    final Word w = new Word(s, Tag.ID);
                    words.put(s, w);
                    return Optional.of(w);
                });
    }

    private Optional<Token> matchLiteral() {
        if (ch != '{') {
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            readch();
        } while (ch != '}' && !eof && ch != '\n');

        if (ch == '}') {
            sb.append(ch);
            ch = ' ';
            return Optional.of(new Literal(sb.toString()));
        }

        if (eof) {
            throw new EndOfFileException(line, "String nao encerrada");
        }

        if (ch == '\n') {
            throw new StringException(line, "String nao encerrada");
        }

        throw new RuntimeException("Estado inalcancavel");
    }

    private Token matchInvalidToken() {
        Token t = new InvalidToken(String.valueOf(ch));
        ch = ' ';
        return t;
    }

    /**
     * @return Proximo token. Caso nao exista mais tokens, retorna null.
     * @throws IOException
     * @throws EndOfFileException se arquivo terminou de forma inesperada
     * @throws StringException se uma string foi mal formada
     */
    public Token scan() {
        while (true) {
            if (eof) {
                return null;
            }
            if (ch == '\n') {
                line++;
            }
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b' || ch == '\n') {
                readch();
                continue;
            }
            if (ch == '/') {
                if (isDivide())
                    return Word.divide;

                skipComment();
                continue;
            }

            break;
        }

        return matchSimpleToken()
                .or(this::matchNumber)
                .or(this::matchIdentifier)
                .or(this::matchLiteral)
                .orElseGet(this::matchInvalidToken);
    }

}
