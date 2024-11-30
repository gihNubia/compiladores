import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import lexic.Token;
import lexic.Lexer;
import lexic.EndOfFileException;


public class Main {
    public static void main(String[] args) {

        ArrayList<Token> tabela_de_tokens = new ArrayList<Token>();
            if (args.length == 0 || args.length > 1) {
                System.out.println("javac Main.java <nome do arquivo>.txt");
            }
            else{
                try{
                    BufferedReader br = new BufferedReader(new FileReader(args[0]));
                    Lexer lexer = new Lexer(br);
                    try {
                        while (true) {
                            Token aux = lexer.scan();
                            if (aux !=null){
                                tabela_de_tokens.addLast(aux);
                            }
                        }
                    }
                    catch (EndOfFileException eof){

                    }
                    catch (Exception e){
                        System.out.printf(e.getMessage());
                    }
                    finally {
                        for (Token tabelaDeToken : tabela_de_tokens) {
                            System.out.println(tabelaDeToken.toString());
                        }
                    }

                }
                catch (Exception e) {
                    System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
                }



            }

    }
}