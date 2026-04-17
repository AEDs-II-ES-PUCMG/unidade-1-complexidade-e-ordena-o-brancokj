import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class HeapSort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;

    public HeapSort() {
        comparacoes = 0;
        movimentacoes = 0;
    }

    @Override
    public T[] ordenar(T[] dados) {
        return ordenar(dados, T::compareTo);
    }

    @Override
    public T[] ordenar(T[] dados, Comparator<T> comparador) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int n = dadosOrdenados.length;
        inicio = LocalDateTime.now();

        // Build heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(dadosOrdenados, n, i, comparador);
        }

        // Extrai elementos do heap
        for (int i = n - 1; i > 0; i--) {
            trocar(dadosOrdenados, 0, i);
            heapify(dadosOrdenados, i, 0, comparador);
        }

        termino = LocalDateTime.now();
        return dadosOrdenados;
    }

    private void heapify(T[] vet, int n, int i, Comparator<T> comparador) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n) {
            comparacoes++;
            if (comparador.compare(vet[esq], vet[maior]) > 0) maior = esq;
        }

        if (dir < n) {
            comparacoes++;
            if (comparador.compare(vet[dir], vet[maior]) > 0) maior = dir;
        }

        if (maior != i) {
            trocar(vet, i, maior);
            heapify(vet, n, maior, comparador);
        }
    }

    private void trocar(T[] vet, int i, int j) {
        movimentacoes += 3;
        T temp = vet[i];
        vet[i] = vet[j];
        vet[j] = temp;
    }

    public long getComparacoes() { return comparacoes; }
    public long getMovimentacoes() { return movimentacoes; }

    @Override
    public double getTempoOrdenacao() {
        Duration duracao = Duration.between(inicio, termino);
        return duracao.toNanos() / 1_000_000.0;
    }
}
