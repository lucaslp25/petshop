package dao;

import entities.Produto;
import enums.CategoriaDeProdutos;

import java.util.List;

public interface ProdutoDao {

    void insertProduto(Produto produto);
    void updateProduto(Produto produto);
    void deleteProdutoById(Integer id);
    Produto findById(Integer id);
    List<Produto> findAll();
    Produto findByUniqueAtributs(String nome, String descricao, String fornecedor);
    //achar produtos pelas caracteristicas de nome, descrição e forncedor! elas tem que ser unicas!


}
