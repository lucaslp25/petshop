package services;

import entities.Produto;

import java.util.List;

public interface ProdutoService {

    void cadastrarProduto(Produto produto);
    void alterarProduto(Produto produto);
    void excluirProduto(Integer id);
    List<Produto> listarProduto();
    Produto buscarPorId(Integer id);
}
