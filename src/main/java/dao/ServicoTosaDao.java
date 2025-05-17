package dao;

import entities.ServicoTosa;

import java.util.List;

public interface ServicoTosaDao {

    void insert(ServicoTosa servico);
    void update(ServicoTosa servico);
    void deleteById(Integer id);
    ServicoTosa findById(Integer id);
    List<ServicoTosa> findAll();
    ServicoTosa findByUniqueAtributs(String nome, String descricao, Double preco, boolean incluiEscovacao, boolean incluiBanhoPrevio);
}
