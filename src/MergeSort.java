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
        iniciar();
        mergesort(dados, 0, dados.length - 1);
        terminar();
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
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
    }

    /**
     * Algoritmo que intercala os elementos localizados entre as posições esq e dir
     * 
     * @param int esq: início do array a ser ordenado
     * @param int meio: posição do meio do array a ser ordenado
     * @param int dir: fim do array a ser ordenado
     */
    @SuppressWarnings("unchecked")
    private void intercalar(T[] array, int esq, int meio, int dir) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;
        T[] a1 = (T[]) new Comparable[n1];
        T[] a2 = (T[]) new Comparable[n2];

        for (int i = 0; i < n1; i++) a1[i] = array[esq + i];
        for (int j = 0; j < n2; j++) a2[j] = array[meio + 1 + j];

        int i = 0, j = 0, k = esq;
        while (i < n1 && j < n2) {
            comparacoes++;
            if (a1[i].compareTo(a2[j]) <= 0) {
                array[k++] = a1[i++];
                movimentacoes++;
            } else {
                array[k++] = a2[j++];
                movimentacoes++;
            }
        }
        while (i < n1) { array[k++] = a1[i++]; movimentacoes++; }
        while (j < n2) { array[k++] = a2[j++]; movimentacoes++; }
    }
}
