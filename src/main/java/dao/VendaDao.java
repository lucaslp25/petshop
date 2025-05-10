package dao;

import entities.Venda;

import java.util.List;

public interface VendaDao {

    void insert(Venda venda);
    void update(Venda venda);
    void deleteById(Integer id);
    Venda findById(Integer id);
    List<Venda> findAll();

}
