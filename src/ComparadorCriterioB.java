import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if(o1.getQuantosProdutos() == o1.getQuantosProdutos()){
            if(o1.getDataPedido().equals(o2.getDataPedido())){
                return Integer.compare(o1.getIdPrimeiroProduto(), o2.getIdPrimeiroProduto());
            }else{
                return o1.getDataPedido().compareTo(o2.getDataPedido());
            }
        }else{
            return Integer.compare(o1.getQuantosProdutos(), o2.getQuantosProdutos());
        }
    }
}
