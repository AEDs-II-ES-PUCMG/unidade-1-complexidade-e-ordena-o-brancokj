
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina<T extends Comparable<T>> {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;
    static Produto[] ordenacaoProdutosID;
    static Produto[] ordenacaoProdutosDesc;
    static Produto[] ordenacaoProdutosPreco;

    // #region utilidades
    static Scanner teclado;

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");

        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("5 - Heap");
        System.out.println("6 - Quick");
        System.out.println("0 - Finalizar");

        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        System.out.println("3 - Por preço");

        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo) {
        Scanner dados;
        Produto[] dadosCarregados;
        try {
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());

            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        } catch (FileNotFoundException fex) {
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizando um produto");
        int opcao = exibirMenuLocalizador();
        Produto localizado = null;
        switch (opcao) {
            case 1:
                Integer id = lerNumero("Qual o ID?", Integer.class);
                if(id != null) localizado = pesquisaBinariaPorId(ordenacaoProdutosID, id);
                break;

            case 2:
                System.out.println("Qual a desrição?");
                String desc = teclado.nextLine();
                localizado = pesquisaBinariaPorDesc(ordenacaoProdutosDesc, desc);
                break;
            case 3:
                double alvo = lerNumero("Qual o preço?", double.class);
                localizado = pesquisaBinariaPorPreco(ordenacaoProdutosPreco, alvo);
                break;
        }

        return localizado;
    }

    static Produto pesquisaBinariaPorPreco(Produto[] vetor, double alvo) {
        int inicio = 0;
        int fim = quantProdutos -1;
        while(inicio <= fim){
            int meio = (inicio + fim) / 2;
            if(vetor[meio].valorDeVenda() == alvo){
                return vetor[meio];
            }else if(vetor[meio].valorDeVenda() < alvo){
                inicio = meio + 1;
            }else{
                fim = meio - 1;
            }
        }
        return null;
    }

    static Produto pesquisaBinariaPorId(Produto[] vetor, int idAlvo) {
        int inicio = 0;
        int fim = quantProdutos - 1;
        while(inicio <= fim){
            int meio = (inicio + fim) / 2;
            if(vetor[meio].hashCode() == idAlvo){
                return vetor[meio];
            }else if(vetor[meio].hashCode() < idAlvo){
                inicio = meio + 1;
            }else{
                fim = meio - 1;
            }
        }
        return null;
    }

    static Produto pesquisaBinariaPorDesc(Produto[] vetor, String descAlvo){
        int inicio = 0;
        int fim = quantProdutos - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = vetor[meio].descricao.compareTo(descAlvo);
            if(comparacao == 0){
                return vetor[meio];
            }else if(comparacao < 0){
                inicio = meio + 1;
            }else{
                fim = meio - 1;
            }
        }
        return null;
    }

    private static int exibirMenuLocalizador() {
        System.out.println("Como voce quer localizar o produto?");
        System.out.println("1- Por código");
        System.out.println("2- Por descrição");
        return lerNumero("Digite sua opção", Integer.class);
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";

        if (produto != null) {
            mensagem = String.format("Dados do produto:\n%s", produto);
        }

        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo() {
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if (produtos[i].valorDeVenda() < valor)
                relatorio.append(produtos[i] + "\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos() {
        cabecalho();

        int opcao = exibirMenuOrdenadores();
        Produto[] vetor = carregarProdutos(nomeArquivoDados);
        switch (opcao) {
            case 1 -> ordenador = new BubbleSort<>();
            case 2 -> ordenador = new InsertionSort<>();
            case 3 -> ordenador = new SelectionSort<>();
            case 4 -> ordenador = new MergeSort<>();
            case 5 -> ordenador = new HeapSort<>();
            case 6 -> ordenador = new QuickSort<>();
        }

        if (ordenador != null) {
            opcao = exibirMenuComparadores();
            ComparadorPorCodigo comparadorCodigo = new ComparadorPorCodigo();
            ComparadorPorDescricao comparadorDesc = new ComparadorPorDescricao();
            ComparadorPorPreco comparadorPrec = new ComparadorPorPreco();
            switch (opcao) {
                case 2 -> ordenador.ordenar(vetor, comparadorCodigo);
                case 3 -> ordenador.ordenar(vetor, comparadorPrec);
                default -> ordenador.ordenar(vetor, comparadorDesc);
            }
        }
        ordenador.ordenar(vetor);

        ordenador = null;
    }

    static void embaralharProdutos() {
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados) {
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if (resposta.equals("S"))
            dadosOriginais = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos() {
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        produtos = carregarProdutos(nomeArquivoDados);
        embaralharProdutos();
        
        if(produtos != null){
            ordenacaoProdutosDesc = new Produto[quantProdutos];
            ordenacaoProdutosID = new Produto[quantProdutos];
            ordenacaoProdutosPreco = new Produto[quantProdutos];
            copiarParaVetores(); 
        }    

        int opcao = -1;

        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        } while (opcao != 0);
        teclado.close();
    }

    static void copiarParaVetores() {
        for (int i = 0; i < produtos.length; i++) {
            ordenacaoProdutosDesc[i] = produtos[i];
            ordenacaoProdutosID[i] = produtos[i];
            ordenacaoProdutosPreco[i] = produtos[i];
        }

        ComparadorPorCodigo comparadorCodigo = new ComparadorPorCodigo();
        ComparadorPorDescricao comparadorDesc = new ComparadorPorDescricao();
        ComparadorPorPreco comparadorPrec = new ComparadorPorPreco();
        ordenador = new MergeSort<>();
        ordenador.ordenar(ordenacaoProdutosDesc, comparadorDesc);
        ordenador.ordenar(ordenacaoProdutosID, comparadorCodigo);
        ordenador.ordenar(ordenacaoProdutosPreco, comparadorPrec);
        ordenador = null;
    }
}
