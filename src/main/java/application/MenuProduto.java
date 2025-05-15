package application;

import dao.DaoFactory;
import dao.ProdutoDao;
import entities.Produto;
import enums.CategoriaDeProdutos;
import exceptions.ExceptionEntitieNotFound;
import exceptions.ExceptionInsufficientStock;
import services.impl.ProdutoServiceImpl;

import java.util.*;

public class MenuProduto {

    Scanner sc = new Scanner(System.in);

    ProdutoServiceImpl produtoService = new ProdutoServiceImpl();

    public void menuProduto()throws Exception{

        while (true) {

            System.out.println();
            System.out.println("== SUPER PET SHOP ==\n");
            System.out.println("[1] - Cadastrar Produtos");
            System.out.println("[2] - Listar Produtos");
            System.out.println("[3] - Atualizar Produto");
            System.out.println("[4] - Remover Produto");
            System.out.println("[5] - Comprar Produtos");
            System.out.println("[6] - Gerar relatórios");
            System.out.println("[0] - Voltar\n");

            System.out.println("Digite a opção que deseja: ");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1:
                        cadastrarProduto();
                        break;
                    case 2:
                        listarProduto();
                        break;
                    case 3:
                        atualizarProduto();
                        break;
                    case 4:
                        removerProduto();
                        break;
                    case 5:
                        comprarProdutos();
                        break;
                    case 6:

                        break;
                    case 0:
                        System.err.println("Voltando....");
                        return;
                    default:
                        System.err.println("Opção inválida! ");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Apenas números!");
            }
        }
    }

    public void cadastrarProduto()throws Exception{

        try {
            System.out.print("Nome do produto: ");
            String nome = sc.nextLine();
            System.out.print("Descrição do produto: ");
            String descricao = sc.nextLine();
            System.out.print("Preço de custo do produto: ");
            Double precoCusto = sc.nextDouble();
            sc.nextLine();
            System.out.print("Preço de venda do produto: ");
            Double precoVenda = sc.nextDouble();
            sc.nextLine();
            System.out.print("Quantidade em estoque: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();
            System.out.print("Fornecedor: ");
            String fornecedor = sc.nextLine();
            System.out.println("Selecione a categoria do produto: ");

            String categoria = null;

            System.out.println("[1] - BRINQUEDO");
            System.out.println("[2] - HIGIENE");
            System.out.println("[3] - MEDICAMENTO");
            System.out.println("[4] - ALIMENTACAO");
            System.out.println("[5] - ACESSORIO");
            Integer opcao = null;
            while (categoria == null) {

                try {
                    opcao = sc.nextInt();
                    sc.nextLine();
                    switch (opcao) {
                        case 1:
                            categoria = "BRINQUEDO";
                            break;
                        case 2:
                            categoria = "HIGIENE";
                            break;
                        case 3:
                            categoria = "MEDICAMENTO";
                            break;
                        case 4:
                            categoria = "ALIMENTACAO";
                            break;
                        case 5:
                            categoria = "ACESSORIO";
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            System.out.println("Selecione a opção de catgeoria: ");
                            break;
                    }
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Apenas números!");
                }
            }
            Produto produto = new Produto(nome, descricao, precoCusto, precoVenda, quantidade, fornecedor, CategoriaDeProdutos.valueOf(categoria));

            produtoService.cadastrarProduto(produto);
            System.out.println("Produto cadastrado com sucesso!");
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

    }

    public void listarProduto()throws ExceptionEntitieNotFound {

        try {
            List<Produto> produtos = produtoService.listarProduto();

            System.out.println("Produtos Cadastrados!\n");
            for (Produto produto : produtos) {

                System.out.println(produto);
                System.out.println("ID: " + produto.getId());
                System.out.println();
            }
        }catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao Listar produtos: " + e.getMessage());
        }
    }

    public void atualizarProduto()throws Exception{

        ProdutoDao produtoDao = DaoFactory.createProdutoDao();

        try {
            System.out.print("Qual o ID do produto que deseja atualizar?: ");
            Integer id = sc.nextInt();
            sc.nextLine();

            Produto produto =  produtoDao.findById(id);

            if (produto != null){
                System.out.println("Produto encontrado: " + produto.getNome());
            }

            System.out.println("PREENCHA OS DADOS PARA ATUALIZAR O PRODUTO " + produto.getNome());

            System.out.print("Novo nome do produto: ");
            String nome = sc.nextLine();
            System.out.print("Descrição do produto: ");
            String descricao = sc.nextLine();
            System.out.print("Preço de custo do produto: ");
            Double precoCusto = sc.nextDouble();
            sc.nextLine();
            System.out.print("Preço de venda do produto: ");
            Double precoVenda = sc.nextDouble();
            sc.nextLine();
            System.out.print("Quantidade em estoque: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();
            System.out.print("Fornecedor: ");
            String fornecedor = sc.nextLine();
            System.out.println("Selecione a categoria do produto: ");

            String categoria = null;

            System.out.println("[1] - BRINQUEDO");
            System.out.println("[2] - HIGIENE");
            System.out.println("[3] - MEDICAMENTO");
            System.out.println("[4] - ALIMENTACAO");
            System.out.println("[5] - ACESSORIO");
            Integer opcao = null;
            while (categoria == null) {

                try {
                    opcao = sc.nextInt();
                    sc.nextLine();
                    switch (opcao) {
                        case 1:
                            categoria = "BRINQUEDO";
                            break;
                        case 2:
                            categoria = "HIGIENE";
                            break;
                        case 3:
                            categoria = "MEDICAMENTO";
                            break;
                        case 4:
                            categoria = "ALIMENTACAO";
                            break;
                        case 5:
                            categoria = "ACESSORIO";
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            System.out.println("Selecione a opção de catgeoria: ");
                            break;
                    }
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Apenas números!");
                }
            }

            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPrecoDeCusto(precoCusto);
            produto.setPrecoDeVenda(precoVenda);
            produto.setQuantidadeEstoque(quantidade);
            produto.setFornecedor(fornecedor);
            produto.setCategoria(CategoriaDeProdutos.valueOf(categoria));

            produtoService.alterarProduto(produto);

            System.out.println("Produto Atualizado com sucesso!");

        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public void removerProduto()throws Exception{

        try {
            ProdutoDao produtoDao = DaoFactory.createProdutoDao();

            System.out.println("Digite o ID do produto que deseja remover: ");
            Integer id = sc.nextInt();
            sc.nextLine();

            Produto produto = produtoDao.findById(id);


            if (produto == null){
                throw new ExceptionEntitieNotFound("Nenhum produto encontrado COM esse ID!");

            }

            if (produto != null) {
                System.out.println("Produto encontrado: " + produto.getNome());
            }

            System.out.println("Deseja remover " + produto.getNome() + "?");
            System.out.println("[1]-SIM    [2]-NÃO");
            Integer opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    produtoService.excluirProduto(id);
                    System.out.println("Produto removido com sucesso!");
                    break;
                case 2:
                    System.out.println("O Produto " + produto.getNome() + " não foi removido!");
            }
        }catch (ExceptionEntitieNotFound e){
            System.out.println("Erro ao remover produto: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Apenas números!");
            sc.nextLine();
        }
    }

    public void comprarProdutos()throws Exception{

        try {
            System.out.println("== COMPRAS DE PRODUTOS ==\n");
            List<Produto> produtos = produtoService.listarProduto();

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto disponivel para a compra ainda!");
                return;
            }

            System.out.println("Produtos disponíveis:");
            for (Produto produto : produtos) {
                System.out.println("[" + produto.getId() + "] - " + produto.getNome() + " - R$" + String.format("%.2f", produto.getPrecoDeVenda()));
            }

            Map<Integer, Produto> produtosDisponiveisMap = new HashMap<>();
            for (Produto produto : produtos) {
                produtosDisponiveisMap.put(produto.getId(), produto);
            }

            System.out.println("DIGITE O ID DO PRODUTO QUE DESEJA COMPRAR: ");
            Integer id = sc.nextInt();
            sc.nextLine();

            ProdutoDao produtoDao = DaoFactory.createProdutoDao();
            Produto produto = produtoDao.findById(id);
            if (produto == null) {
                throw new ExceptionEntitieNotFound("Nenhum produto encontrado com esse ID!");
            }

            System.out.println("Digite a quantidade de " + produto.getNome() + " que deseja comprar: ");
            Integer quantidade = sc.nextInt();
            sc.nextLine();
            if (quantidade > produto.getQuantidadeEstoque()) {
                throw new ExceptionInsufficientStock("Estoque insuficiente!");
            }
            Integer novoEstoque = produto.getQuantidadeEstoque() - quantidade;
            produto.setQuantidadeEstoque(novoEstoque);
            produtoDao.updateProduto(produto);
            Double valorTotal = quantidade * produto.getPrecoDeVenda();

            System.out.println("Compra realizada com sucesso! Valor Total R$:" + valorTotal);


        }catch (ExceptionEntitieNotFound e) {
            System.out.println("Erro ao comprar produto: " + e.getMessage());
        }catch (ExceptionInsufficientStock e) {
            System.out.println("Erro ao comprar produto: " + e.getMessage());
        }
    }
}
