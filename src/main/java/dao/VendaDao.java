package dao;

import entities.Cliente;
import entities.ItemVenda;
import entities.Venda;
import enums.TipoDePagamento;

import java.time.LocalDate;
import java.util.List;

public interface VendaDao {

    void insert(Venda venda);
    void update(Venda venda);
    void deleteById(Integer id);
    Venda findById(Integer id);
    List<Venda> findAll();
    Venda findByUniqueAtributs(LocalDate dataVenda, Double valorTotal, Cliente cliente, Integer metodoPagamentoId);

}
