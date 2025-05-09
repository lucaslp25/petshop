package dao;

import entities.Produto;

import java.util.List;

public interface ProdutoDao {

    void insertProduto(Produto produto);
    void updateProduto(Produto produto);
    void deleteProdutoById(Integer id);
    Produto findById(Integer id);
    List<Produto> findAll();

}
