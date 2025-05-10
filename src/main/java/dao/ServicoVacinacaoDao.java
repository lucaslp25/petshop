package dao;

import entities.ServicoVacinacao;

import java.util.List;

public interface ServicoVacinacaoDao {

    void insert(ServicoVacinacao servico);
    void update(ServicoVacinacao servico);
    void deleteById(Integer id);
    ServicoVacinacao findById(Integer id);
    List<ServicoVacinacao> findAll();

}
