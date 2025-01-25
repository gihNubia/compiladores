import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lexical.EndOfFileException;
import lexical.Lexer;
import lexical.StringException;
import lexical.Token;
import sintatic.Asd;

public class Main {
    public static void main(String[] args) {
        printLexic(args);
    }

    public static void printLexic(final String[] args) {
        Optional.of(args)
                .map(Main::getFileName)
                .map(Main::getLexer)
                .ifPresent(lexer -> {
                    System.out.println("Iniciar analise");
                    List<Token> tokens = Main.getTokens(lexer).toList();
                    System.out.println("==========================");
                    System.out.println("Tokens: ");
                    tokens.stream().map(Token::toString).forEach(System.out::println);
                    System.out.println("==========================");
                    System.out.println("Simbolos: ");
                    lexer.words.keySet().forEach(System.out::println);
                    //Main.runSintaticAnalysis(tokens);
                });

                // .map(Main::getTokens)
                // .ifPresent(tokens -> {
                //     tokens.map(Token::toString)
                //             .forEach(System.out::println);
                // });
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

    private static void runSintaticAnalysis(List<Token> tokens) {
        try {
            Asd asd = new Asd(tokens); // Presume-se que o Asd tenha um construtor que aceita uma lista de tokens
            //asd.analyze(); // Método para iniciar a análise sintática
            System.out.println("Análise sintática concluída com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro durante a análise sintática: " + e.getMessage());
        }
    }
}