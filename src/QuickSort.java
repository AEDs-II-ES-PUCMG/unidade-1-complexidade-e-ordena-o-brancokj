import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class QuickSort<T extends Comparable<T>> implements IOrdenador<T> {

    private long comparacoes;
    private long movimentacoes;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private Comparator<T> comparador;

    public QuickSort() {
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
        inicio = LocalDateTime.now();
        if(this.comparador != null){
            comparador = this.comparador;
        }
        
        executarQuickSort(dadosOrdenados, 0, dadosOrdenados.length - 1, comparador);
        
        termino = LocalDateTime.now();
        return dadosOrdenados;
    }

    private void executarQuickSort(T[] vet, int esq, int dir, Comparator<T> comparador) {
        if (esq < dir) {
            int pivoIndice = particionar(vet, esq, dir, comparador);
            executarQuickSort(vet, esq, pivoIndice - 1, comparador);
            executarQuickSort(vet, pivoIndice + 1, dir, comparador);
        }
    }

    private int particionar(T[] vet, int esq, int dir, Comparator<T> comparador) {
        T pivo = vet[dir];
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            comparacoes++;
            if (comparador.compare(vet[j], pivo) <= 0) {
                i++;
                trocar(vet, i, j);
            }
        }
        trocar(vet, i + 1, dir);
        return i + 1;
    }

    private void trocar(T[] vet, int i, int j) {
        if (i != j) {
            movimentacoes += 3; // Conta como 3 movimentações (padrão de swap)
            T temp = vet[i];
            vet[i] = vet[j];
            vet[j] = temp;
        }
    }

    public long getComparacoes() { return comparacoes; }
    public long getMovimentacoes() { return movimentacoes; }

    @Override
    public double getTempoOrdenacao() {
        Duration duracao = Duration.between(inicio, termino);
        return duracao.toNanos() / 1_000_000.0;
    }

    @Override
    public void setComparador(Comparator<T> comparador) {
        this.comparador = comparador;
    }
}
