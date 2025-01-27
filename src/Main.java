import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lexical.*;
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
                    List<TokenWithLine> tokens = Main.getTokens(lexer).toList();
                    System.out.println("==========================");
                    System.out.println("Tokens: ");
                    tokens.stream().map(twl -> twl.getToken().toString() + ", linha: " + twl.getLine()).forEach(System.out::println);
                    System.out.println("==========================");
                    System.out.println("Simbolos: ");
                    lexer.words.keySet().forEach(System.out::println);
                    Main.runSintaticAnalysis(tokens);
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

    private static Stream<TokenWithLine> getTokens(final Lexer lexer) {

        return Stream.<TokenWithLine>generate(() -> {
            while(true) {
                try {
                    Token l =  lexer.scan();
                    if(l != null){
                        TokenWithLine t = new TokenWithLine(l, lexer.getLine());
                        return t;
                    }
                    else {
                        return null;
                    }
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

    private static void runSintaticAnalysis(List<TokenWithLine> tokens) {
        try {
            Asd asd = new Asd(tokens);
            System.out.println("Análise sintática concluída com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro durante a análise sintática: " + e.getMessage());
        }
    }
}