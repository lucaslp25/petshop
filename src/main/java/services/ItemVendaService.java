package services;

import entities.ItemVenda;
import exceptions.ExceptionEntitieNotFound;

import java.util.List;

public interface ItemVendaService {

    void addItemVenda(ItemVenda itemVenda);

    void addItemVenda2(ItemVenda itemVenda)throws ExceptionEntitieNotFound;

    void updateItemVenda(ItemVenda itemVenda);
    void deleteItemVendaById(Integer id);
    List<ItemVenda> listarItemVendas();
    ItemVenda buscarItemVendaById(Integer id);
}
