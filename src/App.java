import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande = { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio = { 12_500, 25_000, 50_000, 100_000, 200_000 };
    static final int[] tamanhosTestePequeno = { 3, 6, 12, 24, 48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0 / 1_000_000;
    static Scanner teclado;

    static void pausa() {
        System.out.println("Digite enter para continuar...");
        if (teclado.hasNextLine()) {
            teclado.nextLine();
        }
    }

    static void cabecalho() {
        System.out.println("AEDII ORDENAÇÃO DE VETOREZINHOS");
        System.out.println("===========================");
    }

    static int menu() {
        cabecalho();
        System.out.println("1 - BubbleSort");
        System.out.println("2 - InsertionSort");
        System.out.println("3 - SelectionSort");
        System.out.println("4 - MergeSort");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido.
     * 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2),
     *         desordenado.
     */
    static int[] gerarVetor(int tamanho) {
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho / 2);
        }
        return vetor;
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho
     * pré-definido.
     * 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e
     *         (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    static void geradorBolha() {
        int i = exibirTamVetores();
        Integer[] vetor = gerarVetorObjetos(i);
        BubbleSort<Integer> bolha = new BubbleSort<>();

        Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);

        System.out.println("\nVetor ordenado método Bolha:");
        System.out.println("Comparações: " + bolha.getComparacoes());
        System.out.println("Movimentações: " + bolha.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());
        System.out.printf("\n\n");
    }

    static void geradorInsertion() {
        int i = exibirTamVetores();
        Integer[] vetor = gerarVetorObjetos(i);
        InsertionSort<Integer> insertion = new InsertionSort<>();

        Integer[] vetorOrdenadoInsertion = insertion.ordenar(vetor);

        System.out.println("\nVetor ordenado método Insertion:");
        System.out.println("Comparações: " + insertion.getComparacoes());
        System.out.println("Movimentações: " + insertion.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + insertion.getTempoOrdenacao());
        System.out.printf("\n\n");
    }

    static void geradorSelection() {
        int i = exibirTamVetores();
        Integer[] vetor = gerarVetorObjetos(i);
        SelectionSort<Integer> Selection = new SelectionSort<>();

        Integer[] vetorOrdenadoSelection = Selection.ordenar(vetor);

        System.out.println("\nVetor ordenado método SelectionSort:");
        System.out.println("Comparações: " + Selection.getComparacoes());
        System.out.println("Movimentações: " + Selection.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + Selection.getTempoOrdenacao());
        System.out.printf("\n\n");
    }

    static void geradorMerge() {
        int i = exibirTamVetores();
        Integer[] vetor = gerarVetorObjetos(i);
        MergeSort<Integer> Merge = new MergeSort<>();

        Integer[] vetorOrdenadoMerge = Merge.ordenar(vetor);

        System.out.println("\nVetor ordenado método MergeSort:");
        System.out.println("Comparações: " + Merge.getComparacoes());
        System.out.println("Movimentações: " + Merge.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + Merge.getTempoOrdenacao());
        System.out.printf("\n\n");
    }

    static int exibirTamVetores() {
        int escolhaTeclado;
        int param = 0;
        System.out.printf("\n\n");
        System.out.println("Qual dos vetores você quer exibir?");
        System.out.println("1- Vetor de tamanhos grandes");
        System.out.println("2- Vetor de tamanhos médios");
        System.out.println("3- Vetor de tamanhos pequenos");

        try {
            escolhaTeclado = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Escolha inválida");
            return exibirTamVetores();
        }

        if (escolhaTeclado < 1 || escolhaTeclado > 3) {
            System.out.println("Escolha inválida");
            return exibirTamVetores();
        }
        System.out.printf("\n\n");
        switch (escolhaTeclado) {
            case 1:
                for (int i = 0; i < tamanhosTesteGrande.length; i++) {
                    System.out.println((i + 1) + "- " + tamanhosTesteGrande[i]);
                }
                try {
                    param = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Escolha inválida");
                    return exibirTamVetores();
                }
                param--;
                param = tamanhosTesteGrande[param];
                break;
            case 2:
                for (int i = 0; i < tamanhosTesteMedio.length; i++) {
                    System.out.println((i + 1) + "- " + tamanhosTesteMedio[i]);
                }
                try {
                    param = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Escolha inválida");
                    return exibirTamVetores();
                }
                param--;
                param = tamanhosTesteMedio[param];
                break;
            case 3:
                for (int i = 0; i < tamanhosTestePequeno.length; i++) {
                    System.out.println((i + 1) + "- " + tamanhosTestePequeno[i]);
                }
                try {
                    param = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Escolha inválida");
                    return exibirTamVetores();
                }
                param--;
                param = tamanhosTestePequeno[param];
        }
        return param;
    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
        int opcao = -1;
        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> geradorBolha();
                case 2 -> geradorInsertion();
                case 3 -> geradorSelection();
                case 4 -> geradorMerge();
            }
        } while (opcao != 0);
        System.out.println("VLW, FLW T+");
        pausa();
        teclado.close();
    }
}
