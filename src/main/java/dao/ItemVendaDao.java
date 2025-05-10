package dao;

import entities.ItemVenda;

import java.util.List;

public interface ItemVendaDao {

    void insert(ItemVenda itemVenda);
    void update(ItemVenda itemVenda);
    void deleteById(Integer id);
    ItemVenda findById(int id);
    List<ItemVenda> findAll();

}
