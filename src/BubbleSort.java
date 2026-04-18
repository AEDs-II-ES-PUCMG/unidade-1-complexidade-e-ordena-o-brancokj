import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;


public class BubbleSort<T extends Comparable<T>> implements IOrdenador<T>{

	private long comparacoes;
	private long movimentacoes;
	private LocalDateTime inicio;
	private LocalDateTime termino;	
	private Comparator<T> comparador;
	
	public BubbleSort() {
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
		int tamanho = dadosOrdenados.length;
		if(this.comparador != null){
            comparador = this.comparador;
        }
		
		inicio = LocalDateTime.now();
		
		for (int posReferencia = tamanho - 1; posReferencia > 0; posReferencia--) {
			int trocas = 0;
			for (int posicao = 0; posicao < posReferencia; posicao++) {
				comparacoes++;
				if (comparador.compare(dadosOrdenados[posicao], dadosOrdenados[posicao+1]) > 0){
					swap (posicao, posicao + 1, dadosOrdenados);
					trocas++;
				}
			}
			if(trocas == 0 )
				posReferencia = 0;
		}	
		termino = LocalDateTime.now();

		return dadosOrdenados;
	}
	
	private void swap(int i, int j, T[] vet) {
		movimentacoes++;
		
		T temp = vet[i];
	    vet[i] = vet[j];
	    vet[j] = temp;
	}
	
	public long getComparacoes() {
		return comparacoes;
	}
	
	public long getMovimentacoes() {
		return movimentacoes;
	}
	
	@Override
    public double getTempoOrdenacao() {
        Duration duracao = Duration.between(inicio, termino);
        double milis = duracao.toNanos() / 1_000_000.0;
        return milis;
    }

	@Override
	public void setComparador(Comparator<T> comparador) {
		this.comparador = comparador;
	}

	
}