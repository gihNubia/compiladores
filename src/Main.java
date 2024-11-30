import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.stream.Stream;

import lexic.Token;
import lexic.Lexer;
import lexic.StringException;
import lexic.EndOfFileException;

public class Main {
    public static void main(String[] args) {
        printLexic(args);
    }

    public static void printLexic(final String[] args) {
        Optional.of(args)
                .map(Main::getFileName)
                .map(Main::getLexer)
                .map(Main::getTokens)
                .ifPresent(tokens -> {
                    tokens.map(Token::toString)
                            .forEach(System.out::println);
                });
    }

    private static String getFileName(final String[] args) {
        if (args.length != 1) {
            // O comando seria esse mesmo? javac?
            System.out.println("javac Main.java <nome do arquivo>.txt");
            return null;
        }

        return args[0];
    }

    private static Lexer getLexer(final String fileName) {
        return Optional.ofNullable(fileName)
                .<FileReader>map(fn -> {
                    try {
                        return new FileReader(fn);
                    } catch (FileNotFoundException e) {
                        System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
                        return null;
                    }
                })
                .<BufferedReader>map(BufferedReader::new)
                .<Lexer>map(Lexer::new)
                .orElse(null);
    }

    private static Stream<Token> getTokens(final Lexer lexer) {

        return Stream.<Token>generate(() -> {
            while(true) {
                try {
                    return lexer.scan();
                } catch (EndOfFileException eof) {
                    System.out.println(eof.getMessage());
                } catch (StringException se) {
                    System.out.println(se.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).takeWhile(tok -> tok != null);
    }
}