import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {
    private long comparacoes;
    private long movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0 / 1_000_000;

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public long getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar() {
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar() {
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    @Override
    public T[] ordenar(T[] dados) {
        mergesort(dados, 0, dados.length - 1);
        return dados;
    }

    /**
     * Algoritmo de ordenação Mergesort.
     * 
     * @param int esq: início do array a ser ordenado
     * @param int dir: fim do array a ser ordenado
     */
    // 1.a chamada do método mergesort: esq: 0; dir: array.length - 1
    private void mergesort(T[] array, int esq, int dir) {
        iniciar();
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
        terminar();
    }

    /**
     * Algoritmo que intercala os elementos localizados entre as posições esq e dir
     * 
     * @param int esq: início do array a ser ordenado
     * @param int meio: posição do meio do array a ser ordenado
     * @param int dir: fim do array a ser ordenado
     */
    private void intercalar(T[] array, int esq, int meio, int dir) {

        int n1, n2, i, j, k;

        // Definir tamanho dos dois subarrays
        n1 = meio - esq + 1;
        n2 = dir - meio;
        int[] arrayInt = Arrays.stream(array)
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] a1 = new int[n1];
        int[] a2 = new int[n2];

        // Object[] objArray = { "1", "2", "3" };
        // int[] intArray = new int[objArray.length];
        // for (int i = 0; i < objArray.length; i++) {
        //     intArray[i] = Integer.parseInt(objArray[i].toString());
        // }

        // Inicializar primeiro subarray
        for (i = 0; i < n1; i++) {
            a1[i] = array[esq + i];
        }

        // Inicializar segundo subarray
        for (j = 0; j < n2; j++) {
            a2[j] = array[meio + j + 1];
        }

        // Intercalação propriamente dita
        for (i = j = 0, k = esq; (i < n1 && j < n2); k++) {
            if (a1[i] <= 0)
                array[k] = a1[i++];
            else
                array[k] = a2[j++];
        }

        if (i == n1)
            for (; k <= dir; k++) {
                array[k] = a2[j++];
            }
        else
            for (; k <= dir; k++) {
                array[k] = a1[i++];
            }
    }

}
