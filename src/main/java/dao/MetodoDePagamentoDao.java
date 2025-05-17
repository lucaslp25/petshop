package dao;

import entities.MetodoDePagamento;
import enums.TipoDePagamento;

import java.util.List;

public interface MetodoDePagamentoDao {

    void insert(MetodoDePagamento metodoDePagamento);
    void update(MetodoDePagamento metodoDePagamento);
    void deleteById(Integer id);
    MetodoDePagamento findById(Integer id);
    List<MetodoDePagamento>findAll();
    MetodoDePagamento findByTipo(TipoDePagamento tipoDePagamento);

}
