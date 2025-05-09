package dao;

import entities.MetodoDePagamento;

import java.util.List;

public interface MetodoDePagamentoDao {

    //padrão CRUD

    void insert(MetodoDePagamento metodoDePagamento);
    void update(MetodoDePagamento metodoDePagamento);
    void deleteById(Integer id);
    MetodoDePagamento findById(Integer id);
    List<MetodoDePagamento>findAll();

}
