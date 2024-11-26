import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

            if (args.length == 0 || args.length > 1) {
                System.out.println("javac Main.java <nome do arquivo>.txt");
            }
            else{
                try{
                    BufferedReader br = new BufferedReader(new FileReader(args[0]));
                }
                catch (Exception e) {
                    System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
                }



            }

    }
}