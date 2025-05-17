package dao;

import entities.Servicos;

import java.util.List;

public interface ServicoDao {

    void insert(Servicos servico);
    void update(Servicos servico);
    void deleteById(Integer id);
    Servicos findById(Integer id);
    List<Servicos> findAll();
}
