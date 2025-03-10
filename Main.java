import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorNomes gNomes;
        gNomes = new GerenciadorNomesMem();
        Ihm ihm = new Ihm(gNomes);
        ihm.dialogar();
    }
}